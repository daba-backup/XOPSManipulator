package com.daxie.basis.matrix;

import com.daxie.basis.vector.Vector;

/**
 * Offers methods to handle matrices.
 * @author Daba
 *
 */
public class MatrixFunctions {
	/**
	 * Multiplies two matrices together.
	 * @param m1 First matrix
	 * @param m2 Second matrix
	 * @return m1*m2
	 */
	public static Matrix MMult(Matrix m1,Matrix m2) {
		Matrix ret=new Matrix();
		
		float value;
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				value=0.0f;
				
				for(int k=0;k<4;k++) {
					value+=m1.GetValue(i, k)*m2.GetValue(k, j);
				}
				
				ret.SetValue(i, j, value);
			}
		}
		
		return ret;
	}
	/**
	 * Returns an identity matrix.
	 * @return Identity matrix
	 */
	public static Matrix MGetIdent() {
		Matrix ret=new Matrix();
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)ret.SetValue(i, j, 0.0f);
		}
		for(int i=0;i<4;i++)ret.SetValue(i, i, 1.0f);
		
		return ret;
	}
	/**
	 * Returns a scaling matrix.
	 * @param scale Scaling vector
	 * @return Scaling matrix
	 */
	public static Matrix MGetScale(Vector scale) {
		Matrix ret=new Matrix();
		
		ret.SetValue(0, 0, scale.GetX());
		ret.SetValue(0, 1, 0.0f);
		ret.SetValue(0, 2, 0.0f);
		ret.SetValue(0, 3, 0.0f);
		ret.SetValue(1, 0, 0.0f);
		ret.SetValue(1, 1, scale.GetY());
		ret.SetValue(1, 2, 0.0f);
		ret.SetValue(1, 3, 0.0f);
		ret.SetValue(2, 0, 0.0f);
		ret.SetValue(2, 1, 0.0f);
		ret.SetValue(2, 2, scale.GetZ());
		ret.SetValue(2, 3, 0.0f);
		ret.SetValue(3, 0, 0.0f);
		ret.SetValue(3, 1, 0.0f);
		ret.SetValue(3, 2, 0.0f);
		ret.SetValue(3, 3, 1.0f);
		
		return ret;
	}
	/**
	 * Returns a translation matrix.
	 * @param translate Translation vector
	 * @return Translation matrix
	 */
	public static Matrix MGetTranslate(Vector translate) {
		Matrix ret=new Matrix();
		
		ret.SetValue(0, 0, 1.0f);
		ret.SetValue(0, 1, 0.0f);
		ret.SetValue(0, 2, 0.0f);
		ret.SetValue(0, 3, translate.GetX());
		ret.SetValue(1, 0, 0.0f);
		ret.SetValue(1, 1, 1.0f);
		ret.SetValue(1, 2, 0.0f);
		ret.SetValue(1, 3, translate.GetY());
		ret.SetValue(2, 0, 0.0f);
		ret.SetValue(2, 1, 0.0f);
		ret.SetValue(2, 2, 1.0f);
		ret.SetValue(2, 3, translate.GetZ());
		ret.SetValue(3, 0, 0.0f);
		ret.SetValue(3, 1, 0.0f);
		ret.SetValue(3, 2, 0.0f);
		ret.SetValue(3, 3, 1.0f);
		
		return ret;
	}
	/**
	 * Returns a rotation matrix around x-axis.
	 * @param th Rotation angle (radian)
	 * @return Rotation matrix
	 */
	public static Matrix MGetRotX(float th) {
		Matrix ret=new Matrix();
		
		ret.SetValue(0, 0, 1.0f);
		ret.SetValue(0, 1, 0.0f);
		ret.SetValue(0, 2, 0.0f);
		ret.SetValue(0, 3, 0.0f);
		ret.SetValue(1, 0, 0.0f);
		ret.SetValue(1, 1, (float)Math.cos(th));
		ret.SetValue(1, 2, (float)-Math.sin(th));
		ret.SetValue(1, 3, 0.0f);
		ret.SetValue(2, 0, 0.0f);
		ret.SetValue(2, 1, (float)Math.sin(th));
		ret.SetValue(2, 2, (float)Math.cos(th));
		ret.SetValue(2, 3, 0.0f);
		ret.SetValue(3, 0, 0.0f);
		ret.SetValue(3, 1, 0.0f);
		ret.SetValue(3, 2, 0.0f);
		ret.SetValue(3, 3, 1.0f);
		
		return ret;
	}
	/**
	 * Returns a rotation matrix around y-axis.
	 * @param th Rotation angle (radian)
	 * @return Rotation matrix
	 */
	public static Matrix MGetRotY(float th) {
		Matrix ret=new Matrix();
		
		ret.SetValue(0, 0, (float)Math.cos(th));
		ret.SetValue(0, 1, 0.0f);
		ret.SetValue(0, 2, (float)Math.sin(th));
		ret.SetValue(0, 3, 0.0f);
		ret.SetValue(1, 0, 0.0f);
		ret.SetValue(1, 1, 1.0f);
		ret.SetValue(1, 2, 0.0f);
		ret.SetValue(1, 3, 0.0f);
		ret.SetValue(2, 0, (float)-Math.sin(th));
		ret.SetValue(2, 1, 0.0f);
		ret.SetValue(2, 2, (float)Math.cos(th));
		ret.SetValue(2, 3, 0.0f);
		ret.SetValue(3, 0, 0.0f);
		ret.SetValue(3, 1, 0.0f);
		ret.SetValue(3, 2, 0.0f);
		ret.SetValue(3, 3, 1.0f);
		
		return ret;
	}
	/**
	 * Returns a rotation matrix around z-axis.
	 * @param th Rotation angle (radian)
	 * @return Rotation matrix
	 */
	public static Matrix MGetRotZ(float th) {
		Matrix ret=new Matrix();
		
		ret.SetValue(0, 0, (float)Math.cos(th));
		ret.SetValue(0, 1, (float)-Math.sin(th));
		ret.SetValue(0, 2, 0.0f);
		ret.SetValue(0, 3, 0.0f);
		ret.SetValue(1, 0, (float)Math.sin(th));
		ret.SetValue(1, 1, (float)Math.cos(th));
		ret.SetValue(1, 2, 0.0f);
		ret.SetValue(1, 3, 0.0f);
		ret.SetValue(2, 0, 0.0f);
		ret.SetValue(2, 1, 0.0f);
		ret.SetValue(2, 2, 1.0f);
		ret.SetValue(2, 3, 0.0f);
		ret.SetValue(3, 0, 0.0f);
		ret.SetValue(3, 1, 0.0f);
		ret.SetValue(3, 2, 0.0f);
		ret.SetValue(3, 3, 1.0f);
		
		return ret;
	}
	/**
	 * Transposes a matrix.
	 * @param m Original matrix
	 * @return Transposed matrix
	 */
	public static Matrix MTranspose(Matrix m) {
		Matrix ret=new Matrix();
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++) {
				ret.SetValue(i, j, m.GetValue(j, i));
			}
		}
		
		return ret;
	}
}
