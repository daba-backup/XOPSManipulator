package com.github.dabasan.xops.mif;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manipulates a MIF file.
 * 
 * @author Daba
 *
 */
public class MIFManipulator {
	private final Logger logger = LoggerFactory.getLogger(MIFManipulator.class);

	private MissionInfo mission_info;

	public MIFManipulator(String mif_filename, String encoding)
			throws IOException {
		final MIFReader mif_parser = new MIFReader(mif_filename, encoding);
		mission_info = mif_parser.GetMissionInfo();
	}
	public MIFManipulator() {
		mission_info = new MissionInfo();
	}

	public void SetMissionInfo(MissionInfo mission_info) {
		if (mission_info == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.mission_info = mission_info;
	}
	public MissionInfo GetMissionInfo() {
		return new MissionInfo(mission_info);
	}

	public int Write(String mif_filename, String encoding) {
		final MIFWriter mif_writer = new MIFWriter(mission_info);
		final int ret = mif_writer.Write(mif_filename, encoding);

		if (ret < 0) {
			logger.error("Failed to write in a MIF file. mif_filename={}",
					mif_filename);
			return -1;
		}

		return 0;
	}
}
