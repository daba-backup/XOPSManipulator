package com.daxie.gl.shape;

public class Triangle {
	private Vertex[] vertices;
	
	public Triangle() {
		vertices=new Vertex[3];
	}
	
	public void SetVertex(int index,Vertex v) {
		vertices[index]=v;
	}
	public Vertex GetVertex(int index) {
		return new Vertex(vertices[index]);
	}
	public Vertex[] GetVertices() {
		Vertex[] ret=new Vertex[3];
		for(int i=0;i<3;i++)ret[i]=new Vertex(vertices[i]);
		
		return ret;
	}
}
