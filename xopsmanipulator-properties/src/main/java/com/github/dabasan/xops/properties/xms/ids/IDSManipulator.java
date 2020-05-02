package com.github.dabasan.xops.properties.xms.ids;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;

/**
 * Manipulates an IDS file.
 * @author Daba
 *
 */
public class IDSManipulator {
	private Logger logger=LoggerFactory.getLogger(IDSManipulator.class);
	
	private WeaponData weapon_data=null;
	
	/**
	 * 
	 * @param ids_filename IDS filename to load
	 * @throws IOException
	 */
	public IDSManipulator(String ids_filename) throws IOException{
		IDSParser ids_parser=new IDSParser(ids_filename);
		weapon_data=ids_parser.GetWeaponData();
	}
	public IDSManipulator() {
		
	}
	
	/**
	 * Returns weapon data.
	 * @return Weapon data
	 */
	public WeaponData GetWeaponData() {
		if(weapon_data==null)return null;
		return new WeaponData(weapon_data);
	}
	/**
	 * Sets weapon data.
	 * @param weapon_data Weapon data
	 */
	public void SetWeaponData(WeaponData weapon_data) {
		if(weapon_data==null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.weapon_data=weapon_data;
	}
	
	/**
	 * Writes out data to an IDS file.
	 * @param ids_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String ids_filename) {
		IDSWriter ids_writer=new IDSWriter(weapon_data);
		int ret=ids_writer.Write(ids_filename);
		
		if(ret==-1) {
			logger.error("Failed to write data in an IDS file. ids_filename={}",ids_filename);
			return -1;
		}
		
		return 0;
	}
}
