package com.daxie.xops.properties.xms.xgs;

import java.io.FileNotFoundException;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.properties.entity.weapon.WeaponData;

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
		if(weapon_data_array==null) {
			LogFile.WriteWarn("[XGSManipulator-SetWeaponDataArray] Null argument where non-null required.",true);
			return;
		}
		this.weapon_data_array=weapon_data_array;
	}
	
	/**
	 * Writes out data to a XGS file.
	 * @param xgs_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String xgs_filename) {
		XGSWriter xgs_writer=new XGSWriter(weapon_data_array);
		try {
			xgs_writer.Write(xgs_filename);
		}
		catch(FileNotFoundException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[XGSManipulator-Write] Failed to write data.",true);
			LogFile.WriteWarn("Below is the stack trace.",false);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		
		return 0;
	}
}
