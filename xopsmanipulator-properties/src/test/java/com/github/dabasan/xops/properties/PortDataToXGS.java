package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;
import com.github.dabasan.xops.properties.xms.xgs.XGSManipulator;

public class PortDataToXGS {
	public static void main(String[] args) {
		XOPSExeManipulator exe_manipulator;
		XGSManipulator xgs_manipulator;

		try {
			exe_manipulator = new XOPSExeManipulator(
					"./TestData/xops0975t.exe");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		xgs_manipulator = new XGSManipulator();

		final WeaponData[] weapon_data = exe_manipulator.GetWeaponData();
		xgs_manipulator.SetWeaponData(weapon_data);

		xgs_manipulator.Write("./TestData/ported_data.xgs");
	}
}
