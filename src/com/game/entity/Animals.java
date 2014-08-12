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

import com.engine.core.Material;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 18/07/14.
 */
public class Animals extends Entity
{
	public Animals( Mesh model )
	{
		super( model );
		Material material = new Material();
		material.addFloat( "gravity", 0 );
		material.addFloat( "mass", 0 );
		material.addFloat( "dragCoefficient", 0.47f );
		material.addFloat( "surface", 1f );
		setMaterial( material );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		setVelocity( new Vector3f( 0, 0, 0 ) );
	}
}
