package com.github.dabasan.xops.properties.xms.xcs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;
import com.github.dabasan.xops.properties.XOPSConstants;
import com.github.dabasan.xops.properties.entity.character.CharacterAILevel;
import com.github.dabasan.xops.properties.entity.character.CharacterBinSpecifierAndEnumConverter;
import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.character.CharacterModelType;
import com.github.dabasan.xops.properties.entity.character.CharacterTextureType;
import com.github.dabasan.xops.properties.entity.character.CharacterType;

/**
 * Writes data to a XCS file.
 * @author Daba
 *
 */
class XCSWriter {
	private Logger logger=LoggerFactory.getLogger(XCSWriter.class);
	
	private CharacterData[] character_data_array=null;
	
	public XCSWriter(CharacterData[] character_data_array) {
		this.character_data_array=character_data_array;
	}
	
	public int Write(String xcs_filename){
		if(character_data_array==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		if(character_data_array.length!=XOPSConstants.CHARACTER_NUM) {
			logger.warn("Invalid number of data. data_num={}",character_data_array.length);
			return -1;
		}
		
		List<Byte> bin=new ArrayList<>();
		
		bin.add((byte)0x58);
		bin.add((byte)0x43);
		bin.add((byte)0x53);
		
		bin.add((byte)0x00);
		bin.add((byte)0x01);
		bin.add((byte)0x00);
		bin.add((byte)0x0C);
		bin.add((byte)0x00);
		bin.add((byte)0x2B);
		bin.add((byte)0x00);
		bin.add((byte)0x07);
		bin.add((byte)0x00);
		
		for(int i=0;i<XOPSConstants.CHARACTER_NUM;i++) {
			int itemp;
			
			//Texture
			CharacterTextureType texture_type=character_data_array[i].GetTextureType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterTextureType(texture_type);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Model
			CharacterModelType model_type=character_data_array[i].GetModelType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterModelType(model_type);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//HP
			itemp=character_data_array[i].GetHP();
			ByteFunctions.AddUShortValueToBin_LE(bin, itemp);
			
			//AI level
			CharacterAILevel ai_level=character_data_array[i].GetAILevel();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterAILevel(ai_level);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Weapon A
			itemp=character_data_array[i].GetWeaponID(0);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Weapon B
			itemp=character_data_array[i].GetWeaponID(1);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Type
			CharacterType type=character_data_array[i].GetType();
			itemp=CharacterBinSpecifierAndEnumConverter.GetBinSpecifierFromCharacterType(type);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		}
		
		try {
			FileFunctions.CreateBinFile(xcs_filename, bin);
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
}
