package com.github.dabasan.xops.properties.exe;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.tool.DateFunctions;
import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.tool.FilenameFunctions;
import com.github.dabasan.xops.properties.XOPSConstants;
import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;

/**
 * Manipulates an EXE file.
 * 
 * @author Daba
 *
 */
public class XOPSExeManipulator {
	private final Logger logger = LoggerFactory
			.getLogger(XOPSExeManipulator.class);

	private WeaponData[] weapon_data_array;
	private CharacterData[] character_data_array;

	/**
	 * 
	 * @param xops_filename
	 *            EXE filename to load
	 * @throws IOException
	 */
	public XOPSExeManipulator(String xops_filename) throws IOException {
		final List<Byte> bin = FileFunctions.GetFileAllBin(xops_filename);
		final XOPSVersion version = XOPSExeFunctions.GetXOPSVersion(bin);

		if (version == XOPSVersion.UNKNOWN_VERSION) {
			logger.warn("Unknown version of X operations.");

			weapon_data_array = new WeaponData[XOPSConstants.WEAPON_NUM];
			for (int i = 0; i < weapon_data_array.length; i++) {
				weapon_data_array[i] = new WeaponData();
			}
			character_data_array = new CharacterData[XOPSConstants.CHARACTER_NUM];
			for (int i = 0; i < character_data_array.length; i++) {
				character_data_array[i] = new CharacterData();
			}

			return;
		}

		int weapon_data_start_pos;
		int weapon_name_start_pos;
		int character_data_start_pos;

		switch (version) {
			case XOPS096 :
				weapon_data_start_pos = 0x0005D32C;
				weapon_name_start_pos = 0x000661E4;
				character_data_start_pos = 0x0005D864;
				break;
			case XOPS096T :
				weapon_data_start_pos = 0x0005D32C;
				weapon_name_start_pos = 0x000661E4;
				character_data_start_pos = 0x0005D864;
				break;
			case XOPS097FT :
				weapon_data_start_pos = 0x0005E32C;
				weapon_name_start_pos = 0x000671E4;
				character_data_start_pos = 0x0005E864;
				break;
			case XOPS0975T :
				weapon_data_start_pos = 0x00077FB0;
				weapon_name_start_pos = 0x00079140;
				character_data_start_pos = 0x000784E8;
				break;
			case XOPSOLT18F2 :
				weapon_data_start_pos = 0x0006640C;
				weapon_name_start_pos = 0x0006EF84;
				character_data_start_pos = 0x00066944;
				break;
			case XOPSOLT19F2 :
				weapon_data_start_pos = 0x000777E8;
				weapon_name_start_pos = 0x00077370;
				character_data_start_pos = 0x00077D20;
				break;
			default :
				weapon_data_start_pos = 0x00000000;
				weapon_name_start_pos = 0x00000000;
				character_data_start_pos = 0x00000000;
				break;
		}

		final XOPSExeWeaponDataReader weapon_data_parser = new XOPSExeWeaponDataReader(
				bin, weapon_data_start_pos, weapon_name_start_pos);
		final XOPSExeCharacterDataReader character_data_parser = new XOPSExeCharacterDataReader(
				bin, character_data_start_pos);

		weapon_data_array = weapon_data_parser.GetWeaponData();
		character_data_array = character_data_parser.GetCharacterData();
	}
	public XOPSExeManipulator(String xops_filename, int weapon_data_start_pos,
			int weapon_name_start_pos, int character_data_start_pos)
			throws IOException {
		final List<Byte> bin = FileFunctions.GetFileAllBin(xops_filename);

		XOPSExeWeaponDataReader weapon_data_parser = null;
		XOPSExeCharacterDataReader character_data_parser = null;

		try {
			weapon_data_parser = new XOPSExeWeaponDataReader(bin,
					weapon_data_start_pos, weapon_name_start_pos);
			character_data_parser = new XOPSExeCharacterDataReader(bin,
					character_data_start_pos);
		} catch (final IndexOutOfBoundsException e) {
			logger.error("Error while reading.", e);
			return;
		}

		weapon_data_array = weapon_data_parser.GetWeaponData();
		character_data_array = character_data_parser.GetCharacterData();
	}
	public XOPSExeManipulator() {

	}

