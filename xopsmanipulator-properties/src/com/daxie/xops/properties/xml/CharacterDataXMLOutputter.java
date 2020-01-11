package com.daxie.xops.properties.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.XMLFunctions;
import com.daxie.xops.properties.entity.character.CharacterAILevel;
import com.daxie.xops.properties.entity.character.CharacterData;
import com.daxie.xops.properties.entity.character.CharacterTextureType;
import com.daxie.xops.properties.openxops.CharacterSpecifierConverter;

/**
 * Writes out character data in a XML file.
 * @author Daba
 *
 */
public class CharacterDataXMLOutputter {
	private List<CharacterData> character_data_list;
	private boolean openxops_compatible_flag;
	
	/**
	 * @param character_data_list List of character data
	 */
	public CharacterDataXMLOutputter(List<CharacterData> character_data_list) {
		this.character_data_list=character_data_list;
		openxops_compatible_flag=true;
	}
	
	public void SetOpenXOPSCompatibleFlag(boolean openxops_compatible_flag) {
		this.openxops_compatible_flag=openxops_compatible_flag;
	}
	
	/**
	 * Outputs character data in a XML file.
	 * @param xml_filename XML filename
	 * @return -1 on error and 0 on success
	 */
	public int WriteXML(String xml_filename) {
		if(character_data_list==null) {
			LogFile.WriteWarn("[CharacterDataXMLOutputter-WriteXML] Data is null.",true);
			return -1;
		}
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[CharacterDataXMLOutputter-WriteXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		Document document=builder.newDocument();
		
		Element el_character_list=document.createElement("character_list");
		document.appendChild(el_character_list);
		
		int id=0;
		for(CharacterData character_data:character_data_list) {
			Element el_character=document.createElement("character");
			el_character.setAttribute("id", ""+id);
			
			//Texture
			CharacterTextureType texture_type=character_data.GetTextureType();
			int texture_type_specifier;
			if(openxops_compatible_flag==true) {
				texture_type_specifier=CharacterSpecifierConverter.GetOpenXOPSTextureIDFromXOPSTextureType(texture_type);
			}
			else {
				texture_type_specifier=texture_type.ordinal();
			}
			Element el_texture_type=document.createElement("texture");
			el_texture_type.setTextContent(""+texture_type_specifier);
			el_character.appendChild(el_texture_type);
			//Model
			Element el_model_type=document.createElement("model");
			el_model_type.setTextContent(""+character_data.GetModelType().ordinal());
			el_character.appendChild(el_model_type);
			//HP
			Element el_hp=document.createElement("hp");
			el_hp.setTextContent(""+character_data.GetHP());
			el_character.appendChild(el_hp);
			//AI level
			CharacterAILevel ai_level=character_data.GetAILevel();
			int ai_level_specifier;
			if(openxops_compatible_flag==true) {
				ai_level_specifier=CharacterSpecifierConverter.GetOpenXOPSAILevelFromXOPSAILevel(ai_level);
			}
			else {
				ai_level_specifier=ai_level.ordinal();
			}
			Element el_ai_level=document.createElement("AIlevel");
			el_ai_level.setTextContent(""+ai_level_specifier);
			el_character.appendChild(el_ai_level);
			//Weapon A
			Element el_weapon_a=document.createElement("WeaponA");
			el_weapon_a.setTextContent(""+character_data.GetWeaponID(0));
			el_character.appendChild(el_weapon_a);
			//Weapon B
			Element el_weapon_b=document.createElement("WeaponB");
			el_weapon_b.setTextContent(""+character_data.GetWeaponID(1));
			el_character.appendChild(el_weapon_b);
			//Type
			Element el_type=document.createElement("type");
			el_type.setTextContent(""+character_data.GetType().ordinal());
			el_character.appendChild(el_type);
			
			el_character_list.appendChild(el_character);
			
			id++;
		}
		
		File file=new File(xml_filename);
		
		int res=XMLFunctions.WriteXML(file, document);
		if(res<0)return -1;
		
		return 0;
	}
}
