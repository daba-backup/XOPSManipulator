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
			byte[] b=new byte[2];
			int itemp;
			Vector vtemp;
			
			//Attack power
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetAttackPower(itemp);
			pos+=2;
			
			//Penetration
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetPenetration(itemp);
			pos+=2;
			
			//Firing interval
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetFiringInterval(itemp);
			pos+=2;
			
			//Velocity
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetBulletSpeed(itemp);
			pos+=2;
			
			//Number of bullets
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetNumberOfBullets(itemp);
			pos+=2;
			
			//Reloading time
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetReloadingTime(itemp);
			pos+=2;
			
			//Recoil
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetRecoil(itemp);
			pos+=2;
			
			//Minimum range of error
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetErrorRangeMin(itemp);
			pos+=2;
			
			//Maximum range of error
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetErrorRangeMax(itemp);
			pos+=2;
			
			//Position 
			vtemp=new Vector();
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetPosition(vtemp);
			
			//Flash position
			vtemp=new Vector();
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetFlashPosition(vtemp);
			
			//Cartridge position
			vtemp=new Vector();
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetZ((float)itemp);
			pos+=2;
			
			weapon_data_array[i].SetCartridgePosition(vtemp);
			
			//Shooting stance
			WeaponShootingStance shooting_stance;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			shooting_stance=WeaponBinSpecifierAndEnumConverter.GetWeaponShootingStanceFromBinSpecifier(itemp);
			weapon_data_array[i].SetShootingStance(shooting_stance);
			
			//Rapid fire
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			if(itemp==0)weapon_data_array[i].SetRapidFireEnabledFlag(true);
			else weapon_data_array[i].SetRapidFireEnabledFlag(false);
			pos+=2;
			
			//Scope mode
			WeaponScopeMode scope_mode;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			scope_mode=WeaponBinSpecifierAndEnumConverter.GetWeaponScopeModeFromBinSpecifier(itemp);
			weapon_data_array[i].SetScopeMode(scope_mode);
			
			//Texture
			WeaponTextureType texture_type;
			String texture_filename;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			texture_type=WeaponBinSpecifierAndEnumConverter.GetWeaponTextureTypeFromBinSpecifier(itemp);
			texture_filename=WeaponTextureFilenamesStock.GetTextureFilename(texture_type);
			weapon_data_array[i].SetTextureFilename(texture_filename);
			
			//Model
			WeaponModelType model_type;
			String model_filename;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			pos+=2;
			
			model_type=WeaponBinSpecifierAndEnumConverter.GetWeaponModelTypeFromBinSpecifier(itemp);
			model_filename=WeaponModelFilenamesStock.GetModelFilename(model_type);
			weapon_data_array[i].SetModelFilename(model_filename);
			
			//Scale
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetScale(itemp*0.1f);
			pos+=2;
			
			//Cartridge velocity
			vtemp=new Vector();
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetX((float)itemp);
			pos+=2;
			
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			vtemp.SetY((float)itemp);
			pos+=2;
			
			vtemp.SetZ(0.0f);
			
			weapon_data_array[i].SetCartridgeVelocity(vtemp);
			
			//Sound ID
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetSoundID(itemp);
			pos+=2;
			
			//Sound volume
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			weapon_data_array[i].SetSoundVolume(itemp);
			pos+=2;
			
			//Suppressor
			b[0]=bin.get(pos);
			b[1]=bin.get(pos+1);
			itemp=ByteFunctions.byte_to_short_le(b);
			if(itemp==0)weapon_data_array[i].SetSuppressorEnabledFlag(false);
			else weapon_data_array[i].SetSuppressorEnabledFlag(true);
			pos+=2;
			
			//Changeable weapon
			if(i==4)weapon_data_array[i].SetChangeableWeapon(16);
			else if(i==16)weapon_data_array[i].SetChangeableWeapon(4);
			
			//Number of projectiles
			if(i==19)weapon_data_array[i].SetNumberOfProjectiles(6);
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
