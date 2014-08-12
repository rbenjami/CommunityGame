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

import com.engine.CoreEngine;
import com.engine.core.GameObject;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

/**
 * Created on 27/04/14.
 */
public abstract class GameComponent
{
	private GameObject parent;

	public void input( float delta ) {}

	public void update( float delta ) {}

	public void render( Shader shader, RenderEngine renderEngine ) {}

	public void setParent( GameObject parent )
	{
		this.parent = parent;
		onAddParent( this.parent );
	}

	public void onAddParent( GameObject parent ) {}

	public Transform getTransform()
	{
		if ( parent != null )
			return parent.getTransform();
		return new Transform();
	}

	public void addToEngine( CoreEngine engine ) {}
}

