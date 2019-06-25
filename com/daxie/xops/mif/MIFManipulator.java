package com.daxie.xops.mif;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

/**
 * Manipulates a MIF file.
 * @author Daba
 *
 */
public class MIFManipulator {
	private MissionInfo mission_info;
	
	public MIFManipulator(String mif_filename,String encoding) 
			throws FileNotFoundException,UnsupportedEncodingException,NumberFormatException {
		MIFParser mif_parser=new MIFParser(mif_filename, encoding);
		mission_info=mif_parser.GetMissionInfo();
	}
	public MIFManipulator() {
		mission_info=new MissionInfo();
	}
	
	public void SetMissionInfo(MissionInfo mission_info) {
		this.mission_info=mission_info;
	}
	public MissionInfo GetMissionInfo() {
		return new MissionInfo(mission_info);
	}
	
	public void Write(String mif_filename,String encoding) throws FileNotFoundException,UnsupportedEncodingException{
		MIFWriter mif_writer=new MIFWriter(mission_info);
		mif_writer.Write(mif_filename, encoding);
	}
}
