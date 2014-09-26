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

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.geometry.Triangle;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;
import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class DynamicMesh extends GameComponent
{
	private MeshResource resource;
	private Material     material;

	private ArrayList<Triangle> triangles;

	public DynamicMesh()
	{
		this( new ArrayList<Triangle>() );
	}

	public DynamicMesh( ArrayList<Triangle> triangles )
	{
		this.triangles = new ArrayList<Triangle>();
		this.material = Material.DEFAULT;
		initVertices( triangles );
	}

	private void initVertices( ArrayList<Triangle> triangles )
	{
		resource = new MeshResource();
		this.triangles.addAll( triangles );

		resource.setVbo( Utils.createFlippedBuffer( triangles ) );
		bufferData( GL_ARRAY_BUFFER, resource.getVboIndex(), resource.getVbo() );

		resource.setIbo( Utils.createRegularFlippedBuffer( triangles.size() * 3 ) );
		bufferData( GL_ELEMENT_ARRAY_BUFFER, resource.getIboIndex(), resource.getIbo() );
	}

	public void delTriangle( Triangle triangle ) throws Exception
	{
		delTriangle( triangle, true );
	}

	private void delTriangle( Triangle triangle, boolean bind ) throws Exception
	{
		int index = this.triangles.indexOf( triangle ) * 3 * 9;

		if ( index < 0 )
			throw new Exception( "Can't find index of triangle: " + triangle );
		this.triangles.remove( triangle );

		if ( bind )
			glBindBuffer( GL_ARRAY_BUFFER, resource.getVboIndex() );

		FloatBuffer tmpVbo = resource.getVbo().duplicate();
		tmpVbo.position( index + ( 3 * 9 ) );
		resource.getVbo().position( index );
		resource.getVbo().put( tmpVbo );
		glBufferSubData( GL_ARRAY_BUFFER, index * 4, resource.getVbo() );
		resource.getVbo().limit( resource.getVbo().limit() - 3 * 9 );
		resource.getVbo().flip();

		if ( bind )
			glBindBuffer( GL_ARRAY_BUFFER, 0 );
	}

	public void delTriangles( ArrayList<Triangle> triangles ) throws Exception
	{
		glBindBuffer( GL_ARRAY_BUFFER, resource.getVboIndex() );
		for ( Triangle triangle : triangles )
			delTriangle( triangle, false );
		glBindBuffer( GL_ARRAY_BUFFER, 0 );
	}

	public void addTriangles( ArrayList<Triangle> triangles )
	{
		int size = triangles.size();

		this.triangles.addAll( triangles );

		if ( resource.getVbo().capacity() > resource.getVbo().limit() + ( size * 3 * 9 ) )
		{
			FloatBuffer tmpVbo = Utils.createFlippedBuffer( triangles );
			resource.getVbo().limit( tmpVbo.capacity() );
			resource.getVbo().put( tmpVbo );
			resource.getVbo().flip();
			tmpVbo.flip();
			bufferSubData( GL_ARRAY_BUFFER, resource.getVboIndex(), resource.getVbo().limit() * 4, tmpVbo );
		}
		else
		{
			FloatBuffer tmpVbo = BufferUtils.createFloatBuffer( resource.getVbo().limit() + ( size * 3 * 9 ) );
			tmpVbo.put( resource.getVbo() );
			tmpVbo.put( Utils.createFlippedBuffer( triangles ) );
			resource.setVbo( (FloatBuffer) tmpVbo.flip() );
			bufferData( GL_ARRAY_BUFFER, resource.getVboIndex(), resource.getVbo() );

			IntBuffer tmpIbo = BufferUtils.createIntBuffer( resource.getIbo().capacity() + ( size * 3 ) );
			tmpIbo.put( resource.getIbo() );
			tmpIbo.put( Utils.createRegularFlippedBuffer( size * 3, resource.getIbo().capacity() ) );
			resource.setIbo( (IntBuffer) tmpIbo.flip() );
			bufferData( GL_ELEMENT_ARRAY_BUFFER, resource.getIboIndex(), resource.getIbo() );
		}
	}

	private void bufferSubData( int target, int buffer, long offset, FloatBuffer data )
	{
		glBindBuffer( target, buffer );
		glBufferSubData( target, offset, data );
		glBindBuffer( target, 0 );
	}

	private void bufferData( int target, int buffer, FloatBuffer data )
	{
		glBindBuffer( target, buffer );
		glBufferData( target, data, GL_DYNAMIC_DRAW );
		glBindBuffer( target, 0 );
	}

	private void bufferData( int target, int buffer, IntBuffer data )
	{
		glBindBuffer( target, buffer );
		glBufferData( target, data, GL_DYNAMIC_DRAW );
		glBindBuffer( target, 0 );
	}

	@Override
	public void render( Shader shader, RenderEngine renderingEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderingEngine );
		if ( resource != null )
			draw();
	}

	private void draw()
	{
		glEnableVertexAttribArray( 0 );
		glEnableVertexAttribArray( 1 );
		glEnableVertexAttribArray( 2 );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVboIndex() );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 9 * 4, 0 );
		glVertexAttribPointer( 1, 3, GL_FLOAT, true, 9 * 4, 12 );
		glVertexAttribPointer( 2, 3, GL_FLOAT, false, 9 * 4, 24 );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIboIndex() );
		glDrawElements( GL_TRIANGLES, resource.getVbo().limit(), GL_UNSIGNED_INT, 0 );

		glDisableVertexAttribArray( 0 );
		glDisableVertexAttribArray( 1 );
		glDisableVertexAttribArray( 2 );

		glBindBuffer( GL_ARRAY_BUFFER, 0 );
		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, 0 );
	}
}
