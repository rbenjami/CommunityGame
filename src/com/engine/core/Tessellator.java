package com.engine.core;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.NoiseHelper;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 10/04/14.
 */
public class Tessellator extends GameObject
{
	private int       fraction;
	private float[][] noise;
	private float     smooth;
	private Color     color;

	public Tessellator( int fraction, float smooth, Color color, long seed )
	{
		this.fraction = fraction;
		if ( fraction % 2 != 0 )
			this.fraction--;
		this.color = color;
		this.smooth = smooth;
		this.noise = NoiseHelper.generatePerlinNoise( NoiseHelper.generateWhiteNoise( fraction + 1, fraction + 1, seed ), 6 );
		Mesh model = calculateTesselator();
		setModel( model );
		addComponent( model );
	}

	@Deprecated
	private Mesh calculateTesselator()
	{
		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
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
					vertices.add( new Vector3f( ( x + rand1 ) / fraction, noise[x + fraction / 2][z + fraction / 2] / ( fraction / smooth ), ( z + rand2 ) / fraction, this.color ) );
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
		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
		for ( int i = 0; i < indices.size(); i += 3 )
			triangles.add( new Triangle( vertices.get( indices.get( i ) ), vertices.get( indices.get( i + 1 ) ), vertices.get( indices.get( i + 2 ) ) ) );
		return new Mesh( triangles );
	}
}

