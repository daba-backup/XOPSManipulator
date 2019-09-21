package com.daxie.xops.xml;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.character.CharacterAILevel;
import com.daxie.xops.character.CharacterData;
import com.daxie.xops.character.CharacterModelType;
import com.daxie.xops.character.CharacterTextureType;
import com.daxie.xops.character.CharacterType;
import com.daxie.xops.openxops.CharacterSpecifierConverter;

/**
 * Loads character data from a XML file.
 * @author Daba
 *
 */
public class CharacterDataXMLParser {
	private Map<Integer, CharacterData> character_data_map;
	private boolean openxops_compatible_flag;
	
	public CharacterDataXMLParser() {
		character_data_map=new HashMap<>();
		openxops_compatible_flag=true;
	}
	
	public void SetOpenXOPSCompatibleFlag(boolean openxops_compatible_flag) {
		this.openxops_compatible_flag=openxops_compatible_flag;
	}
	
	/**
	 * Loads character data from a XML file.
	 * @param xml_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int LoadCharacterDataXML(String xml_filename) {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		Document document=null;
		try {
			document=builder.parse(Paths.get(xml_filename).toFile());
		}
		catch(Exception e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		Element el_character_data_list=document.getDocumentElement();
		NodeList node_list=el_character_data_list.getChildNodes();
		
		for(int i=0;i<node_list.getLength();i++) {
			Node node=node_list.item(i);
			if(node.getNodeType()!=Node.ELEMENT_NODE)continue;
			
			Element element=(Element)node;
			String element_name=element.getNodeName();
			
			if(element_name.equals("character")) {
				int character_id=-1;
				CharacterData character_data=new CharacterData();
				
				String strtemp;
				int itemp;
				
				strtemp=element.getAttribute("id");
				try {
					character_id=Integer.parseInt(strtemp);
				}
				catch(NumberFormatException e) {
					LogFile.WriteWarn("[CharacterDataXMLParser] Invalid format of number. id:"+strtemp,true);
					continue;
				}
				
				NodeList node_list_sub=element.getChildNodes();
				for(int j=0;j<node_list_sub.getLength();j++) {
					Node node_sub=node_list_sub.item(j);
					if(node_sub.getNodeType()!=Node.ELEMENT_NODE)continue;
					
					Element element_sub=(Element)node_sub;
					String element_sub_name=element_sub.getNodeName();
					
					strtemp=element_sub.getTextContent();
					
					//Texture
					if(element_sub_name.equals("texture")) {
						itemp=Integer.parseInt(strtemp);
						
						if(openxops_compatible_flag==true) {
							CharacterTextureType texture_type;
							texture_type=CharacterSpecifierConverter.GetXOPSTextureTypeFromOpenXOPSTextureID(itemp);
							character_data.SetTextureType(texture_type);
						}
						else {
							CharacterTextureType[] values=CharacterTextureType.values();
							if(!(0<=itemp&&itemp<values.length)) {
								LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Specifier out of bounds.",true);
								
								String str="";
								str+="character_id"+character_id+" ";
								str+="texture:"+itemp;
								
								LogFile.WriteLine(str);
								
								character_data.SetTextureType(CharacterTextureType.SOLDIER_BLACK);
							}
							else {
								character_data.SetTextureType(values[itemp]);
							}
						}
					}
					//Model
					else if(element_sub_name.equals("model")) {
						itemp=Integer.parseInt(strtemp);
						
						CharacterModelType[] values=CharacterModelType.values();
						if(!(0<=itemp&&itemp<values.length)) {
							LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Specifier out of bounds.",true);
							
							String str="";
							str+="character_id:"+character_id+" ";
							str+="model:"+itemp;
							
							LogFile.WriteLine(str);
							
							character_data.SetModelType(CharacterModelType.MALE);
						}
						else character_data.SetModelType(values[itemp]);
					}
					//HP
					else if(element_sub_name.equals("hp")) {
						itemp=Integer.parseInt(strtemp);
						character_data.SetHP(itemp);
					}
					//AI level
					else if(element_sub_name.equals("AIlevel")) {
						itemp=Integer.parseInt(strtemp);
						
						if(openxops_compatible_flag==true) {
							CharacterAILevel ai_level=CharacterSpecifierConverter.GetXOPSAILevelFromOpenXOPSAILevel(itemp);
							character_data.SetAILevel(ai_level);
						}
						else {
							CharacterAILevel[] values=CharacterAILevel.values();
							if(!(0<=itemp&&itemp<values.length)) {
								LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Specifier out of bounds.",true);
								
								String str="";
								str+="character_id:"+character_id+" ";
								str+="ai_level:"+itemp;
								
								LogFile.WriteLine(str);
								
								character_data.SetAILevel(CharacterAILevel.C);
							}
							else character_data.SetAILevel(values[itemp]);
						}
					}
					//Weapon A
					else if(element_sub_name.equals("WeaponA")) {
						itemp=Integer.parseInt(strtemp);
						character_data.SetWeaponID(0, itemp);
					}
					//Weapon B
					else if(element_sub_name.equals("WeaponB")) {
						itemp=Integer.parseInt(strtemp);
						character_data.SetWeaponID(1, itemp);
					}
					//Type
					else if(element_sub_name.equals("type")) {
						itemp=Integer.parseInt(strtemp);
						
						CharacterType[] values=CharacterType.values();
						if(!(0<=itemp&&itemp<values.length)) {
							LogFile.WriteWarn("[CharacterDataXMLParser-LoadCharacterDataXML] Specifier out of bounds.",true);
							
							String str="";
							str+="character_id:"+character_id+" ";
							str+="type:"+itemp;
							
							LogFile.WriteLine(str);
							
							character_data.SetType(CharacterType.HUMAN);
						}
						else character_data.SetType(values[itemp]);
					}
				}
				
				character_data_map.put(character_id, character_data);
			}
		}
		
		return 0;
	}
	
	public Map<Integer, CharacterData> GetCharacterDataMap(){
		return new HashMap<>(character_data_map);
	}
}
