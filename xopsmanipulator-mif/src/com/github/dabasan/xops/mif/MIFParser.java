package com.github.dabasan.xops.mif;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.tool.FileFunctions;

/**
 * Reads data from a MIF file.
 * @author Daba
 *
 */
class MIFParser {
	private Logger logger=LoggerFactory.getLogger(MIFParser.class);
	
	private MissionInfo mission_info;
	
	public MIFParser(String mif_filename,String encoding) throws IOException{
		mission_info=new MissionInfo();
		
		List<String> lines=FileFunctions.GetFileAllLines(mif_filename,encoding);
		if(lines.size()<10) {
			logger.warn("Invalid number of lines in the MIF file specified. mif_filename={}",mif_filename);
			return;
		}
		
		mission_info.SetMissionName(lines.get(0));
		mission_info.SetMissionFormalName(lines.get(1));
		mission_info.SetBD1Filename(lines.get(2));
		mission_info.SetPD1Filename(lines.get(3));
		
		int sky_type_index=0;
		try {
			sky_type_index=Integer.parseInt(lines.get(4));
		}
		catch(NumberFormatException e) {
			logger.warn("Invalid format of number. sky_type");
		}
		
		SkyType sky_type=SkyType.NONE;
		SkyType[] sky_types=SkyType.values();
		if(0<=sky_type_index&&sky_type_index<sky_types.length) {
			sky_type=sky_types[sky_type_index];
		}
		else {
			logger.warn("Index out of bouds. sky_type");
		}
		mission_info.SetSkyType(sky_type);
		
		int flags;
		flags=Integer.parseInt(lines.get(5));
		
		if((flags&0b00000010)!=0) {
			mission_info.SetDarkenScreenFlag(true);
		}
		else {
			mission_info.SetDarkenScreenFlag(false);
		}
		if((flags&0b00000001)!=0) {
			mission_info.SetExtraHitcheckFlag(true);
		}
		else {
			mission_info.SetExtraHitcheckFlag(false);
		}
		
		mission_info.SetArticleInfoFilename(lines.get(6));
		mission_info.SetImage1Filename(lines.get(7));
		mission_info.SetImage2Filename(lines.get(8));
		
		for(int i=9;i<lines.size();i++) {
			mission_info.AddBriefingTextLine(lines.get(i));
		}
	}
	
	public MissionInfo GetMissionInfo() {
		return new MissionInfo(mission_info);
	}
}
