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

import com.engine.core.GameObject;
import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.AABB;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import java.util.ArrayList;
import java.util.Arrays;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh extends GameComponent
{
	private MeshResource resource;
	private Material     material;
	private AABB         axisAlignedBoundingBox;

	/**
	 * Info
	 */
	private ArrayList<Triangle> triangles;

	public Mesh( String fileName )
	{
		this( (ArrayList<Triangle>) MeshLoader.loadOBJ( "./res/models/" + fileName ) );
	}

	public Mesh( ArrayList<Triangle> triangles )
	{
		this.triangles = triangles;
		this.material = Material.DEFAULT;
		axisAlignedBoundingBox = AABB.createMeshAABB( this );
		addVertices( triangles );
	}

	private void addVertices( ArrayList<Triangle> triangles )
	{
		resource = new MeshResource( triangles.size() * 3 );
		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( triangles ), GL_STATIC_DRAW );
		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createRegularFlippedBuffer( triangles.size() * 3 ), GL_STATIC_DRAW );
	}

	@Deprecated
	public Mesh( Vector3f[] vertices, int[] indices )
	{
//		this.triangles = triangles;
		this.material = Material.DEFAULT;
//		axisAlignedBoundingBox = AABB.createMeshAABB( this );
		addVertices( vertices, indices );
	}

	@Deprecated
	private void addVertices( Vector3f[] vertices, int[] indices )
	{
		resource = new MeshResource( indices.length );
		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( vertices, indices ), GL_STATIC_DRAW );
		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer( indices ), GL_STATIC_DRAW );
	}

	public Mesh( Triangle[] triangles )
	{
		this( new ArrayList<Triangle>( Arrays.asList( triangles ) ) );
	}

	@Override
	public void render( Shader shader, RenderEngine renderingEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderingEngine );
		if ( resource != null )
			draw();
	}

	@Override
	public void onAddParent( GameObject parent )
	{
		super.onAddParent( parent );
		if ( triangles != null )
		{
			for ( Triangle triangle : triangles )
				parent.addComponent( triangle );
		}
	}

	public void draw()
	{
		glEnableVertexAttribArray( 0 );
		glEnableVertexAttribArray( 1 );
		glEnableVertexAttribArray( 2 );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glVertexAttribPointer( 0, 3, GL_FLOAT, false, 9 * 4, 0 );
		glVertexAttribPointer( 1, 3, GL_FLOAT, true, 9 * 4, 12 );
		glVertexAttribPointer( 2, 3, GL_FLOAT, false, 9 * 4, 24 );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glDrawElements( GL_TRIANGLES, resource.getSize(), GL_UNSIGNED_INT, 0 );

		glDisableVertexAttribArray( 0 );
		glDisableVertexAttribArray( 1 );
		glDisableVertexAttribArray( 2 );
	}


	/**
	 * GETTER
	 */
	public Material getMaterial()
	{
		return material;
	}

	/**
	 * SETTER
	 */
	public void setMaterial( Material material )
	{
		this.material = material;
	}

	public AABB getAxisAlignedBoundingBox()
	{
		return axisAlignedBoundingBox;
	}

	public ArrayList<Triangle> getTriangles()
	{
		return triangles;
	}
}

