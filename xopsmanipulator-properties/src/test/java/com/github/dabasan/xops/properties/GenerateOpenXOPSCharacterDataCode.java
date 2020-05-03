package com.github.dabasan.xops.properties;

import java.io.IOException;
import java.util.List;

import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.xops.properties.entity.character.CharacterData;
import com.github.dabasan.xops.properties.openxops.CharacterDataCodeOutputter;
import com.github.dabasan.xops.properties.xms.xcs.XCSManipulator;

public class GenerateOpenXOPSCharacterDataCode {
	public static void main(String[] args) {
		XCSManipulator manipulator;
		CharacterDataCodeOutputter outputter;

		try {
			manipulator = new XCSManipulator("./TestData/characters.xcs");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final CharacterData[] character_data = manipulator.GetCharacterData();
		outputter = new CharacterDataCodeOutputter(character_data);
		final List<String> code = outputter.GetCode();

		try {
			FileFunctions.CreateTextFile("./TestData/character_code.txt",
					"UTF-8", code);
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}
	}
}
