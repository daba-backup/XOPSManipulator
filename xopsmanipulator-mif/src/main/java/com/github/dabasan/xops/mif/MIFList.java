package com.github.dabasan.xops.mif;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.tool.XMLFunctions;

/**
 * Loads multiple MIF files and makes a list.
 * 
 * @author Daba
 *
 */
public class MIFList {
	private final Logger logger = LoggerFactory.getLogger(MIFList.class);

	public static final int MISSION_NAME = 0x0001;
	public static final int MISSION_FORMAL_NAME = 0x0002;
	public static final int BD1_FILENAME = 0x0004;
	public static final int PD1_FILENAME = 0x0008;
	public static final int SKY_TYPE = 0x0010;
	public static final int EXTRA_HITCHECK_FLAG = 0x0020;
	public static final int DARKEN_SCREEN_FLAG = 0x0040;
	public static final int ARTICLE_INFO_FILENAME = 0x0080;
	public static final int IMAGE1_FILENAME = 0x0100;
	public static final int IMAGE2_FILENAME = 0x0200;
	public static final int BRIEFING_TEXT = 0x0400;

	public static final int NONE = 0x0000;
	public static final int ALL = 0x07FF;

	private Map<String, MissionInfo> mission_info_map;

	/**
	 * @param directory_name
	 *            Name of the directory
	 * @param encoding
	 *            Encoding
	 * @throws IOException
	 */
	public MIFList(String directory_name, String encoding) throws IOException {
		mission_info_map = new HashMap<>();

		final File dir = new File(directory_name);
		if (dir.isDirectory() == false) {
			logger.warn("{} is not a directory.", directory_name);
			return;
		}

		final FilenameFilter filter = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if (name.endsWith("mif") || name.endsWith("MIF")) {
					return true;
				}
				return false;
			}
		};

		final File[] files = dir.listFiles(filter);
		if (files == null) {
			logger.error("listFiles() returned null.");
			return;
		}

		for (final File file : files) {
			MIFManipulator mif_manipulator = null;
			try {
				mif_manipulator = new MIFManipulator(file.getPath(), encoding);
			} catch (final IOException e) {
				logger.error("Failed to load a MIF file. filepath={}",
						file.getPath());
				return;
			}

			final MissionInfo mif = mif_manipulator.GetMissionInfo();
			mission_info_map.put(file.getName(), mif);
		}
	}

	/**
	 * Returns a map of mission info.
	 * 
	 * @return Mission info map
	 */
	public Map<String, MissionInfo> GetMissionInfo() {
		return new HashMap<>(mission_info_map);
	}

	/**
	 * Shows a list of addons.
	 * 
	 * @param flags
	 *            Flags to set what information will be shown
	 */
	public void ShowList(int flags) {
		for (final Map.Entry<String, MissionInfo> entry : mission_info_map
				.entrySet()) {
			System.out.println("[" + entry.getKey() + "]");

			final MissionInfo mif = entry.getValue();

			if ((flags & MISSION_NAME) != 0) {
				System.out.print("mission_name:");
				System.out.println(mif.GetMissionName());
			}
			if ((flags & MISSION_FORMAL_NAME) != 0) {
				System.out.print("mission_formal_name:");
				System.out.println(mif.GetMissionFormalName());
			}
			if ((flags & BD1_FILENAME) != 0) {
				System.out.print("bd1_filename:");
				System.out.println(mif.GetBD1Filename());
			}
			if ((flags & PD1_FILENAME) != 0) {
				System.out.print("pd1_filename:");
				System.out.println(mif.GetPD1Filename());
			}
			if ((flags & SKY_TYPE) != 0) {
				System.out.print("sky_type:");
				System.out.println(mif.GetSkyType());
			}
			if ((flags & EXTRA_HITCHECK_FLAG) != 0) {
				System.out.print("extra_hitcheck_flag:");
				System.out.println(mif.GetExtraHitcheckFlag());
			}
			if ((flags & DARKEN_SCREEN_FLAG) != 0) {
				System.out.print("darken_screen_flag:");
				System.out.println(mif.GetDarkenScreenFlag());
			}
			if ((flags & ARTICLE_INFO_FILENAME) != 0) {
				System.out.print("article_info_filename:");
				System.out.println(mif.GetArticleInfoFilename());
			}
			if ((flags & IMAGE1_FILENAME) != 0) {
				System.out.print("image1_filename:");
				System.out.println(mif.GetImage1Filename());
			}
			if ((flags & IMAGE2_FILENAME) != 0) {
				System.out.print("image2_filename:");
				System.out.println(mif.GetImage2Filename());
			}
			if ((flags & BRIEFING_TEXT) != 0) {
				System.out.println("briefing_text:");

				final List<String> briefing_text = mif.GetBriefingText();
				for (final String line : briefing_text) {
					System.out.println(line);
				}
			}

			System.out.println("------------------------------");
		}
	}
	/**
	 * Outputs a list of addons in a XML file.
	 * 
	 * @param xml_filename
	 *            XML filename
	 * @param flags
	 *            Flags to set what information will be written
	 * @return -1 on error and 0 on success
	 */
	public int WriteXML(String xml_filename, int flags) {
		final DocumentBuilderFactory factory = DocumentBuilderFactory
				.newInstance();

		DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (final ParserConfigurationException e) {
			logger.error("Error during configuration for XML output.", e);
			return -1;
		}
		final Document document = builder.newDocument();

		final Element el_addon_list = document.createElement("addon_list");
		document.appendChild(el_addon_list);

		for (final Map.Entry<String, MissionInfo> entry : mission_info_map
				.entrySet()) {
			final Element el_mission = document.createElement(entry.getKey());

			final MissionInfo mif = entry.getValue();

			if ((flags & MISSION_NAME) != 0) {
				final Element el_mission_name = document
						.createElement("mission_name");
				el_mission_name.appendChild(
						document.createTextNode(mif.GetMissionName()));
				el_mission.appendChild(el_mission_name);
			}
			if ((flags & MISSION_FORMAL_NAME) != 0) {
				final Element el_mission_formal_name = document
						.createElement("mission_formal_name");
				el_mission_formal_name.appendChild(
						document.createTextNode(mif.GetMissionFormalName()));
				el_mission.appendChild(el_mission_formal_name);
			}
			if ((flags & BD1_FILENAME) != 0) {
				final Element el_bd1_filename = document
						.createElement("bd1_filename");
				el_bd1_filename.appendChild(
						document.createTextNode(mif.GetBD1Filename()));
				el_mission.appendChild(el_bd1_filename);
			}
			if ((flags & PD1_FILENAME) != 0) {
				final Element el_pd1_filename = document
						.createElement("pd1_filename");
				el_pd1_filename.appendChild(
						document.createTextNode(mif.GetPD1Filename()));
				el_mission.appendChild(el_pd1_filename);
			}
			if ((flags & SKY_TYPE) != 0) {
				final Element el_sky_type = document.createElement("sky_type");
				el_sky_type.appendChild(
						document.createTextNode("" + mif.GetSkyType()));
				el_mission.appendChild(el_sky_type);
			}
			if ((flags & EXTRA_HITCHECK_FLAG) != 0) {
				final Element el_extra_hitcheck_flag = document
						.createElement("extra_hitcheck_flag");
				el_extra_hitcheck_flag.appendChild(document
						.createTextNode("" + mif.GetExtraHitcheckFlag()));
				el_mission.appendChild(el_extra_hitcheck_flag);
			}
			if ((flags & DARKEN_SCREEN_FLAG) != 0) {
				final Element el_darken_screen_flag = document
						.createElement("darken_screen_flag");
				el_darken_screen_flag.appendChild(document
						.createTextNode("" + mif.GetDarkenScreenFlag()));
				el_mission.appendChild(el_darken_screen_flag);
			}
			if ((flags & ARTICLE_INFO_FILENAME) != 0) {
				final Element el_article_info_filename = document
						.createElement("article_info_filename");
				el_article_info_filename.appendChild(
						document.createTextNode(mif.GetArticleInfoFilename()));
				el_mission.appendChild(el_article_info_filename);
			}
			if ((flags & IMAGE1_FILENAME) != 0) {
				final Element el_image1_filename = document
						.createElement("image1_filename");
				el_image1_filename.appendChild(
						document.createTextNode(mif.GetImage1Filename()));
				el_mission.appendChild(el_image1_filename);
			}
			if ((flags & IMAGE2_FILENAME) != 0) {
				final Element el_image2_filename = document
						.createElement("image2_filename");
				el_image2_filename.appendChild(
						document.createTextNode(mif.GetImage2Filename()));
				el_mission.appendChild(el_image2_filename);
			}
			if ((flags & BRIEFING_TEXT) != 0) {
				final Element el_briefing_text = document
						.createElement("briefing_text");
				el_mission.appendChild(el_briefing_text);

				final List<String> briefing_text_lines = mif.GetBriefingText();

				int line_count = 0;
				for (final String line : briefing_text_lines) {
					final Element el_briefing_text_line = document
							.createElement("line" + line_count);
					el_briefing_text_line
							.appendChild(document.createTextNode(line));
					el_briefing_text.appendChild(el_briefing_text_line);

					line_count++;
				}
			}

			el_addon_list.appendChild(el_mission);
		}

		final File file = new File(xml_filename);

		final int res = XMLFunctions.WriteXML(file, document);
		if (res < 0) {
			return -1;
		}

		return 0;
	}
	/**
	 * Outputs a list of addons in a CSV file.
	 * 
	 * @param csv_filename
	 *            CSV filename
	 * @param flags
	 *            Flags to set what information will be written
	 * @return -1 on error and 0 on success
	 */
	public int WriteCSV(String csv_filename, int flags) {
		final List<String> lines = new ArrayList<>();

		// Add columns.
		String columns = "";
		if ((flags & MISSION_NAME) != 0) {
			columns += "\"" + "Mission name" + "\",";
		}
		if ((flags & MISSION_FORMAL_NAME) != 0) {
			columns += "\"" + "Mission formal name" + "\",";
		}
		if ((flags & BD1_FILENAME) != 0) {
			columns += "\"" + "BD1 filename" + "\",";
		}
		if ((flags & PD1_FILENAME) != 0) {
			columns += "\"" + "PD1 filename" + "\",";
		}
		if ((flags & SKY_TYPE) != 0) {
			columns += "\"" + "Sky type" + "\",";
		}
		if ((flags & EXTRA_HITCHECK_FLAG) != 0) {
			columns += "\"" + "Extra hitcheck flag" + "\",";
		}
		if ((flags & DARKEN_SCREEN_FLAG) != 0) {
			columns += "\"" + "Darken screen flag" + "\",";
		}
		if ((flags & ARTICLE_INFO_FILENAME) != 0) {
			columns += "\"" + "Article info filename" + "\",";
		}
		if ((flags & IMAGE1_FILENAME) != 0) {
			columns += "\"" + "Image 1 filename" + "\",";
		}
		if ((flags & IMAGE2_FILENAME) != 0) {
			columns += "\"" + "Image 2 filename" + "\",";
		}
		if ((flags & BRIEFING_TEXT) != 0) {
			columns += "\"" + "Briefing text" + "\",";
		}

		if (columns.length() != 0) {
			columns = columns.substring(0, columns.length() - 1);
		}

		lines.add(columns);

		// Add data.
		for (final Map.Entry<String, MissionInfo> entry : mission_info_map
				.entrySet()) {
			final MissionInfo mif = entry.getValue();

			String line = "";
			if ((flags & MISSION_NAME) != 0) {
				line += "\"" + mif.GetMissionName() + "\",";
			}
			if ((flags & MISSION_FORMAL_NAME) != 0) {
				line += "\"" + mif.GetMissionFormalName() + "\",";
			}
			if ((flags & BD1_FILENAME) != 0) {
				line += "\"" + mif.GetBD1Filename() + "\",";
			}
			if ((flags & PD1_FILENAME) != 0) {
				line += "\"" + mif.GetPD1Filename() + "\",";
			}
			if ((flags & SKY_TYPE) != 0) {
				line += mif.GetSkyType() + ",";
			}
			if ((flags & EXTRA_HITCHECK_FLAG) != 0) {
				final boolean extra_hitcheck_flag = mif.GetExtraHitcheckFlag();

				if (extra_hitcheck_flag == false) {
					line += "false";
				} else {
					line += "true";
				}
				line += ",";
			}
			if ((flags & DARKEN_SCREEN_FLAG) != 0) {
				final boolean darken_screen_flag = mif.GetDarkenScreenFlag();

				if (darken_screen_flag == false) {
					line += "false";
				} else {
					line += "true";
				}
				line += ",";
			}
			if ((flags & ARTICLE_INFO_FILENAME) != 0) {
				line += "\"" + mif.GetArticleInfoFilename() + "\",";
			}
			if ((flags & IMAGE1_FILENAME) != 0) {
				line += "\"" + mif.GetImage1Filename() + "\",";
			}
			if ((flags & IMAGE2_FILENAME) != 0) {
				line += "\"" + mif.GetImage2Filename() + "\",";
			}
			if ((flags & BRIEFING_TEXT) != 0) {
				line += "\"";
				final List<String> briefing_text = mif.GetBriefingText();
				for (final String btline : briefing_text) {
					line += btline + "<br>";
				}
				line += "\",";
			}

			if (line.length() != 0) {
				line = line.substring(0, line.length() - 1);
			}

			lines.add(line);
		}

		try {
			FileFunctions.CreateTextFile(csv_filename, "UTF-8", lines);
		} catch (final IOException e) {
			logger.error("Error while writing.", e);
			return -1;
		}

		return 0;
	}
}
