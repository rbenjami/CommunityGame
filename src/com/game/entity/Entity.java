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

import com.engine.core.GameObject;
import com.engine.core.Material;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Quaternion;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 17/07/14.
 */
public class Entity extends GameObject
{
	public Entity( Mesh model )
	{
		this.setModel( model );
		this.addComponent( model );
		Material material = new Material();
		material.addFloat( "gravity", 1 );
		material.addFloat( "mass", 1 );
		material.addFloat( "dragCoefficient", 0.47f );
		material.addFloat( "surface", 0.5f );
		setMaterial( material );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		if ( !getVelocity().equals( Vector3f.NULL ) )
		{
			getTransform().translate( getVelocity() );
			getTransform().lookAt( getTransform().getPos().add( getVelocity() ), new Vector3f( 0, 1, 0 ).rotate( new Quaternion( getVelocity().normalized(), getRollAngle() ) ) );
		}
	}

	/**
	 * GETTER
	 */

	/**
	 * SETTER
	 */
}
