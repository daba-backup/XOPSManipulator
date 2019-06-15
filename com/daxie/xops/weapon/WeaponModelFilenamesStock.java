package com.daxie.xops.weapon;

import java.util.HashMap;
import java.util.Map;

import com.daxie.log.LogFile;
import com.daxie.xops.XOPSConstants;

public class WeaponModelFilenamesStock {
	private static Map<Integer, String> model_filenames_map;
	
	static{
		model_filenames_map=new HashMap<>();
		
		model_filenames_map.put(0, "");
		model_filenames_map.put(1, "./data/model/weapon/mp5.x");
		model_filenames_map.put(2, "./data/model/weapon/psg1.x");
		model_filenames_map.put(3, "./data/model/weapon/m92f.x");
		model_filenames_map.put(4, "./data/model/weapon/glock18.x");
		model_filenames_map.put(5, "./data/model/weapon/de.x");
		model_filenames_map.put(6, "./data/model/weapon/mac10.x");
		model_filenames_map.put(7, "./data/model/weapon/ump.x");
		model_filenames_map.put(8, "./data/model/weapon/p90.x");
		model_filenames_map.put(9, "./data/model/weapon/m4.x");
		model_filenames_map.put(10, "./data/model/weapon/ak47.x");
		model_filenames_map.put(11, "./data/model/weapon/aug.x");
		model_filenames_map.put(12, "./data/model/weapon/m249.x");
		model_filenames_map.put(13, "./data/model/weapon/grenade.x");
		model_filenames_map.put(14, "./data/model/weapon/mp5sd.x");
		model_filenames_map.put(15, "./data/model/weapon/case.x");
		model_filenames_map.put(16, "./data/model/weapon/cg.x");
		model_filenames_map.put(17, "./data/model/weapon/m1.x");
		model_filenames_map.put(18, "./data/model/weapon/famas.x");
		model_filenames_map.put(19, "./data/model/weapon/mk23.x");
		model_filenames_map.put(20, "./data/model/weapon/mk23sd.x");
	}
	
	public static void SetModelFilename(int index,String model_filename) {
		model_filenames_map.put(index, model_filename);
	}
	public static String GetModelFilename(WeaponModelType model_type) {
		int ordinal=model_type.ordinal();
		return model_filenames_map.get(ordinal);
	}
	
	public static boolean KeyExists(int index) {
		return model_filenames_map.containsKey(index);
	}
	
	public static WeaponModelType GetWeaponModelTypeFromFilename(String model_filename) {
		WeaponModelType model_type=WeaponModelType.NONE;
		
		if(model_filenames_map.size()!=XOPSConstants.WEAPON_MODEL_NUM) {
			LogFile.WriteError("[WeaponModelFilenamesStock-GetWeaponModelTypeFromFilename]");
			LogFile.WriteLine("The number of data stocked in the map is invalid and cannot convert the filename to an enum item.");
			
			return model_type;
		}
		if(model_filenames_map.containsValue(model_filename)==false) {
			LogFile.WriteError("[WeaponModelFilenamesStock-GetWeaponModelTypeFromFilename]");
			LogFile.WriteLine("No such filename in the map. filename:"+model_filename);
			
			return model_type;
		}
		
		for(int i=0;i<model_filenames_map.size();i++) {
			String filename=model_filenames_map.get(i);
			
			if(model_filename.equals(filename)==true) {
				WeaponModelType[] model_types=WeaponModelType.values();
				model_type=model_types[i];
				
				break;
			}
		}
		
		return model_type;
	}
}
