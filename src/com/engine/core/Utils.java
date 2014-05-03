package com.engine.core;

import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Vertex3f;
import org.lwjgl.BufferUtils;

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

	public static IntBuffer createFlippedBuffer( int... values )
	{
		IntBuffer buffer = createIntBuffer( values.length );
		buffer.put( values );
		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFlippedBuffer( Vertex3f[] vertices )
	{
		FloatBuffer buffer = createFloatBuffer( vertices.length * Vertex3f.SIZE );

		for ( int i = 0; i < vertices.length; i++ )
		{
			buffer.put( vertices[i].getX() );
			buffer.put( vertices[i].getY() );
			buffer.put( vertices[i].getZ() );
			buffer.put( vertices[i].getColor().getRed() );
			buffer.put( vertices[i].getColor().getGreen() );
			buffer.put( vertices[i].getColor().getBlue() );
			buffer.put( vertices[i].getNormal().getX() );
			buffer.put( vertices[i].getNormal().getY() );
			buffer.put( vertices[i].getNormal().getZ() );
		}

		buffer.flip();

		return buffer;
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
}
