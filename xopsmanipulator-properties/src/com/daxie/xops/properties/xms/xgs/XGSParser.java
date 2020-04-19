package com.daxie.xops.properties.xms.xgs;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.xops.properties.XOPSConstants;
import com.daxie.xops.properties.entity.weapon.WeaponBinSpecifierAndEnumConverter;
import com.daxie.xops.properties.entity.weapon.WeaponData;
import com.daxie.xops.properties.entity.weapon.WeaponModelFilenamesStock;
import com.daxie.xops.properties.entity.weapon.WeaponModelType;
import com.daxie.xops.properties.entity.weapon.WeaponScopeMode;
import com.daxie.xops.properties.entity.weapon.WeaponShootingStance;
import com.daxie.xops.properties.entity.weapon.WeaponTextureFilenamesStock;
import com.daxie.xops.properties.entity.weapon.WeaponTextureType;

/**
 * Reads data from a XGS file.
 * @author Daba
 *
 */
class XGSParser {
	private Logger logger=LoggerFactory.getLogger(XGSParser.class);
	
	private WeaponData[] weapon_data_array=null;
	
	public XGSParser(String xgs_filename) throws IOException{
		List<Byte> bin=FileFunctions.GetFileAllBin(xgs_filename);
		
		if(bin.size()!=1732) {
			logger.warn("Invalid file size. xgs_filename={}",xgs_filename);
			return;
		}
		
		int itemp;
		Vector vtemp;
		
		int pos=0x0000000E;
		
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			//Attack power
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetAttackPower(itemp);
			pos+=2;
			
			//Penetration
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetPenetration(itemp);
			pos+=2;
			
			//Firing interval
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetFiringInterval(itemp);
			pos+=2;
			
			//Velocity
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetBulletSpeed(itemp);
			pos+=2;
			
			//Number of bullets
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetNumberOfBullets(itemp);
			pos+=2;
			
			//Reloading time
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetReloadingTime(itemp);
			pos+=2;
			
			//Recoil
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetRecoil(itemp);
			pos+=2;
			
			//Minimum range of error
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetErrorRangeMin(itemp);
			pos+=2;
			
			//Maximum range of error
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetErrorRangeMax(itemp);
			pos+=2;
			
			//Position 
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetPosition(vtemp);
			
			//Flash position
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetFlashPosition(vtemp);
			
			//Cartridge position
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetCartridgePosition(vtemp);
			
			//Shooting stance
			WeaponShootingStance shooting_stance;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			shooting_stance=WeaponBinSpecifierAndEnumConverter.GetWeaponShootingStanceFromBinSpecifier(itemp);
			weapon_data_array[i].SetShootingStance(shooting_stance);
			pos+=2;
			
			//Rapid fire
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			if(itemp==0) {
				weapon_data_array[i].SetRapidFireEnabledFlag(true);
			}
			else {
				weapon_data_array[i].SetRapidFireEnabledFlag(false);
			}
			pos+=2;
			
			//Scope mode
			WeaponScopeMode scope_mode;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			scope_mode=WeaponBinSpecifierAndEnumConverter.GetWeaponScopeModeFromBinSpecifier(itemp);
			weapon_data_array[i].SetScopeMode(scope_mode);
			pos+=2;
			
			//Texture
			WeaponTextureType texture_type;
			String texture_filename;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			texture_type=WeaponBinSpecifierAndEnumConverter.GetWeaponTextureTypeFromBinSpecifier(itemp);
			texture_filename=WeaponTextureFilenamesStock.GetTextureFilename(texture_type);
			weapon_data_array[i].SetTextureFilename(texture_filename);
			pos+=2;
			
			//Model
			WeaponModelType model_type;
			String model_filename;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			model_type=WeaponBinSpecifierAndEnumConverter.GetWeaponModelTypeFromBinSpecifier(itemp);
			model_filename=WeaponModelFilenamesStock.GetModelFilename(model_type);
			weapon_data_array[i].SetModelFilename(model_filename);
			pos+=2;
			
			//Scale
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetScale(itemp*0.1f);
			pos+=2;
			
			//Cartridge velocity
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			vtemp.SetZ(0.0f);
			
			weapon_data_array[i].SetCartridgeVelocity(vtemp);
			
			//Sound ID
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetSoundID(itemp);
			pos+=2;
			
			//Sound volume
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			weapon_data_array[i].SetSoundVolume(itemp);
			pos+=2;
			
			//Suppressor
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			if(itemp==0) {
				weapon_data_array[i].SetSuppressorEnabledFlag(false);
			}
			else {
				weapon_data_array[i].SetSuppressorEnabledFlag(true);
			}
			pos+=2;
			
			//Changeable weapon
			if(i==4) {
				weapon_data_array[i].SetChangeableWeapon(16);
			}
			else if(i==16) {
				weapon_data_array[i].SetChangeableWeapon(4);
			}
			
			//Number of projectiles
			if(i==19) {
				weapon_data_array[i].SetNumberOfProjectiles(6);
			}
		}
		
		//Name
		String strtemp;
		
		pos=0x00000544;
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			byte[] name_buffer=new byte[15+1];
			
			for(int j=0;j<15;j++) {
				name_buffer[j]=bin.get(pos+j);
			}
			name_buffer[15]=0;
			pos+=16;
			
			strtemp=new String(name_buffer);
			
			int first_null_pos=15;
			for(int j=0;j<16;j++) {
				if(strtemp.charAt(j)=='\0') {
					first_null_pos=j;
					break;
				}
			}
			
			strtemp=strtemp.substring(0, first_null_pos);
			
			weapon_data_array[XOPSConstants.WEAPON_NUM-1-i].SetName(strtemp);
		}
	}
	
	public WeaponData[] GetWeaponDataArray() {
		WeaponData[] ret=new WeaponData[weapon_data_array.length];
		
		for(int i=0;i<ret.length;i++) {
			ret[i]=new WeaponData(weapon_data_array[i]);
		}
		
		return ret;
	}
}
