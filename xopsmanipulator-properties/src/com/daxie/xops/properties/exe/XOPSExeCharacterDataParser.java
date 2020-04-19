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
			
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			texture_type=CharacterBinSpecifierAndEnumConverter.GetCharacterTextureTypeFromBinSpecifier(itemp);
			character_data_array[i].SetTextureType(texture_type);
			
			//Model
			CharacterModelType model_type;
			
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			model_type=CharacterBinSpecifierAndEnumConverter.GetCharacterModelTypeFromBinSpecifier(itemp);
			character_data_array[i].SetModelType(model_type);
			
			//HP
			itemp=this.GetUShortFromBin(bin, pos);
			pos+=2;
			character_data_array[i].SetHP(itemp);
			
			//AI level
			CharacterAILevel ai_level;
			
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			ai_level=CharacterBinSpecifierAndEnumConverter.GetCharacterAILevelFromBinSpecifier(itemp);
			character_data_array[i].SetAILevel(ai_level);
			
			//Weapon A
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			character_data_array[i].SetWeaponID(0, itemp);
			
			//Weapon B
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			character_data_array[i].SetWeaponID(1, itemp);
			
			//Type
			CharacterType type;
			
			itemp=this.GetShortFromBin(bin, pos);
			pos+=2;
			type=CharacterBinSpecifierAndEnumConverter.GetCharacterTypeFromBinSpecifier(itemp);
			character_data_array[i].SetType(type);
		}
	}
	private int GetShortFromBin(List<Byte> bin,int pos) {
		byte[] buffer=new byte[2];
		buffer[0]=bin.get(pos);
		buffer[1]=bin.get(pos+1);
		
		int ret=ByteFunctions.byte_to_short_le(buffer);
		return ret;
	}
	private int GetUShortFromBin(List<Byte> bin,int pos) {
		byte[] buffer=new byte[2];
		buffer[0]=bin.get(pos);
		buffer[1]=bin.get(pos+1);
		
		int ret=ByteFunctions.byte_to_ushort_le(buffer);
		return ret;
	}
	
	public CharacterData[] GetCharacterDataArray() {
		CharacterData[] ret=new CharacterData[character_data_array.length];
		for(int i=0;i<ret.length;i++) {
			ret[i]=new CharacterData(character_data_array[i]);
		}
		
		return ret;
	}
}
