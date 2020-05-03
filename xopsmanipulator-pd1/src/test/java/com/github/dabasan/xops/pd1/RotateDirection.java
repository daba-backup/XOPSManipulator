package com.github.dabasan.xops.pd1;

import java.io.IOException;

import com.github.dabasan.tool.MathFunctions;

public class RotateDirection {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./TestData/tr.pd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final float rad = MathFunctions.DegToRad(45.0f);
		manipulator.RotateDirection(rad);

		manipulator.Write("./TestData/tr_rotate_direction.pd1");
	}
}
