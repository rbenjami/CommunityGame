package com.engine.core;

import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created on 13/04/14.
 */
public class Utils
{
	public static FloatBuffer createFloatBuffer( int size )
	{
		return BufferUtils.createFloatBuffer( size );
	}

	public static IntBuffer createIntBuffer( int size )
	{
		return BufferUtils.createIntBuffer( size );
	}

	public static ByteBuffer createByteBuffer( int size )
	{
		return BufferUtils.createByteBuffer( size );
	}

	public static IntBuffer createFlippedBuffer( int... values )
	{
		IntBuffer buffer = createIntBuffer( values.length );
		buffer.put( values );
		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFlippedBuffer( ArrayList<Vertex3f> vertices )
	{
		FloatBuffer verticlesBuffer = BufferUtils.createFloatBuffer( vertices.size() * 6 );
		for ( Vertex3f vert : vertices )
		{
			verticlesBuffer.put( vert.toFloat() );
			verticlesBuffer.put( (float) vert.getColor().getRed() / 256 );
			verticlesBuffer.put( (float) vert.getColor().getGreen() / 256 );
			verticlesBuffer.put( (float) vert.getColor().getBlue() / 256 );
		}
		verticlesBuffer.flip();
		return verticlesBuffer;
	}

	public static FloatBuffer createFlippedBuffer( Vertex3f[] vertices )
	{
		FloatBuffer verticlesBuffer = BufferUtils.createFloatBuffer( vertices.length * 6 );
		for ( int i = 0; i < vertices.length; i++ )
		{
			verticlesBuffer.put( vertices[i].toFloat() );
			verticlesBuffer.put( (float) vertices[i].getColor().getRed() / 256 );
			verticlesBuffer.put( (float) vertices[i].getColor().getGreen() / 256 );
			verticlesBuffer.put( (float) vertices[i].getColor().getBlue() / 256 );
		}
		verticlesBuffer.flip();
		return verticlesBuffer;
	}

	public static FloatBuffer createFlippedBuffer( Matrix4f value )
	{
		FloatBuffer buffer = createFloatBuffer( 4 * 4 );

		for ( int i = 0; i < 4; i++ )
			for ( int j = 0; j < 4; j++ )
				buffer.put( value.get( i, j ) );

		buffer.flip();

		return buffer;
	}

	public static int[] toIntArray( Integer[] data )
	{
		int[] result = new int[data.length];

		for ( int i = 0; i < data.length; i++ )
			result[i] = data[i].intValue();

		return result;
	}
}
