package com.daxie.xops.openxops;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import com.daxie.log.LogFile;
import com.daxie.tool.FileFunctions;
import com.daxie.tool.StringFunctions;
import com.daxie.xops.character.CharacterAILevel;
import com.daxie.xops.character.CharacterData;
import com.daxie.xops.character.CharacterModelType;
import com.daxie.xops.character.CharacterTextureType;
import com.daxie.xops.character.CharacterType;

/**
 * Parses the source code of OpenXOPS and obtains character data.
 * @author Daba
 *
 */
public class CharacterDataCodeParser {
	private Map<Integer, CharacterData> character_data_map;
	
	/**
	 * In case you already have lines of the code.
	 * @param lines Lines of the code
	 */
	public CharacterDataCodeParser(String[] lines) {
		character_data_map=new HashMap<>();
		
		if(lines==null) {
			LogFile.WriteError("[CharacterDataCodeParser-<init>] Null argument.");
			return;
		}
		
		this.ParseLines(lines);
	}
	/**
	 * Parses the code after loading it from a file.
	 * @param code_filename Filename of the code
	 * @param encoding Encoding
	 * @throws FileNotFoundException Specified file not found
	 * @throws UnsupportedEncodingException Specified encoding not supported
	 */
	public CharacterDataCodeParser(String code_filename,String encoding) 
			throws FileNotFoundException,UnsupportedEncodingException{
		character_data_map=new HashMap<>();
		
		String[] lines=FileFunctions.GetFileAllLines(code_filename, encoding);
		
		this.ParseLines(lines);
	}
	
	private void ParseLines(String[] lines) {
		for(int i=0;i<lines.length;i++) {
			String line=lines[i];
			line=line.replace(";", "");
			
			String[] split_by_equal=line.split("=");
			if(split_by_equal.length!=2) {
				LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Syntax error. line:"+i);
				continue;
			}
			
			String left=split_by_equal[0];
			String[] split_by_dot=left.split(Pattern.quote("."));
			
			if(split_by_dot.length!=2) {
				LogFile.WriteWarn("[WeaponDataCodeParser-ParseLines] Syntax error. line:"+i);
				continue;
			}
			
			String field_name=split_by_dot[1];
			field_name=field_name.replace(" ", "");
			
			String array_name_and_index=split_by_dot[0];
			String index_str=StringFunctions.GetFirstStringInBetween(array_name_and_index, '[', ']');
			
			int index=-1;
			try {
				index=Integer.parseInt(index_str);
			}
			catch(NumberFormatException e) {
				LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Invalid index. line:"+i);
				continue;
			}
			if(index<0) {
				LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Index must be a number of 0 or more.");
				continue;
			}
			
			//Create character data if it does not exist in the map.
			if(character_data_map.containsKey(index)==false) {
				CharacterData character_data=new CharacterData();
				character_data_map.put(index, character_data);
			}
			
			CharacterData character_data=character_data_map.get(index);
			String value=split_by_equal[1];
			
			int itemp;
			
			try {
				switch(field_name) {
				case "texture":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					CharacterTextureType texture_type=CharacterSpecifierConverter.GetXOPSTextureTypeFromOpenXOPSTextureID(itemp);
					character_data.SetTextureType(texture_type);
					break;
				case "model":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					CharacterModelType[] model_types=CharacterModelType.values();
					if(!(0<=itemp&&itemp<model_types.length)) {
						LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Value out of bounds. line:"+i);
						continue;
					}
					character_data.SetModelType(model_types[itemp]);
					break;
				case "hp":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					character_data.SetHP(itemp);
					break;
				case "AIlevel":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					CharacterAILevel ai_level=CharacterSpecifierConverter.GetXOPSAILevelFromOpenXOPSAILevel(itemp);
					character_data.SetAILevel(ai_level);
					break;
				case "Weapon[0]":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					character_data.SetWeaponID(0, itemp);
					break;
				case "Weapon[1]":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					character_data.SetWeaponID(1, itemp);
					break;
				case "type":
					value=value.replace(" ","");
					itemp=Integer.parseInt(value);
					CharacterType[] types=CharacterType.values();
					if(!(0<=itemp&&itemp<types.length)) {
						LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Value out of bounds. line:"+i);
						continue;
					}
					character_data.SetType(types[itemp]);
					break;
				default:
					LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Unknown field name. line:"+i);
					continue;
				}
			}
			catch(NumberFormatException e) {
				LogFile.WriteWarn("[CharacterDataCodeParser-ParseLines] Invalid number format. line:"+i);
				continue;
			}
			
			character_data_map.put(index, character_data);
		}
	}
	
	public HashMap<Integer, CharacterData> GetCharacterDataMap(){
		return new HashMap<>(character_data_map);
	}
	public TreeMap<Integer, CharacterData> GetCharacterDataOrderedMap(){
		return new TreeMap<>(character_data_map);
	}
}
