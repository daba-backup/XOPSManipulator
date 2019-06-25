package com.daxie.xops.pd1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.daxie.basis.matrix.Matrix;
import com.daxie.basis.matrix.MatrixFunctions;
import com.daxie.basis.vector.Vector;
import com.daxie.basis.vector.VectorFunctions;
import com.daxie.log.LogFile;

/**
 * Manipulates a PD1 file.
 * @author Daba
 *
 */
public class PD1Manipulator {
	private List<Point>points;
	
	/**
	 * 
	 * @param pd1_filename PD1 filename to load
	 * @throws FileNotFoundException PD1 file not found
	 */
	public PD1Manipulator(String pd1_filename) throws FileNotFoundException{
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
	public List<Point> GetPoints(){
		return new ArrayList<>(points);
	}
	/**
	 * Sets points.
	 * @param points Points
	 */
	public void SetPoints(List<Point> points) {
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
		
		for(Point point:points) {
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
		for(Point point:points) {
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
		
		for(Point point:points) {
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
		for(Point point:points) {
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
		for(Point point:points) {
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
		for(Point point:points) {
			float rotation=point.GetRotation();
			rotation+=rotate;
			point.SetRotation(rotation);
		}
	}
	
	/**
	 * Inverts z-axis.
	 * This method will be used to make a mission for a mirrored map.
	 */
	public void InvertZ() {
		for(Point point:points) {
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
		try {
			pd1_writer.Write(pd1_filename);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteError("[PD1Manipulator-Write] Failed to write data.");
			return -1;
		}
		
		return 0;
	}
}
