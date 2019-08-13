package com.daxie.tool;

import java.io.FileNotFoundException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * Methods to handle hash values.
 * @author Daba
 *
 */
public class HashFunctions {
	/**
	 * Returns a hash value of a string computed with the specified algorithm.
	 * @param value String
	 * @param algorithm Algorithm to compute a hash value
	 * @return Computed hash value
	 * @throws NoSuchAlgorithmException Unknown algorithm specified
	 */
	public static String GetHash(String value,String algorithm) throws NoSuchAlgorithmException{
		byte[] cipher_bytes;
		
		MessageDigest md=MessageDigest.getInstance(algorithm);
		
		md.update(value.getBytes());
		cipher_bytes=md.digest();
		
		StringBuilder sb=new StringBuilder(2*cipher_bytes.length);
		for(byte b:cipher_bytes) {
			sb.append(String.format("%02x", b&0xff));
		}
		
		return sb.toString();
	}
	/**
	 * Returns a hash value of a byte list computed with the specified algorithm.
	 * @param value Byte list
	 * @param algorithm Algorithm to compute a hash value
	 * @return Computed hash value
	 * @throws NoSuchAlgorithmException Unknown algorithm specified
	 */
	public static String GetHash(List<Byte> value,String algorithm) throws NoSuchAlgorithmException{
		byte[] cipher_bytes;
		
		MessageDigest md=MessageDigest.getInstance(algorithm);
		
		byte[] bytes=new byte[value.size()];
		for(int i=0;i<bytes.length;i++) {
			bytes[i]=value.get(i);
		}
		
		md.update(bytes);
		cipher_bytes=md.digest();
		
		StringBuilder sb=new StringBuilder(2*cipher_bytes.length);
		for(byte b:cipher_bytes) {
			sb.append(String.format("%02x", b&0xff));
		}
		
		return sb.toString();
	}
	/**
	 * Calculates a hash value of a file.
	 * @param filename Filename
	 * @param algorithm Algorithm
	 * @return Hash value
	 * @throws FileNotFoundException Specified file not found
	 * @throws NoSuchAlgorithmException Specified algorithm not found
	 */
	public static String GetFileHash(String filename,String algorithm) throws FileNotFoundException,NoSuchAlgorithmException{
		List<Byte> bin=FileFunctions.GetFileAllBin(filename);
		String hash=HashFunctions.GetHash(bin, algorithm);
		
		return hash;
	}
}
