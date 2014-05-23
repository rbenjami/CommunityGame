package com.engine.core.components;

import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.TimeHelper;
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

		/* Subdivide each starting triangle (maxlevels - 1) times */
		for ( int level = 1; level < maxLevels; level++ )
		{
			MeshData newdata = new MeshData();
			Vector3f[] vertices = data.getVertices();

			for ( int i = 0; i < vertices.length / 3; i++ )
			{
				Vector3f pt0 = vertices[i * 3];
				Vector3f pt1 = vertices[i * 3 + 1];
				Vector3f pt2 = vertices[i * 3 + 2];
				Vector3f va = createMidpoint( pt0, pt2 ).mul( this.radius );
				Vector3f vb = createMidpoint( pt0, pt1 ).mul( this.radius );
				Vector3f vc = createMidpoint( pt1, pt2 ).mul( this.radius );
				newdata.put( new Vector3f( pt0 ) );
				newdata.put( new Vector3f( vb ) );
				newdata.put( new Vector3f( va ) );
				newdata.put( new Vector3f( vb ) );
				newdata.put( new Vector3f( pt1 ) );
				newdata.put( new Vector3f( vc ) );
				newdata.put( new Vector3f( va ) );
				newdata.put( new Vector3f( vb ) );
				newdata.put( new Vector3f( vc ) );
				newdata.put( new Vector3f( va ) );
				newdata.put( new Vector3f( vc ) );
				newdata.put( new Vector3f( pt2 ) );
			}
			data = newdata;
		}
		int len = data.getVertices().length;
		for ( int i = 0; i < len; i++ )
			data.put( i );
	}

	protected Vector3f createMidpoint( Vector3f a, Vector3f b )
	{
		return new Vector3f( a.add( b ).div( 2 ) ).normalized();
	}

	/**
	 * GETTER
	 */
	public Mesh getMesh()
	{
		return new Mesh( data );
	}
}
