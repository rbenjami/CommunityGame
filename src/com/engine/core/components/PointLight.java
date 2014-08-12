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

import com.engine.core.Attenuation;
import com.engine.core.helpers.ColorHelper;
import com.engine.render.Shader;

import java.awt.*;

/**
 * Created on 29/04/14.
 */
public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256;

	private Attenuation attenuation;
	private float       range;

	public PointLight( Color color, float intensity, Attenuation attenuation )
	{
		super( color, intensity );
		this.attenuation = attenuation;

		float a = attenuation.getExponent();
		float b = attenuation.getLinear();
		float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * ColorHelper.max( color );

		this.range = (float) ( ( -b + Math.sqrt( b * b - 4 * a * c ) ) / ( 2 * a ) );

		setShader( new Shader( "point" ) );
	}

	public float getRange()
	{
		return range;
	}

	public void setRange( float range )
	{
		this.range = range;
	}

	public Attenuation getAttenuation()
	{
		return attenuation;
	}
}
