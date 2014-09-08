/*
 * Copyright (C) 2014 Repingon Benjamin
 * This file is part of CommunityGame.
 * CommunityGame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * CommunityGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
 */

package com.engine.core;

import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

/**
 * Created on 13/04/14.
 */
public class Utils
{
	public static IntBuffer createRegularFlippedBuffer( int size )
	{
		IntBuffer buffer = createIntBuffer( size );

		for ( int i = 0; i < size; i++ )
			buffer.put( i );
		buffer.flip();

		return buffer;
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

	@Deprecated
	public static FloatBuffer createFlippedBuffer( Vector3f[] vertices, int[] indices )
	{
		FloatBuffer buffer = createFloatBuffer( vertices.length * 9 );

		int i = 0;
		for ( Vector3f vertex : vertices )
		{
			vertexToBuffer( buffer, vertex, calcNormals( vertices, indices, i / 3 ) );
			i++;
		}

		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFloatBuffer( int size )
	{
		return BufferUtils.createFloatBuffer( size );
	}

	private static void vertexToBuffer( FloatBuffer buffer, Vector3f vertex, Vector3f normal )
	{
		buffer.put( vertex.getX() );
		buffer.put( vertex.getY() );
		buffer.put( vertex.getZ() );
		buffer.put( (float) vertex.getColor().getRed() / 255 );
		buffer.put( (float) vertex.getColor().getGreen() / 255 );
		buffer.put( (float) vertex.getColor().getBlue() / 255 );
		buffer.put( normal.getX() );
		buffer.put( normal.getY() );
		buffer.put( normal.getZ() );
	}

	@Deprecated
	private static Vector3f calcNormals( Vector3f[] vertices, int[] indices, int index )
	{
		int i0 = indices[index];
		int i1 = indices[index + 1];
		int i2 = indices[index + 2];
		Vector3f v1 = new Vector3f( vertices[i0], vertices[i1] );
		Vector3f v2 = new Vector3f( vertices[i0], vertices[i2] );
		return v1.cross( v2 ).normalized();// TODO: normal
//		return new Vector3f( 0, 1, 0 );
	}

	public static FloatBuffer createFlippedBuffer( ArrayList<Triangle> triangles )
	{
		FloatBuffer buffer = createFloatBuffer( triangles.size() * 3 * 9 );

		for ( int i = 0; i < triangles.size(); i++ )
		{
			vertexToBuffer( buffer, triangles.get( i ).getPoint( 1 ), triangles.get( i ).getNormal() );
			vertexToBuffer( buffer, triangles.get( i ).getPoint( 2 ), triangles.get( i ).getNormal() );
			vertexToBuffer( buffer, triangles.get( i ).getPoint( 3 ), triangles.get( i ).getNormal() );
		}
		buffer.flip();

		return buffer;
	}

	public static FloatBuffer createFlippedBuffer( Triangle triangle )
	{
		FloatBuffer buffer = createFloatBuffer( 3 * 9 );

		vertexToBuffer( buffer, triangle.getPoint( 1 ), triangle.getNormal() );
		vertexToBuffer( buffer, triangle.getPoint( 2 ), triangle.getNormal() );
		vertexToBuffer( buffer, triangle.getPoint( 3 ), triangle.getNormal() );

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

	public static ArrayList<Triangle> convertVerticesToTriangles( Vector3f[] vertices, int[] indices )
	{
		ArrayList<Triangle> list = new ArrayList<Triangle>();
		int index = 0;

		while ( index < indices.length )
			list.add( new Triangle( vertices[indices[index++]], vertices[indices[index++]], vertices[indices[index++]] ) );
		return list;
	}

	public static String[] removeEmptyStrings( String[] data )
	{
		ArrayList<String> result = new ArrayList<String>();

		for ( String aData : data )
			if ( !aData.equals( "" ) )
				result.add( aData );

		String[] res = new String[result.size()];
		result.toArray( res );

		return res;
	}

	public static int[] toIntArray( Integer[] data )
	{
		int[] result = new int[data.length];

		for ( int i = 0; i < data.length; i++ )
			result[i] = data[i].intValue();

		return result;
	}


}
