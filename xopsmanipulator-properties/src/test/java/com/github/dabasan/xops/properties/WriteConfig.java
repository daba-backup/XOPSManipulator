package com.github.dabasan.xops.properties;

import com.github.dabasan.xops.properties.config.Config;
import com.github.dabasan.xops.properties.config.ConfigManipulator;

public class WriteConfig {
	public static void main(String[] args) {
		final ConfigManipulator manipulator = new ConfigManipulator();

		final Config config = new Config();
		config.SetAnotherGunsight(true);
		config.SetName("TestName");

		manipulator.SetConfig(config);

		manipulator.Write("./TestData/config_test.dat");
	}
}
