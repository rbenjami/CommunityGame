package com.engine.core;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.NoiseHelper;
import com.engine.core.helpers.dimensions.Vertex3f;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created on 10/04/14.
 */
public class Tessellator
{
	private int   fraction;
	private float[][] noise;
	private float smooth;
	private Color color;
	private Mesh  mesh;

	public Tessellator( int fraction, float smooth, Color color )
	{
		this.fraction = fraction;
		this.color = color;
		this.smooth = smooth;
		this.noise = NoiseHelper.generatePerlinNoise(NoiseHelper.generateWhiteNoise( fraction + 1, fraction + 1 ), 6);
	}

	public void calculateTesselator()
	{
		ArrayList<Vertex3f> vertices = new ArrayList<Vertex3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		float[][] pok = NoiseHelper.generateWhiteNoise( fraction + 1, fraction + 1 );

		int z = 0;
		while ( z <= fraction )
		{
			int x = 0;
			while ( x <= fraction )
			{
				vertices.add( new Vertex3f( (float)x / fraction, noise[x][z] / (fraction / smooth) + ( pok[x][z] ) / fraction, (float)z / fraction, this.color ) );
				x++;
			}
			z++;
		}
		int iz = 0;
		while ( iz <= fraction - 1 )
		{
			int ix = 0;
			while ( ix <= fraction - 1 )
			{
				int actual = ( iz * fraction ) + ix + iz;
				indices.add( actual );
				indices.add( actual + fraction + 1 );
				indices.add( actual + 1 );
				indices.add( actual + 1 );
				indices.add( actual + fraction + 1 );
				indices.add( actual + fraction + 2 );
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

