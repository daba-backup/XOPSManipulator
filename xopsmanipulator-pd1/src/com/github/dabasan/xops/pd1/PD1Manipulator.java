package com.github.dabasan.xops.pd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.dabasan.basis.matrix.Matrix;
import com.github.dabasan.basis.matrix.MatrixFunctions;
import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Manipulates a PD1 file.
 * @author Daba
 *
 */
public class PD1Manipulator {
	private Logger logger=LoggerFactory.getLogger(PD1Manipulator.class);
	
	private List<PD1Point>points;
	
	/**
	 * 
	 * @param pd1_filename PD1 filename to load
	 * @throws IOException
	 */
	public PD1Manipulator(String pd1_filename) throws IOException{
		PD1Parser pd1_parser=new PD1Parser(pd1_filename);
		points=pd1_parser.GetPoints();
	}
	public PD1Manipulator() {
		points=new ArrayList<>();
	}
	
	/**
	 * Returns points.
	 * @return Points
	 */
	public List<PD1Point> GetPoints(){
		return new ArrayList<>(points);
	}
	/**
	 * Sets points.
	 * @param points Points
	 */
	public void SetPoints(List<PD1Point> points) {
		if(points==null) {
			logger.warn("Null argument where non-null required.");
			return;
		}
		this.points=points;
	}
	
	/**
	 * Returns the number of points contained in the PD1 file.
	 * @return Number of points
	 */
	public int GetPointNum() {
		return points.size();
	}
	/**
	 * Returns the number of the specified type of points contained in the PD1 file.
	 * @param param_1 Parameter 1
	 * @return Number of points
	 */
	public int GetPointNum(int param_1) {
		int point_num=0;
		
		for(PD1Point point:points) {
			int p=point.GetParameter(0);
			if(p==param_1)point_num++;
		}
		
		return point_num;
	}
	
	/**
	 * Translates every point.
	 * @param translate Translation vector
	 */
	public void Translate(Vector translate) {
		for(PD1Point point:points) {
			Vector position=point.GetPosition();
			position=VectorFunctions.VAdd(position, translate);
			point.SetPosition(position);
		}
	}
	/**
	 * Rotates every point.
	 * @param rotate Rotation vector
	 */
	public void Rotate(Vector rotate) {
		Matrix rot_x=MatrixFunctions.MGetRotX(rotate.GetX());
		Matrix rot_y=MatrixFunctions.MGetRotY(rotate.GetY());
		Matrix rot_z=MatrixFunctions.MGetRotZ(rotate.GetZ());
		
		for(PD1Point point:points) {
			Vector position=point.GetPosition();
			
			position=VectorFunctions.VTransform(position, rot_x);
			position=VectorFunctions.VTransform(position, rot_y);
			position=VectorFunctions.VTransform(position, rot_z);
			
			point.SetPosition(position);
		}
	}
	/**
	 * Rescales every point.
	 * @param scale Scaling vector
	 */
	public void Rescale(Vector scale) {
		for(PD1Point point:points) {
			Vector position=point.GetPosition();
			
			float x,y,z;
			x=position.GetX();
			y=position.GetY();
			z=position.GetZ();
			
			position.SetX(x*scale.GetX());
			position.SetY(y*scale.GetY());
			position.SetZ(z*scale.GetZ());
			
			point.SetPosition(position);
		}
	}
	/**
	 * Transforms every point with a matrix.
	 * @param m Transformation matrix
	 */
	public void SetMatrix(Matrix m) {
		for(PD1Point point:points) {
			Vector position=point.GetPosition();
			position=VectorFunctions.VTransform(position, m);
			point.SetPosition(position);
		}
	}
	/**
	 * Rotates the direction of every point.
	 * @param rotate Rotation angle (radian)
	 */
	public void RotateDirection(float rotate) {
		for(PD1Point point:points) {
			float rotation=point.GetRotation();
			rotation+=rotate;
			point.SetRotation(rotation);
		}
	}
	
	/**
	 * Inverts z-axis.
	 */
	public void InvertZ() {
		for(PD1Point point:points) {
			Vector position=point.GetPosition();
			position.SetZ(position.GetZ()*(-1.0f));
			point.SetPosition(position);
			
			float rotation=point.GetRotation();
			rotation*=(-1.0f);
			rotation+=(float)Math.PI;
			point.SetRotation(rotation);
		}
	}
	
	/**
	 * Writes out data to a PD1 file.
	 * @param pd1_filename Filename
	 * @return -1 on error and 0 on success
	 */
	public int Write(String pd1_filename) {
		PD1Writer pd1_writer=new PD1Writer(points);
		int ret=pd1_writer.Write(pd1_filename);
		
		if(ret<0) {
			logger.error("Failed to write points in a PD1 file. pd1_filename={}",pd1_filename);
			return -1;
		}
		
		return 0;
	}
}
