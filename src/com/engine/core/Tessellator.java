package com.engine.core;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.NoiseHelper;
import com.engine.core.helpers.dimensions.Vector2f;
import com.engine.core.helpers.dimensions.Vertex3f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 10/04/14.
 */
public class Tessellator
{
	private int       fraction;
	private float[][] noise;
	private float     smooth;
	private Color     color;
	private Mesh      mesh;

	public Tessellator( int fraction, float smooth, Color color )
	{
		this.fraction = fraction;
		if ( fraction % 2 != 0 )
			this.fraction--;
		this.color = color;
		this.smooth = smooth;
		this.noise = NoiseHelper.generatePerlinNoise( NoiseHelper.generateWhiteNoise( fraction + 1, fraction + 1 ), 6 );
	}

	public void calculateTesselator()
	{
		ArrayList<Vertex3f> vertices = new ArrayList<Vertex3f>();
		ArrayList<Integer> indices = new ArrayList<Integer>();

		int z = -fraction / 2;
		int x;
		while ( z <= fraction / 2 )
		{
			x = -fraction / 2;
			while ( x <= fraction / 2 )
			{
				float rand1 = MathHelper.rand( -0.3f, 0.3f );
				float rand2 = MathHelper.rand( -0.3f, 0.3f );
				for ( int i = 0; i < 6; i++ )
					vertices.add( new Vertex3f( ( x + rand1 ) / fraction, noise[x + fraction / 2][z + fraction / 2] / ( fraction / smooth ), ( z + rand2 ) / fraction, this.color ) );
				x++;
			}
			z++;
		}
		z = 0;
		int test = ( fraction + 1 ) * 6;
		int actual = 0;
		int index = 0;
		while ( z < fraction )
		{
			x = 0;
			while ( x < fraction )
			{
				if ( actual != fraction * 6 + index )
				{
					indices.add( actual );
					indices.add( actual + test + 2 );
					indices.add( actual + 6 + 4 );
					indices.add( actual + 6 + 5 );
					indices.add( actual + test + 1 );
					indices.add( actual + test + 6 + 3 );
				}
				else
					index += fraction * 6 + 6;
				actual += 6;
				x++;
			}
			z++;
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

