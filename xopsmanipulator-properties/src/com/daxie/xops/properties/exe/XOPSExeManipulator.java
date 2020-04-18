package com.daxie.xops.properties.exe;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.log.LogWriter;
import com.daxie.tool.DateFunctions;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.tool.FilenameFunctions;
import com.daxie.xops.properties.XOPSConstants;
import com.daxie.xops.properties.entity.character.CharacterData;
import com.daxie.xops.properties.entity.weapon.WeaponData;

/**
 * Manipulates an EXE file.
 * @author Daba
 *
 */
public class XOPSExeManipulator {
	private Logger logger=LoggerFactory.getLogger(XOPSExeManipulator.class);
	
	private WeaponData[] weapon_data_array=null;
	private CharacterData[] character_data_array=null;
	
	/**
	 * 
	 * @param xops_filename EXE filename to load
	 * @throws IOException
	 */
	public XOPSExeManipulator(String xops_filename) throws IOException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		XOPSVersion version=XOPSExeFunctions.GetXOPSVersion(bin);
		
		if(version==XOPSVersion.UNKNOWN_VERSION) {
			logger.warn("Unknown version of X operations.");
			
			weapon_data_array=new WeaponData[XOPSConstants.WEAPON_NUM];
			for(int i=0;i<weapon_data_array.length;i++) {
				weapon_data_array[i]=new WeaponData();
			}
			character_data_array=new CharacterData[XOPSConstants.CHARACTER_NUM];
			for(int i=0;i<character_data_array.length;i++) {
				character_data_array[i]=new CharacterData();
			}
			
			return;
		}
		
		int weapon_data_start_pos;
		int weapon_name_start_pos;
		int character_data_start_pos;
		
		switch(version) {
		case XOPS096:
			weapon_data_start_pos=0x0005D32C;
			weapon_name_start_pos=0x000661E4;
			character_data_start_pos=0x0005D864;
			break;
		case XOPS096T:
			weapon_data_start_pos=0x0005D32C;
			weapon_name_start_pos=0x000661E4;
			character_data_start_pos=0x0005D864;
			break;
		case XOPS097FT:
			weapon_data_start_pos=0x0005E32C;
			weapon_name_start_pos=0x000671E4;
			character_data_start_pos=0x0005E864;
			break;
		case XOPS0975T:
			weapon_data_start_pos=0x00077FB0;
			weapon_name_start_pos=0x00079140;
			character_data_start_pos=0x000784E8;
			break;
		case XOPSOLT18F2:
			weapon_data_start_pos=0x0006640C;
			weapon_name_start_pos=0x0006EF84;
			character_data_start_pos=0x00066944;
			break;
		case XOPSOLT19F2:
			weapon_data_start_pos=0x000777E8;
			weapon_name_start_pos=0x00077370;
			character_data_start_pos=0x00077D20;
			break;
		default:
			weapon_data_start_pos=0x00000000;
			weapon_name_start_pos=0x00000000;
			character_data_start_pos=0x00000000;
			break;
		}
		
		XOPSExeWeaponDataParser weapon_data_parser=new XOPSExeWeaponDataParser(bin,weapon_data_start_pos,weapon_name_start_pos);
		XOPSExeCharacterDataParser character_data_parser=new XOPSExeCharacterDataParser(bin,character_data_start_pos);
		
