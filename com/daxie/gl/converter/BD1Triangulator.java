package com.daxie.gl.converter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.basis.coloru8.ColorU8Functions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.gl.shape.Triangle;
import com.daxie.gl.shape.Vertex;
import com.daxie.xops.bd1.Block;

/**
 * Triangulates faces of BD1 blocks.
 * @author Daba
 *
 */
public class BD1Triangulator {
	private Map<Integer, List<Triangle>> triangles_map;//(texture index, list of triangles)
	
	public BD1Triangulator() {
		triangles_map=new HashMap<>();
	}
	
	private int[] GetFaceCorrespondingVertexIndices(int face_index) {
		int[] ret=new int[4];
		
		switch (face_index) {
		case 0:
			ret[0] = 0;
			ret[1] = 1;
			ret[2] = 2;
			ret[3] = 3;
			break;
		case 1:
			ret[0] = 5;
			ret[1] = 4;
			ret[2] = 7;
			ret[3] = 6;
			break;
		case 2:
			ret[0] = 1;
			ret[1] = 0;
			ret[2] = 4;
			ret[3] = 5;
			break;
		case 3:
			ret[0] = 2;
			ret[1] = 1;
			ret[2] = 5;
			ret[3] = 6;
			break;
		case 4:
			ret[0] = 3;
			ret[1] = 2;
			ret[2] = 6;
			ret[3] = 7;
			break;
		case 5:
			ret[0] = 0;
			ret[1] = 3;
			ret[2] = 7;
			ret[3] = 4;
			break;
		default:
			for (int i = 0; i<4; i++)ret[i] = 0;
			break;
		}
		
		return ret;
	}
	
	public void TriangulateBlock(Block block) {
		Vector[] positions=block.GetVertexPositions();
		float[] us=block.GetUs();
		float[] vs=block.GetVs();
		int[] texture_ids=block.GetTextureIDs();
		
		Vertex[] vertices=new Vertex[24];
		for(int i=0;i<24;i++)vertices[i]=new Vertex();
		
		for(int i=0;i<6;i++) {
			int[] vertex_indices=this.GetFaceCorrespondingVertexIndices(i);
			for(int j=0;j<4;j++) {
				int array_index=i*4+j;
				int vertex_index=vertex_indices[j];
				
				vertices[array_index].SetPos(positions[vertex_index]);
				vertices[array_index].SetDif(ColorU8Functions.GetColorU8(1.0f, 1.0f, 1.0f, 1.0f));
				vertices[array_index].SetSpc(ColorU8Functions.GetColorU8(0.0f, 0.0f, 0.0f, 0.0f));
				vertices[array_index].SetU(us[array_index]);
				vertices[array_index].SetV(vs[array_index]);
				
				Vector v1=VectorFunctions.VSub(positions[vertex_indices[3]],positions[vertex_indices[0]]);
				Vector v2=VectorFunctions.VSub(positions[vertex_indices[1]],positions[vertex_indices[0]]);
				Vector normal=VectorFunctions.VCross(v1, v2);
				normal=VectorFunctions.VNorm(normal);
				
				vertices[array_index].SetNorm(normal);
			}
		}
		
		Triangle[] triangles=new Triangle[12];
		for(int i=0;i<12;i++)triangles[i]=new Triangle();
		
		triangles[0].SetVertex(0, vertices[0]);
		triangles[0].SetVertex(1, vertices[1]);
		triangles[0].SetVertex(2, vertices[2]);
		triangles[1].SetVertex(0, vertices[2]);
		triangles[1].SetVertex(1, vertices[3]);
		triangles[1].SetVertex(2, vertices[0]);
		
		for(int i=0;i<6;i++) {
			int vertex_array_index;
			for(int j=0;j<3;j++) {
				vertex_array_index=i*4+j;
				triangles[i].SetVertex(j, vertices[vertex_array_index]);
			}
			for(int j=0;j<3;j++) {
				vertex_array_index=i*4+(j+2)%4;
				triangles[i+1].SetVertex(j, vertices[vertex_array_index]);
			}
		}
		
		for(int i=0;i<6;i++) {
			int texture_id=texture_ids[i];
			
			if(triangles_map.containsKey(texture_id)==false) {
				List<Triangle> list_temp=new ArrayList<>();
				triangles_map.put(texture_id, list_temp);
			}
			List<Triangle> triangles_list=triangles_map.get(texture_id);
			triangles_list.add(triangles[i]);
			triangles_list.add(triangles[i+1]);
		}
	}
	public void TriangulateBlocks(List<Block> blocks) {
		for(Block block:blocks) {
			TriangulateBlock(block);
		}
	}
	
	public Map<Integer, List<Triangle>> GetTrianglesMap(){
		return triangles_map;//Returns a ref.
	}
}
