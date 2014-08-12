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

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created on 14/04/14.
 */
public class MeshResource
{
	private int vbo;
	private int ibo;
	private int size;
	private int refCount;

	public MeshResource( int size )
	{
		vbo = glGenBuffers();
		ibo = glGenBuffers();
		this.size = size;
		this.refCount = 1;
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers( vbo );
		glDeleteBuffers( ibo );
	}

	public void addReference()
	{
		refCount++;
	}

	public boolean removeReference()
	{
		refCount--;
		return refCount == 0;
	}

	public int getVbo()
	{
		return vbo;
	}

	public int getIbo()
	{
		return ibo;
	}

	public int getSize()
	{
		return size;
	}
}