		weapon_data_array=weapon_data_parser.GetWeaponDataArray();
		character_data_array=character_data_parser.GetCharacterDataArray();
	}
	public XOPSExeManipulator(String xops_filename,
			int weapon_data_start_pos,int weapon_name_start_pos,int character_data_start_pos) throws IOException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		XOPSExeWeaponDataParser weapon_data_parser=null;
		XOPSExeCharacterDataParser character_data_parser=null;
		
		try {
			weapon_data_parser=new XOPSExeWeaponDataParser(bin,weapon_data_start_pos,weapon_name_start_pos);
			character_data_parser=new XOPSExeCharacterDataParser(bin,character_data_start_pos);
		}
		catch(IndexOutOfBoundsException e) {
			logger.error("Error while reading.",e);
			return;
		}
		
		weapon_data_array=weapon_data_parser.GetWeaponDataArray();
		character_data_array=character_data_parser.GetCharacterDataArray();
	}
	public XOPSExeManipulator() {
		
	}
	
	/**
	 * Returns a weapon data array.
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
	 * Sets a weapon data array.
	 * @param weapon_data_array A weapon data array
	 */
	public void SetWeaponDataArray(WeaponData[] weapon_data_array) {
		if(weapon_data_array==null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.weapon_data_array=weapon_data_array;
	}
	/**
	 * Sets a character data array.
	 * @param character_data_array A character data array
	 */
	public void SetCharacterDataArray(CharacterData[] character_data_array) {
		if(character_data_array==null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.character_data_array=character_data_array;
	}
	
	/**
	 * Writes out data to an EXE file.
	 * @param xops_filename Filename of the file where data will be overwritten
	 * @param create_backup_flag Flag to set whether to create a backup file
	 * @throws IOException
	 */
	public void Write(String xops_filename,boolean create_backup_flag) throws IOException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		//Create a backup.
		if(create_backup_flag==true) {
			String date=DateFunctions.GetDateStringWithoutDelimiters();
			String filename_without_extension=FilenameFunctions.GetFilenameWithoutExtension(xops_filename);
			String backup_filename=filename_without_extension+"_"+date+".exe";
			
			FileFunctions.CreateBinFile(backup_filename, bin);
		}
		
		//Create a modified file (overwrite).
		XOPSExeWeaponDataWriter weapon_data_writer=new XOPSExeWeaponDataWriter(weapon_data_array);
		XOPSExeCharacterDataWriter character_data_writer=new XOPSExeCharacterDataWriter(character_data_array);
		
		XOPSVersion version=XOPSExeFunctions.GetXOPSVersion(bin);
		
		int weapon_data_start_pos;
		int weapon_name_start_pos;
		int character_data_start_pos;
		
		switch(version) {
		case XOPS096:
			weapon_data_start_pos=0x0005D32C;
			weapon_name_start_pos=0x000661E4;
			character_data_start_pos=0x0005D864;
			break;
		case XOPS096T:
			weapon_data_start_pos=0x0005D32C;
			weapon_name_start_pos=0x000661E4;
			character_data_start_pos=0x0005D864;
			break;
		case XOPS097FT:
			weapon_data_start_pos=0x0005E32C;
			weapon_name_start_pos=0x000671E4;
			character_data_start_pos=0x0005E864;
			break;
		case XOPS0975T:
			weapon_data_start_pos=0x00077FB0;
			weapon_name_start_pos=0x00079140;
			character_data_start_pos=0x000784E8;
			break;
		case XOPSOLT18F2:
			weapon_data_start_pos=0x0006640C;
			weapon_name_start_pos=0x0006EF84;
			character_data_start_pos=0x00066944;
			break;
		case XOPSOLT19F2:
			weapon_data_start_pos=0x000777E8;
			weapon_name_start_pos=0x00077370;
			character_data_start_pos=0x00077D20;
			break;
		default:
			weapon_data_start_pos=0x00000000;
			weapon_name_start_pos=0x00000000;
			character_data_start_pos=0x00000000;
			break;
		}
		
		weapon_data_writer.Write(bin,weapon_data_start_pos,weapon_name_start_pos);
		character_data_writer.Write(bin,character_data_start_pos);
		
		FileFunctions.CreateBinFile(xops_filename, bin);
	}
	/**
	 * Writes out data to an EXE file.<br>
	 * Start addresses of each data must be set by the user.
	 * @param xops_filename Filename
	 * @param weapon_data_start_pos Start address of weapon data
	 * @param weapon_name_start_pos Start address of weapon name
	 * @param character_data_start_pos Start address of character data
	 * @param create_backup_flag Flag to set whether to create a backup file
	 * @throws IOException
	 */
	public void Write(String xops_filename,int weapon_data_start_pos,int weapon_name_start_pos,
			int character_data_start_pos,boolean create_backup_flag) throws IOException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		//Create a backup.
		if(create_backup_flag==true) {
			String date=DateFunctions.GetDateStringWithoutDelimiters();
			String filename_without_extension=FilenameFunctions.GetFilenameWithoutExtension(xops_filename);
			String backup_filename=filename_without_extension+"_"+date+".exe";
			
			FileFunctions.CreateBinFile(backup_filename, bin);
		}
		
		//Create a modified file (overwrite).
		XOPSExeWeaponDataWriter weapon_data_writer=new XOPSExeWeaponDataWriter(weapon_data_array);
		XOPSExeCharacterDataWriter character_data_writer=new XOPSExeCharacterDataWriter(character_data_array);
		
		try {
			weapon_data_writer.Write(bin,weapon_data_start_pos,weapon_name_start_pos);
			character_data_writer.Write(bin,character_data_start_pos);
		}
		catch(IndexOutOfBoundsException e) {
			logger.error("Error while writing.",e);
			return;
		}
		
		FileFunctions.CreateBinFile(xops_filename, bin);
	}
}
