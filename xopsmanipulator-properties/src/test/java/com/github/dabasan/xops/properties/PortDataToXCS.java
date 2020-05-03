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
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		xcs_manipulator = new XCSManipulator();

		CharacterData[] character_data = exe_manipulator
				.GetCharacterDataArray();
		xcs_manipulator.SetCharacterDataArray(character_data);

		xcs_manipulator.Write("./TestData/ported_data.xcs");
	}
}
