package com.engine.core.helpers.geometry;

import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 20/07/14.
 */
public class Triangle
{
	private Vector3f point1;
	private Vector3f point2;
	private Vector3f point3;
	private Vector3f normal;

	public Triangle( Vector3f point1, Vector3f point3, Vector3f point2 )
	{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;

		Vector3f v1 = new Vector3f( point1, point3 );
		Vector3f v2 = new Vector3f( point1, point2 );
		normal = v1.cross( v2 ).normalized();
	}

	/**
	 * GETTER
	 */
	public Vector3f getPoint( int i )
	{
		if ( i == 1 )
			return point1;
		if ( i == 2 )
			return point2;
		if ( i == 3 )
			return point3;
		return null;
	}

	public Vector3f getPoint1()
	{
		return point1;
	}

	/**
	 * SETTER
	 */
	public void setPoint1( Vector3f point1 )
	{
		this.point1 = point1;
	}

	public Vector3f getPoint2()
	{
		return point2;
	}

	public void setPoint2( Vector3f point2 )
	{
		this.point2 = point2;
	}

	public Vector3f getPoint3()
	{
		return point3;
	}

	public void setPoint3( Vector3f point3 )
	{
		this.point3 = point3;
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public void setNormal( Vector3f normal )
	{
		this.normal = normal;
	}
}
