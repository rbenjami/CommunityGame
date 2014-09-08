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

package com.engine.physic;

import com.engine.core.GameObject;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.physic.collider.Collider;

import java.util.ArrayList;

public class PhysicObject extends GameObject
{
	private ArrayList<Collider> colliders;
	private Vector3f            velocity;
	private PhysicalProperties  physicalProperties;

	public PhysicObject()
	{
		this.velocity = new Vector3f( 0, 0, 0 );
		this.colliders = new ArrayList<Collider>();
	}

	public void addCollider( Collider collider )
	{
		colliders.add( collider );
	}

	/**
	 * GETTER
	 */
	public Vector3f getVelocity()
	{
		return velocity;
	}

	/**
	 * SETTER
	 */
	public void setVelocity( Vector3f velocity )
	{
		this.velocity = velocity;
	}

	public ArrayList<Collider> getColliders()
	{
		for ( Collider collider : colliders )
			collider.setPos( getTransform().getPos() );
		return colliders;
	}

	public PhysicalProperties getPhysicalProperties()
	{
		return physicalProperties;
	}

	public void setPhysicalProperties( PhysicalProperties physicalProperties )
	{
		this.physicalProperties = physicalProperties;
	}
}
