package com.daxie.xops.bd1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.tool.FilenameFunctions;

import de.javagl.obj.Mtl;
import de.javagl.obj.MtlWriter;
import de.javagl.obj.Mtls;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjWriter;
import de.javagl.obj.Objs;

/**
 * Writes out BD1 blocks into an OBJ file.
 * @author Daba
 *
 */
class BD1OBJWriter {
	private Logger logger=LoggerFactory.getLogger(BD1OBJWriter.class);
	
	private boolean data_prepared_flag;
	
	private Map<Integer, String> texture_filenames_map;
	private Map<Integer, List<BD1Face>> faces_map;
	
	public BD1OBJWriter(Map<Integer, String> texture_filenames_map,List<BD1Block> blocks) {
		data_prepared_flag=false;
		if(texture_filenames_map==null||blocks==null) {
			logger.warn("Null argument(s) where non-null required.");
			return;
		}
		
		this.texture_filenames_map=texture_filenames_map;
		faces_map=new HashMap<>();
		
		for(BD1Block block:blocks) {
			int[] texture_ids=block.GetTextureIDs();
			
			Vector[] vertex_positions=block.GetVertexPositions();
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			
			//Calculate normals.
			Vector[] normals=new Vector[6];
			Vector v1,v2;
			for(int i=0;i<6;i++) {
				int[] vertex_indices=BD1Functions.GetFaceCorrespondingVertexIndices(i);
				
				v1=VectorFunctions.VSub(vertex_positions[vertex_indices[3]], vertex_positions[vertex_indices[0]]);
				v2=VectorFunctions.VSub(vertex_positions[vertex_indices[1]], vertex_positions[vertex_indices[0]]);
				
				normals[i]=VectorFunctions.VCross(v1, v2);
				normals[i]=VectorFunctions.VNorm(normals[i]);
			}
			
			BD1Face[] faces=new BD1Face[6];
			for(int i=0;i<6;i++)faces[i]=new BD1Face();
			
			for(int i=0;i<6;i++) {
				int[] vertex_indices=BD1Functions.GetFaceCorrespondingVertexIndices(i);
				int[] uv_indices=BD1Functions.GetFaceCorrespondingUVIndices(i);
				
				for(int j=0;j<4;j++) {
					faces[i].SetVertexPosition(j, vertex_positions[vertex_indices[j]]);
					faces[i].SetUV(j, us[uv_indices[j]], vs[uv_indices[j]]);
				}
				faces[i].SetNormal(normals[i]);
			}
			
			for(int i=0;i<6;i++) {
				int texture_id=texture_ids[i];
				
				if(faces_map.containsKey(texture_id)==false) {
					List<BD1Face> list_temp=new ArrayList<>();
					faces_map.put(texture_id, list_temp);
				}
				List<BD1Face> faces_list=faces_map.get(texture_id);
				faces_list.add(faces[i]);
			}
		}
		
		data_prepared_flag=true;
	}
	
	/**
	 * Writes out data into an OBJ file.
	 * @param obj_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String obj_filename) {
		if(data_prepared_flag==false) {
			logger.warn("Data not prepared.");
			return -1;
		}
		
		String mtllib_str;
		mtllib_str=FilenameFunctions.GetFilenameWithoutDirectory(obj_filename);
		mtllib_str=FilenameFunctions.GetFilenameWithoutExtension(mtllib_str)+".mtl";
		String mtl_filename;
		mtl_filename=FilenameFunctions.GetFilenameWithoutExtension(obj_filename);
		mtl_filename=mtl_filename+".mtl";
		
		Obj obj=Objs.create();
		List<Mtl> mtls=new ArrayList<>();
		
		obj.setMtlFileNames(Arrays.asList(mtllib_str));
		obj.setActiveGroupNames(Arrays.asList("map"));
		
		int count=0;
		for(Map.Entry<Integer, List<BD1Face>> entry:faces_map.entrySet()) {
			int texture_id=entry.getKey();
			String texture_filename=texture_filenames_map.get(texture_id);
			if(texture_filename==null) {
				texture_filename="unknown_"+texture_id;
			}
			
			String material_name;
			material_name=FilenameFunctions.GetFilenameWithoutDirectory(texture_filename);
			material_name=FilenameFunctions.GetFilenameWithoutExtension(material_name);
			material_name+="_"+texture_id;
			
			Mtl mtl=Mtls.create(material_name);
			mtl.setNs(0.0f);
			mtl.setKa(1.0f,1.0f,1.0f);
			mtl.setKd(1.0f, 1.0f, 1.0f);
			mtl.setKs(0.0f, 0.0f, 0.0f);
			mtl.setD(1.0f);
			mtl.setNs(0.0f);
			mtl.setMapKd(texture_filename);
			mtls.add(mtl);
			
			obj.setActiveMaterialGroupName(material_name);
			
			List<BD1Face> faces=entry.getValue();
			
			for(BD1Face face:faces) {
				Vector[] vertex_positions=face.GetVertexPositions();
				Vector normal=face.GetNormal();
				float[] us=face.GetUs();
				float[] vs=face.GetVs();
				
				for(int i=3;i>=0;i--) {
					obj.addVertex(vertex_positions[i].GetX(), vertex_positions[i].GetY(), vertex_positions[i].GetZ());
					obj.addNormal(normal.GetX(), normal.GetY(), normal.GetZ());
					obj.addTexCoord(us[i], -vs[i]);
				}
				
				int[] indices=new int[] {count,count+1,count+2,count+3};
				obj.addFace(indices,indices,indices);
				
				count+=4;
			}
		}
		
		OutputStream os_obj=null;
		OutputStream os_mtl=null;
		try {
			os_obj=new FileOutputStream(obj_filename);
			os_mtl=new FileOutputStream(mtl_filename);
		}
		catch(IOException e) {
			logger.error("Failed to create a stream.",e);
			return -1;
		}
		
		try {
			ObjWriter.write(obj, os_obj);
			MtlWriter.write(mtls, os_mtl);
		}
		catch(IOException e) {
			logger.error("Failed to write data.",e);
			return -1;
		}
		
		return 0;
	}
}
