package com.github.dabasan.xops.bd1;

import java.io.IOException;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.VectorFunctions;

public class MatrixOperation {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator = new BD1Manipulator("./TestData/temp.bd1");
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		Matrix translate = MatrixFunctions
				.MGetTranslate(VectorFunctions.VGet(100.0f, 100.0f, 100.0f));
		manipulator.SetMatrix(translate);
		manipulator.WriteAsBD1("./TestData/temp_matrix.bd1");
	}
}
