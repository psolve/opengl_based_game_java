package renderEngine;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Loader {
	
	//Position is in 3d space ;-)
	public RawModel loadToVAO(float []positions){
		int vaoID=createVAO();
		
		storeDataInAttributeList(vaoID, positions);
		unbindVAO();
		
		return new RawModel(vaoID, positions.length/3);
		
	}
	
	private int createVAO(){
		 int vaoID = GL30.glGenVertexArrays();
		 GL30.glBindVertexArray(vaoID);
		 return vaoID;
	}
	
	private void storeDataInAttributeList(int attributeNumber,float[] data){
		int vboID = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
		
		FloatBuffer buffer = storeDataInFloatBuffer(data);
		
		//store the data to the vbo
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
		
		//Tells the vao what type of vbo it is 
		GL20.glVertexAttribPointer(attributeNumber, 3, GL11.GL_FLOAT, false,0,0);
		
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER,0);
	}
	
	private void unbindVAO(){
		GL30.glBindVertexArray(0);
	}
	
	private FloatBuffer storeDataInFloatBuffer(float []buffer){
		FloatBuffer rbuffer = BufferUtils.createFloatBuffer(buffer.length);
		rbuffer.put(buffer);
		rbuffer.flip();
		
		return rbuffer;
	}
	
}
