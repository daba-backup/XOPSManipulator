package com.daxie.xops.addon;

import java.io.FileNotFoundException;
import java.security.NoSuchAlgorithmException;

import com.daxie.log.LogFile;
import com.daxie.tool.HashFunctions;

/**
 * Calculates hash values for a set of files contained in an add-on package.
 * @author Daba
 *
 */
public class AddonHashCalculator {
	private String algorithm;
	
	private String bd1_hash;
	private String pd1_hash;
	private String mif_hash;
	
	private String combined_hash;
	
	public AddonHashCalculator(String algorithm) {
		this.algorithm=algorithm;
		
		bd1_hash="";
		pd1_hash="";
		mif_hash="";
		
		combined_hash="";
	}
	
	public int CalculateBD1Hash(String bd1_filename) {
		try {
			bd1_hash=HashFunctions.GetFileHash(bd1_filename, algorithm);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateBD1Hash] File not found. filename:"+bd1_filename,true);
			return -1;
		}
		catch(NoSuchAlgorithmException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateBD1Hash] No such algorithm. algorithm:"+algorithm,true);
			return -1;
		}
		
		return 0;
	}
	public int CalculatePD1Hash(String pd1_filename) {
		try {
			pd1_hash=HashFunctions.GetFileHash(pd1_filename, algorithm);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculatePD1Hash] File not found. filename:"+pd1_filename,true);
			return -1;
		}
		catch(NoSuchAlgorithmException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculatePD1Hash] No such algorithm. algorithm:"+algorithm,true);
			return -1;
		}
		
		return 0;
	}
	public int CalculateMIFHash(String mif_filename) {
		try {
			mif_hash=HashFunctions.GetFileHash(mif_filename, algorithm);
		}
		catch(FileNotFoundException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateMIFHash] File not found. filename:"+mif_filename,true);
			return -1;
		}
		catch(NoSuchAlgorithmException e) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateMIFHash] No such algorithm. algorithm:"+algorithm,true);
			return -1;
		}
		
		return 0;
	}
	
	public int CalculateCombinedHash() {
		boolean hash_not_calculated_flag=false;
		if(bd1_hash.equals("")) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateCombinedHash] BD1 hash not calculated yet.",true);
			hash_not_calculated_flag=true;
		}
		if(pd1_hash.equals("")) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateCombinedHash] PD1 hash not calculated yet.",true);
			hash_not_calculated_flag=true;
		}
		if(mif_hash.equals("")) {
			LogFile.WriteWarn("[AddonHashCalculator-CalculateCombinedHash] MIF hash not calculated yet.",true);
			hash_not_calculated_flag=true;
		}
		
		if(hash_not_calculated_flag==true) {
			return -1;
		}
		
		String str=bd1_hash+pd1_hash+mif_hash;
		try {
			combined_hash=HashFunctions.GetHash(str, algorithm);
		}
		catch(NoSuchAlgorithmException e) {
			return -1;
		}
		
		return 0;
	}
	
	public String GetBD1Hash() {
		return bd1_hash;
	}
	public String GetPD1Hash() {
		return pd1_hash;
	}
	public String GetMIFHash() {
		return mif_hash;
	}
	public String GetCombinedHash() {
		return combined_hash;
	}
}
