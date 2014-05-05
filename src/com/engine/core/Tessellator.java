package com.engine.core;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.dimensions.Vertex3f;

import java.util.ArrayList;

import java.awt.*;

/**
 * Created on 10/04/14.
 */
public class Tessellator
{
	private int  fraction;
	private float noise;
	private Color color;
	private Mesh mesh;

	public Tessellator( int fraction, float noise, Color color )
	{
		this.fraction = fraction;
		this.noise = noise / (float)fraction;
		this.color = color;
	}

	public void calculateTesselator()
	{
		ArrayList<Vertex3f> vertices = new ArrayList<Vertex3f>();
		ArrayList<Integer>  indices = new ArrayList<Integer>();

		float z = 0;
		while ( z <= 1 )
		{
			float x = 0;
			while ( x <= 1 )
			{
				vertices.add( new Vertex3f( x, MathHelper.rand( 0.0f, this.noise ), z, this.color ) );
				x += (float) 1 / fraction;
			}
			z += (float) 1 / fraction;
		}
		int iz = 0;
		while ( iz <= fraction - 1 )
		{
			int ix = 0;
			while ( ix <= fraction - 1 )
			{
				int actual = (iz * fraction) + ix + iz;
				indices.add( actual );
				indices.add( actual + fraction + 1 );
				indices.add( actual + 1 );
				indices.add( actual + 1 );
				indices.add( actual + fraction + 1 );
				indices.add( actual + fraction + 2 );
				System.out.println( ix + ", " + iz + " : " + actual );
				ix++;
			}
			iz++;
		}
		Vertex3f[] vertexData = new Vertex3f[vertices.size()];
		vertices.toArray( vertexData );

		Integer[] indexData = new Integer[indices.size()];
		indices.toArray( indexData );
		mesh = new Mesh( vertexData, Utils.toIntArray( indexData ) );
	}

	public Mesh getMesh()
	{
		return mesh;
	}
}

