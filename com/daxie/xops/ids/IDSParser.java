package com.daxie.xops.ids;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogFile;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.weapon.WeaponBinSpecifierAndEnumConverter;
import com.daxie.xops.weapon.WeaponData;
import com.daxie.xops.weapon.WeaponEquipmentMethod;
import com.daxie.xops.weapon.WeaponModelFilenamesStock;
import com.daxie.xops.weapon.WeaponModelType;
import com.daxie.xops.weapon.WeaponScopeMode;
import com.daxie.xops.weapon.WeaponTextureFilenamesStock;
import com.daxie.xops.weapon.WeaponTextureType;

/**
 * Reads data from an IDS file.
 * @author Daba
 *
 */
class IDSParser {
	private WeaponData weapon_data=null;
	
	public IDSParser(String ids_filename) throws FileNotFoundException{
		List<Byte> bin=new ArrayList<Byte>();
		
		DataInputStream dis;	
		dis=new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(ids_filename)));
		
		weapon_data=new WeaponData();
		
		try {
			byte read_byte;
			while(true) {
				read_byte=dis.readByte();
				bin.add(read_byte);
			}
		}
		catch(EOFException e) {
			//to the finally block.
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteFatal("[IDSParser-<init>] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
		}
		finally {
			try {
				if(dis!=null) {
					dis.close();
				}
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteFatal("[IDSParser-<init>] Below is the stack trace.");
				LogFile.WriteLine(str);
				
				LogFile.CloseLogFile();
				
				System.exit(1);
			}
		}
		
		if(bin.size()!=84) {
			LogFile.WriteError("[IDSParser-<init>] Invalid file size. filename:"+ids_filename);
			return;
		}
		
		byte[] b=new byte[2];
		int itemp;
		Vector vtemp;
		String strtemp;
		
		int count=0x0000000A;
		
		//Attack power
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetAttackPower(itemp);
		count+=2;
		
		//Penetration
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetPenetration(itemp);
		count+=2;
		
		//Firing interval
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetFiringInterval(itemp);
		count+=2;
		
		//Velocity
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetVelocity(itemp);
		count+=2;
		
		//Number of bullets
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetNumberOfBullets(itemp);
		count+=2;
		
		//Reloading time
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetReloadingTime(itemp);
		count+=2;
		
		//Recoil
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetRecoil(itemp);
		count+=2;
		
		//Minimum range of error
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetErrorRangeMin(itemp);
		count+=2;
		
		//Maximum range of error
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetErrorRangeMax(itemp);
		count+=2;
		
		//Position 
		vtemp=new Vector();
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetX((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetY((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetZ((float)itemp);
		count+=2;
		
		weapon_data.SetPosition(vtemp);
		
		//Flash position
		vtemp=new Vector();
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetX((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetY((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetZ((float)itemp);
		count+=2;
		
		weapon_data.SetFlashPosition(vtemp);
		
		//Cartridge position
		vtemp=new Vector();
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetX((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetY((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetZ((float)itemp);
		count+=2;
		
		weapon_data.SetCartridgePosition(vtemp);
		
		//Equipment method
		WeaponEquipmentMethod equipment_method;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		count+=2;
		
		equipment_method=WeaponBinSpecifierAndEnumConverter.GetWeaponEquipmentMethodFromBinSpecifier(itemp);
		weapon_data.SetEquipmentMethod(equipment_method);
		
		//Rapid fire
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		if(itemp==0)weapon_data.SetRapidFireEnabledFlag(true);
		else weapon_data.SetRapidFireEnabledFlag(false);
		count+=2;
		
		//Scope mode
		WeaponScopeMode scope_mode;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		count+=2;
		
		scope_mode=WeaponBinSpecifierAndEnumConverter.GetWeaponScopeModeFromBinSpecifier(itemp);
		weapon_data.SetScopeMode(scope_mode);
		
		//Texture
		WeaponTextureType texture_type;
		String texture_filename;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		count+=2;
		
		texture_type=WeaponBinSpecifierAndEnumConverter.GetWeaponTextureTypeFromBinSpecifier(itemp);
		texture_filename=WeaponTextureFilenamesStock.GetTextureFilename(texture_type);
		weapon_data.SetTextureFilename(texture_filename);
		
		//Model
		WeaponModelType model_type;
		String model_filename;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		count+=2;
		
		model_type=WeaponBinSpecifierAndEnumConverter.GetWeaponModelTypeFromBinSpecifier(itemp);
		model_filename=WeaponModelFilenamesStock.GetModelFilename(model_type);
		weapon_data.SetModelFilename(model_filename);
		
		//Scale
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetScale(itemp*0.1f);
		count+=2;
		
		//Cartridge velocity
		vtemp=new Vector();
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetX((float)itemp);
		count+=2;
		
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		vtemp.SetY((float)itemp);
		count+=2;
		
		vtemp.SetZ(0.0f);
		
		weapon_data.SetCartridgeVelocity(vtemp);
		
		//Sound ID
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetSoundID(itemp);
		count+=2;
		
		//Sound volume
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		weapon_data.SetSoundVolume(itemp);
		count+=2;
		
		//Suppressor
		b[0]=bin.get(count);
		b[1]=bin.get(count+1);
		itemp=ByteFunctions.byte_to_short_le(b);
		if(itemp==0)weapon_data.SetSuppressorEnabledFlag(false);
		else weapon_data.SetSuppressorEnabledFlag(true);
		count+=2;
		
		//Name
		byte[] name_buffer=new byte[15+1];
		for(int i=0;i<15;i++) {
			name_buffer[i]=bin.get(count+i);
		}
		name_buffer[15]=0;
		
		strtemp=new String(name_buffer);
		
		int first_null_pos=15;
		for(int i=0;i<16;i++) {
			if(strtemp.charAt(i)=='\0') {
				first_null_pos=i;
				break;
			}
		}
		
		strtemp=strtemp.substring(0, first_null_pos);
		weapon_data.SetName(strtemp);
		
		//Changeable weapon
		weapon_data.SetChangeableWeapon(-1);
	}
	
	public WeaponData GetWeaponData() {
		return new WeaponData(weapon_data);
	}
}
