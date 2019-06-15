package com.daxie.xops.exe;

import java.util.List;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogFile;
import com.daxie.tool.ByteFunctions;
import com.daxie.xops.XOPSConstants;
import com.daxie.xops.weapon.WeaponBinSpecifierAndEnumConverter;
import com.daxie.xops.weapon.WeaponData;
import com.daxie.xops.weapon.WeaponEquipmentMethod;
import com.daxie.xops.weapon.WeaponModelFilenamesStock;
import com.daxie.xops.weapon.WeaponModelType;
import com.daxie.xops.weapon.WeaponScopeMode;
import com.daxie.xops.weapon.WeaponTextureFilenamesStock;
import com.daxie.xops.weapon.WeaponTextureType;

/**
 * Writes data to an EXE file.
 * @author Daba
 *
 */
class XOPSExeWeaponDataWriter {
	private WeaponData[] weapon_data_array;
	
	public XOPSExeWeaponDataWriter(WeaponData[] weapon_data_array) {
		this.weapon_data_array=weapon_data_array;
	}
	
	public void Write(List<Byte> bin,int weapon_data_start_pos,int weapon_name_start_pos) {
		if(weapon_data_array==null) {
			LogFile.WriteError("[XOPSExeWeaponDataWriter-Write] Data is null.");
			return;
		}
		if(weapon_data_array.length!=XOPSConstants.WEAPON_NUM) {
			LogFile.WriteError("[XOPSExeWeaponDataWriter-Write] Invalid number of data. data_num:"+weapon_data_array.length);
			return;
		}
		
		int pos=weapon_data_start_pos;
		
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			int itemp;
			Vector vtemp;
			byte[] b;
			
			//Attack power
			itemp=weapon_data_array[i].GetAttackPower();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Penetration
			itemp=weapon_data_array[i].GetPenetration();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Firing interval
			itemp=weapon_data_array[i].GetFiringInterval();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Speed
			itemp=weapon_data_array[i].GetSpeed();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Number of bullets
			itemp=weapon_data_array[i].GetNumberOfBullets();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Reloading time
			itemp=weapon_data_array[i].GetReloadingTime();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Recoil
			itemp=weapon_data_array[i].GetRecoil();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Minimum range of error
			itemp=weapon_data_array[i].GetErrorRangeMin();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Maximum range of error
			itemp=weapon_data_array[i].GetErrorRangeMax();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Position
			vtemp=weapon_data_array[i].GetPosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Flash position
			vtemp=weapon_data_array[i].GetFlashPosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Cartridge position
			vtemp=weapon_data_array[i].GetCartridgePosition();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Equipment method
			WeaponEquipmentMethod equipment_method=weapon_data_array[i].GetEquipmentMethod();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponEquipmentMethod(equipment_method);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Rapid fire
			if(weapon_data_array[i].GetRapidFireEnabledFlag()==false)itemp=1;
			else itemp=0;
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Scope mode
			WeaponScopeMode scope_mode=weapon_data_array[i].GetScopeMode();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Texture
			String texture_filename=weapon_data_array[i].GetTextureFilename();
			WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Model
			String model_filename=weapon_data_array[i].GetModelFilename();
			WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Scale
			itemp=Math.round(weapon_data_array[i].GetScale()*10.0f);
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Cartridge speed
			vtemp=weapon_data_array[i].GetCartridgeSpeed();
			
			itemp=Math.round(vtemp.GetX());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Sound ID
			itemp=weapon_data_array[i].GetSoundID();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Sound volume
			itemp=weapon_data_array[i].GetSoundVolume();
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
			
			//Suppressor
			if(weapon_data_array[i].GetSuppressorEnabledFlag()==false)itemp=0;
			else itemp=1;
			b=ByteFunctions.short_to_byte_le((short)itemp);
			bin.set(pos, b[0]);
			bin.set(pos+1, b[1]);
			pos+=2;
		}
		
		//Name
		pos=weapon_name_start_pos;
		
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			String name=weapon_data_array[XOPSConstants.WEAPON_NUM-1-i].GetName();
			
			byte[] name_buffer=new byte[15+1];
			for(int j=0;j<16;j++) {
				name_buffer[j]=0;
			}
			
			for(int j=0;j<name.length();j++) {
				if(j>=15)break;
				name_buffer[j]=(byte)name.charAt(j);
			}
			
			for(int j=0;j<16;j++) {
				bin.set(pos+j, name_buffer[j]);
			}
			pos+=16;
		}
	}
}
