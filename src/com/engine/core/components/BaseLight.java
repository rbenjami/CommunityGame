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
import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.render.Shader;

import java.awt.*;

/**
 * Created on 29/04/14.
 */
public class BaseLight extends GameComponent
{
	private Color  color;
	private float  intensity;
	private Shader shader;

	public BaseLight( Color color, float intensity )
	{
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine( CoreEngine engine )
	{
		engine.getRenderEngine().addLight( this );
	}

	public Matrix4f getDepthMVP()
	{
		Matrix4f depthProjectionMatrix = new Matrix4f().initOrthographic( -10, 10, -10, 10, -10, 20 );
		Matrix4f worldMatrix = getTransform().getTransformation();

		return depthProjectionMatrix.mul( worldMatrix );
	}

	public Shader getShader()
	{
		return shader;
	}

	public void setShader( Shader shader )
	{
		this.shader = shader;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity( float intensity )
	{
		this.intensity = intensity;
	}
}