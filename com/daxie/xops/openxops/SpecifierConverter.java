package com.daxie.xops.openxops;

import com.daxie.xops.character.CharacterAILevel;

/**
 * Offers methods to convert values that depend on XOPS and OpenXOPS.
 * @author Daba
 *
 */
public class SpecifierConverter {
	/**
	 * Converts XOPS sound ID to OpenXOPS sound ID.
	 * @param xops_sound_id XOPS sound ID
	 * @return OpenXOPS sound ID
	 */
	public static int GetOpenXOPSSoundIDFromXOPSSoundID(int xops_sound_id) {
		int ret;
		
		switch(xops_sound_id) {
		case 0:
			ret=0;
			break;
		case 1:
			ret=1;
			break;
		case 2:
			ret=2;
			break;
		case 3:
			ret=3;
			break;
		case 9:
			ret=13;
			break;
		case 13:
			ret=4;
			break;
		default:
			ret=0;
			break;
		}
		
		return ret;
	}
	/**
	 * Converts OpenXOPS sound ID to XOPS sound ID.
	 * @param openxops_sound_id OpenXOPS sound ID
	 * @return XOPS sound ID
	 */
	public static int GetXOPSSoundIDFromOpenXOPSSoundID(int openxops_sound_id) {
		int ret;
		
		switch(openxops_sound_id) {
		case 0:
			ret=0;
			break;
		case 1:
			ret=1;
			break;
		case 2:
			ret=2;
			break;
		case 3:
			ret=3;
			break;
		case 13:
			ret=9;
			break;
		case 4:
			ret=13;
			break;
		default:
			ret=0;
			break;
		}
		
		return ret;
	}
	
	/**
	 * Converts OpenXOPS AI level from XOPS AI level.
	 * @param xops_ai_level XOPS AI level
	 * @return OpenXOPS AI level
	 */
	public static int GetOpenXOPSAILevelFromXOPSAILevel(CharacterAILevel xops_ai_level) {
		int ret=0;
		
		switch(xops_ai_level) {
		case NONE:
		case NO_WEAPON:
			ret=0;
			break;
		case D:
			ret=0;
			break;
		case C:
			ret=1;
			break;
		case B:
			ret=2;
			break;
		case A:
			ret=3;
			break;
		case S:
		case SS:
			ret=4;
			break;
		}
		
		return ret;
	}
	/**
	 * Converts OpenXOPS AI level to XOPS AI level.
	 * @param openxops_ai_level OpenXOPS AI level
	 * @return XOPS AI level
	 */
	public static CharacterAILevel GetXOPSAILevelFromOpenXOPSAILevel(int openxops_ai_level) {
		CharacterAILevel ret=CharacterAILevel.D;
		
		switch(openxops_ai_level) {
		case 0:
			ret=CharacterAILevel.D;
			break;
		case 1:
			ret=CharacterAILevel.C;
			break;
		case 2:
			ret=CharacterAILevel.B;
			break;
		case 3:
			ret=CharacterAILevel.A;
			break;
		case 4:
			ret=CharacterAILevel.S;
			break;
		default:
			ret=CharacterAILevel.D;
			break;
		}
		
		return ret;
	}
}
