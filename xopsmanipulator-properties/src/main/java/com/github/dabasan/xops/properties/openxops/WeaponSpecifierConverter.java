package com.github.dabasan.xops.properties.openxops;

/**
 * Provides methods to convert values that depend on XOPS and OpenXOPS.
 * 
 * @author Daba
 *
 */
public class WeaponSpecifierConverter {
	/**
	 * Converts XOPS sound ID to OpenXOPS sound ID.
	 * 
	 * @param xops_sound_id
	 *            XOPS sound ID
	 * @return OpenXOPS sound ID
	 */
	public static int GetOpenXOPSSoundIDFromXOPSSoundID(int xops_sound_id) {
		int ret;

		switch (xops_sound_id) {
			case 0 :
				ret = 0;
				break;
			case 1 :
				ret = 1;
				break;
			case 2 :
				ret = 2;
				break;
			case 3 :
				ret = 3;
				break;
			case 9 :
				ret = 13;
				break;
			case 13 :
				ret = 4;
				break;
			default :
				ret = 0;
				break;
		}

		return ret;
	}
	/**
	 * Converts OpenXOPS sound ID to XOPS sound ID.
	 * 
	 * @param openxops_sound_id
	 *            OpenXOPS sound ID
	 * @return XOPS sound ID
	 */
	public static int GetXOPSSoundIDFromOpenXOPSSoundID(int openxops_sound_id) {
		int ret;

		switch (openxops_sound_id) {
			case 0 :
				ret = 0;
				break;
			case 1 :
				ret = 1;
				break;
			case 2 :
				ret = 2;
				break;
			case 3 :
				ret = 3;
				break;
			case 13 :
				ret = 9;
				break;
			case 4 :
				ret = 13;
				break;
			default :
				ret = 0;
				break;
		}

		return ret;
	}
}
