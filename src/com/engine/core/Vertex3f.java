package com.engine.core;

import java.awt.*;

/**
 * Created on 10/04/14.
 */
public class Vertex3f
{
	private float    x;
	private float    y;
	private float    z;
	private Vector3f normal;
	private Color    color;
	public static final int SIZE = 6;

	/**
	 * CONSTRUCTOR
	 *
	 * @param x
	 * @param y
	 * @param z
	 * @param color
	 */
	public Vertex3f( float x, float y, float z, Color color )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = color;
	}

	public Vertex3f( float x, float y, float z )
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.color = new Color( 255, 255, 255 );
	}

	public float[] toFloat()
	{
		float array[] = { this.x, this.y, this.z };
		return array;
	}

	public Vector3f sub( Vertex3f vertice )
	{
		float _x = x - vertice.getX();
		float _y = y - vertice.getY();
		float _z = z - vertice.getZ();
		return new Vector3f( _x, _y, _z );
	}

	public void transform( Vector3f vec )
	{
		this.x += vec.getX();
		this.y += vec.getY();
		this.z += vec.getZ();
	}


	/**
	 * SETTER
	 */
	public void setZ( float z )
	{
		this.z = z;
	}

	public void setY( float y )
	{
		this.y = y;
	}

	public void setX( float x )
	{
		this.x = x;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	public void setNormal( Vector3f normal )
	{
		this.normal = normal;
	}

	/**
	 * GETTER
	 */
	public float getZ()
	{
		return ( z );
	}

	public float getX()
	{
		return ( x );
	}

	public float getY()
	{
		return ( z );
	}

	public Color getColor()
	{
		return ( color );
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public String toString()
	{
		return ( "Vertex3f( x: " + this.x + ", y: " + this.y + ", z: " + this.z + ")" );
	}
}
