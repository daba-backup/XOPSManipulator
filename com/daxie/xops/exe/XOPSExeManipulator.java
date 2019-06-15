package com.daxie.xops.exe;

import java.io.FileNotFoundException;
import java.util.List;

import com.daxie.log.LogFile;
import com.daxie.tool.DateFunctions;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.tool.FilenameFunctions;
import com.daxie.xops.XOPSConstants;
import com.daxie.xops.character.CharacterData;
import com.daxie.xops.weapon.WeaponData;

/**
 * Manipulates an EXE file.
 * @author Daba
 *
 */
public class XOPSExeManipulator {
	private WeaponData[] weapon_data_array;
	private CharacterData[] character_data_array;
	
	/**
	 * 
	 * @param xops_filename EXE filename to load
	 * @throws FileNotFoundException EXE file not found
	 */
	public XOPSExeManipulator(String xops_filename) throws FileNotFoundException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		XOPSVersion version=XOPSExeFunctions.GetXOPSVersion(bin);
		
		if(version==XOPSVersion.UNKNOWN_VERSION) {
			LogFile.WriteError("[XOPSExeManipulator-<init>] Unknown version of X operations.");
			
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
			int weapon_data_start_pos,int weapon_name_start_pos,int character_data_start_pos) throws FileNotFoundException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		XOPSExeWeaponDataParser weapon_data_parser=null;
		XOPSExeCharacterDataParser character_data_parser=null;
		
		try {
			weapon_data_parser=new XOPSExeWeaponDataParser(bin,weapon_data_start_pos,weapon_name_start_pos);
			character_data_parser=new XOPSExeCharacterDataParser(bin,character_data_start_pos);
		}
		catch(IndexOutOfBoundsException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteFatal("[XOPSExeManipulator-<init>] Index out of bounds. Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
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
		this.weapon_data_array=weapon_data_array;
	}
	/**
	 * Sets a character data array.
	 * @param character_data_array A character data array
	 */
	public void SetCharacterDataArray(CharacterData[] character_data_array) {
		this.character_data_array=character_data_array;
	}
	
	/**
	 * Writes out data to an EXE file.
	 * @param xops_filename Filename of the file where data will be overwritten
	 * @throws FileNotFoundException File to overwrite not found
	 */
	public void Write(String xops_filename) throws FileNotFoundException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		//Create a backup.
		String date=DateFunctions.GetDateStringWithoutDelimiters();
		String filename_without_extension=FilenameFunctions.GetFilenameWithoutExtension(xops_filename);
		String backup_filename=filename_without_extension+date+".exe";
		
		FileFunctions.CreateBinFile(backup_filename, bin);
		
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
	 * @throws FileNotFoundException File to overwrite not found
	 */
	public void Write(String xops_filename,
			int weapon_data_start_pos,int weapon_name_start_pos,int character_data_start_pos) throws FileNotFoundException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		
		//Create a backup.
		String date=DateFunctions.GetDateStringWithoutDelimiters();
		String filename_without_extension=FilenameFunctions.GetFilenameWithoutExtension(xops_filename);
		String backup_filename=filename_without_extension+date+".exe";
		
		FileFunctions.CreateBinFile(backup_filename, bin);
		
		//Create a modified file (overwrite).
		XOPSExeWeaponDataWriter weapon_data_writer=new XOPSExeWeaponDataWriter(weapon_data_array);
		XOPSExeCharacterDataWriter character_data_writer=new XOPSExeCharacterDataWriter(character_data_array);
		
		try {
			weapon_data_writer.Write(bin,weapon_data_start_pos,weapon_name_start_pos);
			character_data_writer.Write(bin,character_data_start_pos);
		}
		catch(IndexOutOfBoundsException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteFatal("[XOPSExeManipulator-<init>] Index out of bounds. Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
		}
		
		FileFunctions.CreateBinFile(xops_filename, bin);
	}
}
