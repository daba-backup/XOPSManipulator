package com.github.dabasan.xops.properties.exe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.tool.ByteFunctions;
import com.github.dabasan.xops.properties.XOPSConstants;
import com.github.dabasan.xops.properties.entity.character.CharacterAILevel;
import com.github.dabasan.xops.properties.entity.character.CharacterBinSpecifierAndEnumConverter;
import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.character.CharacterModelType;
import com.github.dabasan.xops.properties.entity.character.CharacterTextureType;
import com.github.dabasan.xops.properties.entity.character.CharacterType;

/**
 * Writes data to an EXE file.
 * 
 * @author Daba
 *
 */
class XOPSExeCharacterDataWriter {
	private Logger logger = LoggerFactory
			.getLogger(XOPSExeCharacterDataWriter.class);

	private CharacterData[] character_data_array;

	public XOPSExeCharacterDataWriter(CharacterData[] character_data_array) {
		this.character_data_array = character_data_array;
	}

	public int Write(List<Byte> bin, int character_data_start_pos) {
		if (character_data_array == null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		if (character_data_array.length != XOPSConstants.CHARACTER_NUM) {
			logger.warn("Invalid number of data. data_num={}",
					character_data_array.length);
			return -1;
		}

		int pos = character_data_start_pos;

		for (int i = 0; i < XOPSConstants.CHARACTER_NUM; i++) {
			int itemp;

			// Texture
			CharacterTextureType texture_type = character_data_array[i]
					.GetTextureType();
			itemp = CharacterBinSpecifierAndEnumConverter
					.GetBinSpecifierFromCharacterTextureType(texture_type);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;

			// Model
			CharacterModelType model_type = character_data_array[i]
					.GetModelType();
			itemp = CharacterBinSpecifierAndEnumConverter
					.GetBinSpecifierFromCharacterModelType(model_type);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;

			// HP
			itemp = character_data_array[i].GetHP();
			ByteFunctions.SetUShortValueToBin_LE(bin, pos, itemp);
			pos += 2;

			// AI level
			CharacterAILevel ai_level = character_data_array[i].GetAILevel();
			itemp = CharacterBinSpecifierAndEnumConverter
					.GetBinSpecifierFromCharacterAILevel(ai_level);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;

			// Weapon A
			itemp = character_data_array[i].GetWeaponID(0);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;

			// Weapon B
			itemp = character_data_array[i].GetWeaponID(1);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;

			// Type
			CharacterType type = character_data_array[i].GetType();
			itemp = CharacterBinSpecifierAndEnumConverter
					.GetBinSpecifierFromCharacterType(type);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short) itemp);
			pos += 2;
		}

		return 0;
	}
}
