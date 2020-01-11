package com.daxie.xops.pd1;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;

/**
 * Reads data from a PD1 file.
 * @author Daba
 *
 */
class PD1Parser {
	private List<PD1Point> points;
	
	public PD1Parser(String pd1_filename) throws FileNotFoundException{
		points=new ArrayList<>();		
		List<Byte> bin=FileFunctions.GetFileAllBin(pd1_filename);
		
		int count=0;
		
		//Number of points
		byte[] point_num_buffer=new byte[2];
		point_num_buffer[0]=bin.get(count);
		point_num_buffer[1]=bin.get(count+1);
		
		int point_num=ByteFunctions.byte_to_ushort_le(point_num_buffer);
		
		count+=2;
		
		//Points
		for(int i=0;i<point_num;i++) {
			PD1Point point=new PD1Point();
			
			byte[] byte_buffer=new byte[4];
			float ftemp;
			
			//Point position
			for(int j=0;j<4;j++) {
				byte_buffer[j]=bin.get(count);
				count++;
			}
			ftemp=ByteFunctions.byte_to_float_le(byte_buffer);
			point.SetPositionX(ftemp);
			for(int j=0;j<4;j++) {
				byte_buffer[j]=bin.get(count);
				count++;
			}
			ftemp=ByteFunctions.byte_to_float_le(byte_buffer);
			point.SetPositionY(ftemp);
			for(int j=0;j<4;j++) {
				byte_buffer[j]=bin.get(count);
				count++;
			}
			ftemp=ByteFunctions.byte_to_float_le(byte_buffer);
			point.SetPositionZ(ftemp);
			
			//Rotation
			for(int j=0;j<4;j++) {
				byte_buffer[j]=bin.get(count);
				count++;
			}
			ftemp=ByteFunctions.byte_to_float_le(byte_buffer);
			point.SetRotation(ftemp);
			
			//Parameters
			int itemp;
			for(int j=0;j<4;j++) {
				itemp=Byte.toUnsignedInt(bin.get(count));
				count++;
				
				point.SetParameter(j, itemp);
			}
			
			points.add(point);
		}
	}
	
	public List<PD1Point> GetPoints(){
		return new ArrayList<>(points);
	}
}
