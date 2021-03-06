package com.github.dabasan.xops.properties.openxops;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.tool.StringFunctions;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponScopeMode;
import com.github.dabasan.xops.properties.entity.weapon.WeaponShootingStance;

/**
 * Parses the source code of OpenXOPS and obtains weapon data.
 * 
 * @author Daba
 *
 */
public class WeaponDataCodeParser {
	private final Logger logger = LoggerFactory
			.getLogger(WeaponDataCodeParser.class);

	private Map<Integer, WeaponData> weapon_data_map;

	/**
	 * In case you already have lines of the code.
	 * 
	 * @param lines
	 *            Lines of the code
	 */
	public WeaponDataCodeParser(List<String> lines) {
		weapon_data_map = new HashMap<>();

		if (lines == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}

		this.ParseLines(lines);
	}
	/**
	 * Parses the code after loading it from a file.
	 * 
	 * @param code_filename
	 *            Filename of the code
	 * @param encoding
	 *            Encoding
	 * @throws IOException
	 */
	public WeaponDataCodeParser(String code_filename, String encoding)
			throws IOException {
		weapon_data_map = new HashMap<>();

		final List<String> lines = FileFunctions.GetFileAllLines(code_filename,
				encoding);
		this.ParseLines(lines);
	}

	private void ParseLines(List<String> lines) {
		for (int i = 0; i < lines.size(); i++) {
			String line = lines.get(i);
			line = line.replace(";", "");

			final String[] split_by_equal = line.split("=");
			if (split_by_equal.length != 2) {
				logger.warn("Syntax error. line:{}", i);
				continue;
			}

			final String left = split_by_equal[0];
			final String[] split_by_dot = left.split(Pattern.quote("."));

			if (split_by_dot.length != 2) {
				logger.warn("Syntax error. line:{}", i);
				continue;
			}

			String field_name = split_by_dot[1];
			field_name = field_name.replace(" ", "");

			final String array_name_and_index = split_by_dot[0];
			final String index_str = StringFunctions
					.GetFirstStringInBetween(array_name_and_index, '[', ']');

			int index = -1;
			try {
				index = Integer.parseInt(index_str);
			} catch (final NumberFormatException e) {
				logger.warn("Invalid index. line:{}", i);
				continue;
			}
			if (index < 0) {
				logger.warn(
						"Index must be a number greater than or equal to zero.");
				continue;
			}

			// Create weapon data if it does not exist in the map.
			if (weapon_data_map.containsKey(index) == false) {
				final WeaponData weapon_data = new WeaponData();
				weapon_data_map.put(index, weapon_data);
			}

			final WeaponData weapon_data = weapon_data_map.get(index);
			String value = split_by_equal[1];

			int itemp;
			float ftemp;
			Vector vtemp;

			try {
				switch (field_name) {
					case "name" :
						value = StringFunctions.GetFirstStringInBetween(value,
								'\"', '\"');
						weapon_data.SetName(value);
						break;
					case "model" :
						value = StringFunctions.GetFirstStringInBetween(value,
								'\"', '\"');
						weapon_data.SetModelFilename(value);
						break;
					case "texture" :
						value = StringFunctions.GetFirstStringInBetween(value,
								'\"', '\"');
						weapon_data.SetTextureFilename(value);
						break;
					case "attacks" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetAttackPower(itemp);
						break;
					case "penetration" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetPenetration(itemp);
						break;
					case "blazings" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetFiringInterval(itemp);
						break;
					case "speed" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetBulletSpeed(itemp);
						break;
					case "nbsmax" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetNumberOfBullets(itemp);
						break;
					case "reloads" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetReloadingTime(itemp);
						break;
					case "reaction" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetRecoil(itemp);
						break;
					case "ErrorRangeMIN" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetErrorRangeMin(itemp);
						break;
					case "ErrorRangeMAX" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetErrorRangeMax(itemp);
						break;
					case "mx" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetX(ftemp);
						weapon_data.SetPosition(vtemp);
						break;
					case "my" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetY(ftemp);
						weapon_data.SetPosition(vtemp);
						break;
					case "mz" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetZ(ftemp);
						weapon_data.SetPosition(vtemp);
						break;
					case "flashx" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetFlashPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetX(ftemp);
						weapon_data.SetFlashPosition(vtemp);
						break;
					case "flashy" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetFlashPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetY(ftemp);
						weapon_data.SetFlashPosition(vtemp);
						break;
					case "flashz" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetFlashPosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetZ(ftemp);
						weapon_data.SetFlashPosition(vtemp);
						break;
					case "yakkyou_px" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetCartridgePosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetX(ftemp);
						weapon_data.SetCartridgePosition(vtemp);
						break;
					case "yakkyou_py" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetCartridgePosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetY(ftemp);
						weapon_data.SetCartridgePosition(vtemp);
						break;
					case "yakkyou_pz" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetCartridgePosition();
						ftemp = Float.parseFloat(value);
						vtemp.SetZ(ftemp);
						weapon_data.SetCartridgePosition(vtemp);
						break;
					case "yakkyou_sx" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetCartridgeVelocity();
						ftemp = Float.parseFloat(value);
						vtemp.SetX(ftemp);
						weapon_data.SetCartridgeVelocity(vtemp);
						break;
					case "yakkyou_sy" :
						value = value.replace(" ", "");
						vtemp = weapon_data.GetCartridgeVelocity();
						ftemp = Float.parseFloat(value);
						vtemp.SetY(ftemp);
						weapon_data.SetCartridgeVelocity(vtemp);
						break;
					case "blazingmode" :
						value = value.replace(" ", "");
						if (value.equals("false")) {
							weapon_data.SetRapidFireEnabledFlag(false);
						} else if (value.equals("true")) {
							weapon_data.SetRapidFireEnabledFlag(true);
						} else {
							logger.warn("Invalid value. line:{}", i);
							continue;
						}
						break;
					case "scopemode" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						final WeaponScopeMode[] scope_modes = WeaponScopeMode
								.values();
						if (!(0 <= itemp && itemp < scope_modes.length)) {
							logger.warn("Value out of bounds. line:{}", i);
							continue;
						}
						weapon_data.SetScopeMode(scope_modes[itemp]);
						break;
					case "size" :
						value = value.replace(" ", "");
						ftemp = Float.parseFloat(value);
						weapon_data.SetScale(ftemp);
						break;
					case "soundid" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						itemp = WeaponSpecifierConverter
								.GetXOPSSoundIDFromOpenXOPSSoundID(itemp);
						weapon_data.SetSoundID(itemp);
						break;
					case "soundvolume" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetSoundVolume(itemp);
						break;
					case "silencer" :
						value = value.replace(" ", "");
						if (value.equals("false")) {
							weapon_data.SetSuppressorEnabledFlag(false);
						} else if (value.equals("true")) {
							weapon_data.SetSuppressorEnabledFlag(true);
						} else {
							logger.warn("Invalid value. line:{}", i);
							continue;
						}
						break;
					case "WeaponP" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						final WeaponShootingStance[] shooting_stances = WeaponShootingStance
								.values();
						if (!(0 <= itemp && itemp < shooting_stances.length)) {
							logger.warn("Value out of bounds. line:{}", i);
							continue;
						}
						weapon_data.SetShootingStance(shooting_stances[itemp]);
						break;
					case "ChangeWeapon" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetChangeableWeapon(itemp);
						break;
					case "burst" :
						value = value.replace(" ", "");
						itemp = Integer.parseInt(value);
						weapon_data.SetNumberOfProjectiles(itemp);
						break;
					default :
						logger.warn("Unknown field name. line:{}", i);
						continue;
				}
			} catch (final NumberFormatException e) {
				logger.warn("Invalid number format. line:{}", i);
				continue;
			}

			weapon_data_map.put(index, weapon_data);
		}
	}

	public HashMap<Integer, WeaponData> GetData() {
		return new HashMap<>(weapon_data_map);
	}
	public TreeMap<Integer, WeaponData> GetOrderedData() {
		return new TreeMap<>(weapon_data_map);
	}
}
