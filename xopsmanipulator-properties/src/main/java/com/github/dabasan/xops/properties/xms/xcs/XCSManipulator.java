package com.github.dabasan.xops.properties.xms.xcs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.xops.properties.entity.character.CharacterData;

/**
 * Manipulates a XCS file.
 * 
 * @author Daba
 *
 */
public class XCSManipulator {
	private Logger logger = LoggerFactory.getLogger(XCSManipulator.class);

	private CharacterData[] character_data_array;

	/**
	 * 
	 * @param xcs_filename
	 *            XCS filename to load
	 * @throws IOException
	 */
	public XCSManipulator(String xcs_filename) throws IOException {
		XCSParser xcs_parser = new XCSParser(xcs_filename);
		character_data_array = xcs_parser.GetCharacterDataArray();
	}
	public XCSManipulator() {

	}

	/**
	 * Returns a character data array.
	 * 
	 * @return A character data array
	 */
	public CharacterData[] GetCharacterDataArray() {
		if (character_data_array == null) {
			return null;
		}

		CharacterData[] ret = new CharacterData[character_data_array.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new CharacterData(character_data_array[i]);
		}

		return ret;
	}
	/**
	 * Sets a character data array.
	 * 
	 * @param character_data_array
	 *            A character data array
	 */
	public void SetCharacterDataArray(CharacterData[] character_data_array) {
		if (character_data_array == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.character_data_array = character_data_array;
	}

	/**
	 * Writes out data to a XCS file.
	 * 
	 * @param xcs_filename
	 *            Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String xcs_filename) {
		XCSWriter xcs_writer = new XCSWriter(character_data_array);
		int ret = xcs_writer.Write(xcs_filename);

		if (ret < 0) {
			logger.error("Failed to write data in a XCS file.");
			return -1;
		}

		return 0;
	}
}
