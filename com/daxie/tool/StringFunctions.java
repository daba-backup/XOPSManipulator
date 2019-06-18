package com.daxie.tool;

/**
 * Offers methods to handle strings.
 * @author Daba
 *
 */
public class StringFunctions {
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,int value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+=value;
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,float value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+=value;
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,boolean value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		
		if(value==false)ret+="false";
		else ret+=true;
		
		ret+=";";
		
		return ret;
	}
	/**
	 * Generates a string that represents assignment of a value to a field of an arrayed class.
	 * @param array_name Name of the array
	 * @param index Index of the instance
	 * @param field_name Name of the field
	 * @param value Value to be assigned
	 * @return C++ code
	 */
	public static String GetCPPArrayFormatString(String array_name,int index,String field_name,String value) {
		String ret="";
		
		ret+=array_name;
		ret+="[";
		ret+=index;
		ret+="].";
		ret+=field_name;
		ret+="=";
		ret+="\"";
		ret+=value;
		ret+="\"";
		ret+=";";
		
		return ret;
	}
	
	/**
	 * Returns the first non-space character.
	 * @param str A string
	 * @return First non-space character
	 */
	public static char GetFirstNonSpaceCharacter(String str) {
		char ret=0;
		
		for(int i=0;i<str.length();i++) {
			if(str.charAt(i)!=' ') {
				ret=str.charAt(i);
				break;
			}
		}
		
		return ret;
	}
	
	/**
	 * Removes double quotation marks from a string enclosed with them.
	 * @param str A string
	 * @return A string without double quotation marks
	 */
	public static String RemoveDQMs(String str) {
		int first_dqm_pos=str.indexOf('\"');
		int last_dqm_pos=str.indexOf('\"');
		
		if(first_dqm_pos<0)return str;
		else if(first_dqm_pos==last_dqm_pos)return str;
		
		return str.substring(first_dqm_pos+1, last_dqm_pos);
	}
}
