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

package com.engine.core.helpers;

/**
 * Created on 30/04/14.
 */
public class MathHelper
{
	public static final float EPSILON = 0.00000001f;

	public static int rand( int lower, int higher )
	{
		return (int) ( Math.random() * ( ( higher + 1 ) - lower ) ) + lower;
	}

	public static float rand( float lower, float higher )
	{
		return (float) ( Math.random() * ( higher - lower ) ) + lower;
	}
}
