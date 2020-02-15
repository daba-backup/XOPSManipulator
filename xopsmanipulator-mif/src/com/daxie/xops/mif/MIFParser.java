package com.daxie.xops.mif;

import java.io.IOException;
import java.util.List;

import com.daxie.log.LogWriter;
import com.daxie.tool.FileFunctions;

/**
 * Reads data from a MIF file.
 * @author Daba
 *
 */
class MIFParser {
	private MissionInfo mission_info;
	
	public MIFParser(String mif_filename,String encoding) throws IOException{
		mission_info=new MissionInfo();
		
		List<String> lines=FileFunctions.GetFileAllLines(mif_filename,encoding);
		if(lines.size()<10) {
			LogWriter.WriteWarn("[MIFParser-<init>] Too few lines in the MIF file.",true);
			return;
		}
		
		mission_info.SetMissionName(lines.get(0));
		mission_info.SetMissionFormalName(lines.get(1));
		mission_info.SetBD1Filename(lines.get(2));
		mission_info.SetPD1Filename(lines.get(3));
		mission_info.SetSkyType(Integer.parseInt(lines.get(4)));
		
		int flags;
		flags=Integer.parseInt(lines.get(5));
		
		if((flags&0b00000010)!=0)mission_info.SetDarkenScreenFlag(true);
		else mission_info.SetDarkenScreenFlag(false);
		if((flags&0b00000001)!=0)mission_info.SetExtraHitcheckFlag(true);
		else mission_info.SetExtraHitcheckFlag(false);
		
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
