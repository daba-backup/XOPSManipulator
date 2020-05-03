package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;

public class LoadDataFromEXE {
	public static void main(String[] args) {
		XOPSExeManipulator manipulator;
		try {
			manipulator = new XOPSExeManipulator("./TestData/xops0975t.exe");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		WeaponData[] weapon_data = manipulator.GetWeaponData();
		CharacterData[] character_data = manipulator.GetCharacterData();

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
