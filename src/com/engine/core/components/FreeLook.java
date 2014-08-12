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

package com.engine.core.components;

import com.engine.core.Input;
import com.engine.core.helpers.dimensions.Vector2f;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.Window;

public class FreeLook extends GameComponent
{
	private static final Vector3f yAxis = new Vector3f( 0, 1, 0 );

	private boolean mouseLocked = false;
	private float sensitivity;
	private int   unlockMouseKey;

	public FreeLook( float sensitivity )
	{
		this( sensitivity, Input.KEY_ESCAPE );
	}

	public FreeLook( float sensitivity, int unlockMouseKey )
	{
		this.sensitivity = sensitivity;
		this.unlockMouseKey = unlockMouseKey;
	}

	@Override
	public void input( float delta )
	{
		Vector2f centerPosition = new Vector2f( Window.getWidth() / 2, Window.getHeight() / 2 );

		if ( Input.getKey( unlockMouseKey ) )
		{
			Input.setCursor( true );
			mouseLocked = false;
		}
		if ( Input.getMouseDown( 0 ) )
		{
			Input.setMousePosition( centerPosition );
			Input.setCursor( false );
			mouseLocked = true;
		}

		if ( mouseLocked )
		{
			Vector2f deltaPos = Input.getMousePosition().sub( centerPosition );

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if ( rotY )
				getTransform().rotate( yAxis, (float) Math.toRadians( deltaPos.getX() * sensitivity ) );
			if ( rotX )
				getTransform().rotate( getTransform().getRot().getRight(), (float) Math.toRadians( -deltaPos.getY() * sensitivity ) );

			if ( rotY || rotX )
				Input.setMousePosition( centerPosition );
		}
	}
}
