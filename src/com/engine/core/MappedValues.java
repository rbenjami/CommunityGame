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

import com.engine.core.helpers.dimensions.Vector3f;

import java.util.HashMap;

/**
 * Created on 29/04/14.
 */
public abstract class MappedValues
{
	private HashMap<String, Vector3f> vector3fHashMap;
	private HashMap<String, Float>    floatHashMap;

	public MappedValues()
	{
		vector3fHashMap = new HashMap<String, Vector3f>();
		floatHashMap = new HashMap<String, Float>();
	}

	public MappedValues addVector3f( String name, Vector3f vector3f )
	{
		vector3fHashMap.put( name.toLowerCase(), vector3f );
		return this;
	}

	public MappedValues addFloat( String name, float floatValue )
	{
		floatHashMap.put( name.toLowerCase(), floatValue );
		return this;
	}

	public Vector3f getVector3f( String name )
	{
		Vector3f result = vector3fHashMap.get( name.toLowerCase() );
		if ( result != null )
			return result;

		return new Vector3f( 0, 0, 0 );
	}

	public float getFloat( String name )
	{
		Float result = floatHashMap.get( name.toLowerCase() );
		if ( result != null )
			return result;

		return 0;
	}
}
