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

package com.engine.physic.collider;

import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.physic.IntersectData;

public class SphereCollider extends Collider
{
	private Vector3f center;
	private float    radius;

	public SphereCollider( float radius )
	{
		this( new Vector3f( 0, 0, 0 ), radius );
	}

	public SphereCollider( Vector3f center, float radius )
	{
		super( ColliderType.SPHERE );
		this.center = center;
		this.radius = radius;
	}

	@Override
	public void setPos( Vector3f pos )
	{
		this.center = pos;
	}

	public IntersectData intersect( SphereCollider collider )
	{
		float radiusDistance = radius + collider.getRadius();
		Vector3f direction = ( collider.getCenter().sub( center ) );
		float centerDistance = direction.length();
		direction = direction.div( centerDistance );
		float distance = centerDistance - radiusDistance;

		return new IntersectData( distance < 0, direction.mul( distance ) );
	}

	public float getRadius()
	{
		return radius;
	}

	/**
	 * GETTER
	 */
	public Vector3f getCenter()
	{
		return center;
	}

	/**
	 * SETTER
	 */
	public void setCenter( Vector3f center )
	{
		this.center = center;
	}

	public void setRadius( float radius )
	{
		this.radius = radius;
	}

	public IntersectData intersect( Vector3f vector )
	{
		if ( center.sub( vector ).length() <= radius )
			return new IntersectData( true, null );
		return null;
	}
}
