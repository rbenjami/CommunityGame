/*
 * Copyright (C) 2014 Repingon Benjamin
 * This file is part of CommunityGame.
 * CommunityGame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * CommunityGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
 */

package com.engine.core.helpers.dimensions;

import java.awt.*;

/**
 * Created on 10/04/14.
 */
@Deprecated
public class Vertex3f
{
	public static final int SIZE = 9;

	private float    x;
	private float    y;
	private float    z;
	private Color    color;
	private Vertex3f normal;

	/**
	 * CONSTRUCTOR
	 */
	public Vertex3f( Vertex3f pos )
	{
		this( pos, new Color( 255, 255, 255 ) );
	}

	public Vertex3f( Vertex3f pos, Color color )
	{
		this( pos, color, new Vertex3f( 0, 0, 0 ) );
	}

	public Vertex3f( Vertex3f pos, Color color, Vertex3f normal )
	{
		this.x = pos.getX();
		this.y = pos.getY();
		this.z = pos.getZ();
		this.color = color;
		this.normal = normal;
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

	public void setZ( float z )
	{
		this.z = z;
	}

	public void setY( float y )
	{
		this.y = y;
	}

	/**
	 * SETTER
	 */
	public void setX( float x )
	{
		this.x = x;
	}

	public Vertex3f( float x, float y, float z )
	{
		this( new Vertex3f( x, y, z ), new Color( 255, 255, 255 ) );
	}

	public Vertex3f( float x, float y, float z, Color color )
	{
		this( new Vertex3f( x, y, z ), color, new Vertex3f( 0, 0, 0 ) );
	}

	public Vertex3f normalized()
	{
		float lenght = this.lenght();

		return ( new Vertex3f( x / lenght, y / lenght, z / lenght ) );
	}

	/**
	 * METHODES
	 */
	public float lenght()
	{
		return ( (float) Math.sqrt( x * x + y * y + z * z ) );
	}

	public Vertex3f add( Vertex3f v )
	{
		return ( new Vertex3f( this.x + v.getX(), this.y + v.getY(), this.z + v.getZ() ) );
	}

	public Vertex3f add( float v )
	{
		return ( new Vertex3f( this.x + v, this.y + v, this.z + v ) );
	}

	public Vertex3f sub( Vertex3f v )
	{
		return ( new Vertex3f( this.x - v.getX(), this.y - v.getY(), this.z - v.getZ() ) );
	}

	public Vertex3f sub( float v )
	{
		return ( new Vertex3f( this.x - v, this.y - v, this.z - v ) );
	}

	public Vertex3f mul( Vertex3f v )
	{
		return ( new Vertex3f( this.x * v.getX(), this.y * v.getY(), this.z * v.getZ() ) );
	}

	public Vertex3f mul( float v )
	{
		return ( new Vertex3f( this.x * v, this.y * v, this.z * v ) );
	}

	public Vertex3f div( Vertex3f v )
	{
		return ( new Vertex3f( this.x / v.getX(), this.y / v.getY(), this.z / v.getZ() ) );
	}

	public Vertex3f div( float v )
	{
		return ( new Vertex3f( this.x / v, this.y / v, this.z / v ) );
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	public Vertex3f getNormal()
	{
		return normal;
	}

	public void setNormal( Vertex3f normal )
	{
		this.normal = normal;
	}

	public void setPos( Vertex3f pos )
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

	public boolean equals( Vertex3f r )
	{
		return x == r.getX() && y == r.getY() && z == r.getZ();
	}

	public String toString()
	{
		return ( "Vertex3f( x: " + this.x + ", y: " + this.y + ", z: " + this.z + ")" );
	}
}
