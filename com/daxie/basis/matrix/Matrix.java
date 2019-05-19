package com.daxie.basis.matrix;

/**
 * 4 by 4 matrix
 * @author Daba
 *
 */
public class Matrix {
	private float[][] m;
	
	public Matrix() {
		m=new float[4][4];
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)m[i][j]=0.0f;
		}
	}
	
	@Override
	public String toString() {
		String ret="";
		
		for(int i=0;i<4;i++) {
			for(int j=0;j<4;j++)ret+=m[i][j]+" ";
			ret+="\n";
		}
		
		return ret;
	}
	
	public void SetValue(int row,int column,float value) {
		m[row][column]=value;
	}
	public float GetValue(int row,int column) {
		return m[row][column];
	}
}
