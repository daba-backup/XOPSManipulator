package com.daxie.xops.xgs;

import java.io.FileNotFoundException;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.weapon.WeaponData;

/**
 * Manipulates a XGS file.
 * @author Daba
 *
 */
public class XGSManipulator {
	private WeaponData[] weapon_data_array=null;
	
	/**
	 * 
	 * @param xgs_filename XGS filename to load
	 * @throws FileNotFoundException XGS file not found
	 */
	public XGSManipulator(String xgs_filename) throws FileNotFoundException {
		XGSParser xgs_parser=new XGSParser(xgs_filename);
		weapon_data_array=xgs_parser.GetWeaponDataArray();
	}
	public XGSManipulator() {
		
	}
	
	/**
	 * Returns a weapon data array.<br>
	 * Returns null in case data is null.
	 * @return A weapon data array
	 */
	public WeaponData[] GetWeaponDataArray() {
		if(weapon_data_array==null)return null;
		
		WeaponData[] ret=new WeaponData[weapon_data_array.length];
		for(int i=0;i<ret.length;i++) {
			ret[i]=new WeaponData(weapon_data_array[i]);
		}
		
		return ret;
	}
	/**
	 * Sets a weapon data array.
	 * @param weapon_data_array A weapon data array
	 */
	public void SetWeaponDataArray(WeaponData[] weapon_data_array) {
		this.weapon_data_array=weapon_data_array;
	}
	
	/**
	 * Writes out data to a XGS file.
	 * @param xgs_filename Filename
	 */
	public void Write(String xgs_filename) {
		XGSWriter xgs_writer=new XGSWriter(weapon_data_array);
		try {
			xgs_writer.Write(xgs_filename);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteFatal("[XGSManipulator-Write] Failed to write data. Below is the stack trace.");
			
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteLine(str);
			
			System.exit(1);
		}
	}
}
