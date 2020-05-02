package com.github.dabasan.xops.mif;

import java.io.IOException;

public class MIFListCSV {
	public static void main(String[] args) {
		MIFList list;
		try {
			list=new MIFList("./TestData/addon", "Shift-JIS");
		}
		catch(IOException e) {
			e.printStackTrace();
			return;
		}
		
		list.WriteCSV("./TestData/mif_list.csv", MIFList.ALL);
	}
}
