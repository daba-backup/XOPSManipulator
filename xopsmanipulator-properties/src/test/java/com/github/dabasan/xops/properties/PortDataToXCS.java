package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;
import com.github.dabasan.xops.properties.xms.xcs.XCSManipulator;

public class PortDataToXCS {
	public static void main(String[] args) {
		XOPSExeManipulator exe_manipulator;
		XCSManipulator xcs_manipulator;

		try {
			exe_manipulator = new XOPSExeManipulator(
					"./TestData/xops0975t.exe");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		xcs_manipulator = new XCSManipulator();

		final CharacterData[] character_data = exe_manipulator
				.GetCharacterData();
		xcs_manipulator.SetCharacterData(character_data);

		xcs_manipulator.Write("./TestData/ported_data.xcs");
	}
}
