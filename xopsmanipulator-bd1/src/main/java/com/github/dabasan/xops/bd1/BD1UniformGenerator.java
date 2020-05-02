package com.github.dabasan.xops.bd1;

import java.util.Random;

import com.github.dabasan.basis.vector.Vector;
import com.github.dabasan.basis.vector.VectorFunctions;

/**
 * Generates blocks according to the uniform distribution.
 * @author Daba
 *
 */
public class BD1UniformGenerator extends BD1Generator{
	public BD1UniformGenerator() {
		
	}
	
	@Override
	public void GenerateCubes(int block_num,float edge_length,float scale) {
		Random random=new Random();
		BD1Creator bd1_creator=this.GetBD1Creator();
		
		for(int i=0;i<block_num;i++) {
			float x=random.nextFloat()*scale;
			float y=random.nextFloat()*scale;
			float z=random.nextFloat()*scale;
			
			int sign_x=random.nextInt(2);
			int sign_y=random.nextInt(2);
			int sign_z=random.nextInt(2);
			
			if(sign_x%2==1)x*=(-1.0f);
			if(sign_y%2==1)y*=(-1.0f);
			if(sign_z%2==1)z*=(-1.0f);
			
			Vector center=VectorFunctions.VGet(x, y, z);
			
			bd1_creator.CreateCube(center, edge_length);
		}
	}
}
