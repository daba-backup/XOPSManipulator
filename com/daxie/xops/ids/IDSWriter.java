package com.daxie.xops.ids;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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
 * Writes data to an IDS file.
 * @author Daba
 *
 */
class IDSWriter {
	private WeaponData weapon_data=null;
	
	public IDSWriter(WeaponData weapon_data) {
		this.weapon_data=weapon_data;
	}
	
	public void Write(String ids_filename) throws FileNotFoundException{
		if(weapon_data==null) {
			LogFile.WriteError("[IDSWriter-Write] Data is null.");
			return;
		}
		
		DataOutputStream dos;
		dos=new DataOutputStream(
				new BufferedOutputStream(
						new FileOutputStream(ids_filename)));
		
		try {
			dos.writeByte(0x49);//I
			dos.writeByte(0x44);//D
			dos.writeByte(0x53);//S
			
			dos.writeByte(0x00);
			dos.writeByte(0x01);
			dos.writeByte(0x00);
			dos.writeByte(0x0A);
			dos.writeByte(0x00);
			dos.writeByte(0x1D);
			dos.writeByte(0x00);
			
			int itemp;
			Vector vtemp;
			byte[] b;
			
			//Attack power
			itemp=weapon_data.GetAttackPower();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Penetration
			itemp=weapon_data.GetPenetration();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Firing interval
			itemp=weapon_data.GetFiringInterval();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Velocity
			itemp=weapon_data.GetBulletSpeed();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Number of bullets
			itemp=weapon_data.GetNumberOfBullets();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Reloading time
			itemp=weapon_data.GetReloadingTime();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Recoil
			itemp=weapon_data.GetRecoil();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Minimum range of error
			itemp=weapon_data.GetErrorRangeMin();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Maximum range of error
			itemp=weapon_data.GetErrorRangeMax();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Position
			vtemp=weapon_data.GetPosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Flash position
			vtemp=weapon_data.GetFlashPosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Cartridge position
			vtemp=weapon_data.GetCartridgePosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Equipment method
			WeaponEquipmentMethod equipment_method=weapon_data.GetEquipmentMethod();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponEquipmentMethod(equipment_method);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Rapid fire
			if(weapon_data.GetRapidFireEnabledFlag()==false)itemp=1;
			else itemp=0;
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Scope mode
			WeaponScopeMode scope_mode=weapon_data.GetScopeMode();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Texture
			String texture_filename=weapon_data.GetTextureFilename();
			WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Model
			String model_filename=weapon_data.GetModelFilename();
			WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Scale
			itemp=Math.round(weapon_data.GetScale()*10.0f);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Cartridge velocity
			vtemp=weapon_data.GetCartridgeVelocity();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Sound ID
			itemp=weapon_data.GetSoundID();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Sound volume
			itemp=weapon_data.GetSoundVolume();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Suppressor
			if(weapon_data.GetSuppressorEnabledFlag()==false)itemp=0;
			else itemp=1;
			b=ByteFunctions.short_to_byte_le((short)itemp);
			dos.write(b);
			
			//Name
			String name=weapon_data.GetName();
			
			byte[] name_buffer=new byte[15+1];
			for(int i=0;i<16;i++) {
				name_buffer[i]=0;
			}
			
			for(int i=0;i<name.length();i++) {
				if(i>=15)break;
				name_buffer[i]=(byte)name.charAt(i);
			}
			
			dos.write(name_buffer);
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteFatal("[IDSWriter-Write] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
		}
		finally {
			try {
				dos.close();
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteFatal("[IDSWriter-Write] Below is the stack trace.");
				LogFile.WriteLine(str);
				
				LogFile.CloseLogFile();
				
				System.exit(1);
			}
		}
	}
}
