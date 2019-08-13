package com.daxie.xops.bd1;

/**
 * Base class for BD1 generators.
 * @author Daba
 *
 */
abstract class BD1Generator {
	private BD1Creator bd1_creator;
	
	public BD1Generator() {
		bd1_creator=new BD1Creator();
	}
	
	protected BD1Creator GetBD1Creator() {
		return bd1_creator;
	}
	
	public int SetTextureFilename(int texture_id,String texture_filename) {
		return bd1_creator.SetTextureFilename(texture_id, texture_filename);
	}
	
	/**
	 * Generates cubes.
	 * @param block_num Number of blocks
	 * @param edge_length Length of edges
	 * @param scale Scale
	 */
	public abstract void GenerateCubes(int block_num,float edge_length,float scale);
	
	/**
	 * Writes out blocks into a BD1 file.
	 * @param bd1_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsBD1(String bd1_filename) {
		return bd1_creator.WriteAsBD1(bd1_filename);
	}
}
