package com.github.dabasan.xops.pd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;

public class Translate {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./TestData/tr.pd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.Translate(VectorFunctions.VGet(100.0f, 100.0f, 100.0f));

		manipulator.Write("./TestData/tr_translate.pd1");
	}
}
