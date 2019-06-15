package com.daxie.xops.weapon;

import com.daxie.log.LogFile;

public class WeaponBinSpecifierAndEnumConverter {
	public static WeaponModelType GetWeaponModelTypeFromBinSpecifier(int spc) {
		WeaponModelType model_type;
		
		switch(spc) {
		case 0x00:
			model_type=WeaponModelType.NONE;
			break;
		case 0x0B:
			model_type=WeaponModelType.MP5;
			break;
		case 0x0A:
			model_type=WeaponModelType.PSG_1;
			break;
		case 0x0D:
			model_type=WeaponModelType.M92F;
			break;
		case 0x10:
			model_type=WeaponModelType.GLOCK;
			break;
		case 0x15:
			model_type=WeaponModelType.DESERT_EAGLE;
			break;
		case 0x0E:
			model_type=WeaponModelType.MAC10;
			break;
		case 0x1E:
			model_type=WeaponModelType.UMP;
			break;
		case 0x0F:
			model_type=WeaponModelType.P90;
			break;
		case 0x1D:
			model_type=WeaponModelType.M4;
			break;
		case 0x18:
			model_type=WeaponModelType.AK47;
			break;
		case 0x16:
			model_type=WeaponModelType.AUG;
			break;
		case 0x1C:
			model_type=WeaponModelType.M249;
			break;
		case 0x17:
			model_type=WeaponModelType.GRENADE;
			break;
		case 0x19:
			model_type=WeaponModelType.MP5SD;
			break;
		case 0x20:
			model_type=WeaponModelType.CASE;
			break;
		case 0x22:
			model_type=WeaponModelType.M1911;
			break;
		case 0x39:
			model_type=WeaponModelType.M1;
			break;
		case 0x3A:
			model_type=WeaponModelType.FAMAS;
			break;
		case 0x3B:
			model_type=WeaponModelType.MK23;
			break;
		case 0x3C:
			model_type=WeaponModelType.MK23SD;
			break;
		default:
			LogFile.WriteWarn("[WeaponBinSpecifierAndEnumConverter-GetWeaponModelTypeFromBinSpecifier]");
			LogFile.WriteLine("Unknown model type specifier. specifier:"+spc);
			
			model_type=WeaponModelType.NONE;
			break;
		}
		
		return model_type;
	}
	public static int GetBinSpecifierFromWeaponModelType(WeaponModelType model_type) {
		int spc=0x00;
		
		switch(model_type) {
		case NONE:
			spc=0x00;
			break;
		case MP5:
			spc=0x0B;
			break;
		case PSG_1:
			spc=0x0A;
			break;
		case M92F:
			spc=0x0D;
			break;
		case GLOCK:
			spc=0x10;
			break;
		case DESERT_EAGLE:
			spc=0x15;
			break;
		case MAC10:
			spc=0x0E;
			break;
		case UMP:
			spc=0x1E;
			break;
		case P90:
			spc=0x0F;
			break;
		case M4:
			spc=0x1D;
			break;
		case AK47:
			spc=0x18;
			break;
		case AUG:
			spc=0x16;
			break;
		case M249:
			spc=0x1C;
			break;
		case GRENADE:
			spc=0x17;
			break;
		case MP5SD:
			spc=0x19;
			break;
		case CASE:
			spc=0x20;
			break;
		case M1911:
			spc=0x22;
			break;
		case M1:
			spc=0x39;
			break;
		case FAMAS:
			spc=0x3A;
			break;
		case MK23:
			spc=0x3B;
			break;
		case MK23SD:
			spc=0x3C;
			break;
		}
		
		return spc;
	}
	public static WeaponTextureType GetWeaponTextureTypeFromBinSpecifier(int spc) {
		WeaponTextureType texture_type;
		
		switch(spc) {
		case 0x00:
			texture_type=WeaponTextureType.NONE;
			break;
		case 0x10:
			texture_type=WeaponTextureType.MP5;
			break;
		case 0x0B:
			texture_type=WeaponTextureType.PSG_1;
			break;
		case 0x13:
			texture_type=WeaponTextureType.M92F;
			break;
		case 0x11:
			texture_type=WeaponTextureType.GLOCK18;
			break;
		case 0x32:
			texture_type=WeaponTextureType.DESERT_EAGLE;
			break;
		case 0x28:
			texture_type=WeaponTextureType.MAC10;
			break;
		case 0x27:
			texture_type=WeaponTextureType.UMP;
			break;
		case 0x24:
			texture_type=WeaponTextureType.P90;
			break;
		case 0x26:
			texture_type=WeaponTextureType.M4;
			break;
		case 0x21:
			texture_type=WeaponTextureType.AK47;
			break;
		case 0x33:
			texture_type=WeaponTextureType.AUG;
			break;
		case 0x25:
			texture_type=WeaponTextureType.M249;
			break;
		case 0x20:
			texture_type=WeaponTextureType.GRENADE;
			break;
		case 0x22:
			texture_type=WeaponTextureType.MP5SD;
			break;
		case 0x2C:
			texture_type=WeaponTextureType.CASE;
			break;
		case 0x2D:
			texture_type=WeaponTextureType.M1911;
			break;
		case 0x30:
			texture_type=WeaponTextureType.GLOCK17;
			break;
		case 0x36:
			texture_type=WeaponTextureType.M1;
			break;
		case 0x37:
			texture_type=WeaponTextureType.FAMAS;
			break;
		case 0x38:
			texture_type=WeaponTextureType.MK23;
			break;
		default:
			LogFile.WriteWarn("[WeaponBinSpecifierAndEnumConverter-GetWeaponTextureTypeFromBinSpecifier]");
			LogFile.WriteLine("Unknown texture type specifier. specifier:"+spc);
			
			texture_type=WeaponTextureType.NONE;
			break;
		}
		
		return texture_type;
	}
	public static int GetBinSpecifierFromWeaponTextureType(WeaponTextureType texture_type) {
		int spc=0x00;
		
		switch(texture_type) {
		case NONE:
			spc=0x00;
			break;
		case MP5:
			spc=0x10;
			break;
		case PSG_1:
			spc=0x0B;
			break;
		case M92F:
			spc=0x13;
			break;
		case GLOCK18:
			spc=0x11;
			break;
		case DESERT_EAGLE:
			spc=0x32;
			break;
		case MAC10:
			spc=0x28;
			break;
		case UMP:
			spc=0x27;
			break;
		case P90:
			spc=0x24;
			break;
		case M4:
			spc=0x26;
			break;
		case AK47:
			spc=0x21;
			break;
		case AUG:
			spc=0x33;
			break;
		case M249:
			spc=0x25;
			break;
		case GRENADE:
			spc=0x20;
			break;
		case MP5SD:
			spc=0x22;
			break;
		case CASE:
			spc=0x2C;
			break;
		case M1911:
			spc=0x2D;
			break;
		case GLOCK17:
			spc=0x30;
			break;
		case M1:
			spc=0x36;
			break;
		case FAMAS:
			spc=0x37;
			break;
		case MK23:
			spc=0x38;
			break;
		}
		
		return spc;
	}
	public static WeaponEquipmentMethod GetWeaponEquipmentMethodFromBinSpecifier(int spc) {
		WeaponEquipmentMethod equipment_method;
		
		switch(spc) {
		case 0x08:
			equipment_method=WeaponEquipmentMethod.RIFLE;
			break;
		case 0x09:
			equipment_method=WeaponEquipmentMethod.HANDGUN;
			break;
		case 0x1B:
			equipment_method=WeaponEquipmentMethod.CARRY;
			break;
		default:
			LogFile.WriteWarn("[WeaponBinSpecifierAndEnumConverter-GetWeaponEquipmentMethodFromBinSpecifier]");
			LogFile.WriteLine("Unknown equipment method specifier. specifier:"+spc);
			
			equipment_method=WeaponEquipmentMethod.RIFLE;
			break;
		}
		
		return equipment_method;
	}
	public static int GetBinSpecifierFromWeaponEquipmentMethod(WeaponEquipmentMethod equipment_method) {
		int spc=0x08;
		
		switch(equipment_method) {
		case RIFLE:
			spc=0x08;
			break;
		case HANDGUN:
			spc=0x09;
			break;
		case CARRY:
			spc=0x1B;
			break;
		}
		
		return spc;
	}
	public static WeaponScopeMode GetWeaponScopeModeFromBinSpecifier(int spc) {
		WeaponScopeMode scope_mode;
		
		switch(spc) {
		case 0x00:
			scope_mode=WeaponScopeMode.NONE;
			break;
		case 0x01:
			scope_mode=WeaponScopeMode.LOW;
			break;
		case 0x02:
			scope_mode=WeaponScopeMode.HIGH;
			break;
		case 0x03:
			scope_mode=WeaponScopeMode.EQUAL;
			break;
		default:
			LogFile.WriteWarn("[WeaponBinSpecifierAndEnumConverter-GetWeaponScopeModeFromBinSpecifier]");
			LogFile.WriteLine("Unknown scope mode specifier. specifier:"+spc);
			
			scope_mode=WeaponScopeMode.NONE;
			break;
		}
		
		return scope_mode;
	}
	public static int GetBinSpecifierFromWeaponScopeMode(WeaponScopeMode scope_mode) {
		int spc=0x00;
		
		switch(scope_mode) {
		case NONE:
			spc=0x00;
			break;
		case LOW:
			spc=0x01;
			break;
		case HIGH:
			spc=0x02;
			break;
		case EQUAL:
			spc=0x03;
			break;
		}
		
		return spc;
	}
}
