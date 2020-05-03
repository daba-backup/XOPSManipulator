package com.github.dabasan.xops.bd1;

import java.io.IOException;

public class WriteOBJ {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.WriteAsOBJ("./TestData/temp.obj");
	}
}
