package com.github.dabasan.xops.bd1;

import java.util.Random;
import java.util.Set;

/**
 * Base class for BD1 generators
 * 
 * @author Daba
 *
 */
public abstract class BD1Generator {
	private final BD1Creator bd1_creator;

	public BD1Generator() {
		bd1_creator = new BD1Creator();
	}

	protected BD1Creator GetBD1Creator() {
		return bd1_creator;
	}

	public int SetTextureFilename(int texture_id, String texture_filename) {
		return bd1_creator.SetTextureFilename(texture_id, texture_filename);
	}

	/**
	 * Generates cubes.
	 * 
	 * @param block_num
	 *            Number of blocks
	 * @param edge_length
	 *            Length of edges
	 * @param scale
	 *            Scale
	 */
	public abstract void GenerateCubes(int block_num, float edge_length,
			float scale);

	/**
	 * Randomly sets texture IDs per face.
	 * 
	 * @param bound
	 *            The upper bound of the texture IDs
	 */
	public void RandomizeTextureIDsPerFace(int bound) {
		final Set<Integer> block_handles = bd1_creator.GetBlockHandles();
		final Random random = new Random();

		for (final int block_handle : block_handles) {
			for (int i = 0; i < 6; i++) {
				final int texture_id = random.nextInt(bound);
				bd1_creator.SetBlockTextureID(block_handle, i, texture_id);
			}
		}
	}
	/**
	 * Randomly sets texture IDs per block.
	 * 
	 * @param bound
	 *            The upper bound of the texture IDs
	 */
	public void RandomizeTextureIDsPerBlock(int bound) {
		final Set<Integer> block_handles = bd1_creator.GetBlockHandles();
		final Random random = new Random();

		for (final int block_handle : block_handles) {
			final int texture_id = random.nextInt(bound);

			for (int i = 0; i < 6; i++) {
				bd1_creator.SetBlockTextureID(block_handle, i, texture_id);
			}
		}
	}

	/**
	 * Writes out blocks into a BD1 file.
	 * 
	 * @param bd1_filename
	 *            Filename
	 * @param offset_y
	 *            Y-axis offset
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsBD1(String bd1_filename, float offset_y) {
		return bd1_creator.WriteAsBD1(bd1_filename, offset_y);
	}
	/**
	 * Writes out blocks into an OBJ file.
	 * 
	 * @param obj_filename
	 *            Filename
	 * @param offset_y
	 *            Y-axis offset
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsOBJ(String obj_filename, float offset_y) {
		return bd1_creator.WriteAsOBJ(obj_filename, offset_y);
	}
}
