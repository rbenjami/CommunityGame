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

package com.engine.core;

import com.engine.CoreEngine;
import com.engine.physic.PhysicsEngine;
import com.engine.render.RenderEngine;

/**
 * Created on 27/04/14.
 */
public abstract class Game
{
	private GameObject root;

	public void init() {}

	public void input( float delta )
	{
		getRootObject().inputAll( delta );
	}

	public GameObject getRootObject()
	{
		if ( root == null )
			root = new GameObject();

		return root;
	}

	public void update( float delta )
	{
		getRootObject().updateAll( delta );
	}

	public void physic( PhysicsEngine physicsEngine, float delta )
	{
		physicsEngine.update( getRootObject().getAllPhysicObjects(), delta );
	}

	public void render( RenderEngine renderingEngine )
	{
		renderingEngine.render( getRootObject() );
	}

	public void addObject( GameObject object )
	{
		getRootObject().addChild( object );
	}

	public void setEngine( CoreEngine engine )
	{
		getRootObject().setEngine( engine );
	}
}