package com.daxie.xops.bd1;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.daxie.basis.vector.Vector;
import com.daxie.tool.ByteFunctions;
import com.daxie.tool.FileFunctions;

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
		
		List<Byte> bin=new ArrayList<>();
		
		this.AddTextureFilenamesToBin(bin);
		
		//Number of blocks
		int block_num=blocks.size();
		ByteFunctions.AddUShortValueToBin_LE(bin, block_num);
		
		//Block data
		for(int i=0;i<blocks.size();i++) {
			BD1Block block=blocks.get(i);
			
			Vector[] vertex_positions=block.GetVertexPositions();
			float[] us=block.GetUs();
			float[] vs=block.GetVs();
			int[] texture_ids=block.GetTextureIDs();
			
			//Vertex positions
			for(int j=0;j<8;j++) {
				ByteFunctions.AddFloatValueToBin_LE(bin, vertex_positions[j].GetX());
			}
			for(int j=0;j<8;j++) {
				ByteFunctions.AddFloatValueToBin_LE(bin, vertex_positions[j].GetY());
			}
			for(int j=0;j<8;j++) {
				ByteFunctions.AddFloatValueToBin_LE(bin, vertex_positions[j].GetZ());
			}
			
			//UV coordinates
			for(int j=0;j<24;j++) {
				ByteFunctions.AddFloatValueToBin_LE(bin, us[j]);
			}
			for(int j=0;j<24;j++) {
				ByteFunctions.AddFloatValueToBin_LE(bin, vs[j]);
			}
			
			//Texture IDs
			for(int j=0;j<6;j++) {
				bin.add((byte)texture_ids[j]);
				for(int k=0;k<3;k++) {
					bin.add((byte)0);
				}
			}
			
			//Enabled flag
			if(block.GetEnabledFlag()==true) {
				bin.add((byte)1);
			}
			else{
				bin.add((byte)0);
			}
			for(int j=0;j<3;j++) {
				bin.add((byte)0);
			}
		}
		
		try {
			FileFunctions.CreateBinFile(bd1_filename, bin);
		}
		catch(IOException e) {
			logger.error("Error while writing.",e);
			return -1;
		}
			
		return 0;
	}
	private void AddTextureFilenamesToBin(List<Byte> bin) {
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
			
			for(int i=0;i<31;i++) {
				bin.add(texture_filename_buffer[i]);
			}
			
			texture_count++;
		}
		
		for(int i=texture_count;i<10;i++) {
			for(int j=0;j<31;j++) {
				bin.add((byte)0);
			}
		}
	}
}
