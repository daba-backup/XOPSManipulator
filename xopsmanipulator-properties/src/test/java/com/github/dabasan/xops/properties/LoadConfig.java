package com.github.dabasan.xops.properties;

import java.io.IOException;

import com.github.dabasan.xops.properties.config.Config;
import com.github.dabasan.xops.properties.config.ConfigManipulator;

public class LoadConfig {
	public static void main(String[] args) {
		ConfigManipulator manipulator;
		try {
			manipulator = new ConfigManipulator("./TestData/config.dat");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final Config config = manipulator.GetConfig();
		System.out.println(config);
	}
}
