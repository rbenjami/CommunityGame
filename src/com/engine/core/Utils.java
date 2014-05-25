package com.engine.core;

import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Vector3f;
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

	public static FloatBuffer createFlippedBuffer( Vector3f[] vertices )
	{
		FloatBuffer buffer = createFloatBuffer( vertices.length * 9 );

		for ( Vector3f vertex : vertices )
		{
			if ( vertex == null )
				break ;
			buffer.put( vertex.getX() );
			buffer.put( vertex.getY() );
			buffer.put( vertex.getZ() );
			buffer.put( (float) vertex.getColor().getRed() / 255 );
			buffer.put( (float) vertex.getColor().getGreen() / 255 );
			buffer.put( (float) vertex.getColor().getBlue() / 255 );
			buffer.put( vertex.getNormal().getX() );
			buffer.put( vertex.getNormal().getY() );
			buffer.put( vertex.getNormal().getZ() );
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

	public static int[] toIntArray( Integer[] data )
	{
		int[] result = new int[data.length];

		for ( int i = 0; i < data.length; i++ )
			result[i] = data[i].intValue();

		return result;
	}
}
