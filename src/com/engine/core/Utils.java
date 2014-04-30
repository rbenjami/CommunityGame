package com.engine.core;

import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Vertex3f;
import org.lwjgl.BufferUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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


	public static FloatBuffer createFlippedBuffer( Vertex3f[] verticles )
	{
		FloatBuffer verticlesBuffer = BufferUtils.createFloatBuffer( verticles.length * Vertex3f.SIZE );
		for ( int i = 0; i < verticles.length; i++ )
		{
			verticlesBuffer.put( verticles[i].getX() );
			verticlesBuffer.put( verticles[i].getY() );
			verticlesBuffer.put( verticles[i].getZ() );
			verticlesBuffer.put( (float) verticles[i].getColor().getRed() / 256 );
			verticlesBuffer.put( (float) verticles[i].getColor().getGreen() / 256 );
			verticlesBuffer.put( (float) verticles[i].getColor().getBlue() / 256 );
			verticlesBuffer.put( verticles[i].getNormal().getX() );
			verticlesBuffer.put( verticles[i].getNormal().getY() );
			verticlesBuffer.put( verticles[i].getNormal().getZ() );
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
