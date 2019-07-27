package com.daxie.xops.bd1;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
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
	private boolean data_prepared_flag;
	
	private Map<Integer, String> texture_filenames_map;
	private Map<Integer, List<BD1Face>> faces_map;
	
	public BD1OBJWriter(Map<Integer, String> texture_filenames_map,List<Block> blocks) {
		data_prepared_flag=false;
		if(texture_filenames_map==null||blocks==null) {
			LogFile.WriteError("[BD1OBJWriter-<init>] Null argument(s) where non-null required.");
			return;
		}
		
		this.texture_filenames_map=texture_filenames_map;
		faces_map=new HashMap<>();
		
		for(Block block:blocks) {
			int[] texture_ids=block.GetTextureIDs();
			
			Vector[] vertex_positions=block.GetVertexPositions();
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			
			//Calculate normals.
			Vector[] normals=new Vector[6];
			Vector v1,v2;
			//Face 0
			v1=VectorFunctions.VSub(vertex_positions[3], vertex_positions[0]);
			v2=VectorFunctions.VSub(vertex_positions[1], vertex_positions[0]);
			normals[0]=VectorFunctions.VCross(v1, v2);
			normals[0]=VectorFunctions.VNorm(normals[0]);
			//Face 1
			v1=VectorFunctions.VSub(vertex_positions[5], vertex_positions[4]);
			v2=VectorFunctions.VSub(vertex_positions[7], vertex_positions[4]);
			normals[1]=VectorFunctions.VCross(v1, v2);
			normals[1]=VectorFunctions.VNorm(normals[1]);
			//Face 2
			v1=VectorFunctions.VSub(vertex_positions[7], vertex_positions[3]);
			v2=VectorFunctions.VSub(vertex_positions[2], vertex_positions[3]);
			normals[2]=VectorFunctions.VCross(v1, v2);
			normals[2]=VectorFunctions.VNorm(normals[2]);
			//Face 3
			v1=VectorFunctions.VSub(vertex_positions[6], vertex_positions[2]);
			v2=VectorFunctions.VSub(vertex_positions[1], vertex_positions[2]);
			normals[3]=VectorFunctions.VCross(v1, v2);
			normals[3]=VectorFunctions.VNorm(normals[3]);
			//Face 4
			v1=VectorFunctions.VSub(vertex_positions[5], vertex_positions[1]);
			v2=VectorFunctions.VSub(vertex_positions[0], vertex_positions[1]);
			normals[4]=VectorFunctions.VCross(v1, v2);
			normals[4]=VectorFunctions.VNorm(normals[4]);
			//Face 5
			v1=VectorFunctions.VSub(vertex_positions[4], vertex_positions[0]);
			v2=VectorFunctions.VSub(vertex_positions[3], vertex_positions[0]);
			normals[5]=VectorFunctions.VCross(v1, v2);
			normals[5]=VectorFunctions.VNorm(normals[5]);
			
			BD1Face[] faces=new BD1Face[6];
			for(int i=0;i<6;i++)faces[i]=new BD1Face();
			
			//Face 0
			faces[0].SetVertexPosition(0, vertex_positions[0]);
			faces[0].SetVertexPosition(1, vertex_positions[1]);
			faces[0].SetVertexPosition(2, vertex_positions[2]);
			faces[0].SetVertexPosition(3, vertex_positions[3]);
			faces[0].SetUV(0, us[3], vs[3]);
			faces[0].SetUV(1, us[2], vs[2]);
			faces[0].SetUV(2, us[1], vs[1]);
			faces[0].SetUV(3, us[0], vs[0]);
			faces[0].SetNormal(normals[0]);
			//Face 1
			faces[1].SetVertexPosition(0, vertex_positions[5]);
			faces[1].SetVertexPosition(1, vertex_positions[4]);
			faces[1].SetVertexPosition(2, vertex_positions[7]);
			faces[1].SetVertexPosition(3, vertex_positions[6]);
			faces[1].SetUV(0, us[7], vs[7]);
			faces[1].SetUV(1, us[6], vs[6]);
			faces[1].SetUV(2, us[5], vs[5]);
			faces[1].SetUV(3, us[4], vs[4]);
			faces[1].SetNormal(normals[1]);
			//Face 2
			faces[2].SetVertexPosition(0, vertex_positions[1]);
			faces[2].SetVertexPosition(1, vertex_positions[0]);
			faces[2].SetVertexPosition(2, vertex_positions[4]);
			faces[2].SetVertexPosition(3, vertex_positions[5]);
			faces[2].SetUV(0, us[9], vs[9]);
			faces[2].SetUV(1, us[8], vs[8]);
			faces[2].SetUV(2, us[11], vs[11]);
			faces[2].SetUV(3, us[10], vs[10]);
			faces[2].SetNormal(normals[2]);
			//Face 3
			faces[3].SetVertexPosition(0, vertex_positions[2]);
			faces[3].SetVertexPosition(1, vertex_positions[1]);
			faces[3].SetVertexPosition(2, vertex_positions[5]);
			faces[3].SetVertexPosition(3, vertex_positions[6]);
			faces[3].SetUV(0, us[13], vs[13]);
			faces[3].SetUV(1, us[12], vs[12]);
			faces[3].SetUV(2, us[15], vs[15]);
			faces[3].SetUV(3, us[14], vs[14]);
			faces[3].SetNormal(normals[3]);
			//Face 4
			faces[4].SetVertexPosition(0, vertex_positions[3]);
			faces[4].SetVertexPosition(1, vertex_positions[2]);
			faces[4].SetVertexPosition(2, vertex_positions[6]);
			faces[4].SetVertexPosition(3, vertex_positions[7]);
			faces[4].SetUV(0, us[17], vs[17]);
			faces[4].SetUV(1, us[16], vs[16]);
			faces[4].SetUV(2, us[19], vs[19]);
			faces[4].SetUV(3, us[18], vs[18]);
			faces[4].SetNormal(normals[4]);
			//Face 5
			faces[5].SetVertexPosition(0, vertex_positions[0]);
			faces[5].SetVertexPosition(1, vertex_positions[3]);
			faces[5].SetVertexPosition(2, vertex_positions[7]);
			faces[5].SetVertexPosition(3, vertex_positions[4]);
			faces[5].SetUV(0, us[21], vs[21]);
			faces[5].SetUV(1, us[20], vs[20]);
			faces[5].SetUV(2, us[23], vs[23]);
			faces[5].SetUV(3, us[22], vs[22]);
			faces[5].SetNormal(normals[5]);
			
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
			LogFile.WriteError("[BD1OBJWriter-Write] Data not prepared.");
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
				LogFile.WriteWarn("[BD1OBJWriter-Write] Detected a discrepancy between two maps. texture_id:"+texture_id);
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
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("[BD1OBJWriter-Write] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			return -1;
		}
		
		try {
			ObjWriter.write(obj, os_obj);
			MtlWriter.write(mtls, os_mtl);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("[BD1OBJWriter-Write] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			return -1;
		}
		
		return 0;
	}
}
