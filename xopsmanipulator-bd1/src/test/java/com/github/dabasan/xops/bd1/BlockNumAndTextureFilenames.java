package com.github.dabasan.xops.bd1;

import java.io.IOException;
import java.util.Map;

public class BlockNumAndTextureFilenames {
	public static void main(String[] args) {
		BD1Manipulator manipulator;
		try {
			manipulator=new BD1Manipulator("./TestData/temp.bd1");
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		int block_num=manipulator.GetBlockNum();
		System.out.println(block_num);
		
		Map<Integer, String> texture_filenames_map=manipulator.GetTextureFilenamesMap();
		for(Map.Entry<Integer, String> entry:texture_filenames_map.entrySet()) {
			System.out.printf("(%d,%s)\n", entry.getKey(),entry.getValue());
		}
	}
}
