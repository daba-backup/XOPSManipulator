package com.github.dabasan.xops.mif;

import java.io.IOException;

public class GetMissionInfo {
	public static void main(String[] args) {
		MIFManipulator manipulator;
		try {
			manipulator = new MIFManipulator("./TestData/mission.mif",
					"Shift-JIS");
		} catch (final IOException e) {
			e.printStackTrace();
			return;
		}

		final MissionInfo mif = manipulator.GetMissionInfo();
		System.out.println(mif);
	}
}
