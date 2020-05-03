package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.xms.ids.IDSManipulator;

public class LoadDataFromIDS {
	public static void main(String[] args) {
		IDSManipulator manipulator;
		try {
			manipulator = new IDSManipulator("./TestData/mp5.ids");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final WeaponData weapon_data = manipulator.GetWeaponData();
		System.out.println(weapon_data);
	}
}
