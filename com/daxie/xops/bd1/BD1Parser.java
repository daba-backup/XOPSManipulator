package com.daxie.xops.bd1;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FilenameFunctions;

/**
 * Read data from a BD1 file.
 * @author Daba
 *
 */
class BD1Parser {
	private Map<Integer, String> texture_filenames_map;//(texture_id,texture_filename)
	private List<Block> blocks;
	
	public BD1Parser(String bd1_filename)  throws FileNotFoundException{
		texture_filenames_map=new HashMap<>();
		blocks=new ArrayList<>();
		
		List<Byte> bin=new ArrayList<Byte>();
		
		DataInputStream dis;	
		dis=new DataInputStream(
				new BufferedInputStream(
						new FileInputStream(bd1_filename)));
		
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
			e.printStackTrace();
		}
		finally {
			try {
				if(dis!=null) {
					dis.close();
				}
			}
			catch(IOException e) {
				e.printStackTrace();
			}
		}
		
		int count=0;
		
		//Texture filenames
		byte[] texture_filename_buffer=new byte[31];
		String texture_filename_temp;
		int first_null_pos;
		for(int i=0;i<10;i++) {
			for(int j=0;j<31;j++) {
				texture_filename_buffer[j]=bin.get(count);
				count++;
			}
			texture_filename_temp=new String(texture_filename_buffer);
			
			first_null_pos=30;
			for(int j=0;j<30;j++) {
				if(texture_filename_temp.charAt(j)=='\0') {
					first_null_pos=j;
					break;
				}
			}
			
			texture_filename_temp=texture_filename_temp.substring(0, first_null_pos);
			texture_filename_temp=FilenameFunctions.ReplaceWindowsDelimiterWithLinuxDelimiter(texture_filename_temp);
			
			texture_filenames_map.put(i, texture_filename_temp);
		}
		
		//Number of blocks
		int block_num=Byte.toUnsignedInt(bin.get(count));
		count+=2;
		
		//Blocks
		for(int i=0;i<block_num;i++) {
			Block block=new Block();
			
			byte[] byte_buffer=new byte[4];
			float coordinate_temp;
			
			//Vertex positions
			for(int j=0;j<8;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				coordinate_temp=ByteFunctions.byte_to_float_le(byte_buffer);
				block.SetVertexPositionX(j, coordinate_temp);
			}
			for(int j=0;j<8;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				coordinate_temp=ByteFunctions.byte_to_float_le(byte_buffer);
				block.SetVertexPositionY(j, coordinate_temp);
			}
			for(int j=0;j<8;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				coordinate_temp=ByteFunctions.byte_to_float_le(byte_buffer);
				block.SetVertexPositionZ(j, coordinate_temp);
			}
			
			//UV coordinates
			for(int j=0;j<24;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				coordinate_temp=ByteFunctions.byte_to_float_le(byte_buffer);
				block.SetU(j, coordinate_temp);
			}
			for(int j=0;j<24;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				coordinate_temp=ByteFunctions.byte_to_float_le(byte_buffer);
				block.SetV(j, coordinate_temp);
			}
			
			//Texture IDs
			for(int j=0;j<6;j++) {
				for(int k=0;k<4;k++) {
					byte_buffer[k]=bin.get(count);
					count++;
				}
				block.SetTextureID(j, (char)byte_buffer[0]);
			}
			
			//Enabled flag
			for(int j=0;j<4;j++) {
				byte_buffer[j]=bin.get(count);
				count++;
			}
			if(byte_buffer[0]!=0)block.SetEnabledFlag(true);
			else block.SetEnabledFlag(false);
			
			blocks.add(block);
		}
	}
	
	public List<Block> GetBlocks(){
		return new ArrayList<Block>(blocks);
	}
	public Map<Integer, String> GetTextureFilenamesMap(){
		return new HashMap<Integer,String>(texture_filenames_map);
	}
}
