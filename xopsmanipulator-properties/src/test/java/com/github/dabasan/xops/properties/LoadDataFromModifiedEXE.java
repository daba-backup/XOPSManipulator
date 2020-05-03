package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.exe.XOPSExeManipulator;

public class LoadDataFromModifiedEXE {
	public static void main(String[] args) {
		XOPSExeManipulator manipulator;
		try {
			manipulator=new XOPSExeManipulator("./TestData/GhillieInTheMist.exe", 0x0005E32C, 0x000671E4, 0x0005E864);
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		WeaponData[] weapon_data=manipulator.GetWeaponDataArray();
		CharacterData[] character_data=manipulator.GetCharacterDataArray();
		
		for(int i=0;i<weapon_data.length;i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(weapon_data[i]);
		}
		System.out.println("====================");
		for(int i=0;i<character_data.length;i++) {
			System.out.printf("[%d]\n", i);
			System.out.println(character_data[i]);
		}
	}
}
