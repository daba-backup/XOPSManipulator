package com.github.dabasan.xops.bd1;

import java.io.IOException;

public class InvertZ {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.InvertZ();
		manipulator.WriteAsBD1("./TestData/inverted_temp.bd1");
	}
}
