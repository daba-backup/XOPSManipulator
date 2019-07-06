package com.daxie.xops.bd1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;

/**
 * Manipulates a BD1 file.
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
	public BD1Manipulator() {
		blocks=new ArrayList<Block>();
		texture_filenames_map=new HashMap<>();
	}
	
	/**
	 * Returns blocks.
	 * @return Blocks
	 */
	public List<Block> GetBlocks(){
		return new ArrayList<>(blocks);
	}
	/**
	 * Sets blocks.
	 * @param blocks Blocks
	 */
	public void SetBlocks(List<Block> blocks) {
		this.blocks=blocks;
	}
	
	/**
	 * Returns the number of blocks contained in the BD1 file.
	 * @return Number of blocks
	 */
	public int GetBlockNum() {
		return blocks.size();
	}
	
	/**
	 * Returns the texture filename associated with the texture ID.
	 * @param texture_id Texture ID
	 * @return Texture filename
	 */
	public String GetTextureFilename(int texture_id) {
		if(texture_filenames_map.containsKey(texture_id)==false)return "";
		
		String texture_filename=texture_filenames_map.get(texture_id);
		return texture_filename;
	}
	/**
	 * Sets the texture filename associated with the texture ID.
	 * @param texture_id Texture ID
	 * @param texture_filename Texture filename
	 */
	public void SetTextureFilename(int texture_id,String texture_filename) {
		if(!(0<=texture_id&&texture_id<10))return;
		
		texture_filenames_map.put(texture_id, texture_filename);
	}
	
	/**
	 * Translates every block.
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
	 * Rotates every block.
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
	 * Rescales every block.
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
	 * Transforms every block with a matrix.
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
	 * Inverts z-axis.<br>
	 * This method will be used to make a mirrored map.
	 */
	public void InvertZ() {
		//Invert z-coordinate of the vertices.
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			for(int i=0;i<8;i++) {
				vertex_positions[i].SetZ(vertex_positions[i].GetZ()*(-1.0f));
			}
			for(int i=0;i<8;i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
		//Resolve inconsistencies in the vertices.
		for(Block block:blocks) {
			Vector[] vertex_positions=block.GetVertexPositions();
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			int[] texture_ids=block.GetTextureIDs();
			
			for(int i=0;i<4;i++) {
				block.SetVertexPosition(i, vertex_positions[3-i]);
			}
			for(int i=0;i<4;i++) {
				block.SetVertexPosition(i+4, vertex_positions[7-i]);
			}
			
			float[] us_orig=us.clone();
			float[] vs_orig=vs.clone();
			
			us[0]=us_orig[3];
			us[1]=us_orig[2];
			us[2]=us_orig[1];
			us[3]=us_orig[0];
			us[4]=us_orig[7];
			us[5]=us_orig[6];
			us[6]=us_orig[5];
			us[7]=us_orig[4];
			us[8]=us_orig[17];
			us[9]=us_orig[16];
			us[10]=us_orig[19];
			us[11]=us_orig[18];
			us[12]=us_orig[13];
			us[13]=us_orig[12];
			us[14]=us_orig[15];
			us[15]=us_orig[14];
			us[16]=us_orig[9];
			us[17]=us_orig[8];
			us[18]=us_orig[11];
			us[19]=us_orig[10];
			us[20]=us_orig[21];
			us[21]=us_orig[20];
			us[22]=us_orig[23];
			us[23]=us_orig[22];
			vs[0]=vs_orig[3];
			vs[1]=vs_orig[2];
			vs[2]=vs_orig[1];
			vs[3]=vs_orig[0];
			vs[4]=vs_orig[7];
			vs[5]=vs_orig[6];
			vs[6]=vs_orig[5];
			vs[7]=vs_orig[4];
			vs[8]=vs_orig[17];
			vs[9]=vs_orig[16];
			vs[10]=vs_orig[19];
			vs[11]=vs_orig[18];
			vs[12]=vs_orig[13];
			vs[13]=vs_orig[12];
			vs[14]=vs_orig[15];
			vs[15]=vs_orig[14];
			vs[16]=vs_orig[9];
			vs[17]=vs_orig[8];
			vs[18]=vs_orig[11];
			vs[19]=vs_orig[10];
			vs[20]=vs_orig[21];
			vs[21]=vs_orig[20];
			vs[22]=vs_orig[23];
			vs[23]=vs_orig[22];
			
			for(int i=0;i<24;i++) {
				block.SetUVs(i, us[i], vs[i]);
			}
			
			block.SetTextureID(2, texture_ids[4]);
			block.SetTextureID(4, texture_ids[2]);
		}
	}
	
	/**
	 * Writes out data to a BD1 file.
	 * @param bd1_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String bd1_filename) {
		BD1Writer bd1_writer=new BD1Writer(blocks, texture_filenames_map);
		try {
			bd1_writer.Write(bd1_filename);
		}
		catch(FileNotFoundException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteError("[BD1Manipulator-Write] Failed to write data.");
			LogFile.WriteLine("Below is the stack trace.");
			LogFile.WriteLine(str);
			
			return -1;
		}
		
		return 0;
	}
}
