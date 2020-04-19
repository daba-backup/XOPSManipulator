package com.daxie.xops.pd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;

/**
 * Writes data to a PD1 file.
 * @author Daba
 *
 */
class PD1Writer {
	private Logger logger=LoggerFactory.getLogger(PD1Writer.class);
	
	private List<PD1Point> points;
	
	public PD1Writer(List<PD1Point> points) {
		this.points=points;
	}
	
	public int Write(String pd1_filename){
		if(points==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		
		List<Byte> bin=new ArrayList<>();
		
		//Number of points
		int point_num=points.size();
		this.AddUShortToBin(bin, point_num);
		
		//Point data
		for(int i=0;i<points.size();i++) {
			PD1Point point=points.get(i);
			
			Vector position=point.GetPosition();
			float rotation=point.GetRotation();
			int[] parameters=point.GetParameters();
			
			//Position
			this.AddFloatToBin(bin, position.GetX());
			this.AddFloatToBin(bin, position.GetY());
			this.AddFloatToBin(bin, position.GetZ());
			
			//Rotation
			this.AddFloatToBin(bin, rotation);
			
			//Parameters
			for(int j=0;j<4;j++) {
				bin.add((byte)parameters[j]);
			}
		}
		
		try {
			FileFunctions.CreateBinFile(pd1_filename, bin);
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
	private void AddUShortToBin(List<Byte> bin,int s) {
		byte[] buffer=ByteFunctions.ushort_to_byte_le(s);
		for(int i=0;i<2;i++) {
			bin.add(buffer[i]);
		}
	}
	private void AddFloatToBin(List<Byte> bin,float f) {
		byte[] buffer=ByteFunctions.float_to_byte_le(f);
		for(int i=0;i<4;i++) {
			bin.add(buffer[i]);
		}
	}
}
