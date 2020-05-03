package com.github.dabasan.xops.pd1;

import java.io.IOException;

public class InvertZ {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./TestData/tr.pd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		manipulator.InvertZ();

		manipulator.Write("./TestData/inverted_tr.pd1");
	}
}
