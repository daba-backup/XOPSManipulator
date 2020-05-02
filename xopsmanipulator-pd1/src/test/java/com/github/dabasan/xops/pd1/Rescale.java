package com.github.dabasan.xops.pd1;

import java.io.IOException;

import com.github.dabasan.basis.vector.VectorFunctions;

public class Rescale {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator=new PD1Manipulator("./TestData/tr.pd1");
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		manipulator.Rescale(VectorFunctions.VGet(2.0f, 2.0f, 2.0f));
		
		manipulator.Write("./TestData/tr_scale.pd1");
	}
}
