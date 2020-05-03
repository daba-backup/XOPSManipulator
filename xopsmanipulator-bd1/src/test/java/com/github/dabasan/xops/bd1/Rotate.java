package com.github.dabasan.xops.bd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.tool.MathFunctions;

public class Rotate {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final float rad = MathFunctions.DegToRad(45.0f);
		manipulator.Rotate(VectorFunctions.VGet(rad, rad, rad));
		manipulator.WriteAsBD1("./TestData/temp_rotate.bd1");
	}
}
