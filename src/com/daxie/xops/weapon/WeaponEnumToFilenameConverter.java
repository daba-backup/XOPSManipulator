package com.daxie.xops.weapon;

public class WeaponEnumToFilenameConverter {
	public static String GetModelFilenameFromWeaponModelType(WeaponModelType model_type) {
		String filename="";
		filename=WeaponModelFilenamesStock.GetModelFilename(model_type);
		
		return filename;
	}
	public static String GetTextureFilenameFromWeaponTextureType(WeaponTextureType texture_type) {
		String filename="";
		filename=WeaponTextureFilenamesStock.GetTextureFilename(texture_type);
		
		return filename;
	}
}
