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
import com.engine.core.helpers.dimensions.Vector3f;

public class FreeMove extends GameComponent
{
	private float speed;
	private int   forwardKey;
	private int   backKey;
	private int   leftKey;
	private int   rightKey;
	private int   lShiftKey;
	private int   spaceKey;

	public FreeMove( float speed )
	{
		this( speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_LSHIFT, Input.KEY_SPACE );
	}

	public FreeMove( float speed, int forwardKey, int backKey, int leftKey, int rightKey, int lShiftKey, int spaceKey )
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.lShiftKey = lShiftKey;
		this.spaceKey = spaceKey;
	}

	@Override
	public void input( float delta )
	{
		float movAmt = speed * delta;

		if ( Input.getKey( forwardKey ) )
			move( getTransform().getRot().getForward(), movAmt );
		if ( Input.getKey( backKey ) )
			move( getTransform().getRot().getForward(), -movAmt );
		if ( Input.getKey( leftKey ) )
			move( getTransform().getRot().getLeft(), movAmt );
		if ( Input.getKey( rightKey ) )
			move( getTransform().getRot().getRight(), movAmt );
		if ( Input.getKey( lShiftKey ) )
			move( getTransform().getRot().getDown(), movAmt );
		if ( Input.getKey( spaceKey ) )
			move( getTransform().getRot().getUp(), movAmt );
	}

	private void move( Vector3f dir, float amt )
	{
		getTransform().setPos( getTransform().getPos().add( dir.mul( amt ) ) );
	}
}
