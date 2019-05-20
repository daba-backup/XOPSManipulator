package com.daxie.xops;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Manipulate a BD1 file.
 * @author Daba
 *
 */
public class BD1Manipulator {
	private List<Block> blocks;
	private Map<Integer, String> texture_filenames_map;
	
	/**
	 * @param bd1_filename BD1 filename to load
	 * @throws FileNotFoundException BD1 file not found
	 */
	public BD1Manipulator(String bd1_filename) throws FileNotFoundException{
		BD1Parser bd1_parser=new BD1Parser(bd1_filename);
		
		blocks=bd1_parser.GetBlocks();
		texture_filenames_map=bd1_parser.GetTextureFilenamesMap();
	}
	
	/**
	 * Get blocks.
	 * @return Blocks
	 */
	public List<Block> GetBlocks(){
		return new ArrayList<>(blocks);
	}
	/**
	 * Set blocks.
	 * @param blocks Blocks
	 */
	public void SetBlocks(List<Block> blocks) {
		this.blocks=blocks;
	}
	
	/**
	 * Get the number of blocks contained in the BD1 file.
	 * @return Number of blocks
	 */
	public int GetBlockNum() {
		return blocks.size();
	}
	
	/**
	 * Get the texture filename associated with the texture ID.
	 * @param texture_id Texture ID
	 * @return Texture filename
	 */
	public String GetTextureFilename(int texture_id) {
		if(texture_filenames_map.containsKey(texture_id)==false)return "";
		
		String texture_filename=texture_filenames_map.get(texture_id);
		return texture_filename;
	}
	/**
	 * Set the texture filename associated with the texture ID.
	 * @param texture_id Texture ID
	 * @param texture_filename Texture filename
	 */
	public void SetTextureFilename(int texture_id,String texture_filename) {
		if(!(0<=texture_id&&texture_id<10))return;
		
		texture_filenames_map.put(texture_id, texture_filename);
	}
	
	/**
	 * Translate every block.
	 * @param translate Translation vector
	 */
	public void Translate(Vector translate) {
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<vertex_positions.length;i++) {
				vertex_positions[i]=VectorFunctions.VAdd(vertex_positions[i], translate);
			}
			for(int i=0;i<vertex_positions.length;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Rotate every block.
	 * @param rotate Angles of rotation (radian)
	 */
	public void Rotate(Vector rotate) {
		Matrix rot_x=MatrixFunctions.MGetRotX(rotate.GetX());
		Matrix rot_y=MatrixFunctions.MGetRotY(rotate.GetY());
		Matrix rot_z=MatrixFunctions.MGetRotZ(rotate.GetZ());
		
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<vertex_positions.length;i++) {
				vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_x);
				vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_y);
				vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], rot_z);
			}
			for(int i=0;i<vertex_positions.length;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Rescale every block.
	 * @param scale Scaling vector
	 */
	public void Rescale(Vector scale) {
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<vertex_positions.length;i++) {
				float x,y,z;
				x=vertex_positions[i].GetX();
				y=vertex_positions[i].GetY();
				z=vertex_positions[i].GetZ();
				
				vertex_positions[i].SetX(x*scale.GetX());
				vertex_positions[i].SetY(y*scale.GetY());
				vertex_positions[i].SetZ(z*scale.GetZ());
			}
			for(int i=0;i<vertex_positions.length;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Transform every block with a matrix.
	 * @param m Transformation matrix
	 */
	public void SetMatrix(Matrix m) {
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<vertex_positions.length;i++) {
				vertex_positions[i]=VectorFunctions.VTransform(vertex_positions[i], m);
			}
			for(int i=0;i<vertex_positions.length;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	
	/**
	 * Invert z-axis.<br>
	 * This method will be used to make a mirrored map.
	 */
	public void InvertZ() {
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			
			for(int i=0;i<vertex_positions.length;i++) {
				vertex_positions[i].SetZ(vertex_positions[i].GetZ()*(-1.0f));
			}
			
			for(int i=0;i<vertex_positions.length;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
		
		for(Block block:blocks) {
			//Invert vertex positions.
			Vector[] vertex_positions=block.GetVertexPositions();
			
			int[] vertex_ids;
			int[] vertex_ids_inverted;
			for(int i=0;i<2;i++) {
				vertex_ids=BD1Functions.GetFaceCorrespondingVertexIDs(i);
				vertex_ids_inverted=BD1Functions.GetFaceCorrespondingVertexIDs_InvertedZ(i);
				
				for(int j=0;j<4;j++) {
					block.SetVertexPosition(vertex_ids[j], vertex_positions[vertex_ids_inverted[j]]);
				}
			}
			
			//Invert UVs.
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			
			int[] uvs_ids;
			int[] uvs_ids_inverted;
			for(int i=0;i<6;i++) {
				uvs_ids=BD1Functions.GetFaceCorrespondingUVIDs(i);
				uvs_ids_inverted=BD1Functions.GetFaceCorrespondingUVIDs_InvertedZ(i);
				
				for(int j=0;j<4;j++) {
					block.SetUVs(uvs_ids[j], us[uvs_ids_inverted[j]], vs[uvs_ids_inverted[j]]);
				}
			}
			
			//Swap texture IDs.
			int[] texture_ids=block.GetTextureIDs();
			
			block.SetTextureID(2, texture_ids[4]);
			block.SetTextureID(4, texture_ids[2]);
		}
	}
	
	/**
	 * Write data to a BD1 file.
	 * @param bd1_filename Filename
	 */
	public void Write(String bd1_filename) {
		BD1Writer bd1_writer=new BD1Writer(blocks, texture_filenames_map);
		try {
			bd1_writer.Write(bd1_filename);
		}
		catch(FileNotFoundException e) {
			System.out.println("Failed to write.");
		}
	}
}
