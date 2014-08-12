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
	private Mesh     mesh;

	public AABB( Vector3f min, Vector3f max )
	{
		this.min = min;
		this.max = max;
	}

	public static AABB createMeshAABB( Mesh mesh )
	{
		Vector3f min = new Vector3f( 0, 0, 0 );
		Vector3f max = new Vector3f( 0, 0, 0 );
		for ( Triangle triangle : mesh.getTriangles() )
		{
			for ( int i = 0; i < 3; i++ )
			{
				Vector3f vector = triangle.getPoint( i + 1 );
				float posX = vector.getX();
				float posY = vector.getY();
				float posZ = vector.getZ();
				if ( posX > max.getX() )
					max.setX( posX );
				if ( posY > max.getY() )
					max.setY( posY );
				if ( posZ > max.getZ() )
					max.setZ( posZ );
				if ( posX < min.getX() )
					min.setX( posX );
				if ( posY < min.getY() )
					min.setY( posY );
				if ( posZ < min.getZ() )
					min.setZ( posZ );
			}
		}
		return new AABB( min, max );
	}

	public boolean intersectBounds( AABB aabb )
	{
		return aabb == null ||
				getMin().getX() > aabb.getMax().getX() ||
				getMax().getX() < aabb.getMin().getX() ||
				getMin().getY() > aabb.getMax().getY() ||
				getMax().getY() < aabb.getMin().getY() ||
				getMin().getZ() > aabb.getMax().getZ() ||
				getMax().getZ() < aabb.getMin().getZ();
	}

	/**
	 * GETTER
	 */
	public Vector3f getMin()
	{
		return min;
	}

	public Vector3f getMax()
	{
		return max;
	}

	@Override
	public String toString()
	{
		return "AABB min: " + min + ", max: " + max;
	}
}
