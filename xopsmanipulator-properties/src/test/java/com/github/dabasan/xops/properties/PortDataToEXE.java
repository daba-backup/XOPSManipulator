package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;
import com.github.dabasan.xops.properties.xms.xcs.XCSManipulator;
import com.github.dabasan.xops.properties.xms.xgs.XGSManipulator;

public class PortDataToEXE {
	public static void main(String[] args) {
		XGSManipulator xgs_manipulator;
		XCSManipulator xcs_manipulator;
		XOPSExeManipulator exe_manipulator;

		try {
			xgs_manipulator = new XGSManipulator("./TestData/weapons.xgs");
			xcs_manipulator = new XCSManipulator("./TestData/characters.xcs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		exe_manipulator = new XOPSExeManipulator();

		WeaponData[] weapon_data = xgs_manipulator.GetWeaponDataArray();
		CharacterData[] character_data = xcs_manipulator
				.GetCharacterDataArray();

		exe_manipulator.SetWeaponDataArray(weapon_data);
		exe_manipulator.SetCharacterDataArray(character_data);

		exe_manipulator.Write("./TestData/xops0975t.exe", true);
	}
}
