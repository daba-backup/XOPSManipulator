package com.github.dabasan.xops.properties.entity.character;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CharacterBinSpecifierAndEnumConverter {
	private static Logger logger = LoggerFactory
			.getLogger(CharacterBinSpecifierAndEnumConverter.class);

	public static CharacterTextureType GetCharacterTextureTypeFromBinSpecifier(
			int spc) {
		CharacterTextureType texture_type;

		switch (spc) {
			case 0x00 :
				texture_type = CharacterTextureType.SOLDIER_BLACK;
				break;
			case 0x01 :
				texture_type = CharacterTextureType.SOLDIER_GREEN;
				break;
			case 0x02 :
				texture_type = CharacterTextureType.SOLDIER_WHITE;
				break;
			case 0x03 :
				texture_type = CharacterTextureType.HAGE;
				break;
			case 0x04 :
				texture_type = CharacterTextureType.SOLDIER_VIOLET;
				break;
			case 0x05 :
				texture_type = CharacterTextureType.SOLDIER_BLUE;
				break;
			case 0x06 :
				texture_type = CharacterTextureType.ROBOT;
				break;
			case 0x07 :
				texture_type = CharacterTextureType.GS;
				break;
			case 0x08 :
				texture_type = CharacterTextureType.SOLDIER0;
				break;
			case 0x09 :
				texture_type = CharacterTextureType.POLICE;
				break;
			case 0x0A :
				texture_type = CharacterTextureType.RIIMAN;
				break;
			case 0x0B :
				texture_type = CharacterTextureType.SYATU;
				break;
			case 0x0C :
				texture_type = CharacterTextureType.ISLAM;
				break;
			case 0x0D :
				texture_type = CharacterTextureType.WOMAN;
				break;
			case 0x0E :
				texture_type = CharacterTextureType.CIV1;
				break;
			case 0x0F :
				texture_type = CharacterTextureType.CIV2;
				break;
			case 0x10 :
				texture_type = CharacterTextureType.CIV3;
				break;
			case 0x11 :
				texture_type = CharacterTextureType.SOLDIER1;
				break;
			case 0x12 :
				texture_type = CharacterTextureType.SOLDIER2;
				break;
			case 0x13 :
				texture_type = CharacterTextureType.ZOMBIE1;
				break;
			case 0x14 :
				texture_type = CharacterTextureType.ZOMBIE2;
				break;
			case 0x15 :
				texture_type = CharacterTextureType.ZOMBIE3;
				break;
			case 0x16 :
				texture_type = CharacterTextureType.ZOMBIE4;
				break;
			case 0x17 :
				texture_type = CharacterTextureType.RIIMAN_G;
				break;
			case 0x18 :
				texture_type = CharacterTextureType.RIIMAN_K;
				break;
			case 0x19 :
				texture_type = CharacterTextureType.ISLAM2;
				break;
			case 0x1A :
				texture_type = CharacterTextureType.RIIMAN_B;
				break;
			case 0x1B :
				texture_type = CharacterTextureType.SYATU2;
				break;
			case 0x1C :
				texture_type = CharacterTextureType.SOLDIER3;
				break;
			case 0x1D :
				texture_type = CharacterTextureType.GATES;
				break;
			default :
				logger.warn("Unknown texture type specifier. specifier={}",
						spc);

				texture_type = CharacterTextureType.SOLDIER_BLACK;
				break;
		}

		return texture_type;
	}
	public static int GetBinSpecifierFromCharacterTextureType(
			CharacterTextureType texture_type) {
		final int spc = texture_type.ordinal();
		return spc;
	}
	public static CharacterModelType GetCharacterModelTypeFromBinSpecifier(
			int spc) {
		CharacterModelType model_type;

		switch (spc) {
			case 0x00 :
				model_type = CharacterModelType.MALE;
				break;
			case 0x1F :
				model_type = CharacterModelType.SUN_GLASSES;
				break;
			case 0x21 :
				model_type = CharacterModelType.POLICEMAN;
				break;
			case 0x23 :
				model_type = CharacterModelType.FEMALE;
				break;
			case 0x24 :
				model_type = CharacterModelType.BACK_PACK;
				break;
			case 0x26 :
				model_type = CharacterModelType.HELMET;
				break;
			default :
				logger.warn("Unknown model type specifier. specifier={}", spc);

				model_type = CharacterModelType.MALE;
				break;
		}

		return model_type;
	}
	public static int GetBinSpecifierFromCharacterModelType(
			CharacterModelType model_type) {
		int spc = 0x00;

		switch (model_type) {
			case MALE :
				spc = 0x00;
				break;
			case SUN_GLASSES :
				spc = 0x1F;
				break;
			case POLICEMAN :
				spc = 0x21;
				break;
			case FEMALE :
				spc = 0x23;
				break;
			case BACK_PACK :
				spc = 0x24;
				break;
			case HELMET :
				spc = 0x26;
				break;
		}

		return spc;
	}
	public static CharacterAILevel GetCharacterAILevelFromBinSpecifier(
			int spc) {
		CharacterAILevel ai_level;

		switch (spc) {
			case 0x00 :
				ai_level = CharacterAILevel.NONE;
				break;
			case 0x01 :
				ai_level = CharacterAILevel.D;
				break;
			case 0x02 :
				ai_level = CharacterAILevel.C;
				break;
			case 0x03 :
				ai_level = CharacterAILevel.B;
				break;
			case 0x04 :
				ai_level = CharacterAILevel.A;
				break;
			case 0x05 :
				ai_level = CharacterAILevel.S;
				break;
			case 0x06 :
				ai_level = CharacterAILevel.SS;
				break;
			case 0x07 :
				ai_level = CharacterAILevel.NO_WEAPON;
				break;
			default :
				logger.warn("Unknown AI level specifier. specifier={}", spc);

				ai_level = CharacterAILevel.D;
				break;
		}

		return ai_level;
	}
	public static int GetBinSpecifierFromCharacterAILevel(
			CharacterAILevel ai_level) {
		final int spc = ai_level.ordinal();
		return spc;
	}
	public static CharacterType GetCharacterTypeFromBinSpecifier(int spc) {
		CharacterType type;

		switch (spc) {
			case 0x00 :
				type = CharacterType.HUMAN;
				break;
			case 0x01 :
				type = CharacterType.CYBORG;
				break;
			case 0x02 :
				type = CharacterType.ZOMBIE;
				break;
			default :
				logger.warn("Unknown type specifier. specifier={}", spc);

				type = CharacterType.HUMAN;
				break;
		}

		return type;
	}
	public static int GetBinSpecifierFromCharacterType(CharacterType type) {
		final int spc = type.ordinal();
		return spc;
	}
}
