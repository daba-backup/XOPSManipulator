package com.github.dabasan.xops.properties.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manipulates a config file (config.dat) for X operations.
 * 
 * @author Daba
 *
 */
public class ConfigManipulator {
	private Logger logger = LoggerFactory.getLogger(ConfigManipulator.class);

	private Config config;

	public ConfigManipulator(String config_filename) throws IOException {
		ConfigParser config_parser = new ConfigParser(config_filename);
		config = config_parser.GetConfig();
	}
	public ConfigManipulator() {
		config = new Config();
	}

	public void SetConfig(Config config) {
		if (config == null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.config = config;
	}
	public Config GetConfig() {
		return new Config(config);
	}

	public int Write(String config_filename) {
		ConfigWriter config_writer = new ConfigWriter(config);
		int ret = config_writer.Write(config_filename);

		if (ret < 0) {
			logger.error("Failed to write in a config file. config_filename={}",
					config_filename);
			return -1;
		}

		return 0;
	}
}
