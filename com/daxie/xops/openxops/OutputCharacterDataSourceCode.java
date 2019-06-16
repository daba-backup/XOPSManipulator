package com.daxie.xops.openxops;

import java.util.List;

import com.daxie.log.LogFile;
import com.daxie.tool.StringFunctions;
import com.daxie.xops.character.CharacterAILevel;
import com.daxie.xops.character.CharacterData;
import com.daxie.xops.character.CharacterTextureType;

/**
 * Writes out character data formatted for the source code of OpenXOPS.
 * @author Daba
 *
 */
public class OutputCharacterDataSourceCode {
	private List<CharacterData> character_data_list;
	
	public OutputCharacterDataSourceCode(List<CharacterData> character_data_list) {
		this.character_data_list=character_data_list;
	}
	
	public String GetCharacterDataSourceCode() {
		String ret="";
		
		if(character_data_list==null) {
			LogFile.WriteError("[OutputCharacterDataSourceCode-GetCharacterDataSourceCode] Data is null.");
			return ret;
		}
		
		final String array_name="Human";
		final String separator=System.getProperty("line.separator");
		
		for(int i=0;i<character_data_list.size();i++) {
			CharacterData character_data=character_data_list.get(i);
			
			CharacterTextureType xops_texture_type=character_data.GetTextureType();
			CharacterAILevel xops_ai_level=character_data.GetAILevel();
			int openxops_texture_id=CharacterSpecifierConverter.GetOpenXOPSTextureIDFromXOPSTextureType(xops_texture_type);
			int openxops_ai_level=CharacterSpecifierConverter.GetOpenXOPSAILevelFromXOPSAILevel(xops_ai_level);
			
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "texture", openxops_texture_id)+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "model", character_data.GetModelType().ordinal())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "hp", character_data.GetHP())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "AIlevel", openxops_ai_level)+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "Weapon[0]", character_data.GetWeaponID(0))+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "Weapon[1]", character_data.GetWeaponID(1))+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "type", character_data.GetType().ordinal())+separator;
		}
		
		return ret;
	}
}
