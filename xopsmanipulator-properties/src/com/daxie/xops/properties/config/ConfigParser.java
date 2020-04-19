package com.daxie.xops.properties.config;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.FileFunctions;
import com.daxie.xops.properties.XOPSConstants;

/**
 * Reads data from a config file.
 * @author Daba
 *
 */
class ConfigParser {
	private Logger logger=LoggerFactory.getLogger(ConfigParser.class);
	
	private Config config;
	
	public ConfigParser(String config_filename) throws IOException{
		config=new Config();
		
		List<Byte> bin=FileFunctions.GetFileAllBin(config_filename);
		if(bin.size()!=XOPSConstants.CONFIG_FILE_SIZE) {
			logger.warn("Invalid file size. config_filename={}",config_filename);
			return;
		}
		
		config.SetTurnUp(this.GetKeyCodeFromBin(bin, 0));
		config.SetTurnDown(this.GetKeyCodeFromBin(bin, 1));
		config.SetTurnLeft(this.GetKeyCodeFromBin(bin, 2));
		config.SetTurnRight(this.GetKeyCodeFromBin(bin, 3));
		config.SetMoveForward(this.GetKeyCodeFromBin(bin, 4));
		config.SetMoveBackward(this.GetKeyCodeFromBin(bin, 5));
		config.SetMoveLeft(this.GetKeyCodeFromBin(bin, 6));
		config.SetMoveRight(this.GetKeyCodeFromBin(bin, 7));
		config.SetWalk(this.GetKeyCodeFromBin(bin, 8));
		config.SetJump(this.GetKeyCodeFromBin(bin, 9));
		config.SetReload(this.GetKeyCodeFromBin(bin, 10));
		config.SetDropWeapon(this.GetKeyCodeFromBin(bin, 11));
		config.SetZoom(this.GetKeyCodeFromBin(bin, 12));
		config.SetFireMode(this.GetKeyCodeFromBin(bin, 13));
		config.SetSwitchWeapon(this.GetKeyCodeFromBin(bin, 14));
		config.SetWeapon1(this.GetKeyCodeFromBin(bin, 15));
		config.SetWeapon2(this.GetKeyCodeFromBin(bin, 16));
		config.SetFire(this.GetKeyCodeFromBin(bin, 17));
		
		config.SetMouseSensitivity(Byte.toUnsignedInt(bin.get(18)));
		config.SetWindowMode(this.GetWindowModeFromBin(bin, 19));
		config.SetEnableSound(this.GetFlagFromBin(bin, 20));
		config.SetEnableBlood(this.GetFlagFromBin(bin, 21));
		config.SetBrightness(Byte.toUnsignedInt(bin.get(22)));
		config.SetInvertMouse(this.GetFlagFromBin(bin, 23));
		config.SetFrameSkip(this.GetFlagFromBin(bin, 24));
		config.SetAnotherGunsight(this.GetFlagFromBin(bin, 25));
		config.SetName(this.GetNameFromBin(bin));
	}
	private KeyCode GetKeyCodeFromBin(List<Byte> bin,int pos) {
		KeyCode ret;
		KeyCode[] values=KeyCode.values();
		
		int key_index=Byte.toUnsignedInt(bin.get(pos));
		
		if(0<=key_index&&key_index<values.length) {
			ret=values[key_index];
		}
		else {
			logger.warn("Key index out of bounds. pos={}",pos);
			ret=KeyCode.KEY_UP;
		}
		
		return ret;
	}
	private boolean GetFlagFromBin(List<Byte> bin,int pos) {
		int i=Byte.toUnsignedInt(bin.get(pos));
		
		if(i==0)return false;
		else return true;
	}
	private WindowMode GetWindowModeFromBin(List<Byte> bin,int pos) {
		WindowMode ret;
		int i=Byte.toUnsignedInt(bin.get(pos));
		
		if(i==0x00)ret=WindowMode.WINDOW;
		else ret=WindowMode.FULL_SCREEN;
		
		return ret;
	}
	private String GetNameFromBin(List<Byte> bin) {
		byte[] name_buffer=new byte[20+1];
		for(int i=0;i<20;i++) {
			name_buffer[i]=bin.get(26+i);
		}
		name_buffer[20]=0;
		
		String ret=new String(name_buffer);
		
		int first_null_pos=20;
		for(int i=0;i<20;i++) {
			if(ret.charAt(i)=='\0') {
				first_null_pos=i;
				break;
			}
		}
		
		ret=ret.substring(0, first_null_pos);
		return ret;
	}
	
	public Config GetConfig() {
		return new Config(config);
	}
}
