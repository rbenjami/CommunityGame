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

import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.Shader;

import java.awt.*;

/**
 * Created on 30/04/14.
 */
public class DirectionalLight extends BaseLight
{
	public DirectionalLight( Color color, float intensity )
	{
		super( color, intensity );

		setShader( new Shader( "directional" ) );
	}

	public Vector3f getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
