package com.github.dabasan.xops.properties.exe;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
import com.github.dabasan.xops.properties.XOPSConstants;
import com.github.dabasan.xops.properties.entity.weapon.WeaponBinSpecifierAndEnumConverter;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponModelFilenamesStock;
import com.github.dabasan.xops.properties.entity.weapon.WeaponModelType;
import com.github.dabasan.xops.properties.entity.weapon.WeaponScopeMode;
import com.github.dabasan.xops.properties.entity.weapon.WeaponShootingStance;
import com.github.dabasan.xops.properties.entity.weapon.WeaponTextureFilenamesStock;
import com.github.dabasan.xops.properties.entity.weapon.WeaponTextureType;

/**
 * Writes data to an EXE file.
 * @author Daba
 *
 */
class XOPSExeWeaponDataWriter {
	private Logger logger=LoggerFactory.getLogger(XOPSExeWeaponDataWriter.class);
	
	private WeaponData[] weapon_data_array;
	
	public XOPSExeWeaponDataWriter(WeaponData[] weapon_data_array) {
		this.weapon_data_array=weapon_data_array;
	}
	
	public int Write(List<Byte> bin,int weapon_data_start_pos,int weapon_name_start_pos) {
		if(weapon_data_array==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		if(weapon_data_array.length!=XOPSConstants.WEAPON_NUM) {
			logger.warn("Invalid number of data. data_num={}",weapon_data_array.length);
			return -1;
		}
		
		int pos=weapon_data_start_pos;
		
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			int itemp;
			Vector vtemp;
			
			//Attack power
			itemp=weapon_data_array[i].GetAttackPower();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Penetration
			itemp=weapon_data_array[i].GetPenetration();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Firing interval
			itemp=weapon_data_array[i].GetFiringInterval();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Velocity
			itemp=weapon_data_array[i].GetBulletSpeed();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Number of bullets
			itemp=weapon_data_array[i].GetNumberOfBullets();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Reloading time
			itemp=weapon_data_array[i].GetReloadingTime();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Recoil
			itemp=weapon_data_array[i].GetRecoil();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Minimum range of error
			itemp=weapon_data_array[i].GetErrorRangeMin();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Maximum range of error
			itemp=weapon_data_array[i].GetErrorRangeMax();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Position
			vtemp=weapon_data_array[i].GetPosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Flash position
			vtemp=weapon_data_array[i].GetFlashPosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Cartridge position
			vtemp=weapon_data_array[i].GetCartridgePosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Shooting stance
			WeaponShootingStance shooting_stance=weapon_data_array[i].GetShootingStance();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponShootingStance(shooting_stance);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Rapid fire
			if(weapon_data_array[i].GetRapidFireEnabledFlag()==false) {
				itemp=1;
			}
			else {
				itemp=0;
			}
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Scope mode
			WeaponScopeMode scope_mode=weapon_data_array[i].GetScopeMode();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Texture
			String texture_filename=weapon_data_array[i].GetTextureFilename();
			WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Model
			String model_filename=weapon_data_array[i].GetModelFilename();
			WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Scale
			itemp=Math.round(weapon_data_array[i].GetScale()*10.0f);
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Cartridge velocity
			vtemp=weapon_data_array[i].GetCartridgeVelocity();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Sound ID
			itemp=weapon_data_array[i].GetSoundID();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Sound volume
			itemp=weapon_data_array[i].GetSoundVolume();
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
			pos+=2;
			
			//Suppressor
			if(weapon_data_array[i].GetSuppressorEnabledFlag()==false) {
				itemp=0;
			}
			else {
				itemp=1;
			}
			ByteFunctions.SetShortValueToBin_LE(bin, pos, (short)itemp);
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
		
		return 0;
	}
}
