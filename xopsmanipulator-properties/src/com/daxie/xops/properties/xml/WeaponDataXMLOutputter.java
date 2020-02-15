package com.daxie.xops.properties.xml;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogWriter;
import com.daxie.tool.ExceptionFunctions;
import com.daxie.tool.XMLFunctions;
import com.daxie.xops.properties.entity.weapon.WeaponData;
import com.daxie.xops.properties.openxops.WeaponSpecifierConverter;

/**
 * Writes out weapon data in a XML file.
 * @author Daba
 *
 */
public class WeaponDataXMLOutputter {
	private List<WeaponData> weapon_data_list;
	private boolean openxops_compatible_flag;
	
	/**
	 * @param weapon_data_list List of weapon data
	 */
	public WeaponDataXMLOutputter(List<WeaponData> weapon_data_list) {
		this.weapon_data_list=weapon_data_list;
		openxops_compatible_flag=true;
	}
	
	public void SetOpenXOPSCompatibleFlag(boolean openxops_compatible_flag) {
		this.openxops_compatible_flag=openxops_compatible_flag;
	}
	
	/**
	 * Outputs a list of weapons in a XML file.
	 * @param xml_filename XML filename
	 * @return -1 on error and 0 on success
	 */
	public int WriteXML(String xml_filename) {
		if(weapon_data_list==null) {
			LogWriter.WriteWarn("[WeaponDataXMLOutputter-WriteXML] Data is null.",true);
			return -1;
		}
		
		DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder=null;
		try {
			builder=factory.newDocumentBuilder();
		}
		catch(ParserConfigurationException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			
			LogWriter.WriteWarn("[WeaponDataXMLOutputter-WriteXML] Below is the stack trace.",true);
			LogWriter.WriteWarn(str, false);
			
			return -1;
		}
		Document document=builder.newDocument();
		
		Element el_weapon_list=document.createElement("weapon_list");
		document.appendChild(el_weapon_list);
		
		int id=0;
		for(WeaponData weapon_data:weapon_data_list) {
			Element el_weapon=document.createElement("weapon");
			el_weapon.setAttribute("id", ""+id);
			
			//Name
			Element el_name=document.createElement("name");
			el_name.setTextContent(weapon_data.GetName());
			el_weapon.appendChild(el_name);
			//Model filename
			Element el_model_filename=document.createElement("model");
			el_model_filename.setTextContent(weapon_data.GetModelFilename());
			el_weapon.appendChild(el_model_filename);
			//Texture filename
			Element el_texture_filename=document.createElement("texture");
			el_texture_filename.setTextContent(weapon_data.GetTextureFilename());
			el_weapon.appendChild(el_texture_filename);
			//Attack power
			Element el_attack_power=document.createElement("attacks");
			el_attack_power.setTextContent(""+weapon_data.GetAttackPower());
			el_weapon.appendChild(el_attack_power);
			//Penetration
			Element el_penetration=document.createElement("penetration");
			el_penetration.setTextContent(""+weapon_data.GetPenetration());
			el_weapon.appendChild(el_penetration);
			//Firing interval
			Element el_firing_interval=document.createElement("blazings");
			el_firing_interval.setTextContent(""+weapon_data.GetFiringInterval());
			el_weapon.appendChild(el_firing_interval);
			//Bullet speed
			Element el_bullet_speed=document.createElement("speed");
			el_bullet_speed.setTextContent(""+weapon_data.GetBulletSpeed());
			el_weapon.appendChild(el_bullet_speed);
			//Number of bullets
			Element el_number_of_bullets=document.createElement("nbsmax");
			el_number_of_bullets.setTextContent(""+weapon_data.GetNumberOfBullets());
			el_weapon.appendChild(el_number_of_bullets);
			//Reloading time
			Element el_reloading_time=document.createElement("reloads");
			el_reloading_time.setTextContent(""+weapon_data.GetReloadingTime());
			el_weapon.appendChild(el_reloading_time);
			//Recoil
			Element el_recoil=document.createElement("reaction");
			el_recoil.setTextContent(""+weapon_data.GetRecoil());
			el_weapon.appendChild(el_recoil);
			//Minimal range of error
			Element el_error_range_min=document.createElement("ErrorRangeMIN");
			el_error_range_min.setTextContent(""+weapon_data.GetErrorRangeMin());
			el_weapon.appendChild(el_error_range_min);
			//Maximal range of error
			Element el_error_range_max=document.createElement("ErrorRangeMAX");
			el_error_range_max.setTextContent(""+weapon_data.GetErrorRangeMax());
			el_weapon.appendChild(el_error_range_max);
			//Model position
			Element el_position_x=document.createElement("mx");
			Element el_position_y=document.createElement("my");
			Element el_position_z=document.createElement("mz");
			Vector position=weapon_data.GetPosition();
			el_position_x.setTextContent(""+position.GetX());
			el_position_y.setTextContent(""+position.GetY());
			el_position_z.setTextContent(""+position.GetZ());
			el_weapon.appendChild(el_position_x);
			el_weapon.appendChild(el_position_y);
			el_weapon.appendChild(el_position_z);
			//Flash position
			Element el_flash_x=document.createElement("flashx");
			Element el_flash_y=document.createElement("flashy");
			Element el_flash_z=document.createElement("flashz");
			Vector flash_position=weapon_data.GetFlashPosition();
			el_flash_x.setTextContent(""+flash_position.GetX());
			el_flash_y.setTextContent(""+flash_position.GetY());
			el_flash_z.setTextContent(""+flash_position.GetZ());
			el_weapon.appendChild(el_flash_x);
			el_weapon.appendChild(el_flash_y);
			el_weapon.appendChild(el_flash_z);
			//Cartridge position
			Element el_cartridge_px=document.createElement("yakkyou_px");
			Element el_cartridge_py=document.createElement("yakkyou_py");
			Element el_cartridge_pz=document.createElement("yakkyou_pz");
			Vector cartridge_position=weapon_data.GetCartridgePosition();
			el_cartridge_px.setTextContent(""+cartridge_position.GetX());
			el_cartridge_py.setTextContent(""+cartridge_position.GetY());
			el_cartridge_pz.setTextContent(""+cartridge_position.GetZ());
			el_weapon.appendChild(el_cartridge_px);
			el_weapon.appendChild(el_cartridge_py);
			el_weapon.appendChild(el_cartridge_pz);
			//Cartridge velocity
			Element el_cartridge_sx=document.createElement("yakkyou_sx");
			Element el_cartridge_sy=document.createElement("yakkyou_sy");
			Vector cartridge_velocity=weapon_data.GetCartridgeVelocity();
			el_cartridge_sx.setTextContent(""+cartridge_velocity.GetX());
			el_cartridge_sy.setTextContent(""+cartridge_velocity.GetY());
			el_weapon.appendChild(el_cartridge_sx);
			el_weapon.appendChild(el_cartridge_sy);
			//Rapid fire
			Element el_rapid_fire=document.createElement("blazingmode");
			if(weapon_data.GetRapidFireEnabledFlag()==false)el_rapid_fire.setTextContent("false");
			else el_rapid_fire.setTextContent("true");
			el_weapon.appendChild(el_rapid_fire);
			//Scope mode
			Element el_scope_mode=document.createElement("scopemode");
			el_scope_mode.setTextContent(""+weapon_data.GetScopeMode().ordinal());
			el_weapon.appendChild(el_scope_mode);
			//Scale
			Element el_scale=document.createElement("size");
			el_scale.setTextContent(""+weapon_data.GetScale());
			el_weapon.appendChild(el_scale);
			//Sound ID
			int sound_id=weapon_data.GetSoundID();
			if(openxops_compatible_flag==true) {
				sound_id=WeaponSpecifierConverter.GetOpenXOPSSoundIDFromXOPSSoundID(sound_id);
			}
			Element el_sound_id=document.createElement("soundid");
			el_sound_id.setTextContent(""+sound_id);
			el_weapon.appendChild(el_sound_id);
			//Sound volume
			Element el_sound_volume=document.createElement("soundvolume");
			el_sound_volume.setTextContent(""+weapon_data.GetSoundVolume());
			el_weapon.appendChild(el_sound_volume);
			//Suppressor
			Element el_suppressor=document.createElement("silencer");
			if(weapon_data.GetSuppressorEnabledFlag()==false)el_suppressor.setTextContent("false");
			else el_suppressor.setTextContent("true");
			el_weapon.appendChild(el_suppressor);
			//Shooting stance
			Element el_shooting_stance=document.createElement("WeaponP");
			el_shooting_stance.setTextContent(""+weapon_data.GetShootingStance().ordinal());
			el_weapon.appendChild(el_shooting_stance);
			//Changeable weapon
			Element el_changeable_weapon=document.createElement("ChangeWeapon");
			el_changeable_weapon.setTextContent(""+weapon_data.GetChangeableWeapon());
			el_weapon.appendChild(el_changeable_weapon);
			//Number of projectiles
			Element el_number_of_projectiles=document.createElement("burst");
			el_number_of_projectiles.setTextContent(""+weapon_data.GetNumberOfProjectiles());
			el_weapon.appendChild(el_number_of_projectiles);
			
			el_weapon_list.appendChild(el_weapon);
			
			id++;
		}
		
		File file=new File(xml_filename);
		
		int res=XMLFunctions.WriteXML(file, document);
		if(res<0)return -1;
		
		return 0;
	}
}
