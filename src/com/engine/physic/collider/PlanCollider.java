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

public class PlanCollider extends Collider
{
	private Vector3f normal;
	private float    distance;

	public PlanCollider( Vector3f normal, float distance )
	{
		super( ColliderType.PLAN );
		this.normal = normal;
		this.distance = distance;
	}

	@Override
	public void setPos( Vector3f pos )
	{

	}

	/**
	 * GETTER
	 */
	public Vector3f getNormal()
	{
		return normal;
	}

	/**
	 * SETTER
	 */
	public void setNormal( Vector3f normal )
	{
		this.normal = normal;
	}

	public float getDistance()
	{
		return distance;
	}

	public void setDistance( float distance )
	{
		this.distance = distance;
	}
}
