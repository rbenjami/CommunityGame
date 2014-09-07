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

package com.engine.core.components;

import com.engine.core.Utils;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/08/14.
 */
public class MeshLoader
{
	public static List<Triangle> loadOBJ( String fileName )
	{
		ArrayList<Vector3f> positions = new ArrayList<Vector3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();

		String[] splitArray = fileName.split( "\\." );
		String ext = splitArray[splitArray.length - 1];
		if ( !ext.equals( "obj" ) )
		{
			System.err.println( "Error: '" + ext + "' file format not supported for mesh data." );
			new Exception().printStackTrace();
			System.exit( 1 );
		}

		try
		{
			BufferedReader meshReader = new BufferedReader( new FileReader( fileName ) );
			String line;
			while ( ( line = meshReader.readLine() ) != null )
			{
				String[] tokens = line.split( " " );
				tokens = Utils.removeEmptyStrings( tokens );

				if ( tokens.length != 0 && !tokens[0].equals( "#" ) )
				{
					if ( tokens[0].equals( "v" ) )
						positions.add( new Vector3f( Float.valueOf( tokens[1] ), Float.valueOf( tokens[2] ), Float.valueOf( tokens[3] ) ) );
					else if ( tokens[0].equals( "f" ) )
					{
						for ( int i = 0; i < tokens.length - 3; i++ )
						{
							indices.add( parseOBJIndex( tokens[1] ) );
							indices.add( parseOBJIndex( tokens[2 + i] ) );
							indices.add( parseOBJIndex( tokens[3 + i] ) );
						}
					}
				}
			}
			meshReader.close();

			int offset = 0;
			for ( int i = 0; i < indices.size(); i += 3 )
				triangles.add( new Triangle( positions.get( indices.get( i ) ), positions.get( indices.get( i + 1 ) ), positions.get( indices.get( i + 2 ) ), offset++ ) );
		}
		catch ( FileNotFoundException e )
		{
			System.err.println( "Error: file '" + fileName + "' not found." );
			e.printStackTrace();
			System.exit( 1 );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return triangles;
	}

	private static int parseOBJIndex( String token )
	{
		String[] values = token.split( "/" );
		return Integer.parseInt( values[0] ) - 1;
	}
}
