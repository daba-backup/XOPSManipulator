package com.daxie.xops.mif;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.daxie.log.LogFile;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.tool.XMLFunctions;

/**
 * Loads multiple MIF files and makes a list.
 * @author Daba
 *
 */
public class MIFList {
	public static final int MISSION_NAME=0x0001;
	public static final int MISSION_FORMAL_NAME=0x0002;
	public static final int BD1_FILENAME=0x0004;
	public static final int PD1_FILENAME=0x0008;
	public static final int SKY_TYPE=0x0010;
	public static final int EXTRA_HITCHECK_FLAG=0x0020;
	public static final int DARKEN_SCREEN_FLAG=0x0040;
	public static final int ARTICLE_INFO_FILENAME=0x0080;
	public static final int IMAGE1_FILENAME=0x0100;
	public static final int IMAGE2_FILENAME=0x0200;
	public static final int BRIEFING_TEXT=0x0400;
	
	public static final int NONE=0x0000;
	public static final int ALL=0x07FF;
	
	//Text
	private static String str_mission_name="Mission name";
	private static String str_mission_formal_name="Mission formal name";
	private static String str_bd1_filename="BD1 filename";
	private static String str_pd1_filename="PD1 filename";
	private static String str_sky_type="Sky type";
	private static String str_extra_hitcheck_flag="Extra hitcheck flag";
	private static String str_darken_screen_flag="Darken screen flag";
	private static String str_article_info_filename="Article info filename";
	private static String str_image1_filename="Image 1 filename";
	private static String str_image2_filename="Image 2 filename";
	private static String str_briefing_text="Briefing text";
	
	private Map<String, MissionInfo> mission_info_map;
	
	public static void SetStrMissionName(String a_str_mission_name) {
		str_mission_name=a_str_mission_name;
	}
	public static void SetStrMissionFormalName(String a_str_mission_formal_name) {
		str_mission_formal_name=a_str_mission_formal_name;
	}
	public static void SetStrBD1Filename(String a_str_bd1_filename) {
		str_bd1_filename=a_str_bd1_filename;
	}
	public static void SetStrPD1Filename(String a_str_pd1_filename) {
		str_pd1_filename=a_str_pd1_filename;
	}
	public static void SetStrSkyType(String a_str_sky_type) {
		str_sky_type=a_str_sky_type;
	}
	public static void SetStrExtraHitcheckFlag(String a_str_extra_hitcheck_flag) {
		str_extra_hitcheck_flag=a_str_extra_hitcheck_flag;
	}
	public static void SetStrDarkenScreenFlag(String a_darken_screen_flag) {
		str_darken_screen_flag=a_darken_screen_flag;
	}
	public static void SetStrArticleInfoFilename(String a_article_info_filename) {
		str_article_info_filename=a_article_info_filename;
	}
	public static void SetStrImage1Filename(String a_str_image1_filename) {
		str_image1_filename=a_str_image1_filename;
	}
	public static void SetStrImage2Filename(String a_str_image2_filename) {
		str_image2_filename=a_str_image2_filename;
	}
	public static void SetStrBriefingText(String a_str_briefing_text) {
		str_briefing_text=a_str_briefing_text;
	}
	
