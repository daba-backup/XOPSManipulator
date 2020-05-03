package com.github.dabasan.xops.bd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;

public class Translate {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.Translate(VectorFunctions.VGet(100.0f, 100.0f, 100.0f));
		manipulator.WriteAsBD1("./TestData/temp_translate.bd1");
	}
}
