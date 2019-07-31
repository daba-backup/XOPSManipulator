package com.daxie.xops.weapon;

import java.util.HashMap;
import java.util.Map;

import com.daxie.log.LogFile;
import com.daxie.xops.XOPSConstants;

public class WeaponTextureFilenamesStock {
	private static Map<Integer, String> texture_filenames_map;
	
	static{
		texture_filenames_map=new HashMap<>();
		
		texture_filenames_map.put(0, "");
		texture_filenames_map.put(1, "./data/model/weapon/mp5.bmp");
		texture_filenames_map.put(2, "./data/model/weapon/psg1.bmp");
		texture_filenames_map.put(3, "./data/model/weapon/m92f.bmp");
		texture_filenames_map.put(4, "./data/model/weapon/glock18.bmp");
		texture_filenames_map.put(5, "./data/model/weapon/de.bmp");
		texture_filenames_map.put(6, "./data/model/weapon/mac10.bmp");
		texture_filenames_map.put(7, "./data/model/weapon/ump.bmp");
		texture_filenames_map.put(8, "./data/model/weapon/p90.bmp");
		texture_filenames_map.put(9, "./data/model/weapon/m4.bmp");
		texture_filenames_map.put(10, "./data/model/weapon/ak47.bmp");
		texture_filenames_map.put(11, "./data/model/weapon/aug.bmp");
		texture_filenames_map.put(12, "./data/model/weapon/m249.bmp");
		texture_filenames_map.put(13, "./data/model/weapon/grenade.bmp");
		texture_filenames_map.put(14, "./data/model/weapon/mp5sd.bmp");
		texture_filenames_map.put(15, "./data/model/weapon/case.bmp");
		texture_filenames_map.put(16, "./data/model/weapon/cg.bmp");
		texture_filenames_map.put(17,"./data/model/weapon/glock17.bmp");
		texture_filenames_map.put(18, "./data/model/weapon/m1.bmp");
		texture_filenames_map.put(19, "./data/model/weapon/famas.bmp");
		texture_filenames_map.put(20, "./data/model/weapon/mk23.bmp");
	}
	
	public static void SetTextureFilename(int index,String texture_filename) {
		texture_filenames_map.put(index, texture_filename);
	}
	public static String GetTextureFilename(WeaponTextureType texture_type) {
		int ordinal=texture_type.ordinal();
		return texture_filenames_map.get(ordinal);
	}
	
	public static boolean KeyExists(int index) {
		return texture_filenames_map.containsKey(index);
	}
	
	public static WeaponTextureType GetWeaponTextureTypeFromFilename(String texture_filename) {
		WeaponTextureType texture_type=WeaponTextureType.NONE;
		
		if(texture_filenames_map.size()!=XOPSConstants.WEAPON_TEXTURE_NUM) {
			LogFile.WriteError("[WeaponTextureFilenamesStock-GetWeaponTextureTypeFromFilename]");
			LogFile.WriteLine("The number of data stocked in the map is invalid and cannot convert the filename to an enum item.");
			
			return texture_type;
		}
		if(texture_filenames_map.containsValue(texture_filename)==false) {
			LogFile.WriteError("[WeaponTextureFilenamesStock-GetWeaponTextureTypeFromFilename]");
			LogFile.WriteLine("No such filename in the map. filename:"+texture_filename);
			
			return texture_type;
		}
		
		for(int i=0;i<texture_filenames_map.size();i++) {
			String filename=texture_filenames_map.get(i);
			
			if(texture_filename.equals(filename)==true) {
				WeaponTextureType[] texture_types=WeaponTextureType.values();
				texture_type=texture_types[i];
				
				break;
			}
		}
		
		return texture_type;
	}
}
