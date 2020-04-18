package com.daxie.xops.bd1;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;

/**
 * Writes data to a BD1 file.
 * @author Daba
 *
 */
class BD1Writer {
	private Logger logger=LoggerFactory.getLogger(BD1Writer.class);
	
	private List<BD1Block> blocks;
	private Map<Integer, String> texture_filenames_map;
	
	public BD1Writer(List<BD1Block> blocks,Map<Integer, String> texture_filenames_map) {
		this.blocks=blocks;
		this.texture_filenames_map=texture_filenames_map;
	}
	
	public int Write(String bd1_filename){
		if(blocks==null||texture_filenames_map==null) {
			logger.warn("Data not prepared.");
			return -1;
		}
		
		try(DataOutputStream dos=new DataOutputStream(new BufferedOutputStream(new FileOutputStream(bd1_filename)))) {
			//Texture filenames
			Map<Integer, String> sorted_texture_filenames_map=new TreeMap<>(texture_filenames_map);
			
			int texture_count=0;
			for(int texture_id:sorted_texture_filenames_map.keySet()) {
				String texture_filename=sorted_texture_filenames_map.get(texture_id);
				if(texture_filename.length()>30) {
					texture_filename=texture_filename.substring(0, 30);
				}
				
				byte[] texture_filename_buffer=new byte[31];
				
				int pos;
				for(pos=0;pos<texture_filename.length();pos++) {
					texture_filename_buffer[pos]=(byte)texture_filename.charAt(pos);
				}
				for(;pos<texture_filename_buffer.length;pos++) {
					texture_filename_buffer[pos]=0;
				}
				
				dos.write(texture_filename_buffer);
				texture_count++;
			}
			
			byte[] null_buffer=new byte[31];
			for(int i=0;i<31;i++)null_buffer[i]=0;
			for(int i=texture_count;i<10;i++) {
				dos.write(null_buffer);
			}
			
			//Number of blocks
			int block_num=blocks.size();
			
			byte[] block_num_buffer=ByteFunctions.ushort_to_byte_le(block_num);
			dos.write(block_num_buffer);
			
			//Block data
			for(int i=0;i<blocks.size();i++) {
				BD1Block block=blocks.get(i);
				
				Vector[] vertex_positions=block.GetVertexPositions();
				float[] us=block.GetUs();
				float[] vs=block.GetVs();
				int[] texture_ids=block.GetTextureIDs();
				
				byte[] b;
				
				//Vertex positions
				for(int j=0;j<8;j++) {
					b=ByteFunctions.float_to_byte_le(vertex_positions[j].GetX());
					dos.write(b);
				}
				for(int j=0;j<8;j++) {
					b=ByteFunctions.float_to_byte_le(vertex_positions[j].GetY());
					dos.write(b);
				}
				for(int j=0;j<8;j++) {
					b=ByteFunctions.float_to_byte_le(vertex_positions[j].GetZ());
					dos.write(b);
				}
				
				//UV coordinates
				for(int j=0;j<24;j++) {
					b=ByteFunctions.float_to_byte_le(us[j]);
					dos.write(b);
				}
				for(int j=0;j<24;j++) {
					b=ByteFunctions.float_to_byte_le(vs[j]);
					dos.write(b);
				}
				
				//Texture IDs
				for(int j=0;j<6;j++) {
					dos.writeByte(texture_ids[j]);
					for(int k=0;k<3;k++)dos.writeByte(0);
				}
				
				//Enabled flag
				if(block.GetEnabledFlag()==true)dos.writeByte(1);
				else dos.writeByte(0);
				for(int j=0;j<3;j++)dos.writeByte(0);
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
