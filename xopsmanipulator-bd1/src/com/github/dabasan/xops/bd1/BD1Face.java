package com.github.dabasan.xops.bd1;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

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
		return vertex_positions;
	}
	public Vector GetNormal() {
		return normal;
	}
	public float[] GetUs() {
		return us;
	}
	public float[] GetVs() {
		return vs;
	}
}
