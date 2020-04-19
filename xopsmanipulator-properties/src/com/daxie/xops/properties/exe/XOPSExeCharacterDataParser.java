package com.daxie.xops.properties.exe;

import java.util.List;

import com.daxie.tool.ByteFunctions;
import com.daxie.xops.properties.XOPSConstants;
import com.daxie.xops.properties.entity.character.CharacterAILevel;
import com.daxie.xops.properties.entity.character.CharacterBinSpecifierAndEnumConverter;
import com.daxie.xops.properties.entity.character.CharacterData;
import com.daxie.xops.properties.entity.character.CharacterModelType;
import com.daxie.xops.properties.entity.character.CharacterTextureType;
import com.daxie.xops.properties.entity.character.CharacterType;

/**
 * Reads data from an EXE file.
 * @author Daba
 *
 */
class XOPSExeCharacterDataParser {
	private CharacterData[] character_data_array;
	
	public XOPSExeCharacterDataParser(List<Byte> bin,int character_data_start_pos) {
		character_data_array=new CharacterData[XOPSConstants.CHARACTER_NUM];
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			character_data_array[i]=new CharacterData();
		}
		
		int pos=character_data_start_pos;
		
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			int itemp;
			
			//Texture
			CharacterTextureType texture_type;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			texture_type=CharacterBinSpecifierAndEnumConverter.GetCharacterTextureTypeFromBinSpecifier(itemp);
			character_data_array[i].SetTextureType(texture_type);
			pos+=2;
			
			//Model
			CharacterModelType model_type;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			model_type=CharacterBinSpecifierAndEnumConverter.GetCharacterModelTypeFromBinSpecifier(itemp);
			character_data_array[i].SetModelType(model_type);
			pos+=2;
			
			//HP
			itemp=ByteFunctions.GetUShortValueFromBin_LE(bin, pos);
			character_data_array[i].SetHP(itemp);
			pos+=2;
			
			//AI level
			CharacterAILevel ai_level;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			ai_level=CharacterBinSpecifierAndEnumConverter.GetCharacterAILevelFromBinSpecifier(itemp);
			character_data_array[i].SetAILevel(ai_level);
			pos+=2;
			
			//Weapon A
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			character_data_array[i].SetWeaponID(0, itemp);
			pos+=2;
			
			//Weapon B
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			character_data_array[i].SetWeaponID(1, itemp);
			pos+=2;
			
			//Type
			CharacterType type;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			type=CharacterBinSpecifierAndEnumConverter.GetCharacterTypeFromBinSpecifier(itemp);
			character_data_array[i].SetType(type);
			pos+=2;
		}
	}
	
	public CharacterData[] GetCharacterDataArray() {
		CharacterData[] ret=new CharacterData[character_data_array.length];
		for(int i=0;i<ret.length;i++) {
			ret[i]=new CharacterData(character_data_array[i]);
		}
		
		return ret;
	}
}
