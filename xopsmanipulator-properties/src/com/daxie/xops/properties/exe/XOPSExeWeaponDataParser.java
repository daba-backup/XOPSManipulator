package com.daxie.xops.properties.exe;

import java.util.List;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
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
 * Reads data from an EXE file.
 * @author Daba
 *
 */
class XOPSExeWeaponDataParser {
	private WeaponData[] weapon_data_array;
	
	public XOPSExeWeaponDataParser(List<Byte> bin,int weapon_data_start_pos,int weapon_name_start_pos){
		weapon_data_array=new WeaponData[XOPSConstants.WEAPON_NUM];
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			weapon_data_array[i]=new WeaponData();
		}
		
		int pos=weapon_data_start_pos;
		
		for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
			int itemp;
			Vector vtemp;
			
			//Attack power
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetAttackPower(itemp);
			
			//Penetration
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetPenetration(itemp);
			
			//Firing interval
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetFiringInterval(itemp);
			
			//Velocity
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetBulletSpeed(itemp);
			
			//Number of bullets
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetNumberOfBullets(itemp);
			
			//Reloading time
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetReloadingTime(itemp);
			
			//Recoil
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetRecoil(itemp);
			
			//Minimum range of error
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetErrorRangeMin(itemp);
			
			//Maximum range of error
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetErrorRangeMax(itemp);
			
			//Position 
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetX((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetY((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetZ((float)itemp);
			
			weapon_data_array[i].SetPosition(vtemp);
			
			//Flash position
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetX((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetY((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetZ((float)itemp);
			
			weapon_data_array[i].SetFlashPosition(vtemp);
			
			//Cartridge position
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetX((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetY((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetZ((float)itemp);
			
			weapon_data_array[i].SetCartridgePosition(vtemp);
			
			//Shooting stance
			WeaponShootingStance shooting_stance;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			
			shooting_stance=WeaponBinSpecifierAndEnumConverter.GetWeaponShootingStanceFromBinSpecifier(itemp);
			weapon_data_array[i].SetShootingStance(shooting_stance);
			
			//Rapid fire
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			if(itemp==0) {
				weapon_data_array[i].SetRapidFireEnabledFlag(true);
			}
			else {
				weapon_data_array[i].SetRapidFireEnabledFlag(false);
			}
			
			//Scope mode
			WeaponScopeMode scope_mode;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			scope_mode=WeaponBinSpecifierAndEnumConverter.GetWeaponScopeModeFromBinSpecifier(itemp);
			weapon_data_array[i].SetScopeMode(scope_mode);
			
			//Texture
			WeaponTextureType texture_type;
			String texture_filename;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			texture_type=WeaponBinSpecifierAndEnumConverter.GetWeaponTextureTypeFromBinSpecifier(itemp);
			texture_filename=WeaponTextureFilenamesStock.GetTextureFilename(texture_type);
			weapon_data_array[i].SetTextureFilename(texture_filename);
			
			//Model
			WeaponModelType model_type;
			String model_filename;
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			model_type=WeaponBinSpecifierAndEnumConverter.GetWeaponModelTypeFromBinSpecifier(itemp);
			model_filename=WeaponModelFilenamesStock.GetModelFilename(model_type);
			weapon_data_array[i].SetModelFilename(model_filename);
			
			//Scale
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetScale(itemp*0.1f);
			
			//Cartridge velocity
			vtemp=new Vector();
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetX((float)itemp);
			
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			vtemp.SetY((float)itemp);
			
			vtemp.SetZ(0.0f);
			
			weapon_data_array[i].SetCartridgeVelocity(vtemp);
			
			//Sound ID
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetSoundID(itemp);
			
			//Sound volume
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			weapon_data_array[i].SetSoundVolume(itemp);
			
			//Suppressor
			itemp=ByteFunctions.GetShortValueFromBin_LE(bin, pos);
			pos+=2;
			if(itemp==0) {
				weapon_data_array[i].SetSuppressorEnabledFlag(false);
			}
			else {
				weapon_data_array[i].SetSuppressorEnabledFlag(true);
			}
			
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
		
		pos=weapon_name_start_pos;
		
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
