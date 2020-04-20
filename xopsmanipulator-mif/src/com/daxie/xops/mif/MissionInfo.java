package com.daxie.xops.mif;

import java.util.ArrayList;
import java.util.List;

/**
 * Mission information
 * @author Daba
 *
 */
public class MissionInfo {
	private String mission_name;
	private String mission_formal_name;
	private String bd1_filename;
	private String pd1_filename;
	private SkyType sky_type;
	private boolean extra_hitcheck_flag;
	private boolean darken_screen_flag;
	private String article_info_filename;
	private String image1_filename;
	private String image2_filename;
	private List<String> briefing_text;//per line
	
	public MissionInfo() {
		mission_name="";
		mission_formal_name="";
		bd1_filename="./";
		pd1_filename="./addon/";
		sky_type=SkyType.NONE;
		extra_hitcheck_flag=false;
		darken_screen_flag=false;
		article_info_filename="!";
		image1_filename="./data/briefing/np.bmp";
		image2_filename="!";
		briefing_text=new ArrayList<>();
	}
	public MissionInfo(MissionInfo mi) {
		mission_name=mi.GetMissionName();
		mission_formal_name=mi.GetMissionFormalName();
		bd1_filename=mi.GetBD1Filename();
		pd1_filename=mi.GetPD1Filename();
		sky_type=mi.GetSkyType();
		extra_hitcheck_flag=mi.GetExtraHitcheckFlag();
		darken_screen_flag=mi.GetDarkenScreenFlag();
		article_info_filename=mi.GetArticleInfoFilename();
		image1_filename=mi.GetImage1Filename();
		image2_filename=mi.GetImage2Filename();
		briefing_text=mi.GetBriefingText();
	}
	
	@Override
	public String toString() {
		String ret="";
		final String separator=System.getProperty("line.separator");
		
		ret+="[mission name]"+separator;
		ret+=mission_name+separator;
		ret+="[mission formal name]"+separator;
		ret+=mission_formal_name+separator;
		ret+="[bd1 filename]"+separator;
		ret+=bd1_filename+separator;
		ret+="[pd1 filename]"+separator;
		ret+=pd1_filename+separator;
		ret+="[sky type]"+separator;
		ret+=sky_type.toString()+separator;
		ret+="[extra hitcheck flag]"+separator;
		ret+=extra_hitcheck_flag+separator;
		ret+="[darken screen flag]"+separator;
		ret+=darken_screen_flag+separator;
		ret+="[article info filename]"+separator;
		ret+=article_info_filename+separator;
		ret+="[image 1 filename]"+separator;
		ret+=image1_filename+separator;
		ret+="[image 2 filename]"+separator;
		ret+=image2_filename+separator;
		
		ret+="[briefing text]"+separator;
		int line_num=briefing_text.size();
		for(int i=0;i<line_num;i++) {
			ret+=briefing_text.get(i);
			if(i!=line_num-1) {
				ret+=separator;
			}
		}
		
		return ret;
	}
	
	public void SetMissionName(String mission_name) {
		this.mission_name=mission_name;
	}
	public void SetMissionFormalName(String mission_formal_name) {
		this.mission_formal_name=mission_formal_name;
	}
	public void SetBD1Filename(String bd1_filename) {
		this.bd1_filename=bd1_filename;
	}
	public void SetPD1Filename(String pd1_filename) {
		this.pd1_filename=pd1_filename;
	}
	public void SetSkyType(SkyType sky_type) {
		this.sky_type=sky_type;
	}
	public void SetExtraHitcheckFlag(boolean extra_hitcheck_flag) {
		this.extra_hitcheck_flag=extra_hitcheck_flag;
	}
	public void SetDarkenScreenFlag(boolean darken_screen_flag) {
		this.darken_screen_flag=darken_screen_flag;
	}
	public void SetArticleInfoFilename(String article_info_filename) {
		this.article_info_filename=article_info_filename;
	}
	public void SetImage1Filename(String image1_filename) {
		this.image1_filename=image1_filename;
	}
	public void SetImage2Filename(String image2_filename) {
		this.image2_filename=image2_filename;
	}
	public void AddBriefingTextLine(String line) {
		briefing_text.add(line);
	}
	
	public String GetMissionName() {
		return mission_name;
	}
	public String GetMissionFormalName() {
		return mission_formal_name;
	}
	public String GetBD1Filename() {
		return bd1_filename;
	}
	public String GetPD1Filename() {
		return pd1_filename;
	}
	public SkyType GetSkyType() {
		return sky_type;
	}
	public boolean GetExtraHitcheckFlag() {
		return extra_hitcheck_flag;
	}
	public boolean GetDarkenScreenFlag() {
		return darken_screen_flag;
	}
	public String GetArticleInfoFilename() {
		return article_info_filename;
	}
	public String GetImage1Filename() {
		return image1_filename;
	}
	public String GetImage2Filename() {
		return image2_filename;
	}
	public List<String> GetBriefingText() {
		return new ArrayList<>(briefing_text);
	}
}
