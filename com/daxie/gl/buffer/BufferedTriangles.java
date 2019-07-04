package com.daxie.gl.buffer;

import java.nio.FloatBuffer;
import java.util.List;

import com.daxie.gl.shape.Triangle;

public class BufferedTriangles {
	private FloatBuffer pos_buffer;
	private FloatBuffer norm_buffer;
	private FloatBuffer dif_buffer;
	private FloatBuffer spc_buffer;
	private FloatBuffer uv_buffer;
	
	public BufferedTriangles(List<Triangle> triangles) {
		pos_buffer=BufferGenerator.GetPosBuffer(triangles);
		norm_buffer=BufferGenerator.GetNormBuffer(triangles);
		dif_buffer=BufferGenerator.GetDifBuffer(triangles);
		spc_buffer=BufferGenerator.GetSpcBuffer(triangles);
		uv_buffer=BufferGenerator.GetUVBuffer(triangles);
	}
	
	//Getters return a ref to a buffer.
	public FloatBuffer GetPosBuffer() {
		return pos_buffer;
	}
	public FloatBuffer GetNormBuffer() {
		return norm_buffer;
	}
	public FloatBuffer GetDifBuffer() {
		return dif_buffer;
	}
	public FloatBuffer GetSpcBuffer() {
		return spc_buffer;
	}
	public FloatBuffer GetUVBuffer() {
		return uv_buffer;
	}
}
