package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;

public class LoadDataFromModifiedEXE {
	public static void main(String[] args) {
		XOPSExeManipulator manipulator;
		try {
			manipulator = new XOPSExeManipulator(
					"./TestData/GhillieInTheMist.exe", 0x0005E32C, 0x000671E4,
					0x0005E864);
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final WeaponData[] weapon_data = manipulator.GetWeaponData();
		final CharacterData[] character_data = manipulator.GetCharacterData();

		for (int i = 0; i < weapon_data.length; i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(weapon_data[i]);
		}
		System.out.println("====================");
		for (int i = 0; i < character_data.length; i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(character_data[i]);
		}
	}
}