	/**
	 * Returns a weapon data array.
	 * 
	 * @return A weapon data array
	 */
	public WeaponData[] GetWeaponData() {
		if (weapon_data_array == null) {
			return null;
		}

		final WeaponData[] ret = new WeaponData[weapon_data_array.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new WeaponData(weapon_data_array[i]);
		}

		return ret;
	}
	/**
	 * Returns a character data array.
	 * 
	 * @return A character data array
	 */
	public CharacterData[] GetCharacterData() {
		if (character_data_array == null) {
			return null;
		}

		final CharacterData[] ret = new CharacterData[character_data_array.length];
		for (int i = 0; i < ret.length; i++) {
			ret[i] = new CharacterData(character_data_array[i]);
		}

		return ret;
	}
	/**
	 * Sets a weapon data array.
	 * 
	 * @param weapon_data_array
	 *            A weapon data array
	 */
	public void SetWeaponData(WeaponData[] weapon_data_array) {
		if (weapon_data_array == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.weapon_data_array = weapon_data_array;
	}
	/**
	 * Sets a character data array.
	 * 
	 * @param character_data_array
	 *            A character data array
	 */
	public void SetCharacterData(CharacterData[] character_data_array) {
		if (character_data_array == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.character_data_array = character_data_array;
	}

	/**
	 * Writes out data to an EXE file.
	 * 
	 * @param xops_filename
	 *            Filename of the file where data will be overwritten
	 * @param create_backup_flag
	 *            Flag to set whether to create a backup file
	 * @return -1 on error and 0 on success
	 */
	public int Write(String xops_filename, boolean create_backup_flag) {
		List<Byte> bin;
		try {
			bin = FileFunctions.GetFileAllBin(xops_filename);
		} catch (final IOException e) {
			logger.error("Failed to write in an EXE file. exe_filename={}",
					xops_filename);
			logger.error("", e);
			return -1;
		}

		// Create a backup.
		if (create_backup_flag == true) {
			final String date = DateFunctions.GetDateStringWithoutDelimiters();
			final String filename_without_extension = FilenameFunctions
					.GetFilenameWithoutExtension(xops_filename);
			final String backup_filename = filename_without_extension + "_"
					+ date + ".exe";

			try {
				FileFunctions.CreateBinFile(backup_filename, bin);
			} catch (final IOException e) {
				logger.error("Failed to create a backup file.", e);
				return -1;
			}
		}

		// Create a modified file (overwrite).
		final XOPSExeWeaponDataWriter weapon_data_writer = new XOPSExeWeaponDataWriter(
				weapon_data_array);
		final XOPSExeCharacterDataWriter character_data_writer = new XOPSExeCharacterDataWriter(
				character_data_array);

		final XOPSVersion version = XOPSExeFunctions.GetXOPSVersion(bin);

		int weapon_data_start_pos;
		int weapon_name_start_pos;
		int character_data_start_pos;

		switch (version) {
			case XOPS096 :
				weapon_data_start_pos = 0x0005D32C;
				weapon_name_start_pos = 0x000661E4;
				character_data_start_pos = 0x0005D864;
				break;
			case XOPS096T :
				weapon_data_start_pos = 0x0005D32C;
				weapon_name_start_pos = 0x000661E4;
				character_data_start_pos = 0x0005D864;
				break;
			case XOPS097FT :
				weapon_data_start_pos = 0x0005E32C;
				weapon_name_start_pos = 0x000671E4;
				character_data_start_pos = 0x0005E864;
				break;
			case XOPS0975T :
				weapon_data_start_pos = 0x00077FB0;
				weapon_name_start_pos = 0x00079140;
				character_data_start_pos = 0x000784E8;
				break;
			case XOPSOLT18F2 :
				weapon_data_start_pos = 0x0006640C;
				weapon_name_start_pos = 0x0006EF84;
				character_data_start_pos = 0x00066944;
				break;
			case XOPSOLT19F2 :
				weapon_data_start_pos = 0x000777E8;
				weapon_name_start_pos = 0x00077370;
				character_data_start_pos = 0x00077D20;
				break;
			default :
				weapon_data_start_pos = 0x00000000;
				weapon_name_start_pos = 0x00000000;
				character_data_start_pos = 0x00000000;
				break;
		}

		weapon_data_writer.Write(bin, weapon_data_start_pos,
				weapon_name_start_pos);
		character_data_writer.Write(bin, character_data_start_pos);

		try {
			FileFunctions.CreateBinFile(xops_filename, bin);
		} catch (final IOException e) {
			logger.error("Failed to write in an EXE file. exe_filename={}",
					xops_filename);
			return -1;
		}

		return 0;
	}
	/**
	 * Writes out data to an EXE file.<br>
	 * Start addresses of each data must be known beforehand.
	 * 
	 * @param xops_filename
	 *            Filename
	 * @param weapon_data_start_pos
	 *            Start address of weapon data
	 * @param weapon_name_start_pos
	 *            Start address of weapon name
	 * @param character_data_start_pos
	 *            Start address of character data
	 * @param create_backup_flag
	 *            Flag to set whether to create a backup file
	 * @return -1 on error and 0 on success
	 */
	public int Write(String xops_filename, int weapon_data_start_pos,
			int weapon_name_start_pos, int character_data_start_pos,
			boolean create_backup_flag) {
		List<Byte> bin;
		try {
			bin = FileFunctions.GetFileAllBin(xops_filename);
		} catch (final IOException e) {
			logger.error("Failed to write in an EXE file. exe_filename={}",
					xops_filename);
			logger.error("", e);
			return -1;
		}

		// Create a backup.
		if (create_backup_flag == true) {
			final String date = DateFunctions.GetDateStringWithoutDelimiters();
			final String filename_without_extension = FilenameFunctions
					.GetFilenameWithoutExtension(xops_filename);
			final String backup_filename = filename_without_extension + "_"
					+ date + ".exe";

			try {
				FileFunctions.CreateBinFile(backup_filename, bin);
			} catch (final IOException e) {
				logger.error("Failed to create a backup file.", e);
				return -1;
			}
		}

		// Create a modified file (overwrite).
		final XOPSExeWeaponDataWriter weapon_data_writer = new XOPSExeWeaponDataWriter(
				weapon_data_array);
		final XOPSExeCharacterDataWriter character_data_writer = new XOPSExeCharacterDataWriter(
				character_data_array);

		try {
			weapon_data_writer.Write(bin, weapon_data_start_pos,
					weapon_name_start_pos);
			character_data_writer.Write(bin, character_data_start_pos);
		} catch (final IndexOutOfBoundsException e) {
			logger.error("Error while writing.", e);
			return -1;
		}

		try {
			FileFunctions.CreateBinFile(xops_filename, bin);
		} catch (final IOException e) {
			logger.error("Failed to write in an EXE file. exe_filename={}",
					xops_filename);
			return -1;
		}

		return 0;
	}
}
