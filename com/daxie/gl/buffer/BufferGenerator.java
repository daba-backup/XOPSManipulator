package com.daxie.gl.buffer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import com.daxie.basis.coloru8.ColorU8;
import com.daxie.basis.vector.Vector;
import com.daxie.gl.shape.Triangle;
import com.daxie.gl.shape.Vertex;
import com.daxie.log.LogFile;

/**
 * Generates suitable buffers for OpenGL.
 * @author Daba
 *
 */
public class BufferGenerator {
	private static final int SIZEOF_FLOAT=4;
	
	public static FloatBuffer GetPosBuffer(List<Triangle> triangles) {
		if(triangles==null) {
			LogFile.WriteError("[BufferGenerator-GetPosBuffer] Null argument passed.");
			return null;
		}
		
		ByteBuffer bb=ByteBuffer.allocateDirect(triangles.size()*9*SIZEOF_FLOAT);
		bb.order(ByteOrder.nativeOrder());
		
		FloatBuffer pos_buffer=bb.asFloatBuffer();
		for(Triangle triangle:triangles) {
			Vertex[] vertices=triangle.GetVertices();
			for(int i=0;i<3;i++) {
				Vector pos=vertices[i].GetPos();
				
				pos_buffer.put(pos.GetX());
				pos_buffer.put(pos.GetY());
				pos_buffer.put(pos.GetZ());
			}
		}
		
		pos_buffer.flip();
		
		return pos_buffer;
	}
	public static FloatBuffer GetNormBuffer(List<Triangle> triangles) {
		if(triangles==null) {
			LogFile.WriteError("[BufferGenerator-GetNormBuffer] Null argument passed.");
			return null;
		}
		
		ByteBuffer bb=ByteBuffer.allocateDirect(triangles.size()*9*SIZEOF_FLOAT);
		bb.order(ByteOrder.nativeOrder());
		
		FloatBuffer norm_buffer=bb.asFloatBuffer();
		for(Triangle triangle:triangles) {
			Vertex[] vertices=triangle.GetVertices();
			for(int i=0;i<3;i++) {
				Vector norm=vertices[i].GetNorm();
				
				norm_buffer.put(norm.GetX());
				norm_buffer.put(norm.GetY());
				norm_buffer.put(norm.GetZ());
			}
		}
		
		norm_buffer.flip();
		
		return norm_buffer;
	}
	public static FloatBuffer GetDifBuffer(List<Triangle> triangles) {
		if(triangles==null) {
			LogFile.WriteError("[BufferGenerator-GetDifBuffer] Null argument passed.");
			return null;
		}
		
		ByteBuffer bb=ByteBuffer.allocateDirect(triangles.size()*12*SIZEOF_FLOAT);
		bb.order(ByteOrder.nativeOrder());
		
		FloatBuffer dif_buffer=bb.asFloatBuffer();
		for(Triangle triangle:triangles) {
			Vertex[] vertices=triangle.GetVertices();
			for(int i=0;i<3;i++) {
				ColorU8 dif=vertices[i].GetDif();
				
				dif_buffer.put(dif.GetR());
				dif_buffer.put(dif.GetG());
				dif_buffer.put(dif.GetB());
				dif_buffer.put(dif.GetA());
			}
		}
		
		dif_buffer.flip();
		
		return dif_buffer;
	}
	public static FloatBuffer GetSpcBuffer(List<Triangle> triangles) {
		if(triangles==null) {
			LogFile.WriteError("[BufferGenerator-GetSpcBuffer] Null argument passed.");
			return null;
		}
		
		ByteBuffer bb=ByteBuffer.allocateDirect(triangles.size()*12*SIZEOF_FLOAT);
		bb.order(ByteOrder.nativeOrder());
		
		FloatBuffer spc_buffer=bb.asFloatBuffer();
		for(Triangle triangle:triangles) {
			Vertex[] vertices=triangle.GetVertices();
			for(int i=0;i<3;i++) {
				ColorU8 spc=vertices[i].GetDif();
				
				spc_buffer.put(spc.GetR());
				spc_buffer.put(spc.GetG());
				spc_buffer.put(spc.GetB());
				spc_buffer.put(spc.GetA());
			}
		}
		
		spc_buffer.flip();
		
		return spc_buffer;
	}
	public static FloatBuffer GetUVBuffer(List<Triangle> triangles) {
		if(triangles==null) {
			LogFile.WriteError("[BufferGenerator-GetUVBuffer] Null argument passed.");
			return null;
		}
		
		ByteBuffer bb=ByteBuffer.allocateDirect(triangles.size()*6*SIZEOF_FLOAT);
		bb.order(ByteOrder.nativeOrder());
		
		FloatBuffer uv_buffer=bb.asFloatBuffer();
		for(Triangle triangle:triangles) {
			Vertex[] vertices=triangle.GetVertices();
			for(int i=0;i<3;i++) {
				float u=vertices[i].GetU();
				float v=vertices[i].GetV();
				
				uv_buffer.put(u);
				uv_buffer.put(v);
			}
		}
		
		uv_buffer.flip();
		
		return uv_buffer;
	}
}
