package com.engine.core.components.meshLoading;

import com.engine.core.Utils;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created on 17/06/14.
 */

public class OBJModel
{
	private ArrayList<Triangle> triangles;
	private ArrayList<Vector3f> positions;
	private ArrayList<Integer>  indices;

	public OBJModel( String fileName )
	{
		positions = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();
		triangles = new ArrayList<Triangle>();

		BufferedReader meshReader = null;

		try
		{
			meshReader = new BufferedReader( new FileReader( fileName ) );
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
		catch ( Exception e )
		{
			e.printStackTrace();
			System.exit( 1 );
		}
	}

	private int parseOBJIndex( String token )
	{
		String[] values = token.split( "/" );
		return Integer.parseInt( values[0] ) - 1;
	}

	public ArrayList<Triangle> getTriangles()
	{
		return triangles;
	}
}