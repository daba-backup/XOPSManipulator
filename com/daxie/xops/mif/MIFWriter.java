package com.daxie.xops.mif;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;

/**
 * Write data to a MIF file.
 * @author Daba
 *
 */
class MIFWriter {
	private MissionInfo mission_info;
	
	public MIFWriter(MissionInfo mission_info) {
		this.mission_info=mission_info;
	}
	
	public void Write(String mif_filename,String encoding) throws FileNotFoundException,UnsupportedEncodingException{
		if(mission_info==null)return;
		
		BufferedWriter br=null;
		br=new BufferedWriter(
				new OutputStreamWriter(
						new FileOutputStream(mif_filename),encoding));
		
		String windows_separator="\r\n";
		try {
			br.write(mission_info.GetMissionName()+windows_separator);
			br.write(mission_info.GetMissionFormalName()+windows_separator);
			br.write(mission_info.GetBD1Filename()+windows_separator);
			br.write(mission_info.GetPD1Filename()+windows_separator);
			
			String sky_type=""+mission_info.GetSkyType();
			br.write(sky_type+windows_separator);
	
			int flags=0;
			boolean additional_hitcheck_flag=mission_info.GetAdditionalHitcheckFlag();
			boolean darken_screen_flag=mission_info.GetDarkenScreenFlag();
			if(additional_hitcheck_flag==true)flags=flags|0b00000001;
			if(darken_screen_flag==true)flags=flags|0b00000010;
			
			String str_flags=""+flags;
			br.write(str_flags+windows_separator);
			
			br.write(mission_info.GetArticleInfoFilename()+windows_separator);
			br.write(mission_info.GetImage1Filename()+windows_separator);
			br.write(mission_info.GetImage2Filename()+windows_separator);
			
			List<String> briefing_text=mission_info.GetBriefingText();
			for(String line:briefing_text) {
				br.write(line+windows_separator);
			}
			
			br.flush();
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteFatal("[MIFWriter-Write] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
		}
		finally {
			try {
				br.close();
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteFatal("[MIFWriter-Write] Below is the stack trace.");
				LogFile.WriteLine(str);
				
				LogFile.CloseLogFile();
				
				System.exit(1);
			}
		}
	}
}
