package com.github.dabasan.xops.pd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.dabasan.tool.ByteFunctions;
import com.github.dabasan.tool.FileFunctions;

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
		int point_num=ByteFunctions.GetUShortValueFromBin_LE(bin, pos);
		pos+=2;
		
		//Points
		for(int i=0;i<point_num;i++) {
			PD1Point point=new PD1Point();
			float ftemp;
			
			//Point position
			ftemp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
			point.SetPositionX(ftemp);
			pos+=4;
			
			ftemp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
			point.SetPositionY(ftemp);
			pos+=4;
			
			ftemp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
			point.SetPositionZ(ftemp);
			pos+=4;
			
			//Rotation
			ftemp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
			point.SetRotation(ftemp);
			pos+=4;
			
			//Parameters
			int itemp;
			for(int j=0;j<4;j++) {
				itemp=(int)bin.get(pos);
				point.SetParameter(j, itemp);
				pos++;
			}
			
			points.add(point);
		}
	}
	
	public List<PD1Point> GetPoints(){
		return points;
	}
}
