package com.daxie.xops.bd1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Creates BD1 blocks.
 * @author Daba
 *
 */
public class BD1Creator {
	private Logger logger=LoggerFactory.getLogger(BD1Creator.class);
	
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
			logger.warn("Texture ID out of bounds. texture_id={}",texture_id);
			return -1;
		}
		
		texture_filenames_map.put(texture_id, texture_filename);
		
		return 0;
	}
	
	/**
	 * Returns all block handles.
	 * @return Block handles
	 */
	public Set<Integer> GetBlockHandles(){
		return new HashSet<>(blocks_map.keySet());
	}
	
	/**
	 * Adds a block to the map.
	 * @param block Block
	 * @return Block handle
	 */
	public int AddBlock(BD1Block block) {
		if(block==null) {
			logger.warn("Null argument where non-null required.");
			return -1;
		}
		
		int block_handle=block_count;
		block_count++;
		
		blocks_map.put(block_handle, block);
		
		return block_handle;
	}
	
	/**
	 * Duplicates a block.
	 * @param block_handle Handle of the block to be duplicated
	 * @return Duplicated block handle
	 */
	public int DuplicateBlock(int block_handle) {
		if(blocks_map.containsKey(block_handle)==false) {
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		
		BD1Block orig_block=blocks_map.get(block_handle);
		BD1Block duplicated_block=new BD1Block(orig_block);
		
		int duplicated_block_handle=block_count;
		block_count++;
		
		blocks_map.put(duplicated_block_handle, duplicated_block);
		
		return duplicated_block_handle;
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
			logger.warn("Null argument where non-null required.");
			return -1;
		}
		if(vertex_positions.length!=8) {
			logger.warn("Invalid number of vertices in the array. vertex_num={}",vertex_positions.length);
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
	 * Translates a block.
	 * @param block_handle Block handle
	 * @param translate Translation vector
	 * @return -1 on error and 0 on success
	 */
	public int TranslateBlock(int block_handle,Vector translate) {
		if(blocks_map.containsKey(block_handle)==false) {
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		
		BD1Block block=blocks_map.get(block_handle);
		
		Vector[] vertex_positions=block.GetVertexPositions();
		for(int i=0;i<8;i++) {
			vertex_positions[i]=VectorFunctions.VAdd(vertex_positions[i], translate);
		}
		for(int i=0;i<8;i++) {
			block.SetVertexPosition(i, vertex_positions[i]);
		}
		
		return 0;
	}
	/**
	 * Rotates a block.
	 * @param block_handle Block handle
	 * @param rotate Rotation angles (radian)
	 * @return -1 on error and 0 on success
	 */
	public int RotateBlock(int block_handle,Vector rotate) {
		if(blocks_map.containsKey(block_handle)==false) {
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		
		Matrix rot_x=MatrixFunctions.MGetRotX(rotate.GetX());
		Matrix rot_y=MatrixFunctions.MGetRotY(rotate.GetY());
		Matrix rot_z=MatrixFunctions.MGetRotZ(rotate.GetZ());
		
		BD1Block block=blocks_map.get(block_handle);
		Vector[] vertex_positions=block.GetVertexPositions();
		
		Vector center=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
		for(int i=0;i<8;i++) {
			center=VectorFunctions.VAdd(center, vertex_positions[i]);
		}
		center=VectorFunctions.VScale(center, 1.0f/8.0f);
		
		//Move the block to the origin.
		Vector to_orig_vec=VectorFunctions.VScale(center, -1.0f);
		for(int i=0;i<8;i++) {
			vertex_positions[i]=VectorFunctions.VAdd(vertex_positions[i], to_orig_vec);
		}
		
		//Rotate the block.
		for(int i=0;i<8;i++) {
			vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_x);
			vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_y);
			vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_z);
		}
		
		//Move the block to the original coordinates.
		for(int i=0;i<8;i++) {
			vertex_positions[i]=VectorFunctions.VAdd(vertex_positions[i], center);
		}
		
		//Apply changes to the block.
		for(int i=0;i<8;i++) {
			block.SetVertexPosition(i, vertex_positions[i]);
		}
		
		return 0;
	}
	/**
	 * Rescales a block.
	 * @param block_handle Block handle
	 * @param scale Scale
	 * @return -1 on error and 0 on success
	 */
	public int RescaleBlock(int block_handle,Vector scale) {
		if(blocks_map.containsKey(block_handle)==false) {
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		
		float scale_x=scale.GetX();
		float scale_y=scale.GetY();
		float scale_z=scale.GetZ();
		
		BD1Block block=blocks_map.get(block_handle);
		Vector[] vertex_positions=block.GetVertexPositions();
		
		for(int i=0;i<8;i++) {
			float pos_x=vertex_positions[i].GetX();
			float pos_y=vertex_positions[i].GetY();
			float pos_z=vertex_positions[i].GetZ();
			
			pos_x*=scale_x;
			pos_y*=scale_y;
			pos_z*=scale_z;
			
			vertex_positions[i].SetVector(pos_x, pos_y, pos_z);
		}
		
		for(int i=0;i<8;i++) {
			block.SetVertexPosition(i, vertex_positions[i]);
		}
		
		return 0;
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
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		if(!(0<face_index&&face_index<6)) {
			logger.warn("Face index out of bounds. face_index={}",face_index);
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
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		if(!(0<=face_index&&face_index<6)) {
			logger.warn("Face index out of bounds. face_index={}",face_index);
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
			logger.warn("No such block. block_handle={}",block_handle);
			return -1;
		}
		
		blocks_map.remove(block_handle);
		
		return 0;
	}
	
	/**
	 * Writes out blocks into a BD1 file.
	 * @param bd1_filename BD1 filename
	 * @param offset_y Y-axis offset
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
	/**
	 * Writes out blocks into an OBJ file.
	 * @param obj_filename OBJ filename
	 * @param offset_y Y-axis offset
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsOBJ(String obj_filename,float offset_y) {
		BD1Manipulator bd1_manipulator=new BD1Manipulator();
		
		List<BD1Block> blocks=new ArrayList<>();
		for(BD1Block block:blocks_map.values()) {
			blocks.add(block);
		}
		
		bd1_manipulator.SetTextureFilenamesMap(texture_filenames_map);
		bd1_manipulator.SetBlocks(blocks);
		bd1_manipulator.Translate(VectorFunctions.VGet(0.0f, offset_y, 0.0f));
		
		int ret=bd1_manipulator.WriteAsOBJ(obj_filename);
		
		return ret;
	}
}
