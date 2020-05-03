package com.github.dabasan.xops.properties.openxops;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.xops.properties.entity.character.CharacterAILevel;
import com.github.dabasan.xops.properties.entity.character.CharacterTextureType;

/**
 * Provides methods to convert values that depend on XOPS and OpenXOPS.
 * 
 * @author Daba
 *
 */
public class CharacterSpecifierConverter {
	private static Logger logger = LoggerFactory
			.getLogger(CharacterSpecifierConverter.class);

	/**
	 * Converts XOPS texture type to OpenXOPS texture ID.
	 * 
	 * @param xops_texture_type
	 *            XOPS texture type
	 * @return OpenXOPS texture ID
	 */
	public static int GetOpenXOPSTextureIDFromXOPSTextureType(
			CharacterTextureType xops_texture_type) {
		int ret = 0;

		switch (xops_texture_type) {
			case CIV1 :
				ret = 0;
				break;
			case CIV2 :
				ret = 1;
				break;
			case CIV3 :
				ret = 2;
				break;
			case GATES :
				ret = 3;
				break;
			case GS :
				ret = 4;
				break;
			case HAGE :
				ret = 5;
				break;
			case ISLAM :
				ret = 6;
				break;
			case ISLAM2 :
				ret = 7;
				break;
			case POLICE :
				ret = 8;
				break;
			case RIIMAN :
				ret = 9;
				break;
			case RIIMAN_B :
				ret = 10;
				break;
			case RIIMAN_G :
				ret = 11;
				break;
			case RIIMAN_K :
				ret = 12;
				break;
			case ROBOT :
				ret = 13;
				break;
			case SOLDIER_BLACK :
				ret = 14;
				break;
			case SOLDIER_BLUE :
				ret = 15;
				break;
			case SOLDIER_GREEN :
				ret = 16;
				break;
			case SOLDIER_VIOLET :
				ret = 17;
				break;
			case SOLDIER_WHITE :
				ret = 18;
				break;
			case SOLDIER0 :
				ret = 19;
				break;
			case SOLDIER1 :
				ret = 20;
				break;
			case SOLDIER2 :
				ret = 21;
				break;
			case SOLDIER3 :
				ret = 22;
				break;
			case SYATU :
				ret = 23;
				break;
			case SYATU2 :
				ret = 24;
				break;
			case WOMAN :
				ret = 25;
				break;
			case ZOMBIE1 :
				ret = 26;
				break;
			case ZOMBIE2 :
				ret = 27;
				break;
			case ZOMBIE3 :
				ret = 28;
				break;
			case ZOMBIE4 :
				ret = 29;
				break;
		}

		return ret;
	}
	/**
	 * Converts OpenXOPS texture ID to XOPS texture type.
	 * 
	 * @param openxops_texture_id
	 *            OpenXOPS texture ID
	 * @return XOPS texture type
	 */
	public static CharacterTextureType GetXOPSTextureTypeFromOpenXOPSTextureID(
			int openxops_texture_id) {
		CharacterTextureType xops_texture_type = CharacterTextureType.CIV1;

		switch (openxops_texture_id) {
			case 0 :
				xops_texture_type = CharacterTextureType.CIV1;
				break;
			case 1 :
				xops_texture_type = CharacterTextureType.CIV2;
				break;
			case 2 :
				xops_texture_type = CharacterTextureType.CIV3;
				break;
			case 3 :
				xops_texture_type = CharacterTextureType.GATES;
				break;
			case 4 :
				xops_texture_type = CharacterTextureType.GS;
				break;
			case 5 :
				xops_texture_type = CharacterTextureType.HAGE;
				break;
			case 6 :
				xops_texture_type = CharacterTextureType.ISLAM;
				break;
			case 7 :
				xops_texture_type = CharacterTextureType.ISLAM2;
				break;
			case 8 :
				xops_texture_type = CharacterTextureType.POLICE;
				break;
			case 9 :
				xops_texture_type = CharacterTextureType.RIIMAN;
				break;
			case 10 :
				xops_texture_type = CharacterTextureType.RIIMAN_B;
				break;
			case 11 :
				xops_texture_type = CharacterTextureType.RIIMAN_G;
				break;
			case 12 :
				xops_texture_type = CharacterTextureType.RIIMAN_K;
				break;
			case 13 :
				xops_texture_type = CharacterTextureType.ROBOT;
				break;
			case 14 :
				xops_texture_type = CharacterTextureType.SOLDIER_BLACK;
				break;
			case 15 :
				xops_texture_type = CharacterTextureType.SOLDIER_BLUE;
				break;
			case 16 :
				xops_texture_type = CharacterTextureType.SOLDIER_GREEN;
				break;
			case 17 :
				xops_texture_type = CharacterTextureType.SOLDIER_VIOLET;
				break;
			case 18 :
				xops_texture_type = CharacterTextureType.SOLDIER_WHITE;
				break;
			case 19 :
				xops_texture_type = CharacterTextureType.SOLDIER0;
				break;
			case 20 :
				xops_texture_type = CharacterTextureType.SOLDIER1;
				break;
			case 21 :
				xops_texture_type = CharacterTextureType.SOLDIER2;
				break;
			case 22 :
				xops_texture_type = CharacterTextureType.SOLDIER3;
				break;
			case 23 :
				xops_texture_type = CharacterTextureType.SYATU;
				break;
			case 24 :
				xops_texture_type = CharacterTextureType.SYATU2;
				break;
			case 25 :
				xops_texture_type = CharacterTextureType.WOMAN;
				break;
			case 26 :
				xops_texture_type = CharacterTextureType.ZOMBIE1;
				break;
			case 27 :
				xops_texture_type = CharacterTextureType.ZOMBIE2;
				break;
			case 28 :
				xops_texture_type = CharacterTextureType.ZOMBIE3;
				break;
			case 29 :
				xops_texture_type = CharacterTextureType.ZOMBIE4;
				break;
			default :
				logger.warn("Unknown texture ID. texture_id={}",
						openxops_texture_id);

				xops_texture_type = CharacterTextureType.CIV1;
				break;
		}

		return xops_texture_type;
	}

