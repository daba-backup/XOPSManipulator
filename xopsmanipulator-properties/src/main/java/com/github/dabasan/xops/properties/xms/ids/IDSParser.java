package com.github.dabasan.xops.properties.xms.ids;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.tool.ByteFunctions;
import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.xops.properties.entity.weapon.WeaponBinSpecifierAndEnumConverter;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponModelFilenamesStock;
import com.github.dabasan.xops.properties.entity.weapon.WeaponModelType;
import com.github.dabasan.xops.properties.entity.weapon.WeaponScopeMode;
import com.github.dabasan.xops.properties.entity.weapon.WeaponShootingStance;
import com.github.dabasan.xops.properties.entity.weapon.WeaponTextureFilenamesStock;
import com.github.dabasan.xops.properties.entity.weapon.WeaponTextureType;

/**
 * Reads data from an IDS file.
 * 
 * @author Daba
 *
 */
class IDSParser {
	private Logger logger = LoggerFactory.getLogger(IDSParser.class);

	private WeaponData weapon_data;

	public IDSParser(String ids_filename) throws IOException {
		List<Byte> bin = FileFunctions.GetFileAllBin(ids_filename);
		weapon_data = new WeaponData();

		if (bin.size() != 84) {
			logger.warn("Invalid file size. ids_filename={}", ids_filename);
			return;
		}

		int itemp;
		Vector vtemp;
		String strtemp;

		int pos = 0x0000000A;

		// Attack power
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetAttackPower(itemp);
		pos += 2;

		// Penetration
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetPenetration(itemp);
		pos += 2;

		// Firing interval
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetFiringInterval(itemp);
		pos += 2;

		// Velocity
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetBulletSpeed(itemp);
		pos += 2;

		// Number of bullets
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetNumberOfBullets(itemp);
		pos += 2;

		// Reloading time
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetReloadingTime(itemp);
		pos += 2;

		// Recoil
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetRecoil(itemp);
		pos += 2;

		// Minimum range of error
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetErrorRangeMin(itemp);
		pos += 2;

		// Maximum range of error
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetErrorRangeMax(itemp);
		pos += 2;

		// Position
		vtemp = new Vector();

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetX(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetY(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetZ(itemp);
		pos += 2;

		weapon_data.SetPosition(vtemp);

		// Flash position
		vtemp = new Vector();

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetX(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetY(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetZ(itemp);
		pos += 2;

		weapon_data.SetFlashPosition(vtemp);

		// Cartridge position
		vtemp = new Vector();

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetX(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetY(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetZ(itemp);
		pos += 2;

		weapon_data.SetCartridgePosition(vtemp);

		// Shooting stance
		WeaponShootingStance shooting_stance;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		pos += 2;

		shooting_stance = WeaponBinSpecifierAndEnumConverter
				.GetWeaponShootingStanceFromBinSpecifier(itemp);
		weapon_data.SetShootingStance(shooting_stance);

		// Rapid fire
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		if (itemp == 0) {
			weapon_data.SetRapidFireEnabledFlag(true);
		} else {
			weapon_data.SetRapidFireEnabledFlag(false);
		}
		pos += 2;

		// Scope mode
		WeaponScopeMode scope_mode;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		pos += 2;

		scope_mode = WeaponBinSpecifierAndEnumConverter
				.GetWeaponScopeModeFromBinSpecifier(itemp);
		weapon_data.SetScopeMode(scope_mode);

		// Texture
		WeaponTextureType texture_type;
		String texture_filename;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		pos += 2;

		texture_type = WeaponBinSpecifierAndEnumConverter
				.GetWeaponTextureTypeFromBinSpecifier(itemp);
		texture_filename = WeaponTextureFilenamesStock
				.GetTextureFilename(texture_type);
		weapon_data.SetTextureFilename(texture_filename);

		// Model
		WeaponModelType model_type;
		String model_filename;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		pos += 2;

		model_type = WeaponBinSpecifierAndEnumConverter
				.GetWeaponModelTypeFromBinSpecifier(itemp);
		model_filename = WeaponModelFilenamesStock.GetModelFilename(model_type);
		weapon_data.SetModelFilename(model_filename);

		// Scale
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetScale(itemp * 0.1f);
		pos += 2;

		// Cartridge velocity
		vtemp = new Vector();

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetX(itemp);
		pos += 2;

		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		vtemp.SetY(itemp);
		pos += 2;

		vtemp.SetZ(0.0f);

		weapon_data.SetCartridgeVelocity(vtemp);

		// Sound ID
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetSoundID(itemp);
		pos += 2;

		// Sound volume
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		weapon_data.SetSoundVolume(itemp);
		pos += 2;

		// Suppressor
		itemp = ByteFunctions.GetShortValueFromBin_LE(bin, pos);
		if (itemp == 0) {
			weapon_data.SetSuppressorEnabledFlag(false);
		} else {
			weapon_data.SetSuppressorEnabledFlag(true);
		}
		pos += 2;

		// Name
		byte[] name_buffer = new byte[15 + 1];
		for (int i = 0; i < 15; i++) {
			name_buffer[i] = bin.get(pos + i);
		}
		name_buffer[15] = 0;

		strtemp = new String(name_buffer);

		int first_null_pos = 15;
		for (int i = 0; i < 16; i++) {
			if (strtemp.charAt(i) == '\0') {
				first_null_pos = i;
				break;
			}
		}

		strtemp = strtemp.substring(0, first_null_pos);
		weapon_data.SetName(strtemp);

		// Changeable weapon
		weapon_data.SetChangeableWeapon(-1);
	}

	public WeaponData GetWeaponData() {
		return weapon_data;
	}
}
