package com.github.dabasan.xops.bd1;

import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;

/**
 * Face of a block contained in BD1 files.
 * @author Daba
 *
 */
public class BD1Face {
	private Vector[] vertex_positions;
	private Vector normal;
	private float[] us;
	private float[] vs;
	
	public BD1Face() {
		vertex_positions=new Vector[4];
		normal=new Vector();
		us=new float[4];
		vs=new float[4];
		
		for(int i=0;i<4;i++) {
			vertex_positions[i]=VectorFunctions.VGet(0.0f, 0.0f, 0.0f);
			us[i]=0.0f;
			vs[i]=0.0f;
		}
	}
	
	public void SetVertexPosition(int index,Vector pos) {
		vertex_positions[index]=pos;
	}
	public void SetNormal(Vector normal) {
		this.normal=normal;
	}
	public void SetUV(int index,float u,float v) {
		us[index]=u;
		vs[index]=v;
	}
	
	public Vector[] GetVertexPositions() {
		Vector[] ret=new Vector[4];
		
		for(int i=0;i<4;i++) {
			ret[i]=new Vector(vertex_positions[i]);
		}
		
		return ret;
	}
	public Vector GetNormal() {
		return new Vector(normal);
	}
	public float[] GetUs() {
		return us.clone();
	}
	public float[] GetVs() {
		return vs.clone();
	}
}
