package com.engine.core.helpers;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

/**
 * Created on 17/07/14.
 */
public class AABB
{
	private Vector3f min;
	private Vector3f max;
	private Mesh mesh;

	public AABB( Vector3f min, Vector3f max )
	{
		this.min = min;
		this.max = max;
		mesh = null;
	}

	public AABB( Mesh mesh )
	{
		this.mesh = mesh;
		this.min = new Vector3f( 0, 0, 0 );
		this.max = new Vector3f( 0, 0, 0 );
		for ( Triangle triangle : mesh.getTriangles() )
		{
			for ( int i = 0; i < 3; i++ )
			{
				Vector3f vector = triangle.getPoint( i + 1 );
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
	}

	public boolean intersectBounds( AABB aabb )
	{
		if ( aabb == null )
			return false;
		if ( getMin().getX() > aabb.getMax().getX() )
			return false;
		if ( getMax().getX() < aabb.getMin().getX() )
			return false;
		if ( getMin().getY() > aabb.getMax().getY() )
			return false;
		if ( getMax().getY() < aabb.getMin().getY() )
			return false;
		if ( getMin().getZ() > aabb.getMax().getZ() )
			return false;
		if ( getMax().getZ() < aabb.getMin().getZ() )
			return false;
		return true;
	}

	/**
	 * GETTER
	 */
	public Vector3f getMin()
	{
		if ( mesh != null )
		{
			Vector3f mi = min.mul( mesh.getTransform().getScale() );
			mi = mi.add( mesh.getTransform().getPos() );
			return mi;
		}
		return min;
	}

	public Vector3f getMax()
	{
		if ( mesh != null )
		{
			Vector3f ma = max.mul( mesh.getTransform().getScale() );
			ma = ma.add( mesh.getTransform().getPos() );
			return ma;
		}
		return max;
	}

	@Override
	public String toString()
	{
		return "AABB min: X: " + getMin().getX() + " Y: " + getMin().getY() + " Z: " + getMin().getZ() + " ,max: X: " + getMax().getX() + " Y: " + getMax().getY() + " Z: " + getMax().getZ();
	}
}
