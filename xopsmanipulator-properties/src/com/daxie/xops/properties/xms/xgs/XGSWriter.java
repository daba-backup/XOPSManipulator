package com.daxie.xops.properties.xms.xgs;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogWriter;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.ExceptionFunctions;
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
 * Writes data to a XGS file.
 * @author Daba
 *
 */
class XGSWriter {
	private WeaponData[] weapon_data_array=null;
	
	public XGSWriter(WeaponData[] weapon_data_array) {
		this.weapon_data_array=weapon_data_array;
	}
	
	public void Write(String xgs_filename) throws IOException{
		if(weapon_data_array==null) {
			LogWriter.WriteWarn("[XGSWriter-Write] Data is null.",true);
			return;
		}
		if(weapon_data_array.length!=XOPSConstants.WEAPON_NUM) {
			LogWriter.WriteWarn("[XGSWriter-Write] Invalid number of data. data_num:"+weapon_data_array.length,true);
			return;
		}
		
		try(DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(xgs_filename)))){
			dos.writeByte(0x58);//X
			dos.writeByte(0x47);//G
			dos.writeByte(0x53);//S
			
			dos.writeByte(0x00);
			dos.writeByte(0x01);
			dos.writeByte(0x00);
			dos.writeByte(0x0E);
			dos.writeByte(0x00);
			dos.writeByte(0x17);
			dos.writeByte(0x00);
			dos.writeByte(0x1D);
			dos.writeByte(0x00);
			dos.writeByte(0x08);
			dos.writeByte(0x00);
			
			for(int i=0;i<XOPSConstants.WEAPON_NUM;i++) {
				int itemp;
				Vector vtemp;
				byte[] b;
				
				//Attack power
				itemp=weapon_data_array[i].GetAttackPower();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Penetration
				itemp=weapon_data_array[i].GetPenetration();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Firing interval
				itemp=weapon_data_array[i].GetFiringInterval();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Velocity
				itemp=weapon_data_array[i].GetBulletSpeed();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Number of bullets
				itemp=weapon_data_array[i].GetNumberOfBullets();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Reloading time
				itemp=weapon_data_array[i].GetReloadingTime();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Recoil
				itemp=weapon_data_array[i].GetRecoil();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Minimum range of error
				itemp=weapon_data_array[i].GetErrorRangeMin();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Maximum range of error
				itemp=weapon_data_array[i].GetErrorRangeMax();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Position
				vtemp=weapon_data_array[i].GetPosition();
				
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
				vtemp=weapon_data_array[i].GetFlashPosition();
				
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
				vtemp=weapon_data_array[i].GetCartridgePosition();
				
				itemp=Math.round(vtemp.GetX());
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				itemp=Math.round(vtemp.GetY());
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				itemp=Math.round(vtemp.GetZ());
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Shooting stance
				WeaponShootingStance shooting_stance=weapon_data_array[i].GetShootingStance();
				itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponShootingStance(shooting_stance);
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Rapid fire
				if(weapon_data_array[i].GetRapidFireEnabledFlag()==false)itemp=1;
				else itemp=0;
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Scope mode
				WeaponScopeMode scope_mode=weapon_data_array[i].GetScopeMode();
				itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponScopeMode(scope_mode);
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Texture
				String texture_filename=weapon_data_array[i].GetTextureFilename();
				WeaponTextureType texture_type=WeaponTextureFilenamesStock.GetWeaponTextureTypeFromFilename(texture_filename);
				itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponTextureType(texture_type);
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Model
				String model_filename=weapon_data_array[i].GetModelFilename();
				WeaponModelType model_type=WeaponModelFilenamesStock.GetWeaponModelTypeFromFilename(model_filename);
				itemp=WeaponBinSpecifierAndEnumConverter.GetBinSpecifierFromWeaponModelType(model_type);
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Scale
				itemp=Math.round(weapon_data_array[i].GetScale()*10.0f);
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Cartridge velocity
				vtemp=weapon_data_array[i].GetCartridgeVelocity();
				
				itemp=Math.round(vtemp.GetX());
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				itemp=Math.round(vtemp.GetY());
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Sound ID
				itemp=weapon_data_array[i].GetSoundID();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Sound volume
				itemp=weapon_data_array[i].GetSoundVolume();
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
				
				//Suppressor
				if(weapon_data_array[i].GetSuppressorEnabledFlag()==false)itemp=0;
				else itemp=1;
				b=ByteFunctions.short_to_byte_le((short)itemp);
				dos.write(b);
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
				
				dos.write(name_buffer);
			}
			
			for(int i=0;i<16;i++) {
				dos.writeByte(0x00);
			}
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogWriter.WriteWarn("[XGSWriter-Write] Below is the stack trace.",true);
			LogWriter.WriteWarn(str,false);
			
			return;
		}
	}
}
