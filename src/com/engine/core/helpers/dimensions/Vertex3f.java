package com.engine.core.helpers.dimensions;

import java.awt.*;

/**
 * Created on 10/04/14.
 */
public class Vertex3f
{
	public static final int SIZE = 9;

	private float    x;
	private float    y;
	private float    z;
	private Color    color;
	private Vector3f normal;

	/**
	 * CONSTRUCTOR
	 */
	public Vertex3f( Vector3f pos )
	{
		this( pos, new Color( 0, 0, 0 ) );
	}

	public Vertex3f( float x, float y, float z )
	{
		this( new Vector3f( x, y, z ), new Color( 0, 0, 0 ) );
	}

	public Vertex3f( Vector3f pos, Color color )
	{
		this( pos, color, new Vector3f( 0, 0, 0 ) );
	}

	public Vertex3f( float x, float y, float z, Color color )
	{
		this( new Vector3f( x, y, z ), color, new Vector3f( 0, 0, 0 ) );
	}

	public Vertex3f( Vector3f pos, Color color, Vector3f normal )
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.color = color;
		this.normal = normal;
	}

	/**
	 * METHODES
	 */
	public Vector3f sub( Vertex3f v )
	{
		return ( new Vector3f( this.x - v.getX(), this.y - v.getY(), this.z - v.getZ() ) );
	}

	/**
	 * GETTER
	 */
	public float getX()
	{
		return x;
	}

	public float getY()
	{
		return y;
	}

	public float getZ()
	{
		return z;
	}

	public Color getColor()
	{
		return color;
	}

	public Vector3f getNormal()
	{
		return normal;
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

	public void setColor( Color color )
	{
		this.color = color;
	}

	public void setNormal( Vector3f normal )
	{
		this.normal = normal;
	}

	public void setPos( Vector3f pos )
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
	}

	public void setPos( float x, float y, float z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public boolean equals( Vector3f r )
	{
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

	public String toString()
	{
		return ( "Vertex3f( x: " + this.x + ", y: " + this.y + ", z: " + this.z + ")" );
	}
}
