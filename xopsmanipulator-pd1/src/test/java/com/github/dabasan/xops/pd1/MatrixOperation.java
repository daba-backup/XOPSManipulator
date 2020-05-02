package com.github.dabasan.xops.pd1;

import java.io.IOException;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.VectorFunctions;

public class MatrixOperation {
	public static void main(String[] args) {
		PD1Manipulator manipulator;
		try {
			manipulator=new PD1Manipulator("./TestData/tr.pd1");
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		Matrix translate=MatrixFunctions.MGetTranslate(VectorFunctions.VGet(100.0f, 100.0f, 100.0f));
		manipulator.SetMatrix(translate);
		
		manipulator.Write("./TestData/tr_matrix.pd1");
	}
}
