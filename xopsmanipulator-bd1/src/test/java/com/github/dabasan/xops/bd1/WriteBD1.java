package com.github.dabasan.xops.bd1;

import java.io.IOException;

public class WriteBD1 {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.WriteAsBD1("./TestData/temp2.bd1");
	}
}
