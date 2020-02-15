package com.daxie.xops.properties.xml;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogWriter;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.xops.properties.entity.weapon.WeaponData;
import com.daxie.xops.properties.entity.weapon.WeaponScopeMode;
import com.daxie.xops.properties.entity.weapon.WeaponShootingStance;
import com.daxie.xops.properties.openxops.WeaponSpecifierConverter;

/**
 * Loads weapon data from a XML file created using this library.
 * @author Daba
 *
 */
public class WeaponDataXMLParser {
	private Map<Integer, WeaponData> weapon_data_map;
	private boolean openxops_compatible_flag;
	
	public WeaponDataXMLParser() {
		weapon_data_map=new HashMap<>();
		openxops_compatible_flag=true;
	}
	
	public void SetOpenXOPSCompatibleFlag(boolean openxops_compatible_flag) {
		this.openxops_compatible_flag=openxops_compatible_flag;
	}
	
	/**
	 * Loads weapon data from a XML file.
	 * @param xml_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int LoadWeaponDataXML(String xml_filename) {
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[WeaponDataXMLParser-LoadWeaponDataXML] Below is the stack trace.",true);
			LogWriter.WriteWarn(str,false);
			
			return -1;
		}
		
		Document document=null;
		try {
			document=builder.parse(Paths.get(xml_filename).toFile());
		}
		catch(Exception e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[WeaponDataXMLParser-LoadWeaponDataXML] Below is the stack trace.",true);
			LogWriter.WriteWarn(str,false);
			
			return -1;
		}
		
		Element el_weapon_data_list=document.getDocumentElement();
		NodeList node_list=el_weapon_data_list.getChildNodes();
		
		for(int i=0;i<node_list.getLength();i++) {
			Node node=node_list.item(i);
			if(node.getNodeType()!=Node.ELEMENT_NODE)continue;
			
			Element element=(Element)node;
			String element_name=element.getNodeName();
			
			if(element_name.equals("weapon")) {
				int weapon_id=-1;
				WeaponData weapon_data=new WeaponData();
				
				String strtemp;
				int itemp;
				float ftemp;
				
				strtemp=element.getAttribute("id");
				try {
					weapon_id=Integer.parseInt(strtemp);
				}
				catch(NumberFormatException e) {
					LogWriter.WriteWarn("[WeaponDataXMLParser-LoadWeaponDataXML] Invalid format of number. id:"+strtemp,true);
					continue;
				}
				
				NodeList node_list_sub=element.getChildNodes();
				for(int j=0;j<node_list_sub.getLength();j++) {
					Node node_sub=node_list_sub.item(j);
					if(node_sub.getNodeType()!=Node.ELEMENT_NODE)continue;
					
					Element element_sub=(Element)node_sub;
					String element_sub_name=element_sub.getNodeName();
					
					strtemp=element_sub.getTextContent();
					
					//Name
					if(element_sub_name.equals("name")) {
						weapon_data.SetName(strtemp);
					}
					//Model filename
					else if(element_sub_name.equals("model")) {
						weapon_data.SetModelFilename(strtemp);
					}
					//Texture filename
					else if(element_sub_name.equals("texture")) {
						weapon_data.SetTextureFilename(strtemp);
					}
					//Attack power
					else if(element_sub_name.equals("attacks")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetAttackPower(itemp);
					}
					//Penetration
					else if(element_sub_name.equals("penetration")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetPenetration(itemp);
					}
					//Firing interval
					else if(element_sub_name.equals("blazings")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetFiringInterval(itemp);
					}
					//Bullet speed
					else if(element_sub_name.equals("speed")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetBulletSpeed(itemp);
					}
					//Number of bullets
					else if(element_sub_name.equals("nbsmax")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetNumberOfBullets(itemp);
					}
					//Reloading time
					else if(element_sub_name.equals("reloads")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetReloadingTime(itemp);
					}
					//Recoil
					else if(element_sub_name.equals("reaction")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetRecoil(itemp);
					}
					//Minimal range of error
					else if(element_sub_name.equals("ErrorRangeMIN")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetErrorRangeMin(itemp);
					}
					//Maximal range of error
					else if(element_sub_name.equals("ErrorRangeMAX")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetErrorRangeMax(itemp);
					}
					//Model position x
					else if(element_sub_name.equals("mx")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetPosition();
						vtemp.SetX(ftemp);
						
						weapon_data.SetPosition(vtemp);
					}
					//Model position y
					else if(element_sub_name.equals("my")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetPosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetY(ftemp);
						
						weapon_data.SetPosition(vtemp);
					}
					//Model position z
					else if(element_sub_name.equals("mz")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetPosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetZ(ftemp);
						
						weapon_data.SetPosition(vtemp);
					}
					//Flash position x
					else if(element_sub_name.equals("flashx")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetFlashPosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetX(ftemp);
						
						weapon_data.SetFlashPosition(vtemp);
					}
					//Flash position y
					else if(element_sub_name.equals("flashy")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetFlashPosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetY(ftemp);
						
						weapon_data.SetFlashPosition(vtemp);
					}
					//Flash position z
					else if(element_sub_name.equals("flashz")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetFlashPosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetZ(ftemp);
						
						weapon_data.SetFlashPosition(vtemp);
					}
					//Cartridge position x
					else if(element_sub_name.equals("yakkyou_px")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetCartridgePosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetX(ftemp);
						
						weapon_data.SetCartridgePosition(vtemp);
					}
					//Cartridge position y
					else if(element_sub_name.equals("yakkyou_py")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetCartridgePosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetY(ftemp);
						
						weapon_data.SetCartridgePosition(vtemp);
					}
					//Cartridge position z
					else if(element_sub_name.equals("yakkyou_pz")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetCartridgePosition();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetZ(ftemp);
						
						weapon_data.SetCartridgePosition(vtemp);
					}
					//Cartridge velocity x
					else if(element_sub_name.equals("yakkyou_sx")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetCartridgeVelocity();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetX(ftemp);
						
						weapon_data.SetCartridgeVelocity(vtemp);
					}
					//Cartridge velocity y
					else if(element_sub_name.equals("yakkyou_sy")) {
						ftemp=Float.parseFloat(strtemp);
						
						Vector vtemp=weapon_data.GetCartridgeVelocity();
						ftemp=Float.parseFloat(strtemp);
						vtemp.SetY(ftemp);
						
						weapon_data.SetCartridgeVelocity(vtemp);
					}
					//Rapid fire
					else if(element_sub_name.equals("blazingmode")) {
						if(strtemp.equals("false"))weapon_data.SetRapidFireEnabledFlag(false);
						else weapon_data.SetRapidFireEnabledFlag(true);
					}
					//Scope mode
					else if(element_sub_name.equals("scopemode")) {
						itemp=Integer.parseInt(strtemp);
						
						WeaponScopeMode[] values=WeaponScopeMode.values();
						if(!(0<=itemp&&itemp<values.length)) {
							LogWriter.WriteWarn("[WeaponDataXMLParser-LoadWeaponDataXML] Specifier out of bounds.",true);
							
							String str="";
							str+="weapon_id:"+weapon_id+" ";
							str+="scope_mode:"+itemp;
							LogWriter.WriteLine(str);
							
							weapon_data.SetScopeMode(WeaponScopeMode.NONE);
						}
						else {
							weapon_data.SetScopeMode(values[itemp]);
						}
					}
					//Scale
					else if(element_sub_name.equals("size")) {
						ftemp=Float.parseFloat(strtemp);
						weapon_data.SetScale(ftemp);
					}
					//Sound ID
					else if(element_sub_name.equals("soundid")) {
						itemp=Integer.parseInt(strtemp);
						
						if(openxops_compatible_flag==true) {
							itemp=WeaponSpecifierConverter.GetXOPSSoundIDFromOpenXOPSSoundID(itemp);
						}
						weapon_data.SetSoundID(itemp);
					}
					//Sound volume
					else if(element_sub_name.equals("soundvolume")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetSoundVolume(itemp);
					}
					//Suppressor
					else if(element_sub_name.equals("silencer")) {
						if(strtemp.equals("false"))weapon_data.SetSuppressorEnabledFlag(false);
						else weapon_data.SetSuppressorEnabledFlag(true);
					}
					//Shooting stance
					else if(element_sub_name.equals("WeaponP")) {
						itemp=Integer.parseInt(strtemp);
						
						WeaponShootingStance[] values=WeaponShootingStance.values();
						if(!(0<=itemp&&itemp<values.length)) {
							LogWriter.WriteWarn("[WeaponDataXMLParser-LoadWeaponDataXML] Specifier out of bounds.",true);
							
							String str="";
							str+="weapon_id:"+weapon_id+" ";
							str+="shooting_stance:"+itemp;
							LogWriter.WriteLine(str);
							
							weapon_data.SetShootingStance(WeaponShootingStance.RIFLE);
						}
						else weapon_data.SetShootingStance(values[itemp]);
					}
					//Changeable weapon
					else if(element_sub_name.equals("ChangeWeapon")) {
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetChangeableWeapon(itemp);
					}
					//Number of projectiles
					else if(element_sub_name.equals("burst")){
						itemp=Integer.parseInt(strtemp);
						weapon_data.SetNumberOfProjectiles(itemp);
					}
				}
				
				weapon_data_map.put(weapon_id, weapon_data);
			}
		}
		
		return 0;
	}
	
	public Map<Integer, WeaponData> GetWeaponDataMap(){
		return new HashMap<>(weapon_data_map);
	}
}
