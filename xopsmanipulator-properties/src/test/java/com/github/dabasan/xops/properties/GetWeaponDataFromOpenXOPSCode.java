package com.github.dabasan.xops.properties;

import java.io.IOException;
import java.util.Map;

import com.github.dabasan.xops.properties.entity.weapon.WeaponData;
import com.github.dabasan.xops.properties.openxops.WeaponDataCodeParser;

public class GetWeaponDataFromOpenXOPSCode {
	public static void main(String[] args) {
		WeaponDataCodeParser parser;
		try {
			parser = new WeaponDataCodeParser("./TestData/weapon_code.txt",
					"UTF-8");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final Map<Integer, WeaponData> data = parser.GetOrderedData();
		for (final Map.Entry<Integer, WeaponData> entry : data.entrySet()) {
			System.out.printf("[%d]\n", entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
