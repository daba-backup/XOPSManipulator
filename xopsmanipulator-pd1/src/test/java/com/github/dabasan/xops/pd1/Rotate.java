package com.github.dabasan.xops.pd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;
import com.github.dabasan.tool.MathFunctions;

public class Rotate {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator=new PD1Manipulator("./TestData/tr.pd1");
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		float rad=MathFunctions.DegToRad(45.0f);
		manipulator.Rotate(VectorFunctions.VGet(rad, rad, rad));
		
		manipulator.Write("./TestData/tr_rotate.pd1");
	}
}
