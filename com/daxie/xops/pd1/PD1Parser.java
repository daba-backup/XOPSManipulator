package com.daxie.xops.pd1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.daxie.log.LogFile;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.ExceptionFunctions;

/**
 * Reads data from a PD1 file.
 * @author Daba
 *
 */
class PD1Parser {
	private List<Point> points;
	
	public PD1Parser(String pd1_filename) throws FileNotFoundException{
		points=new ArrayList<>();
		
		List<Byte> bin=new ArrayList<>();
		
		DataInputStream dis;	
		dis=new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(pd1_filename)));
		
		try {
			byte read_byte;
			while(true) {
				read_byte=dis.readByte();
				bin.add(read_byte);
			}
		}
		catch(EOFException e) {
			//to the finally block.
		}
		catch(IOException e) {
			String str=ExceptionFunctions.GetPrintStackTraceString(e);
			LogFile.WriteFatal("[PD1Parser-<init>] Below is the stack trace.");
			LogFile.WriteLine(str);
			
			LogFile.CloseLogFile();
			
			System.exit(1);
		}
		finally {
			try {
				if(dis!=null) {
					dis.close();
				}
			}
			catch(IOException e) {
				String str=ExceptionFunctions.GetPrintStackTraceString(e);
				LogFile.WriteFatal("[PD1Parser-<init>] Below is the stack trace.");
				LogFile.WriteLine(str);
				
				LogFile.CloseLogFile();
				
				System.exit(1);
			}
		}
		
		int count=0;
		
		//Number of points
		int point_num=Byte.toUnsignedInt(bin.get(count));
		count+=2;
		
		//Points
		for(int i=0;i<point_num;i++) {
			Point point=new Point();
			
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
	
	public List<Point> GetPoints(){
		return new ArrayList<>(points);
	}
}
