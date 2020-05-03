package com.github.dabasan.xops.properties.xms.xgs;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;

/**
 * Manipulates a XGS file.
 * 
 * @author Daba
 *
 */
public class XGSManipulator {
	private final Logger logger = LoggerFactory.getLogger(XGSManipulator.class);

	private WeaponData[] weapon_data_array;

	/**
	 * 
	 * @param xgs_filename
	 *            XGS filename to load
	 * @throws IOException
	 */
	public XGSManipulator(String xgs_filename) throws IOException {
		final XGSParser xgs_parser = new XGSParser(xgs_filename);
		weapon_data_array = xgs_parser.GetWeaponData();
	}
	public XGSManipulator() {

	}

	/**
	 * Returns a weapon data array.<br>
	 * Returns null in case data is null.
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
	 * Writes out data to a XGS file.
	 * 
	 * @param xgs_filename
	 *            Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String xgs_filename) {
		final XGSWriter xgs_writer = new XGSWriter(weapon_data_array);
		final int ret = xgs_writer.Write(xgs_filename);

		if (ret < 0) {
			logger.error("Failed to write data in a XGS file. xgs_filename={}",
					xgs_filename);
			return -1;
		}

		return 0;
	}
}
