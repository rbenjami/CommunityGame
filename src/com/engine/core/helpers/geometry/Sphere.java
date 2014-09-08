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

package com.engine.core.helpers.geometry;

import com.engine.core.Utils;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;

public class Sphere
{
	private Vector3f[] vertices;
	private int[]      indices;

	public Sphere( int subdivision, int size )
	{
		vertices = new Vector3f[subdivision * subdivision * 6];
		int perFaceVertexCount = subdivision * subdivision;
		float invSubDiv = 1.f / ( subdivision - 1 );

		for ( int face = 0; face != 3; face++ )
		{
			int faceIndex[][] = new int[][]{ { 0, 1, 2 }, { 2, 0, 1 }, { 1, 2, 0 } };
			int indirect[] = faceIndex[face];
			for ( int v = 0; v < subdivision; v++ )
			{
				float texV = v * invSubDiv;
				for ( int u = 0; u < subdivision; u++ )
				{
					float texU = u * invSubDiv;
					float posU = texU * 2.f - 1.f;
					float posV = texV * 2.f - 1.f;
					float invLen = 1.f / (float) Math.sqrt( posU * posU + posV * posV + 1.f );
					float vertex[] = new float[]{ posU * invLen, posV * invLen, invLen };

					Vector3f result = new Vector3f( vertex[indirect[0]], vertex[indirect[1]], vertex[indirect[2]] );
					vertices[perFaceVertexCount * ( 2 * face ) + u + subdivision * v] = result.mul( size );

					Vector3f result2;
					if ( face == 2 )
						result2 = new Vector3f( vertex[indirect[0]] * -1, vertex[indirect[1]] * -1, vertex[indirect[2]] );
					else
						result2 = new Vector3f( vertex[indirect[0]] * -1, vertex[indirect[1]], vertex[indirect[2]] * -1 );
					vertices[perFaceVertexCount * ( 2 * face + 1 ) + u + subdivision * v] = result2.mul( size );
				}
			}
		}
		int perFaceQuadCount = ( subdivision - 1 ) * ( subdivision - 1 );

		int faceIndices[] = new int[perFaceQuadCount * 2 * 3];
		for ( int v = 0; v != subdivision - 1; v++ )
		{
			for ( int u = 0; u != subdivision - 1; u++ )
			{
				int i0 = ( u + 0 ) + ( v + 0 ) * subdivision;
				int i1 = ( u + 0 ) + ( v + 1 ) * subdivision;
				int i2 = ( u + 1 ) + ( v + 1 ) * subdivision;
				int i3 = ( u + 1 ) + ( v + 0 ) * subdivision;

				int index = ( v * ( subdivision - 1 ) + u ) * 6;
				faceIndices[index] = i1;
				faceIndices[index + 1] = i0;
				faceIndices[index + 2] = i3;
				faceIndices[index + 3] = i1;
				faceIndices[index + 4] = i3;
				faceIndices[index + 5] = i2;
			}
		}
		indices = new int[perFaceQuadCount * 2 * 3 * 6];
		for ( int face = 0; face != 6; face++ )
		{
			int vertexOffs = face * subdivision * subdivision;
			int indexOffs = face * 2 * 3 * perFaceQuadCount;
			int i = 0;
			for ( int idx : faceIndices )
			{
				indices[indexOffs + i] = idx + vertexOffs;
				i++;
			}
		}
	}

	public Mesh getMesh()
	{
//		return new Mesh( vertices, indices );
		return new Mesh( Utils.convertVerticesToTriangles( vertices, indices ) );
	}
}
