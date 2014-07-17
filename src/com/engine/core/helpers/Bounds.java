package com.engine.core.helpers;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 17/07/14.
 */
public class Bounds
{
	private Vector3f min;
	private Vector3f max;

	public Bounds( Vector3f min, Vector3f max )
	{
		this.min = min;
		this.max = max;
	}

	public Bounds( Mesh mesh )
	{
		this.min = new Vector3f( 0, 0, 0 );
		this.max = new Vector3f( 0, 0, 0 );
		for ( Vector3f vector : mesh.getVertex() )
		{
			float posX = vector.getX();
			float posY = vector.getY();
			float posZ = vector.getZ();
			if ( posX > this.max.getX() )
				this.max.setX( posX );
			if ( posY > this.max.getY() )
				this.max.setY( posY );
			if ( posZ > this.max.getZ() )
				this.max.setZ( posZ );
			if ( posX < this.min.getX() )
				this.min.setX( posX );
			if ( posY < this.min.getY() )
				this.min.setY( posY );
			if ( posZ < this.min.getZ() )
				this.min.setZ( posZ );
		}
	}

	public boolean intersect( Bounds bounds )
	{
		if ( this.min.getX() > bounds.getMax().getX() )
			return false;
		if ( this.max.getX() < bounds.getMin().getX() )
			return false;
		if ( this.min.getY() > bounds.getMax().getY() )
			return false;
		if ( this.max.getY() < bounds.getMin().getY() )
			return false;
		if ( this.min.getZ() > bounds.getMax().getZ() )
			return false;
		if ( this.max.getZ() < bounds.getMin().getZ() )
			return false;
		return true;
	}

	public Vector3f getMax()
	{
		return max;
	}

	/**
	 * GETTER
	 */
	public Vector3f getMin()
	{
		return min;
	}

	public Bounds addPos( Vector3f pos )
	{
		return new Bounds( this.min.add( pos ), this.max.add( pos ) );
	}
}
