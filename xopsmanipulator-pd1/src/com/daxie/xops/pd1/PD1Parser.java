package com.daxie.xops.pd1;

import java.io.IOException;
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
	
	public PD1Parser(String pd1_filename) throws IOException{
		points=new ArrayList<>();		
		List<Byte> bin=FileFunctions.GetFileAllBin(pd1_filename);
		
		int pos=0;
		
		//Number of points
		int point_num=this.GetUShortFromBin(bin, pos);
		pos+=2;
		
		//Points
		for(int i=0;i<point_num;i++) {
			PD1Point point=new PD1Point();
			float ftemp;
			
			//Point position
			ftemp=this.GetFloatFromBin(bin, pos);
			pos+=4;
			point.SetPositionX(ftemp);
			
			ftemp=this.GetFloatFromBin(bin, pos);
			pos+=4;
			point.SetPositionY(ftemp);
			
			ftemp=this.GetFloatFromBin(bin, pos);
			pos+=4;
			point.SetPositionZ(ftemp);
			
			//Rotation
			ftemp=this.GetFloatFromBin(bin, pos);
			pos+=4;
			point.SetRotation(ftemp);
			
			//Parameters
			int itemp;
			for(int j=0;j<4;j++) {
				itemp=(int)bin.get(pos);
				pos++;
				
				point.SetParameter(j, itemp);
			}
			
			points.add(point);
		}
	}
	private int GetUShortFromBin(List<Byte> bin,int pos) {
		byte[] buffer=new byte[2];
		buffer[0]=bin.get(pos);
		buffer[1]=bin.get(pos+1);
		
		int ret=ByteFunctions.byte_to_ushort_le(buffer);
		return ret;
	}
	private float GetFloatFromBin(List<Byte> bin,int pos) {
		byte[] buffer=new byte[4];
		buffer[0]=bin.get(pos);
		buffer[1]=bin.get(pos+1);
		buffer[2]=bin.get(pos+2);
		buffer[3]=bin.get(pos+3);
		
		float ret=ByteFunctions.byte_to_float_le(buffer);
		return ret;
	}
	
	public List<PD1Point> GetPoints(){
		return new ArrayList<>(points);
	}
}
