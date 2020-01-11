package com.daxie.xops.properties.xms.xcs;

import java.io.FileNotFoundException;
import java.util.List;

import com.daxie.log.LogFile;
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
	private CharacterData[] character_data_array=null;
	
	public XCSParser(String xcs_filename) throws FileNotFoundException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xcs_filename);
		
		if(bin.size()!=614) {
			LogFile.WriteWarn("[XCSParser-<init>] Invalid file size. filename:"+xcs_filename,true);
			return;
		}
		
		byte[] b=new byte[2];
		int itemp;
		
		int count=0x0000000C;
		
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			//Texture
			CharacterTextureType texture_type;
			
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			count+=2;
			
			texture_type=CharacterBinSpecifierAndEnumConverter.GetCharacterTextureTypeFromBinSpecifier(itemp);
			character_data_array[i].SetTextureType(texture_type);
			
			//Model
			CharacterModelType model_type;
			
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			count+=2;
			
			model_type=CharacterBinSpecifierAndEnumConverter.GetCharacterModelTypeFromBinSpecifier(itemp);
			character_data_array[i].SetModelType(model_type);
			
			//HP
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_ushort_le(b);
			character_data_array[i].SetHP(itemp);
			count+=2;
			
			//AI level
			CharacterAILevel ai_level;
			
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			count+=2;
			
			ai_level=CharacterBinSpecifierAndEnumConverter.GetCharacterAILevelFromBinSpecifier(itemp);
			character_data_array[i].SetAILevel(ai_level);
			
			//Weapon A
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			character_data_array[i].SetWeaponID(0, itemp);
			count+=2;
			
			//Weapon B
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			character_data_array[i].SetWeaponID(1, itemp);
			count+=2;
			
			//Type
			CharacterType type;
			
			b[0]=bin.get(count);
			b[1]=bin.get(count+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			count+=2;
			
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
