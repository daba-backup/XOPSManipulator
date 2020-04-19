package com.daxie.xops.bd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;
import com.daxie.tool.FilenameFunctions;

/**
 * Reads data from a BD1 file.
 * @author Daba
 *
 */
class BD1Parser {
	private Map<Integer, String> texture_filenames_map;//(texture_id,texture_filename)
	private List<BD1Block> blocks;
	
	public BD1Parser(String bd1_filename) throws IOException{
		texture_filenames_map=new HashMap<>();
		blocks=new ArrayList<>();
		
		List<Byte> bin=FileFunctions.GetFileAllBin(bd1_filename);
		
		int pos=0;
		
		//Texture filenames
		byte[] texture_filename_buffer=new byte[31];
		String texture_filename_temp;
		int first_null_pos;
		for(int i=0;i<10;i++) {
			for(int j=0;j<31;j++) {
				texture_filename_buffer[j]=bin.get(pos);
				pos++;
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
		int block_num=ByteFunctions.GetUShortValueFromBin_LE(bin, pos);
		pos+=2;
		
		//Blocks
		for(int i=0;i<block_num;i++) {
			BD1Block block=new BD1Block();
			float coordinate_temp;
			
			//Vertex positions
			for(int j=0;j<8;j++) {
				coordinate_temp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
				pos+=4;
				
				block.SetVertexPositionX(j, coordinate_temp);
			}
			for(int j=0;j<8;j++) {
				coordinate_temp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
				pos+=4;
				
				block.SetVertexPositionY(j, coordinate_temp);
			}
			for(int j=0;j<8;j++) {
				coordinate_temp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
				pos+=4;
				
				block.SetVertexPositionZ(j, coordinate_temp);
			}
			
			//UV coordinates
			for(int j=0;j<24;j++) {
				coordinate_temp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
				pos+=4;
				
				block.SetU(j, coordinate_temp);
			}
			for(int j=0;j<24;j++) {
				coordinate_temp=ByteFunctions.GetFloatValueFromBin_LE(bin, pos);
				pos+=4;
				
				block.SetV(j, coordinate_temp);
			}
			
			//Texture IDs
			for(int j=0;j<6;j++) {
				int texture_id=Byte.toUnsignedInt(bin.get(pos));
				pos+=4;
				
				block.SetTextureID(j, texture_id);
			}
			
			//Enabled flag
			int enabled_flag=Byte.toUnsignedInt(bin.get(pos));
			pos+=4;
			
			if(enabled_flag!=0)block.SetEnabledFlag(true);
			else block.SetEnabledFlag(false);
			
			blocks.add(block);
		}
	}
	
	public List<BD1Block> GetBlocks(){
		return new ArrayList<BD1Block>(blocks);
	}
	public Map<Integer, String> GetTextureFilenamesMap(){
		return new HashMap<Integer,String>(texture_filenames_map);
	}
}
