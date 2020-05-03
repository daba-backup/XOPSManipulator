package com.github.dabasan.xops.pd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.tool.ByteFunctions;
import com.github.dabasan.tool.FileFunctions;

/**
 * Writes data to a PD1 file.
 * 
 * @author Daba
 *
 */
class PD1Writer {
	private final Logger logger = LoggerFactory.getLogger(PD1Writer.class);

	private final List<PD1Point> points;

	public PD1Writer(List<PD1Point> points) {
		this.points = points;
	}

	public int Write(String pd1_filename) {
		if (points == null) {
			logger.warn("Data not prepared.");
			return -1;
		}

		final List<Byte> bin = new ArrayList<>();

		// Number of points
		final int point_num = points.size();
		ByteFunctions.AddUShortValueToBin_LE(bin, point_num);

		// Point data
		for (int i = 0; i < points.size(); i++) {
			final PD1Point point = points.get(i);

			final Vector position = point.GetPosition();
			final float rotation = point.GetRotation();
			final int[] parameters = point.GetParameters();

			// Position
			ByteFunctions.AddFloatValueToBin_LE(bin, position.GetX());
			ByteFunctions.AddFloatValueToBin_LE(bin, position.GetY());
			ByteFunctions.AddFloatValueToBin_LE(bin, position.GetZ());

			// Rotation
			ByteFunctions.AddFloatValueToBin_LE(bin, rotation);

			// Parameters
			for (int j = 0; j < 4; j++) {
				bin.add((byte) parameters[j]);
			}
		}

		try {
			FileFunctions.CreateBinFile(pd1_filename, bin);
		} catch (final IOException e) {
			logger.error("Error while writing.", e);
			return -1;
		}

		return 0;
	}
}
