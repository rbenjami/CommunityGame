package com.engine.core;

/**
 * Created on 10/04/14.
 */
public class Tessellator
{
	private Vertex3f[] vertexBuffer;
	private int vertexBufferIndex = 0;

	public Tessellator()
	{
		this.vertexBuffer = null;
	}

	public void addVertex( Vertex3f newVertex )
	{
		this.vertexBuffer[this.vertexBufferIndex++] = newVertex;
	}

	public void draw()
	{
		if ( vertexBufferIndex == 0 )
			throw new IllegalStateException( "No tessellator!" );
		else if ( vertexBufferIndex == 3 )
		{
		}
	}
}

