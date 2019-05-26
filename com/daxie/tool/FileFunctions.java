package com.daxie.tool;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

/**
 * Methods to handle files.
 * @author Daba
 *
 */
public class FileFunctions {
	/**
	 * Get all lines from a text file.
	 * @param filename Filename to load
	 * @param encoding Encoding of the file
	 * @return An array of strings that are split per line
	 * @throws FileNotFoundException Specified file not found
	 * @throws UnsupportedEncodingException Specified encoding not supported
	 */
	public static String[] GetFileAllLines(String filename,String encoding) throws FileNotFoundException,UnsupportedEncodingException{
		StringBuilder sb=new StringBuilder("");
		
		BufferedReader br=null;
		String line;
		
		br=new BufferedReader(
				new InputStreamReader(
						new FileInputStream(filename),encoding));
		
		try {	
			while(true) {
				line=br.readLine();
				if(line==null)break;
				
				sb.append(line);
				sb.append("\n");
			}
		}
		catch(IOException e) {
			e.printStackTrace();
		}
		finally {
			try {
				if(br!=null) {
					br.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		return sb.toString().split("\n");
	}
}
