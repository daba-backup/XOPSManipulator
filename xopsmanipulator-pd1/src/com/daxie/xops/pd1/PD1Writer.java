package com.daxie.xops.pd1;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.log.LogWriter;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.ExceptionFunctions;

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
		
		try(DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(pd1_filename)))){
			//Number of points
			int point_num=points.size();
			
			byte[] point_num_buffer=ByteFunctions.ushort_to_byte_le(point_num);
			dos.write(point_num_buffer);
			
			//Point data
			for(int i=0;i<points.size();i++) {
				PD1Point point=points.get(i);
				
				Vector position=point.GetPosition();
				float rotation=point.GetRotation();
				int[] parameters=point.GetParameters();
				
				byte[] b;
				
				//Position
				b=ByteFunctions.float_to_byte_le(position.GetX());
				dos.write(b);
				b=ByteFunctions.float_to_byte_le(position.GetY());
				dos.write(b);
				b=ByteFunctions.float_to_byte_le(position.GetZ());
				dos.write(b);
				
				//Rotation
				b=ByteFunctions.float_to_byte_le(rotation);
				dos.write(b);
				
				//Parameters
				byte btemp;
				for(int j=0;j<4;j++) {
					btemp=(byte)parameters[j];
					dos.writeByte(btemp);
				}
			}
			
			dos.flush();
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
		
		return 0;
	}
}
