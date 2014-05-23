package com.engine.core.components;

import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 21/05/2014.
 */
public class GeoSphere
{
	private float    radius;
	private int      maxLevels;
	private MeshData data;

	public GeoSphere( float radius, int maxLevels )
	{
		this.maxLevels = maxLevels;
		this.radius = radius;
		this.data = new MeshData();
		updateGeometry();
	}

	public void updateGeometry()
	{
		float y = 0.4472f * this.radius;
		float a = 0.8944f * this.radius;
		float b = 0.2764f * this.radius;
		float c = 0.7236f * this.radius;
		float d = 0.8507f * this.radius;
		float e = 0.5257f * this.radius;

		data.put( new Vector3f( 0, this.radius, 0 ) );
		data.put( new Vector3f( a, y, 0 ) );
		data.put( new Vector3f( b, y, -d ) );
		data.put( new Vector3f( 0, this.radius, 0 ) );
		data.put( new Vector3f( b, y, -d ) );
		data.put( new Vector3f( -c, y, -e ) );
		data.put( new Vector3f( 0, this.radius, 0 ) );
		data.put( new Vector3f( -c, y, -e ) );
		data.put( new Vector3f( -c, y, e ) );
		data.put( new Vector3f( 0, this.radius, 0 ) );
		data.put( new Vector3f( -c, y, e ) );
		data.put( new Vector3f( b, y, d ) );
		data.put( new Vector3f( 0, this.radius, 0 ) );
		data.put( new Vector3f( b, y, d ) );
		data.put( new Vector3f( a, y, 0 ) );

		data.put( new Vector3f( b, y, -d ) );
		data.put( new Vector3f( a, y, 0 ) );
		data.put( new Vector3f( c, -y, -e ) );
		data.put( new Vector3f( -c, y, -e ) );
		data.put( new Vector3f( b, y, -d ) );
		data.put( new Vector3f( -b, -y, -d ) );
		data.put( new Vector3f( -c, y, e ) );
		data.put( new Vector3f( -c, y, -e ) );
		data.put( new Vector3f( -a, -y, 0 ) );
		data.put( new Vector3f( b, y, d ) );
		data.put( new Vector3f( -c, y, e ) );
		data.put( new Vector3f( -b, -y, d ) );
		data.put( new Vector3f( a, y, 0 ) );
		data.put( new Vector3f( b, y, d ) );
		data.put( new Vector3f( c, -y, e ) );

		data.put( new Vector3f( -b, -y, -d ) );
		data.put( new Vector3f( b, y, -d ) );
		data.put( new Vector3f( c, -y, -e ) );
		data.put( new Vector3f( -a, -y, 0 ) );
		data.put( new Vector3f( -c, y, -e ) );
		data.put( new Vector3f( -b, -y, -d ) );
		data.put( new Vector3f( -b, -y, d ) );
		data.put( new Vector3f( -c, y, e ) );
		data.put( new Vector3f( -a, -y, 0 ) );
		data.put( new Vector3f( c, -y, e ) );
		data.put( new Vector3f( b, y, d ) );
		data.put( new Vector3f( -b, -y, d ) );
		data.put( new Vector3f( c, -y, -e ) );
		data.put( new Vector3f( a, y, 0 ) );
		data.put( new Vector3f( c, -y, e ) );


		data.put( new Vector3f( -b, -y, -d ) );
		data.put( new Vector3f( c, -y, -e ) );
		data.put( new Vector3f( 0, -this.radius, 0 ) );
		data.put( new Vector3f( -a, -y, 0 ) );
		data.put( new Vector3f( -b, -y, -d ) );
		data.put( new Vector3f( 0, -this.radius, 0 ) );
		data.put( new Vector3f( -b, -y, d ) );
		data.put( new Vector3f( -a, -y, 0 ) );
		data.put( new Vector3f( 0, -this.radius, 0 ) );
		data.put( new Vector3f( c, -y, e ) );
		data.put( new Vector3f( -b, -y, d ) );
		data.put( new Vector3f( 0, -this.radius, 0 ) );
		data.put( new Vector3f( c, -y, -e ) );
		data.put( new Vector3f( c, -y, e ) );
		data.put( new Vector3f( 0, -this.radius, 0 ) );

		for ( int i = 0; i < data.getVertices().length / 3; i++ )
		{
			data.put( 3 * i );
			data.put( 3 * i + 1 );
			data.put( 3 * i + 2 );
		}

		/* Subdivide each starting triangle (maxlevels - 1) times */
		for ( int level = 1; level < maxLevels; level++ )
		{
			MeshData newdata = new MeshData();

			for ( int i = 0; i < data.getVertices().length / 3; i++ )
			{
				Vector3f pt0 = data.getVertices()[i * 3];
				Vector3f pt1 = data.getVertices()[i * 3 + 1];
				Vector3f pt2 = data.getVertices()[i * 3 + 2];
				Vector3f va = createMidpoint( pt0, pt2 ).normalized().mul( this.radius );
				Vector3f vb = createMidpoint( pt0, pt1 ).normalized().mul( this.radius );
				Vector3f vc = createMidpoint( pt1, pt2 ).normalized().mul( this.radius );
				newdata.put( pt0 );
				newdata.put( vb );
				newdata.put( va );
				newdata.put( vb );
				newdata.put( pt1 );
				newdata.put( vc );
				newdata.put( va );
				newdata.put( vb );
				newdata.put( vc );
				newdata.put( va );
				newdata.put( vc );
				newdata.put( pt2 );
			}
			data = newdata;
		}
		for ( int j = 0; j < data.getVertices().length; j++ )
			data.put( j );
	}

	protected Vector3f createMidpoint( Vector3f a, Vector3f b )
	{
		return new Vector3f( ( a.getX() + b.getX() ) * 0.5f, ( a.getY() + b.getY() ) * 0.5f, ( a.getZ() + b.getZ() ) * 0.5f );
	}

	/**
	 * GETTER
	 */
	public Mesh getMesh()
	{
		return new Mesh( data );
	}
}
