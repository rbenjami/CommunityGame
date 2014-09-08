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

package com.game.entity;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.physic.PhysicObject;
import com.engine.physic.PhysicalProperties;
import com.engine.physic.collider.SphereCollider;

/**
 * Created on 17/07/14.
 */
public class Entity extends PhysicObject
{
	public Entity( Mesh model )
	{
		this.addComponent( model );
		PhysicalProperties physicalProperties = new PhysicalProperties();
		physicalProperties.addProperty( PhysicalProperties.GRAVITY, 0.5f );
		physicalProperties.addProperty( PhysicalProperties.MASS, 0.1f );
		physicalProperties.addProperty( PhysicalProperties.DRAG_COEFFICIENT, 0.47f );
		physicalProperties.addProperty( PhysicalProperties.RESTITUTION_COEFFICIENT, 0.95f );
		physicalProperties.addProperty( PhysicalProperties.SURFACE, 0.5f );
		setPhysicalProperties( physicalProperties );
		addCollider( new SphereCollider( 1 ) );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		if ( !getVelocity().equals( Vector3f.NULL ) )
		{
			getTransform().translate( getVelocity() );
			//getTransform().lookAt( getTransform().getPos().add( getVelocity() ), new Vector3f( 0, 1, 0 ).rotate( new Quaternion( getVelocity().normalized(), getRollAngle() ) ) );
		}
	}

	/**
	 * GETTER
	 */

	/**
	 * SETTER
	 */
}
