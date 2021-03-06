package com.github.dabasan.xops.bd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Manipulates a BD1 file.
 * 
 * @author Daba
 *
 */
public class BD1Manipulator {
	private final Logger logger = LoggerFactory.getLogger(BD1Manipulator.class);

	private List<BD1Block> blocks;
	private Map<Integer, String> texture_filenames_map;

	/**
	 * @param bd1_filename
	 *            BD1 filename to load
	 * @throws IOException
	 */
	public BD1Manipulator(String bd1_filename) throws IOException {
		final BD1Reader bd1_parser = new BD1Reader(bd1_filename);

		blocks = bd1_parser.GetBlocks();
		texture_filenames_map = bd1_parser.GetTextureFilenames();
	}
	public BD1Manipulator() {
		blocks = new ArrayList<>();
		texture_filenames_map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			texture_filenames_map.put(i, "");
		}
	}

	/**
	 * Returns blocks.
	 * 
	 * @return Blocks
	 */
	public List<BD1Block> GetBlocks() {
		return new ArrayList<>(blocks);
	}
	/**
	 * Sets blocks.
	 * 
	 * @param blocks
	 *            Blocks
	 */
	public void SetBlocks(List<BD1Block> blocks) {
		if (blocks == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.blocks = blocks;
	}

	/**
	 * Returns the number of blocks contained in the BD1 file.
	 * 
	 * @return Number of blocks
	 */
	public int GetBlockNum() {
		return blocks.size();
	}

	/**
	 * Returns the texture filename associated with the texture ID.
	 * 
	 * @param texture_id
	 *            Texture ID
	 * @return Texture filename
	 */
	public String GetTextureFilename(int texture_id) {
		if (texture_filenames_map.containsKey(texture_id) == false) {
			logger.warn("No such texture. texture_id={}", texture_id);
			return "";
		}

		final String texture_filename = texture_filenames_map.get(texture_id);
		return texture_filename;
	}
	/**
	 * Returns the texture filenames.
	 * 
	 * @return Texture filenames
	 */
	public Map<Integer, String> GetTextureFilenames() {
		return new HashMap<>(texture_filenames_map);
	}
	/**
	 * Sets the texture filename associated with the texture ID.
	 * 
	 * @param texture_id
	 *            Texture ID
	 * @param texture_filename
	 *            Texture filename
	 * @return -1 on error and 0 on success
	 */
	public int SetTextureFilename(int texture_id, String texture_filename) {
		if (!(0 <= texture_id && texture_id < 10)) {
			logger.warn("Texture ID out of bounds. texture_id={}", texture_id);
			return -1;
		}

		texture_filenames_map.put(texture_id, texture_filename);

		return 0;
	}
	/**
	 * Sets the texture filenames.
	 * 
	 * @param texture_filenames_map
	 *            Texture filenames
	 * @return -1 on error and 0 on success
	 */
	public int SetTextureFilenames(Map<Integer, String> texture_filenames_map) {
		if (texture_filenames_map == null) {
			logger.warn("Null argument where non-null required.");
			return -1;
		}
		this.texture_filenames_map = texture_filenames_map;

		return 0;
	}

	/**
	 * Translates every block.
	 * 
	 * @param translate
	 *            Translation vector
	 */
	public void Translate(Vector translate) {
		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			for (int i = 0; i < vertex_positions.length; i++) {
				vertex_positions[i] = VectorFunctions.VAdd(vertex_positions[i],
						translate);
			}
			for (int i = 0; i < vertex_positions.length; i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Rotates every block.
	 * 
	 * @param rotate
	 *            Angles of rotation (radian)
	 */
	public void Rotate(Vector rotate) {
		final Matrix rot_x = MatrixFunctions.MGetRotX(rotate.GetX());
		final Matrix rot_y = MatrixFunctions.MGetRotY(rotate.GetY());
		final Matrix rot_z = MatrixFunctions.MGetRotZ(rotate.GetZ());

		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			for (int i = 0; i < vertex_positions.length; i++) {
				vertex_positions[i] = MatrixFunctions
						.VTransform(vertex_positions[i], rot_x);
				vertex_positions[i] = MatrixFunctions
						.VTransform(vertex_positions[i], rot_y);
				vertex_positions[i] = MatrixFunctions
						.VTransform(vertex_positions[i], rot_z);
			}
			for (int i = 0; i < vertex_positions.length; i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Rescales every block.
	 * 
	 * @param scale
	 *            Scaling vector
	 */
	public void Rescale(Vector scale) {
		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			for (int i = 0; i < vertex_positions.length; i++) {
				float x, y, z;
				x = vertex_positions[i].GetX();
				y = vertex_positions[i].GetY();
				z = vertex_positions[i].GetZ();

				vertex_positions[i].SetX(x * scale.GetX());
				vertex_positions[i].SetY(y * scale.GetY());
				vertex_positions[i].SetZ(z * scale.GetZ());
			}
			for (int i = 0; i < vertex_positions.length; i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}
	/**
	 * Transforms every block with a matrix.
	 * 
	 * @param m
	 *            Transformation matrix
	 */
	public void SetMatrix(Matrix m) {
		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			for (int i = 0; i < vertex_positions.length; i++) {
				vertex_positions[i] = MatrixFunctions
						.VTransform(vertex_positions[i], m);
			}
			for (int i = 0; i < vertex_positions.length; i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}
	}

	/**
	 * Inverts z-axis.<br>
	 * This method is used to make a mirrored map.
	 */
	public void InvertZ() {
		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			for (int i = 0; i < 8; i++) {
				vertex_positions[i].SetZ(vertex_positions[i].GetZ() * (-1.0f));
			}
			for (int i = 0; i < 8; i++) {
				block.SetVertexPosition(i, vertex_positions[i]);
			}
		}

		for (final BD1Block block : blocks) {
			final Vector[] vertex_positions = block.GetVertexPositions();
			final float[] us = block.GetUs();
			final float[] vs = block.GetVs();
			final int[] texture_ids = block.GetTextureIDs();

			final Vector[] vertex_positions_orig = new Vector[8];
			for (int i = 0; i < 8; i++) {
				vertex_positions_orig[i] = new Vector(vertex_positions[i]);
			}

			for (int i = 0; i < 4; i++) {
				block.SetVertexPosition(i, vertex_positions_orig[3 - i]);
			}
			for (int i = 0; i < 4; i++) {
				block.SetVertexPosition(i + 4, vertex_positions_orig[7 - i]);
			}

			final float[] us_orig = us.clone();
			final float[] vs_orig = vs.clone();

			for (int i = 0; i < 6; i++) {
				int[] uv_indices;

				if (i == 2) {
					uv_indices = BD1Functions.GetFaceCorrespondingUVIndices(4);
				} else if (i == 4) {
					uv_indices = BD1Functions.GetFaceCorrespondingUVIndices(2);
				} else {
					uv_indices = BD1Functions.GetFaceCorrespondingUVIndices(i);
				}

				for (int j = 0; j < 4; j++) {
					final int index = i * 4 + j;

					us[index] = us_orig[uv_indices[j]];
					vs[index] = vs_orig[uv_indices[j]];
				}
			}
			for (int i = 0; i < 24; i++) {
				block.SetUV(i, us[i], vs[i]);
			}

			final int[] texture_ids_orig = texture_ids.clone();
			block.SetTextureID(2, texture_ids_orig[4]);
			block.SetTextureID(4, texture_ids_orig[2]);
		}
	}

	/**
	 * Writes out data in a BD1 file.
	 * 
	 * @param bd1_filename
	 *            Filename
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsBD1(String bd1_filename) {
		final BD1Writer bd1_writer = new BD1Writer(blocks,
				texture_filenames_map);
		final int ret = bd1_writer.Write(bd1_filename);

		if (ret < 0) {
			logger.error(
					"Failed to write blocks in a BD1 file. bd1_filename={}",
					bd1_filename);
			return -1;
		}

		return 0;
	}

	/**
	 * Writes out data in an OBJ file.
	 * 
	 * @param obj_filename
	 *            Filename
	 * @return -1 on error and 0 on success
	 */
	public int WriteAsOBJ(String obj_filename) {
		final BD1OBJWriter obj_writer = new BD1OBJWriter(texture_filenames_map,
				blocks);

		final int ret = obj_writer.Write(obj_filename);
		if (ret < 0) {
			logger.error(
					"Failed to write blocks in an OBJ file. obj_filename={}",
					obj_filename);
			return -1;
		}

		return 0;
	}
}
