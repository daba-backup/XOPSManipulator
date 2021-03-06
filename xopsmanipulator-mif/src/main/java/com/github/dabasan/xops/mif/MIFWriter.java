package com.github.dabasan.xops.mif;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.tool.FileFunctions;

/**
 * Writes data to a MIF file.
 * 
 * @author Daba
 *
 */
class MIFWriter {
	private final Logger logger = LoggerFactory.getLogger(MIFWriter.class);

	private final MissionInfo mission_info;

	public MIFWriter(MissionInfo mission_info) {
		this.mission_info = mission_info;
	}

	public int Write(String mif_filename, String encoding) {
		if (mission_info == null) {
			logger.warn("Data not prepared.");
			return -1;
		}

		final List<String> lines = new ArrayList<>();

		lines.add(mission_info.GetMissionName());
		lines.add(mission_info.GetMissionFormalName());
		lines.add(mission_info.GetBD1Filename());
		lines.add(mission_info.GetPD1Filename());
		lines.add("" + mission_info.GetSkyType());

		int flags = 0;
		final boolean extra_hitcheck_flag = mission_info.GetExtraHitcheckFlag();
		final boolean darken_screen_flag = mission_info.GetDarkenScreenFlag();
		if (extra_hitcheck_flag == true) {
			flags = flags | 0b00000001;
		}
		if (darken_screen_flag == true) {
			flags = flags | 0b00000010;
		}
		lines.add("" + flags);

		lines.add(mission_info.GetArticleInfoFilename());
		lines.add(mission_info.GetImage1Filename());
		lines.add(mission_info.GetImage2Filename());

		final List<String> briefing_text = mission_info.GetBriefingText();
		for (final String line : briefing_text) {
			lines.add(line);
		}

		try {
			FileFunctions.CreateTextFile(mif_filename, encoding, lines);
		} catch (final IOException e) {
			logger.error("Error while writing.", e);
			return -1;
		}

		return 0;
	}
}
