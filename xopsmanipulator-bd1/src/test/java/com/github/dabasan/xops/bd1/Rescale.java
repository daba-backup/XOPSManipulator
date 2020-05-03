package com.github.dabasan.xops.bd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;

public class Rescale {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.Rescale(VectorFunctions.VGet(2.0f, 2.0f, 2.0f));
		manipulator.WriteAsBD1("./TestData/temp_scale.bd1");
	}
}
