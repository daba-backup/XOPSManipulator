package com.daxie.xops.xcs;

import java.io.FileNotFoundException;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.character.CharacterData;

/**
 * Manipulates a XCS file.
 * @author Daba
 *
 */
public class XCSManipulator {
	private CharacterData[] character_data_array=null;
	
	/**
	 * 
	 * @param xcs_filename XCS filename to load
	 * @throws FileNotFoundException XCS file not found
	 */
	public XCSManipulator(String xcs_filename) throws FileNotFoundException{
		XCSParser xcs_parser=new XCSParser(xcs_filename);
		character_data_array=xcs_parser.GetCharacterDataArray();
	}
	public XCSManipulator() {
		
	}
	
	/**
	 * Returns a character data array.
	 * @return A character data array
	 */
	public CharacterData[] GetCharacterDataArray() {
		if(character_data_array==null)return null;
		
		CharacterData[] ret=new CharacterData[character_data_array.length];
		for(int i=0;i<ret.length;i++) {
			ret[i]=new CharacterData(character_data_array[i]);
		}
		
		return ret;
	}
	/**
	 * Sets a character data array.
	 * @param character_data_array A character data array
	 */
	public void SetCharacterDataArray(CharacterData[] character_data_array) {
		this.character_data_array=character_data_array;
	}
	
	/**
	 * Writes out data to a XCS file.
	 * @param xcs_filename Filename
	 */
	public void Write(String xcs_filename) {
		XCSWriter xcs_writer=new XCSWriter(character_data_array);
		try {
			xcs_writer.Write(xcs_filename);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteFatal("[XCSManipulator-Write] Failed to write data. Below is the stack trace.");
			
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteLine(str);
			
			System.exit(1);
		}
	}
}
