package com.github.dabasan.xops.properties;

import java.io.IOException;
import java.util.Map;

import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.openxops.CharacterDataCodeParser;

public class GetCharacterDataFromOpenXOPSCode {
	public static void main(String[] args) {
		CharacterDataCodeParser parser;
		try {
			parser = new CharacterDataCodeParser(
					"./TestData/character_code.txt", "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Map<Integer, CharacterData> data = parser.GetOrderedData();
		for (Map.Entry<Integer, CharacterData> entry : data.entrySet()) {
			System.out.printf("[%d]\n", entry.getKey());
			System.out.println(entry.getValue());
		}
	}
}
