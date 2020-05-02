package com.github.dabasan.xops.properties.exe;

import java.io.IOException;
import java.util.List;

import com.github.dabasan.tool.FileFunctions;
import com.github.dabasan.xops.properties.XOPSConstants;

/**
 * Provides methods to handle execution files (X operations).
 * @author Daba
 *
 */
public class XOPSExeFunctions {
	/**
	 * Returns the version of XOPS.
	 * @param xops_filename Filename to load
	 * @return Version of XOPS
	 * @throws IOException
	 */
	public static XOPSVersion GetXOPSVersion(String xops_filename) throws IOException{
		XOPSVersion version=XOPSVersion.UNKNOWN_VERSION;
		
		List<Byte> bin=FileFunctions.GetFileAllBin(xops_filename);
		int file_size=bin.size();
		
		if(file_size==XOPSConstants.XOPS_096_OR_096T_FILE_SIZE) {
			byte[] b=new byte[3];
			
			b[0]=bin.get(0x00000100);
			b[1]=bin.get(0x00000101);
			b[2]=bin.get(0x00000102);
			
			if(b[0]==0xB7&&b[1]==0xBF&&b[2]==0x54) {
				version=XOPSVersion.XOPS096;
			}
			else if(b[0]==0x97&&b[1]==0x7B&&b[2]==0xAA) {
				version=XOPSVersion.XOPS096T;
			}
		}
		else if(file_size==XOPSConstants.XOPS097FT_FILE_SIZE) {
			version=XOPSVersion.XOPS097FT;
		}
		else if(file_size==XOPSConstants.XOPS0975T_FILE_SIZE) {
			version=XOPSVersion.XOPS0975T;
		}
		else if(file_size==XOPSConstants.XOPSOLT18F2_FILE_SIZE) {
			version=XOPSVersion.XOPSOLT18F2;
		}
		else if(file_size==XOPSConstants.XOPSOLT19F2_FILE_SIZE) {
			version=XOPSVersion.XOPSOLT19F2;
		}
		else {
			version=XOPSVersion.UNKNOWN_VERSION;
		}
		
		return version;
	}
	/**
	 * Returns the version of XOPS.
	 * @param bin All bytes of an execution file
	 * @return Version of XOPS
	 */
	public static XOPSVersion GetXOPSVersion(List<Byte> bin) {
		XOPSVersion version=XOPSVersion.UNKNOWN_VERSION;
		
		int file_size=bin.size();
		
		if(file_size==XOPSConstants.XOPS_096_OR_096T_FILE_SIZE) {
			byte[] b=new byte[3];
			int[] ub=new int[3];
			
			b[0]=bin.get(0x00000100);
			b[1]=bin.get(0x00000101);
			b[2]=bin.get(0x00000102);
			
			ub[0]=Byte.toUnsignedInt(b[0]);
			ub[1]=Byte.toUnsignedInt(b[1]);
			ub[2]=Byte.toUnsignedInt(b[2]);
			
			if(ub[0]==0xB7&&ub[1]==0xBF&&ub[2]==0x54) {
				version=XOPSVersion.XOPS096;
			}
			else if(ub[0]==0x97&&ub[1]==0x7B&&ub[2]==0xAA) {
				version=XOPSVersion.XOPS096T;
			}
		}
		else if(file_size==XOPSConstants.XOPS097FT_FILE_SIZE) {
			version=XOPSVersion.XOPS097FT;
		}
		else if(file_size==XOPSConstants.XOPS0975T_FILE_SIZE) {
			version=XOPSVersion.XOPS0975T;
		}
		else if(file_size==XOPSConstants.XOPSOLT18F2_FILE_SIZE) {
			version=XOPSVersion.XOPSOLT18F2;
		}
		else if(file_size==XOPSConstants.XOPSOLT19F2_FILE_SIZE) {
			version=XOPSVersion.XOPSOLT19F2;
		}
		else {
			version=XOPSVersion.UNKNOWN_VERSION;
		}
		
		return version;
	}
}
