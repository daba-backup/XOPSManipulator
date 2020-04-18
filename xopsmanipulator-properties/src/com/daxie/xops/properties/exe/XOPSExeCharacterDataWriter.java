package com.daxie.xops.properties.exe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.log.LogWriter;
import com.daxie.tool.ByteFunctions;
import com.daxie.xops.properties.XOPSConstants;
import com.daxie.xops.properties.entity.character.CharacterAILevel;
import com.daxie.xops.properties.entity.character.CharacterBinSpecifierAndEnumConverter;
import com.daxie.xops.properties.entity.character.CharacterData;
import com.daxie.xops.properties.entity.character.CharacterModelType;
import com.daxie.xops.properties.entity.character.CharacterTextureType;
import com.daxie.xops.properties.entity.character.CharacterType;

/**
 * Writes data to an EXE file.
 * @author Daba
 *
 */
class XOPSExeCharacterDataWriter {
	private Logger logger=LoggerFactory.getLogger(XOPSExeCharacterDataWriter.class);
	
	private CharacterData[] character_data_array;
	
	public XOPSExeCharacterDataWriter(CharacterData[] character_data_array) {
		this.character_data_array=character_data_array;
	}
	
	public void Write(List<Byte> bin,int character_data_start_pos) {
		if(character_data_array==null) {
			logger.warn("Data not prepared.");
			return;
		}
		if(character_data_array.length!=XOPSConstants.CHARACTER_NUM) {
			logger.warn("Invalid number of data. data_num={}",character_data_array.length);
			return;
		}
		
		int pos=character_data_start_pos;
		
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			int itemp;
			byte[] b;
			
			//Texture
			CharacterTextureType texture_type=character_data_array[i].GetTextureType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterTextureType(texture_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Model
			CharacterModelType model_type=character_data_array[i].GetModelType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterModelType(model_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//HP
			itemp=character_data_array[i].GetHP();
			b=ByteFunctions.ushort_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//AI level
			CharacterAILevel ai_level=character_data_array[i].GetAILevel();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterAILevel(ai_level);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Weapon A
			itemp=character_data_array[i].GetWeaponID(0);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Weapon B
			itemp=character_data_array[i].GetWeaponID(1);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Type
			CharacterType type=character_data_array[i].GetType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterType(type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
		}
	}
}
