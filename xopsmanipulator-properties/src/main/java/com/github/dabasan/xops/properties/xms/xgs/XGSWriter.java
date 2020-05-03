package com.github.dabasan.xops.properties.xms.xgs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.tool.ByteFunctions;
import com.github.dabasan.tool.FileFunctions;
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
 * Writes data to a XGS file.
 * @author Daba
 *
 */
class XGSWriter {
	private Logger logger=LoggerFactory.getLogger(XGSWriter.class);
	
	private WeaponData[] weapon_data_array;
	
	public XGSWriter(WeaponData[] weapon_data_array) {
		this.weapon_data_array=weapon_data_array;
	}
	
	public int Write(String xgs_filename){
		if(weapon_data_array==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		if(weapon_data_array.length!=XOPSConstants.WEAPON_NUM) {
			logger.warn("Invalid number of data. data_num={}",weapon_data_array.length);
			return -1;
		}
		
		List<Byte> bin=new ArrayList<>();
		
		bin.add((byte)0x58);//X
		bin.add((byte)0x47);//G
		bin.add((byte)0x53);//S
		
		bin.add((byte)0x00);
		bin.add((byte)0x01);
		bin.add((byte)0x00);
		bin.add((byte)0x0E);
		bin.add((byte)0x00);
		bin.add((byte)0x17);
		bin.add((byte)0x00);
		bin.add((byte)0x1D);
		bin.add((byte)0x00);
		bin.add((byte)0x08);
		bin.add((byte)0x00);
			
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			int itemp;
			Vector vtemp;
			
			//Attack power
			itemp=weapon_data_array[i].GetAttackPower();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Penetration
			itemp=weapon_data_array[i].GetPenetration();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Firing interval
			itemp=weapon_data_array[i].GetFiringInterval();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Velocity
			itemp=weapon_data_array[i].GetBulletSpeed();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Number of bullets
			itemp=weapon_data_array[i].GetNumberOfBullets();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Reloading time
			itemp=weapon_data_array[i].GetReloadingTime();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Recoil
			itemp=weapon_data_array[i].GetRecoil();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Minimum range of error
			itemp=weapon_data_array[i].GetErrorRangeMin();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Maximum range of error
			itemp=weapon_data_array[i].GetErrorRangeMax();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Position
			vtemp=weapon_data_array[i].GetPosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Flash position
			vtemp=weapon_data_array[i].GetFlashPosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Cartridge position
			vtemp=weapon_data_array[i].GetCartridgePosition();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetZ());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Shooting stance
			WeaponShootingStance shooting_stance=weapon_data_array[i].GetShootingStance();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponShootingStance(shooting_stance);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Rapid fire
			if(weapon_data_array[i].GetRapidFireEnabledFlag()==false) {
				itemp=1;
			}
			else {
				itemp=0;
			}
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Scope mode
			WeaponScopeMode scope_mode=weapon_data_array[i].GetScopeMode();
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Texture
			String texture_filename=weapon_data_array[i].GetTextureFilename();
			WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Model
			String model_filename=weapon_data_array[i].GetModelFilename();
			WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
			itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Scale
			itemp=Math.round(weapon_data_array[i].GetScale()*10.0f);
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Cartridge velocity
			vtemp=weapon_data_array[i].GetCartridgeVelocity();
			
			itemp=Math.round(vtemp.GetX());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			itemp=Math.round(vtemp.GetY());
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Sound ID
			itemp=weapon_data_array[i].GetSoundID();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Sound volume
			itemp=weapon_data_array[i].GetSoundVolume();
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
			
			//Suppressor
			if(weapon_data_array[i].GetSuppressorEnabledFlag()==false) {
				itemp=0;
			}
			else {
				itemp=1;
			}
			ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		}
		
		//Name
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
				bin.add(name_buffer[j]);
			}
		}
		
		for(int i=0;i<16;i++) {
			bin.add((byte)0);
		}
		
		try {
			FileFunctions.CreateBinFile(xgs_filename, bin);
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
}
