package com.engine.core.helpers.dimensions;

/**
 * Created on 10/04/14.
 */
public class Vector3f
{
	private float x;
	private float y;
	private float z;

	/**
	 * CONSTRUCTOR
	 */
	public Vector3f( float x, float y, float z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3f( Vertex3f direction )
	{
		this.x = direction.getX();
		this.y = direction.getY();
		this.z = direction.getZ();
	}

	public Vector3f( Vertex3f origin, Vertex3f destination )
	{
		this.x = destination.getX() - origin.getX();
		this.y = destination.getY() - origin.getY();
		this.z = destination.getZ() - origin.getZ();
	}


	/**
	 * METHODES
	 */
	public Vector3f rotate( Quaternion rotation )
	{
		Quaternion conjugate = rotation.conjugate();

		Quaternion w = rotation.mul( this ).mul( conjugate );

		return new Vector3f( w.getX(), w.getY(), w.getZ() );
	}

	public float lenght()
	{
		return ( (float) Math.sqrt( x * x + y * y + z * z ) );
	}

	public Vector3f normalized()
	{
		float lenght = this.lenght();

		return ( new Vector3f( x / lenght, y / lenght, z / lenght ) );
	}

	public float dot( Vector3f v )
	{
		return x * v.getX() + y * v.getY() + z * v.getZ();
	}

	public Vector3f cross( Vector3f v )
	{
		float x_ = y * v.getZ() - z * v.getY();
		float y_ = z * v.getX() - x * v.getZ();
		float z_ = x * v.getY() - y * v.getX();

		return ( new Vector3f( x_, y_, z_ ) );
	}

	public Vector3f add( Vector3f v )
	{
		return ( new Vector3f( this.x + v.getX(), this.y + v.getY(), this.z + v.getZ() ) );
	}

	public Vector3f add( float v )
	{
		return ( new Vector3f( this.x + v, this.y + v, this.z + v ) );
	}

	public Vector3f sub( Vector3f v )
	{
		return ( new Vector3f( this.x - v.getX(), this.y - v.getY(), this.z - v.getZ() ) );
	}

	public Vector3f sub( float v )
	{
		return ( new Vector3f( this.x - v, this.y - v, this.z - v ) );
	}

	public Vector3f mul( Vector3f v )
	{
		return ( new Vector3f( this.x * v.getX(), this.y * v.getY(), this.z * v.getZ() ) );
	}

	public Vector3f mul( float v )
	{
		return ( new Vector3f( this.x * v, this.y * v, this.z * v ) );
	}

	public Vector3f div( Vector3f v )
	{
		return ( new Vector3f( this.x / v.getX(), this.y / v.getY(), this.z / v.getZ() ) );
	}

	public Vector3f div( float v )
	{
		return ( new Vector3f( this.x / v, this.y / v, this.z / v ) );
	}


	/**
	 * SETTER
	 */
	public void setX( float x )
	{
		this.x = x;
	}

	public void setY( float y )
	{
		this.y = y;
	}

	public void setZ( float z )
	{
		this.z = z;
	}

	public Vector3f set( float x, float y, float z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		return this;
	}

	public Vector3f set( Vector3f r )
	{
		set( r.getX(), r.getY(), r.getZ() );
		return this;
	}


	/**
	 * GETTER
	 */
	public float getX()
	{
		return ( x );
	}

	public float getY()
	{
		return ( y );
	}

	public float getZ()
	{
		return ( z );
	}

	public boolean equals( Vector3f r )
	{
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

	public String toString()
	{
		return ( "Vector3f( x: " + this.x + ", y: " + this.y + ", z: " + this.z + ")" );
	}
}

