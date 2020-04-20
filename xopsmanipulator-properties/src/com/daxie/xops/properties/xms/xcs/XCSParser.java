package com.daxie.xops.properties.xms.xcs;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.xops.properties.XOPSConstants;
import com.daxie.xops.properties.entity.character.CharacterAILevel;
import com.daxie.xops.properties.entity.character.CharacterBinSpecifierAndEnumConverter;
import com.daxie.xops.properties.entity.character.CharacterData;
import com.daxie.xops.properties.entity.character.CharacterModelType;
import com.daxie.xops.properties.entity.character.CharacterTextureType;
import com.daxie.xops.properties.entity.character.CharacterType;

/**
 * Reads data from a XCS file.
 * @author Daba
 *
 */
class XCSParser {
	private Logger logger=LoggerFactory.getLogger(XCSParser.class);
	
	private CharacterData[] character_data_array;
	
	public XCSParser(String xcs_filename) throws IOException{
		character_data_array=new CharacterData[XOPSConstants.CHARACTER_NUM];
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			character_data_array[i]=new CharacterData();
		}
		
		List<Byte> bin=FileFunctions.GetFileAllBin(xcs_filename);
		
		if(bin.size()!=614) {
			logger.warn("Invalid file size. xcs_filename={}",xcs_filename);
			return;
		}
		
		int itemp;
		
		int pos=0x0000000C;
		
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			//Texture
			CharacterTextureType texture_type;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			
			texture_type=CharacterBinSpecifierAndEnumConverter.GetCharacterTextureTypeFromBinSpecifier(itemp);
			character_data_array[i].SetTextureType(texture_type);
			
			//Model
			CharacterModelType model_type;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			
			model_type=CharacterBinSpecifierAndEnumConverter.GetCharacterModelTypeFromBinSpecifier(itemp);
			character_data_array[i].SetModelType(model_type);
			
			//HP
			itemp=ByteFunctions.GetUShortValueFromBin_LE(bin, pos);
			character_data_array[i].SetHP(itemp);
			pos+=2;
			
			//AI level
			CharacterAILevel ai_level;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			
			ai_level=CharacterBinSpecifierAndEnumConverter.GetCharacterAILevelFromBinSpecifier(itemp);
			character_data_array[i].SetAILevel(ai_level);
			
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
			pos+=2;
			
			type=CharacterBinSpecifierAndEnumConverter.GetCharacterTypeFromBinSpecifier(itemp);
			character_data_array[i].SetType(type);
		}
	}
	
	public CharacterData[] GetCharacterDataArray() {
		CharacterData[] ret=new CharacterData[character_data_array.length];
		
		for(int i=0;i<character_data_array.length;i++) {
			ret[i]=new CharacterData(character_data_array[i]);
		}
		
		return ret;
	}
}
