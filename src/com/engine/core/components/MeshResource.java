package com.engine.core.components;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created on 14/04/14.
 */
public class MeshResource
{
	private int vbo;
	private int ibo;
	private int size;
	private int refCount;

	public MeshResource( int size )
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers( vbo );
		glDeleteBuffers( ibo );
	}

	public void addReference()
	{
		refCount++;
	}

	public boolean removeReference()
	{
		refCount--;
		return refCount == 0;
	}

	public int getVbo()
	{
		return vbo;
	}

	public int getIbo()
	{
		return ibo;
	}

	public int getSize()
	{
		return size;
	}
}
