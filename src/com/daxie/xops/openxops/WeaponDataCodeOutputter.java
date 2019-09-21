package com.daxie.xops.openxops;

import java.util.List;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogFile;
import com.daxie.tool.StringFunctions;
import com.daxie.xops.weapon.WeaponData;

/**
 * Writes out weapon data formatted for the source code of OpenXOPS.
 * @author Daba
 *
 */
public class WeaponDataCodeOutputter {
	private List<WeaponData> weapon_data_list;
	
	public WeaponDataCodeOutputter(List<WeaponData> weapon_data_list) {
		this.weapon_data_list=weapon_data_list;
	}
	
	public String GetWeaponDataSourceCode() {
		String ret="";
		
		if(weapon_data_list==null) {
			LogFile.WriteWarn("[OutputWeaponDataSourceCode-GetWeaponDataSourceCode] Data is null.",true);
			return ret;
		}
		
		final String array_name="Weapon";
		final String separator=System.getProperty("line.separator");
		
		for(int i=0;i<weapon_data_list.size();i++) {
			WeaponData weapon_data=weapon_data_list.get(i);
			
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "name", weapon_data.GetName())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "model", weapon_data.GetModelFilename())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "texture", weapon_data.GetTextureFilename())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "attacks", weapon_data.GetAttackPower())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "penetration", weapon_data.GetPenetration())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "blazings", weapon_data.GetFiringInterval())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "speed", weapon_data.GetBulletSpeed())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "nbsmax", weapon_data.GetNumberOfBullets())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "reloads", weapon_data.GetReloadingTime())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "reaction", weapon_data.GetRecoil())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "ErrorRangeMIN", weapon_data.GetErrorRangeMin())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "ErrorRangeMAX", weapon_data.GetErrorRangeMax())+separator;
			
			Vector position=weapon_data.GetPosition();
			Vector flash_position=weapon_data.GetFlashPosition();
			Vector cartridge_position=weapon_data.GetCartridgePosition();
			Vector cartridge_velocity=weapon_data.GetCartridgeVelocity();
			
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "mx", position.GetX())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "my", position.GetY())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "mz", position.GetZ())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "flashx", flash_position.GetX())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "flashy", flash_position.GetY())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "flashz", flash_position.GetZ())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "yakkyou_px", cartridge_position.GetX())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "yakkyou_py", cartridge_position.GetY())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "yakkyou_pz", cartridge_position.GetZ())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "yakkyou_sx", cartridge_velocity.GetX())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "yakkyou_sy", cartridge_velocity.GetY())+separator;
			
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "blazingmode", weapon_data.GetRapidFireEnabledFlag())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "scopemode", weapon_data.GetScopeMode().ordinal())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "size", weapon_data.GetScale())+separator;
			
			int sound_id=weapon_data.GetSoundID();
			sound_id=WeaponSpecifierConverter.GetOpenXOPSSoundIDFromXOPSSoundID(sound_id);
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "soundid", sound_id)+separator;
			
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "soundvolume", weapon_data.GetSoundVolume())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "silencer", weapon_data.GetSuppressorEnabledFlag())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "WeaponP", weapon_data.GetShootingStance().ordinal())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "ChangeWeapon", weapon_data.GetChangeableWeapon())+separator;
			ret+=StringFunctions.GetCPPArrayFormatString(array_name, i, "burst", weapon_data.GetNumberOfProjectiles())+separator;
		}
		
		return ret;
	}
}
