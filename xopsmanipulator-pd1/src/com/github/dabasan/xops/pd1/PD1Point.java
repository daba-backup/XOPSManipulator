package com.github.dabasan.xops.pd1;

import com.daxie.basis.vector.Vector;

/**
 * A point contained in the PD1 format.
 * @author Daba
 *
 */
public class PD1Point {
	private Vector position;
	private float rotation;
	private int[] parameters;
	
	public PD1Point() {
		position=new Vector();
		rotation=0.0f;
		parameters=new int[4];
		for(int i=0;i<4;i++)parameters[i]=0;
	}
	public PD1Point(PD1Point point) {
		position=point.GetPosition();
		rotation=point.GetRotation();
		parameters=point.GetParameters();
	}
	
	public void SetPositionX(float x) {
		position.SetX(x);
	}
	public void SetPositionY(float y) {
		position.SetY(y);
	}
	public void SetPositionZ(float z) {
		position.SetZ(z);
	}
	public void SetPosition(Vector position) {
		this.position=position;
	}
	public void SetRotation(float rotation) {
		this.rotation=rotation;
	}
	public void SetParameter(int index,int parameter) {
		parameters[index]=parameter;
	}
	
	public Vector GetPosition() {
		return position;
	}
	public float GetRotation() {
		return rotation;
	}
	public int GetParameter(int index) {
		return parameters[index];
	}
	public int[] GetParameters() {
		return parameters;
	}
}
