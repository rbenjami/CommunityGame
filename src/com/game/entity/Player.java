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

import com.engine.core.components.Camera;
import com.engine.core.components.Mesh;
import com.engine.render.Window;

/**
 * Created on 17/07/14.
 */
public class Player extends Entity
{
	public Player( Mesh model )
	{
		super( model );
		this.addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) );
	}

	@Override
	public void update( float delta )
	{

	}
}
