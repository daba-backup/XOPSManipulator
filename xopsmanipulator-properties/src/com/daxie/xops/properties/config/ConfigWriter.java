package com.daxie.xops.properties.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.FileFunctions;

/**
 * Writes data in a config file.
 * @author Daba
 *
 */
class ConfigWriter {
	private Logger logger=LoggerFactory.getLogger(ConfigWriter.class);
	
	private Config config;
	
	public ConfigWriter(Config config) {
		this.config=config;
	}
	
	public int Write(String config_filename) {
		if(config==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		
		List<Byte> bin=new ArrayList<>();
		this.AddKeyCodeToBin(bin, config.GetTurnUp());
		this.AddKeyCodeToBin(bin, config.GetTurnDown());
		this.AddKeyCodeToBin(bin, config.GetTurnLeft());
		this.AddKeyCodeToBin(bin, config.GetTurnRight());
		this.AddKeyCodeToBin(bin, config.GetMoveForward());
		this.AddKeyCodeToBin(bin, config.GetMoveBackward());
		this.AddKeyCodeToBin(bin, config.GetMoveLeft());
		this.AddKeyCodeToBin(bin, config.GetMoveRight());
		this.AddKeyCodeToBin(bin, config.GetWalk());
		this.AddKeyCodeToBin(bin, config.GetJump());
		this.AddKeyCodeToBin(bin, config.GetReload());
		this.AddKeyCodeToBin(bin, config.GetDropWeapon());
		this.AddKeyCodeToBin(bin, config.GetZoom());
		this.AddKeyCodeToBin(bin, config.GetFireMode());
		this.AddKeyCodeToBin(bin, config.GetSwitchWeapon());
		this.AddKeyCodeToBin(bin, config.GetWeapon1());
		this.AddKeyCodeToBin(bin, config.GetWeapon2());
		this.AddKeyCodeToBin(bin, config.GetFire());
		
		bin.add((byte)config.GetMouseSensitivity());
		this.AddWindowModeToBin(bin, config.GetWindowMode());
		this.AddFlagToBin(bin, config.GetEnableSound());
		this.AddFlagToBin(bin, config.GetEnableBlood());
		bin.add((byte)config.GetBrightness());
		this.AddFlagToBin(bin, config.GetInvertMouse());
		this.AddFlagToBin(bin, config.GetFrameSkip());
		this.AddFlagToBin(bin, config.GetAnotherGunsight());
		this.AddNameToBin(bin, config.GetName());
		
		try {
			FileFunctions.CreateBinFile(config_filename, bin);
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
	private void AddKeyCodeToBin(List<Byte> bin,KeyCode key_code) {
		int ordinal=key_code.ordinal();
		bin.add((byte)ordinal);
	}
	private void AddWindowModeToBin(List<Byte> bin,WindowMode window_mode) {
		int ordinal=window_mode.ordinal();
		bin.add((byte)ordinal);
	}
	private void AddFlagToBin(List<Byte> bin,boolean flag) {
		if(flag==false)bin.add((byte)0);
		else bin.add((byte)1);
	}
	private void AddNameToBin(List<Byte> bin,String name) {
		byte[] name_buffer=new byte[20+1];
		for(int i=0;i<21;i++) {
			name_buffer[i]=0;
		}
		
		for(int i=0;i<name.length();i++) {
			if(i>=20)break;
			name_buffer[i]=(byte)name.charAt(i);
		}
		
		for(int i=0;i<21;i++) {
			bin.add(name_buffer[i]);
		}
	}
}
