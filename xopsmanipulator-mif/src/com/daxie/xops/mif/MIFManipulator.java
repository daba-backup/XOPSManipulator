package com.daxie.xops.mif;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manipulates a MIF file.
 * @author Daba
 *
 */
public class MIFManipulator {
	private Logger logger=LoggerFactory.getLogger(MIFManipulator.class);
	
	private MissionInfo mission_info;
	
	public MIFManipulator(String mif_filename,String encoding) throws IOException{
		MIFParser mif_parser=new MIFParser(mif_filename, encoding);
		mission_info=mif_parser.GetMissionInfo();
	}
	public MIFManipulator() {
		mission_info=new MissionInfo();
	}
	
	public void SetMissionInfo(MissionInfo mission_info) {
		if(mission_info==null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.mission_info=mission_info;
	}
	public MissionInfo GetMissionInfo() {
		return new MissionInfo(mission_info);
	}
	
	public void Write(String mif_filename,String encoding) throws IOException,UnsupportedEncodingException{
		MIFWriter mif_writer=new MIFWriter(mission_info);
		mif_writer.Write(mif_filename, encoding);
	}
}
