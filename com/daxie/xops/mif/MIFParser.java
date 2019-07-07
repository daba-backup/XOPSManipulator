package com.daxie.xops.mif;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.daxie.log.LogFile;
import com.daxie.tool.FileFunctions;

/**
 * Reads data from a MIF file.
 * @author Daba
 *
 */
class MIFParser {
	private MissionInfo mission_info;
	
	public MIFParser(String mif_filename,String encoding) 
			throws FileNotFoundException,UnsupportedEncodingException,NumberFormatException{
		mission_info=new MissionInfo();
		
		String[] lines=FileFunctions.GetFileAllLines(mif_filename,encoding);
		if(lines.length<10) {
			LogFile.WriteError("[MIFParser-<init>] Too few lines in the MIF file.");
			return;
		}
		
		mission_info.SetMissionName(lines[0]);
		mission_info.SetMissionFormalName(lines[1]);
		mission_info.SetBD1Filename(lines[2]);
		mission_info.SetPD1Filename(lines[3]);
		mission_info.SetSkyType(Integer.parseInt(lines[4]));
		
		int flags;
		flags=Integer.parseInt(lines[5]);
		
		if((flags&0b00000010)!=0)mission_info.SetDarkenScreenFlag(true);
		else mission_info.SetDarkenScreenFlag(false);
		if((flags&0b00000001)!=0)mission_info.SetExtraHitcheckFlag(true);
		else mission_info.SetExtraHitcheckFlag(false);
		
		mission_info.SetArticleInfoFilename(lines[6]);
		mission_info.SetImage1Filename(lines[7]);
		mission_info.SetImage2Filename(lines[8]);
		
		for(int i=9;i<lines.length;i++) {
			mission_info.AddBriefingTextLine(lines[i]);
		}
	}
	
	public MissionInfo GetMissionInfo() {
		return new MissionInfo(mission_info);
	}
}
