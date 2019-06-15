package com.daxie.basis.vector;

import com.daxie.basis.matrix.Matrix;

/**
 * Offers methods to handle vectors.
 * @author Daba
 *
 */
public class VectorFunctions {
	/**
	 * Generates a vector.
	 * @param x X-coordinate
	 * @param y Y-coordinate
	 * @param z Z-coordinate
	 * @return Vector
	 */
	public static Vector VGet(float x,float y,float z) {
		return new Vector(x,y,z);
	}
	/**
	 * Adds two vectors.
	 * @param lhs First vector
	 * @param rhs Second vector
	 * @return lhs+rhs
	 */
	public static Vector VAdd(Vector lhs,Vector rhs) {
		Vector res=new Vector();
		
		res.SetVector(lhs.GetX()+rhs.GetX(), lhs.GetY()+rhs.GetY(), lhs.GetZ()+rhs.GetZ());
		
		return res;
	}
	/**
	 * Subtracts a vector from another vector.
	 * @param lhs First vector
	 * @param rhs Second vector
	 * @return lhs-rhs
	 */
	public static Vector VSub(Vector lhs,Vector rhs) {
		Vector res=new Vector();
		
		res.SetVector(lhs.GetX()-rhs.GetX(), lhs.GetY()-rhs.GetY(), lhs.GetZ()-rhs.GetZ());
		
		return res;
	}
	/**
	 * Returns the size of a vector.
	 * @param v Vector
	 * @return Size of v
	 */
	public static float VSize(Vector v) {
		float size;
		float x,y,z;
		
		x=v.GetX();
		y=v.GetY();
		z=v.GetZ();
		
		size=(float)Math.sqrt(x*x+y*y+z*z);
		
		return size;
	}
	/**
	 * Returns the square size of a vector.
	 * @param v Vector
	 * @return Square size of v
	 */
	public static float VSquareSize(Vector v) {
		float size;
		float x,y,z;
		
		x=v.GetX();
		y=v.GetY();
		z=v.GetZ();
		
		size=x*x+y*y+z*z;
		
		return size;
	}
	/**
	 * Normalizes a vector.
	 * @param v Vector
	 * @return Normalized vector
	 */
	public static Vector VNorm(Vector v) {
		Vector res=new Vector();
		float size=VectorFunctions.VSize(v);
		
		res.SetVector(v.GetX()/size,v.GetY()/size,v.GetZ()/size);
		
		return res;
	}
	/**
	 * Rescales a vector.
	 * @param v Vector
	 * @param scale Scale
	 * @return Rescaled vector
	 */
	public static Vector VScale(Vector v,float scale) {
		Vector res=new Vector();
		
		res.SetVector(v.GetX()*scale, v.GetY()*scale, v.GetZ()*scale);
		
		return res;
	}
	/**
	 * Returns the dot product.
	 * @param lhs First vector
	 * @param rhs Second vector
	 * @return lhs・rhs
	 */
	public static float VDot(Vector lhs,Vector rhs) {
		float res;
		
		res=(lhs.GetX()*rhs.GetX()+lhs.GetY()*rhs.GetY()+lhs.GetZ()*rhs.GetZ());
		
		return res;
	}
	/**
	 * Returns the cross product.
	 * @param lhs First vector
	 * @param rhs Second vector
	 * @return lhs×rhs
	 */
	public static Vector VCross(Vector lhs,Vector rhs) {
		Vector res=new Vector();
		
		res.SetX(lhs.GetY()*rhs.GetZ()-lhs.GetZ()*rhs.GetY());
		res.SetY(lhs.GetZ()*rhs.GetX()-lhs.GetX()*rhs.GetZ());
		res.SetZ(lhs.GetX()*rhs.GetY()-lhs.GetY()*rhs.GetX());
		
		return res;
	}
	/**
	 * Multiplies a vector by a matrix.
	 * @param v Vector
	 * @param m Matrix
	 * @return m*v
	 */
	public static Vector VTransform(Vector v,Matrix m) {
		Vector ret=new Vector();
		
		float[] ex_v=new float[4];
		ex_v[0]=v.GetX();
		ex_v[1]=v.GetY();
		ex_v[2]=v.GetZ();
		ex_v[3]=1.0f;
		
		float m00,m01,m02,m03;
		float m10,m11,m12,m13;
		float m20,m21,m22,m23;
		m00=m.GetValue(0, 0);
		m01=m.GetValue(0, 1);
		m02=m.GetValue(0, 2);
		m03=m.GetValue(0, 3);
		m10=m.GetValue(1, 0);
		m11=m.GetValue(1, 1);
		m12=m.GetValue(1, 2);
		m13=m.GetValue(1, 3);
		m20=m.GetValue(2, 0);
		m21=m.GetValue(2, 1);
		m22=m.GetValue(2, 2);
		m23=m.GetValue(2, 3);
		
		ret.SetX(m00*ex_v[0]+m01*ex_v[1]+m02*ex_v[2]+m03*ex_v[3]);
		ret.SetY(m10*ex_v[0]+m11*ex_v[1]+m12*ex_v[2]+m13*ex_v[3]);
		ret.SetZ(m20*ex_v[0]+m21*ex_v[1]+m22*ex_v[2]+m23*ex_v[3]);
		
		return ret;
	}
}