	/**
	 * Converts XOPS AI level to OpenXOPS AI level.
	 * 
	 * @param xops_ai_level
	 *            XOPS AI level
	 * @return OpenXOPS AI level
	 */
	public static int GetOpenXOPSAILevelFromXOPSAILevel(
			CharacterAILevel xops_ai_level) {
		int ret = 0;

		switch (xops_ai_level) {
			case NONE :
			case NO_WEAPON :
				ret = 0;
				break;
			case D :
				ret = 0;
				break;
			case C :
				ret = 1;
				break;
			case B :
				ret = 2;
				break;
			case A :
				ret = 3;
				break;
			case S :
			case SS :
				ret = 4;
				break;
		}

		return ret;
	}
	/**
	 * Converts OpenXOPS AI level to XOPS AI level.
	 * 
	 * @param openxops_ai_level
	 *            OpenXOPS AI level
	 * @return XOPS AI level
	 */
	public static CharacterAILevel GetXOPSAILevelFromOpenXOPSAILevel(
			int openxops_ai_level) {
		CharacterAILevel ret = CharacterAILevel.D;

		switch (openxops_ai_level) {
			case 0 :
				ret = CharacterAILevel.D;
				break;
			case 1 :
				ret = CharacterAILevel.C;
				break;
			case 2 :
				ret = CharacterAILevel.B;
				break;
			case 3 :
				ret = CharacterAILevel.A;
				break;
			case 4 :
				ret = CharacterAILevel.S;
				break;
			default :
				ret = CharacterAILevel.D;
				break;
		}

		return ret;
	}
}
