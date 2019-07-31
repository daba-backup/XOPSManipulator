package com.daxie.xops.exe;

import java.util.List;

import com.daxie.tool.ByteFunctions;
import com.daxie.xops.XOPSConstants;
import com.daxie.xops.character.CharacterAILevel;
import com.daxie.xops.character.CharacterBinSpecifierAndEnumConverter;
import com.daxie.xops.character.CharacterData;
import com.daxie.xops.character.CharacterModelType;
import com.daxie.xops.character.CharacterTextureType;
import com.daxie.xops.character.CharacterType;

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
			byte[] b=new byte[2];
			int itemp;
			
			//Texture
			CharacterTextureType texture_type;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			texture_type=CharacterBinSpecifierAndEnumConverter.GetCharacterTextureTypeFromBinSpecifier(itemp);
			character_data_array[i].SetTextureType(texture_type);
			
			//Model
			CharacterModelType model_type;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			model_type=CharacterBinSpecifierAndEnumConverter.GetCharacterModelTypeFromBinSpecifier(itemp);
			character_data_array[i].SetModelType(model_type);
			
			//HP
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_ushort_le(b);
			character_data_array[i].SetHP(itemp);
			pos+=2;
			
			//AI level
			CharacterAILevel ai_level;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			ai_level=CharacterBinSpecifierAndEnumConverter.GetCharacterAILevelFromBinSpecifier(itemp);
			character_data_array[i].SetAILevel(ai_level);
			
			//Weapon A
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			character_data_array[i].SetWeaponID(0, itemp);
			pos+=2;
			
			//Weapon B
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			character_data_array[i].SetWeaponID(1, itemp);
			pos+=2;
			
			//Type
			CharacterType type;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			type=CharacterBinSpecifierAndEnumConverter.GetCharacterTypeFromBinSpecifier(itemp);
			character_data_array[i].SetType(type);
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
