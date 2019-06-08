package com.daxie.tool;

import java.nio.ByteBuffer;

/**
 * Methods to handle binary representation.
 * @author Daba
 *
 */
public class ByteFunctions {
	/**
	 * Convert a 4-byte byte array to a float value.
	 * @param b A 4-byte big-endian byte array
	 * @return A float value
	 */
	public static float byte_to_float(byte[] b) {
		if(b.length!=4)return 0.0f;//If the size of b is inappropriate, then return 0.
		
		int s=0;//Sign
		int e=0;//Exponent
		float m=0;//Mantissa
		
		float res;
		
		int bit_op_res;
		int rhs;
		
		//Get the sign.
		bit_op_res=b[0]&0b10000000;
		if(bit_op_res==0)s=1;
		else s=-1;
		
		//Get the exponent.
		bit_op_res=b[1]&0b10000000;
		if(bit_op_res!=0)e+=1;
		
		rhs=0b00000001;
		for(int i=1;i<=7;i++) {
			bit_op_res=b[0]&rhs;
			if(bit_op_res!=0)e+=Math.pow(2, i);
			
			rhs=rhs<<1;
		}
		
		//Get the mantissa.
		rhs=0b01000000;
		for(int i=1;i<=7;i++) {
			bit_op_res=b[1]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		rhs=0b10000000;
		for(int i=8;i<=15;i++) {
			bit_op_res=b[2]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		rhs=0b10000000;
		for(int i=16;i<=23;i++) {
			bit_op_res=b[3]&rhs;
			if(bit_op_res!=0)m+=Math.pow(2, -i);
			
			rhs=rhs>>1;
		}
		
		if(e==0&&Math.abs(m)<1.0E-16)res=0.0f;
		else res=s*(1.0f+m)*(float)Math.pow(2, e-127);
		
		return res;
	}
	/**
	 * Convert a 4-byte byte array to a float value.<br>
	 * @see byte_to_float(byte[])
	 * @param b A 4-byte little-endian byte array
	 * @return A float value
	 */
	public static float byte_to_float_le(byte[] b) {
		if(b.length!=4)return 0.0f;//If the size of b is inappropriate, then return 0.
		
		byte[] buffer=new byte[4];
		buffer[0]=b[3];
		buffer[1]=b[2];
		buffer[2]=b[1];
		buffer[3]=b[0];
		
		float res;
		res=ByteFunctions.byte_to_float(buffer);
		
		return res;
	}
	
	/**
	 * Convert a float value to a 4-byte byte array.
	 * @param f A float value
	 * @return A 4-byte big-endian byte array
	 */
	public static byte[] float_to_byte(float f) {
		return ByteBuffer.allocate(4).putFloat(f).array();
	}
	/**
	 * Convert a float value to a 4-byte byte array.
	 * @see float_to_byte(float)
	 * @param f A float value
	 * @return A 4-byte little-endian byte array
	 */
	public static byte[] float_to_byte_le(float f) {
		byte[] b=float_to_byte(f);
		
		byte[] res=new byte[4];
		res[0]=b[3];
		res[1]=b[2];
		res[2]=b[1];
		res[3]=b[0];
		
		return res;
	}
	
	/**
	 * Convert a 2-byte byte array to a short value.
	 * @param b A 2-byte little-endian byte array
	 * @return A short value
	 */
	public static short byte_to_short_le(byte[] b) {
		if(b.length!=2)return 0;
		
		short ret;
		if(b[1]==0x00)ret=(short)Byte.toUnsignedInt(b[0]);
		else ret=(short)(Byte.toUnsignedInt(b[0])-0xFF);
		
		return ret;
	}
}
