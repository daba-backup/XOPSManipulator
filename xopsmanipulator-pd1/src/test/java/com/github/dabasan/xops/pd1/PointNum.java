package com.github.dabasan.xops.pd1;

import java.io.IOException;

public class PointNum {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator = new PD1Manipulator("./TestData/tr.pd1");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final int point_num = manipulator.GetPointNum();
		final int character_num = manipulator.GetPointNum(1);
		System.out.printf("point_num=%d character_num=%d\n", point_num,
				character_num);
	}
}
