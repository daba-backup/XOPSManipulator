package com.daxie.xops.bd1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.log.LogFile;

/**
 * Creates BD1 blocks.
 * @author Daba
 *
 */
public class BD1Creator {
	private int block_count;
	private Map<Integer, String> texture_filenames_map;
	private Map<Integer, BD1Block> blocks_map;
	
	public BD1Creator() {
		block_count=0;
		texture_filenames_map=new HashMap<>();
		blocks_map=new HashMap<>();
		
		for(int i=0;i<10;i++) {
			texture_filenames_map.put(i, "");
		}
	}
	
	public int SetTextureFilename(int texture_id,String texture_filename) {
		if(!(0<=texture_id&&texture_id<10)) {
			LogFile.WriteError("[BD1Creator-SetTextureFilename] Texture ID out of bounds. texture_id:"+texture_id);
			return -1;
		}
		
		texture_filenames_map.put(texture_id, texture_filename);
		
		return 0;
	}
	
	public Set<Integer> GetBlockHandles(){
		return new HashSet<>(blocks_map.keySet());
	}
	
	/**
	 * Creates a cube.
	 * @param center Center position
	 * @param edge_length Length of edges
	 * @return Block handle
	 */
	public int CreateCube(Vector center,float edge_length) {
		BD1Block block=new BD1Block();
		
		float edge_half_length=edge_length/2.0f;
		
		Vector[] vertex_positions=new Vector[8];
		float x=center.GetX();
		float y=center.GetY();
		float z=center.GetZ();
		
		vertex_positions[0]=VectorFunctions.VGet(x-edge_half_length, y+edge_half_length, z+edge_half_length);
		vertex_positions[1]=VectorFunctions.VGet(x-edge_half_length, y+edge_half_length, z-edge_half_length);
		vertex_positions[2]=VectorFunctions.VGet(x+edge_half_length, y+edge_half_length, z-edge_half_length);
		vertex_positions[3]=VectorFunctions.VGet(x+edge_half_length, y+edge_half_length, z+edge_half_length);
		vertex_positions[4]=VectorFunctions.VGet(x-edge_half_length, y-edge_half_length, z+edge_half_length);
		vertex_positions[5]=VectorFunctions.VGet(x-edge_half_length, y-edge_half_length, z-edge_half_length);
		vertex_positions[6]=VectorFunctions.VGet(x+edge_half_length, y-edge_half_length, z-edge_half_length);
		vertex_positions[7]=VectorFunctions.VGet(x+edge_half_length, y-edge_half_length, z+edge_half_length);
		
		for(int i=0;i<8;i++) {
			block.SetVertexPosition(i, vertex_positions[i]);
		}
		
		for(int i=0;i<24;i++) {
			if(i%4==0)block.SetUVs(i, 0.0f, 0.0f);
			else if(i%4==1)block.SetUVs(i, 1.0f, 0.0f);
			else if(i%4==2)block.SetUVs(i, 1.0f, 1.0f);
			else block.SetUVs(i, 0.0f, 1.0f);
		}
		
		for(int i=0;i<6;i++) {
			block.SetTextureID(i, 0);
		}
		
		int block_handle=block_count;
		block_count++;
		
		blocks_map.put(block_handle, block);
		
		return block_handle;
	}
	/**
	 * Creates a block.
	 * @param vertex_positions Vertex positions
	 * @return Block handle
	 */
	public int CreateBlock(Vector[] vertex_positions) {
		if(vertex_positions==null) {
			LogFile.WriteError("[BD1Creator-CreateBlock] Null argument where non-null required.");
			return -1;
		}
		if(vertex_positions.length!=8) {
			String str="[BD1Creator-CreateBlock] Invalid number of vertices in the argument array. vertex_num:"+vertex_positions.length;
			LogFile.WriteError(str);
			
			return -1;
		}
		
		BD1Block block=new BD1Block();
		
		for(int i=0;i<8;i++) {
			block.SetVertexPosition(i, vertex_positions[i]);
		}
		
		for(int i=0;i<24;i++) {
			if(i%4==0)block.SetUVs(i, 0.0f, 0.0f);
			else if(i%4==1)block.SetUVs(i, 1.0f, 0.0f);
			else if(i%4==2)block.SetUVs(i, 1.0f, 1.0f);
			else block.SetUVs(i, 0.0f, 1.0f);
		}
		
		for(int i=0;i<6;i++) {
			block.SetTextureID(i, 0);
		}
		
		int block_handle=block_count;
		block_count++;
		
		blocks_map.put(block_handle, block);
		
		return block_handle;
	}
	
	/**
	 * Sets the UVs.
	 * @param block_handle Block handle
	 * @param face_index Face index
	 * @param u U-coordinate
	 * @param v V-coordinate
	 * @return -1 on error and 0 on success
	 */
	public int SetBlockUVs(int block_handle,int face_index,float u,float v) {
		if(blocks_map.containsKey(block_handle)==false) {
			LogFile.WriteError("[BD1Creator-SetBlockUVs] No such block. handle:"+block_handle);
			return -1;
		}
		if(!(0<face_index&&face_index<6)) {
			LogFile.WriteError("[BD1Creator-SetBlockUVs] Face index out of bounds. face_index:"+face_index);
			return -1;
		}
		
		BD1Block block=blocks_map.get(block_handle);
		block.SetUVs(face_index, u, v);
		
		return 0;
	}
	/**
	 * Sets the texture ID.
	 * @param block_handle Block handle
	 * @param face_index Face index
	 * @param texture_id Texture ID
	 * @return -1 on error and 0 on success
	 */
	public int SetBlockTextureID(int block_handle,int face_index,int texture_id) {
		if(blocks_map.containsKey(block_handle)==false) {
			LogFile.WriteError("[BD1Creator-SetBlockTextureID] No such block. handle:"+block_handle);
			return -1;
		}
		if(!(0<=face_index&&face_index<6)) {
			LogFile.WriteError("[BD1Creator-SetBlockTextureID] Face index out of bounds. face_index:"+face_index);
			return -1;
		}
		
		BD1Block block=blocks_map.get(block_handle);
		block.SetTextureID(face_index, texture_id);
		
		return 0;
	}
	
	/**
	 * Removes a block.
	 * @param block_handle Block handle
	 * @return -1 on error and 0 on success
	 */
	public int RemoveBlock(int block_handle) {
		if(blocks_map.containsKey(block_handle)==false) {
			LogFile.WriteError("[BD1Creator-SetBlockTextureID] No such block. handle:"+block_handle);
			return -1;
		}
		
		blocks_map.remove(block_handle);
		
		return 0;
	}
	
	/**
	 * Writes out blocks into a BD1 file.
	 * @param bd1_filename BD1 filename
	 * @param offset_y Y-direction offset
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsBD1(String bd1_filename,float offset_y) {
		BD1Manipulator bd1_manipulator=new BD1Manipulator();
		
		List<BD1Block> blocks=new ArrayList<>();
		for(BD1Block block:blocks_map.values()) {
			blocks.add(block);
		}
		
		bd1_manipulator.SetTextureFilenamesMap(texture_filenames_map);
		bd1_manipulator.SetBlocks(blocks);
		
		bd1_manipulator.Translate(VectorFunctions.VGet(0.0f, offset_y, 0.0f));
		
		int ret=bd1_manipulator.WriteAsBD1(bd1_filename);
		
		return ret;
	}
}
