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
}
