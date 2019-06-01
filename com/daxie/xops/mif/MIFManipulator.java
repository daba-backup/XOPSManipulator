package com.daxie.xops.mif;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;

/**
 * Manipulate a MIF file.
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
	
	public void Write(String mif_filename,String encoding) throws UnsupportedEncodingException{
		MIFWriter mif_writer=new MIFWriter(mission_info);
		try {
			mif_writer.Write(mif_filename, encoding);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteFatal("[MIFManipulator-Write] Failed to write data. Below is the stack trace.");
			
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteLine(str);
			
			System.exit(1);
		}
	}
}
