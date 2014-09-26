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

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;

/**
 * Created on 14/04/14.
 */
public class MeshResource
{
	private int         vboIndex;
	private int         iboIndex;
	private FloatBuffer vbo;
	private IntBuffer   ibo;

	public MeshResource()
	{
		vboIndex = glGenBuffers();
		iboIndex = glGenBuffers();
	}

	@Override
	protected void finalize()
	{
		glDeleteBuffers( vboIndex );
		glDeleteBuffers( iboIndex );
	}

	public int getVboIndex()
	{
		return vboIndex;
	}

	public int getIboIndex()
	{
		return iboIndex;
	}

	public FloatBuffer getVbo()
	{
		return vbo;
	}

	public void setVbo( FloatBuffer vbo )
	{
		this.vbo = vbo;
	}

	public IntBuffer getIbo()
	{
		return ibo;
	}

	public void setIbo( IntBuffer ibo )
	{
		this.ibo = ibo;
	}
}