	/**
	 * @param directory_name Name of the directory
	 * @param encoding Encoding
	 * @throws UnsupportedEncodingException Unsupported encoding specified
	 */
	public MIFList(String directory_name,String encoding) throws UnsupportedEncodingException{
		mission_info_map=new HashMap<>();
		
		File dir=new File(directory_name);
		if(dir.isDirectory()==false) {
			LogFile.WriteWarn("[MIFList-<init>] Not a directory. directory_name:"+directory_name,true);
			return;
		}
		
		FilenameFilter filter=new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.endsWith("mif")||name.endsWith("MIF"))return true;
				return false;
			}
		};
		
		File[] files=dir.listFiles(filter);
		if(files==null) {
			LogFile.WriteWarn("[MIFList-<init>] listFiles() returned null.",true);
			return;
		}
		
		for(File file:files) {
			MIFManipulator mif_manipulator=null;
			try {
				mif_manipulator=new MIFManipulator(file.getPath(), encoding);
			}
			catch(FileNotFoundException e) {
				LogFile.WriteWarn("[MIFList-<init>] Failed to load a MIF file. filename:"+file.getPath(),true);		
				return;
			}
			
			MissionInfo mif=mif_manipulator.GetMissionInfo();
			mission_info_map.put(file.getName(), mif);
		}
	}
	
	/**
	 * Shows a list of addons on the standard output.
	 * @param flags Flags to determine what information will be shown
	 */
	public void ShowList(int flags) {
		for(Map.Entry<String, MissionInfo> entry:mission_info_map.entrySet()) {
			System.out.println("["+entry.getKey()+"]");
			
			MissionInfo mif=entry.getValue();
			
			if((flags&MISSION_NAME)!=0) {
				System.out.print("mission_name:");
				System.out.println(mif.GetMissionName());
			}
			if((flags&MISSION_FORMAL_NAME)!=0) {
				System.out.print("mission_formal_name:");
				System.out.println(mif.GetMissionFormalName());
			}
			if((flags&BD1_FILENAME)!=0) {
				System.out.print("bd1_filename:");
				System.out.println(mif.GetBD1Filename());
			}
			if((flags&PD1_FILENAME)!=0) {
				System.out.print("pd1_filename:");
				System.out.println(mif.GetPD1Filename());
			}
			if((flags&SKY_TYPE)!=0) {
				System.out.print("sky_type:");
				System.out.println(mif.GetSkyType());
			}
			if((flags&EXTRA_HITCHECK_FLAG)!=0) {
				System.out.print("extra_hitcheck_flag:");
				System.out.println(mif.GetExtraHitcheckFlag());
			}
			if((flags&DARKEN_SCREEN_FLAG)!=0) {
				System.out.print("darken_screen_flag:");
				System.out.println(mif.GetDarkenScreenFlag());
			}
			if((flags&ARTICLE_INFO_FILENAME)!=0) {
				System.out.print("article_info_filename:");
				System.out.println(mif.GetArticleInfoFilename());
			}
			if((flags&IMAGE1_FILENAME)!=0) {
				System.out.print("image1_filename:");
				System.out.println(mif.GetImage1Filename());
			}
			if((flags&IMAGE2_FILENAME)!=0) {
				System.out.print("image2_filename:");
				System.out.println(mif.GetImage2Filename());
			}
			if((flags&BRIEFING_TEXT)!=0) {
				System.out.println("briefing_text:");
				
				List<String> briefing_text=mif.GetBriefingText();
				for(String line:briefing_text) {
					System.out.println(line);
				}
			}
			
			System.out.println("------------------------------");
		}
	}
	/**
	 * Outputs a list of addons in a XML file.
	 * @param xml_filename XML filename
	 * @param flags Flags to determine what information will be written
	 * @return -1 on error and 0 on success
	 */
	public int WriteXML(String xml_filename,int flags) {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[MIFList-WriteXML] Below is the stack trace.",true);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		Document document=builder.newDocument();
		
		Element el_addon_list=document.createElement("addon_list");
		document.appendChild(el_addon_list);
		
		for(Map.Entry<String, MissionInfo> entry:mission_info_map.entrySet()) {
			Element el_mission=document.createElement(entry.getKey());
			
			MissionInfo mif=entry.getValue();
			
			if((flags&MISSION_NAME)!=0) {
				Element el_mission_name=document.createElement("mission_name");
				el_mission_name.appendChild(document.createTextNode(mif.GetMissionName()));
				el_mission.appendChild(el_mission_name);
			}
			if((flags&MISSION_FORMAL_NAME)!=0) {
				Element el_mission_formal_name=document.createElement("mission_formal_name");
				el_mission_formal_name.appendChild(document.createTextNode(mif.GetMissionFormalName()));
				el_mission.appendChild(el_mission_formal_name);
			}
			if((flags&BD1_FILENAME)!=0) {
				Element el_bd1_filename=document.createElement("bd1_filename");
				el_bd1_filename.appendChild(document.createTextNode(mif.GetBD1Filename()));
				el_mission.appendChild(el_bd1_filename);
			}
			if((flags&PD1_FILENAME)!=0) {
				Element el_pd1_filename=document.createElement("pd1_filename");
				el_pd1_filename.appendChild(document.createTextNode(mif.GetPD1Filename()));
				el_mission.appendChild(el_pd1_filename);
			}
			if((flags&SKY_TYPE)!=0) {
				Element el_sky_type=document.createElement("sky_type");
				el_sky_type.appendChild(document.createTextNode(""+mif.GetSkyType()));
				el_mission.appendChild(el_sky_type);
			}
			if((flags&EXTRA_HITCHECK_FLAG)!=0) {
				Element el_extra_hitcheck_flag=document.createElement("extra_hitcheck_flag");
				el_extra_hitcheck_flag.appendChild(document.createTextNode(""+mif.GetExtraHitcheckFlag()));
				el_mission.appendChild(el_extra_hitcheck_flag);
			}
			if((flags&DARKEN_SCREEN_FLAG)!=0) {
				Element el_darken_screen_flag=document.createElement("darken_screen_flag");
				el_darken_screen_flag.appendChild(document.createTextNode(""+mif.GetDarkenScreenFlag()));
				el_mission.appendChild(el_darken_screen_flag);
			}
			if((flags&ARTICLE_INFO_FILENAME)!=0) {
				Element el_article_info_filename=document.createElement("article_info_filename");
				el_article_info_filename.appendChild(document.createTextNode(mif.GetArticleInfoFilename()));
				el_mission.appendChild(el_article_info_filename);
			}
			if((flags&IMAGE1_FILENAME)!=0) {
				Element el_image1_filename=document.createElement("image1_filename");
				el_image1_filename.appendChild(document.createTextNode(mif.GetImage1Filename()));
				el_mission.appendChild(el_image1_filename);
			}
			if((flags&IMAGE2_FILENAME)!=0) {
				Element el_image2_filename=document.createElement("image2_filename");
				el_image2_filename.appendChild(document.createTextNode(mif.GetImage2Filename()));
				el_mission.appendChild(el_image2_filename);
			}
			if((flags&BRIEFING_TEXT)!=0) {
				List<String> briefing_text_lines=mif.GetBriefingText();
				int line_count=0;
				
				Element el_briefing_text=document.createElement("briefing_text");
				el_mission.appendChild(el_briefing_text);
				for(String line:briefing_text_lines) {
					Element el_briefing_line=document.createElement("line"+line_count);
					el_briefing_line.appendChild(document.createTextNode(line));
					el_briefing_text.appendChild(el_briefing_line);
					
					line_count++;
				}
			}
			
			el_addon_list.appendChild(el_mission);
		}
		
		File file=new File(xml_filename);
		
		int res=XMLFunctions.WriteXML(file, document);
		if(res<0)return -1;
		
		return 0;
	}
	/**
	 * Outputs a list of addons in a CSV file.
	 * @param csv_filename CSV filename
	 * @param flags Flags to determine what information will be written
	 * @return -1 on error and 0 on success
	 */
	public int WriteCSV(String csv_filename,int flags) {
		List<String> lines=new ArrayList<>();
		
		//Add columns.
		String columns="";
		if((flags&MISSION_NAME)!=0) {
			columns+="\""+str_mission_name+"\",";
		}
		if((flags&MISSION_FORMAL_NAME)!=0) {
			columns+="\""+str_mission_formal_name+"\",";
		}
		if((flags&BD1_FILENAME)!=0) {
			columns+="\""+str_bd1_filename+"\",";
		}
		if((flags&PD1_FILENAME)!=0) {
			columns+="\""+str_pd1_filename+"\",";
		}
		if((flags&SKY_TYPE)!=0) {
			columns+="\""+str_sky_type+"\",";
		}
		if((flags&EXTRA_HITCHECK_FLAG)!=0) {
			columns+="\""+str_extra_hitcheck_flag+"\",";
		}
		if((flags&DARKEN_SCREEN_FLAG)!=0) {
			columns+="\""+str_darken_screen_flag+"\",";
		}
		if((flags&ARTICLE_INFO_FILENAME)!=0) {
			columns+="\""+str_article_info_filename+"\",";
		}
		if((flags&IMAGE1_FILENAME)!=0) {
			columns+="\""+str_image1_filename+"\",";
		}
		if((flags&IMAGE2_FILENAME)!=0) {
			columns+="\""+str_image2_filename+"\",";
		}
		if((flags&BRIEFING_TEXT)!=0) {
			columns+="\""+str_briefing_text+"\",";
		}
		
		if(columns.charAt(columns.length()-1)==',') {
			columns=columns.substring(0, columns.length()-1);
		}
		
		lines.add(columns);
		
		for(Map.Entry<String, MissionInfo> entry:mission_info_map.entrySet()) {
			MissionInfo mif=entry.getValue();
			
			String line="";
			if((flags&MISSION_NAME)!=0) {
				line+=mif.GetMissionName()+",";
			}
			if((flags&MISSION_FORMAL_NAME)!=0) {
				line+=mif.GetMissionFormalName()+",";
			}
			if((flags&BD1_FILENAME)!=0) {
				line+=mif.GetBD1Filename()+",";
			}
			if((flags&PD1_FILENAME)!=0) {
				line+=mif.GetPD1Filename()+",";
			}
			if((flags&SKY_TYPE)!=0) {
				line+=mif.GetSkyType()+",";
			}
			if((flags&EXTRA_HITCHECK_FLAG)!=0) {
				boolean extra_hitcheck_flag=mif.GetExtraHitcheckFlag();
				
				if(extra_hitcheck_flag==false)line+="false";
				else line+="true";
				line+=",";
			}
			if((flags&DARKEN_SCREEN_FLAG)!=0) {
				boolean darken_screen_flag=mif.GetDarkenScreenFlag();
				
				if(darken_screen_flag==false)line+="false";
				else line+="true";
				line+=",";
			}
			if((flags&ARTICLE_INFO_FILENAME)!=0) {
				line+=mif.GetArticleInfoFilename()+",";
			}
			if((flags&IMAGE1_FILENAME)!=0) {
				line+=mif.GetImage1Filename()+",";
			}
			if((flags&IMAGE2_FILENAME)!=0) {
				line+=mif.GetImage2Filename()+",";
			}
			if((flags&BRIEFING_TEXT)!=0) {
				line+="\"";
				List<String> briefing_text=mif.GetBriefingText();
				for(String btline:briefing_text) {
					line+=btline+"<br>";
				}
				line+="\",";
			}
			
			lines.add(line);
		}
		
		try {
			FileFunctions.CreateTextFile(csv_filename, "UTF-8",lines);
		}
		catch(FileNotFoundException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogFile.WriteWarn("[MIFList-WriteCSV] Failed to write in a file.",true);
			LogFile.WriteWarn("Below is the stack trace.", false);
			LogFile.WriteWarn(str,false);
			
			return -1;
		}
		catch(UnsupportedEncodingException e) {
			LogFile.WriteWarn("[MIFList-WriteCSV] Unsupported encoding specified.",true);
			return -1;
		}
		
		return 0;
	}
}
