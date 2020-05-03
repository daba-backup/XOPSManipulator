package com.github.dabasan.xops.properties;

import java.io.IOException;
import java.util.List;

import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.openxops.WeaponDataCodeOutputter;
import com.github.dabasan.xops.properties.xms.xgs.XGSManipulator;

public class GenerateOpenXOPSWeaponDataCode {
	public static void main(String[] args) {
		XGSManipulator manipulator;
		WeaponDataCodeOutputter outputter;

		try {
			manipulator = new XGSManipulator("./TestData/weapons.xgs");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		WeaponData[] weapon_data = manipulator.GetWeaponData();
		outputter = new WeaponDataCodeOutputter(weapon_data);

		List<String> code = outputter.GetCode();
		try {
			FileFunctions.CreateTextFile("./TestData/weapon_code.txt", "UTF-8",
					code);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
