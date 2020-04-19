package com.daxie.xops.properties.xms.ids;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
import com.daxie.xops.properties.entity.weapon.WeaponBinSpecifierAndEnumConverter;
import com.daxie.xops.properties.entity.weapon.WeaponData;
import com.daxie.xops.properties.entity.weapon.WeaponModelFilenamesStock;
import com.daxie.xops.properties.entity.weapon.WeaponModelType;
import com.daxie.xops.properties.entity.weapon.WeaponScopeMode;
import com.daxie.xops.properties.entity.weapon.WeaponShootingStance;
import com.daxie.xops.properties.entity.weapon.WeaponTextureFilenamesStock;
import com.daxie.xops.properties.entity.weapon.WeaponTextureType;

/**
 * Writes data to an IDS file.
 * @author Daba
 *
 */
class IDSWriter {
	private Logger logger=LoggerFactory.getLogger(IDSWriter.class);
	
	private WeaponData weapon_data=null;
	
	public IDSWriter(WeaponData weapon_data) {
		this.weapon_data=weapon_data;
	}
	
	public int Write(String ids_filename){
		if(weapon_data==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		
		List<Byte> bin=new ArrayList<>();
		
		bin.add((byte)0x49);//I
		bin.add((byte)0x44);//D
		bin.add((byte)0x53);//S
		
		bin.add((byte)0x00);
		bin.add((byte)0x01);
		bin.add((byte)0x00);
		bin.add((byte)0x0A);
		bin.add((byte)0x00);
		bin.add((byte)0x1D);
		bin.add((byte)0x00);
		
		int itemp;
		Vector vtemp;
		
		//Attack power
		itemp=weapon_data.GetAttackPower();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Penetration
		itemp=weapon_data.GetPenetration();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Firing interval
		itemp=weapon_data.GetFiringInterval();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Velocity
		itemp=weapon_data.GetBulletSpeed();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Number of bullets
		itemp=weapon_data.GetNumberOfBullets();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Reloading time
		itemp=weapon_data.GetReloadingTime();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Recoil
		itemp=weapon_data.GetRecoil();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Minimum range of error
		itemp=weapon_data.GetErrorRangeMin();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Maximum range of error
		itemp=weapon_data.GetErrorRangeMax();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Position
		vtemp=weapon_data.GetPosition();
		
		itemp=Math.round(vtemp.GetX());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetY());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetZ());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Flash position
		vtemp=weapon_data.GetFlashPosition();
		
		itemp=Math.round(vtemp.GetX());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetY());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetZ());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Cartridge position
		vtemp=weapon_data.GetCartridgePosition();
		
		itemp=Math.round(vtemp.GetX());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetY());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetZ());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Shooting stance
		WeaponShootingStance shooting_stance=weapon_data.GetShootingStance();
		itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponShootingStance(shooting_stance);
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Rapid fire
		if(weapon_data.GetRapidFireEnabledFlag()==false) {
			itemp=1;
		}
		else {
			itemp=0;
		}
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Scope mode
		WeaponScopeMode scope_mode=weapon_data.GetScopeMode();
		itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Texture
		String texture_filename=weapon_data.GetTextureFilename();
		WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
		itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Model
		String model_filename=weapon_data.GetModelFilename();
		WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
		itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Scale
		itemp=Math.round(weapon_data.GetScale()*10.0f);
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Cartridge velocity
		vtemp=weapon_data.GetCartridgeVelocity();
		
		itemp=Math.round(vtemp.GetX());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		itemp=Math.round(vtemp.GetY());
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Sound ID
		itemp=weapon_data.GetSoundID();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Sound volume
		itemp=weapon_data.GetSoundVolume();
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
		//Suppressor
		if(weapon_data.GetSuppressorEnabledFlag()==false) {
			itemp=0;
		}
		else {
			itemp=1;
		}
		ByteFunctions.AddShortValueToBin_LE(bin, (short)itemp);
		
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
		
		for(int i=0;i<16;i++) {
			bin.add(name_buffer[i]);
		}
		
		return 0;
	}
}
