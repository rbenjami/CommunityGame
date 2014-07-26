package com.engine.core.components;

import com.engine.core.GameObject;
import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.components.meshLoading.OBJModel;
import com.engine.core.helpers.AABB;
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
	private AABB axisAlignedBoundingBox;

	/**
	 * info
	 */
	private ArrayList<Triangle> triangles;

	public Mesh( String fileName )
	{
		loadMesh( fileName );
	}

	public void loadMesh( String fileName )
	{
		String[] splitArray = fileName.split( "\\." );
		String ext = splitArray[splitArray.length - 1];

		if ( !ext.equals( "obj" ) )
		{
			System.err.println( "Error: '" + ext + "' file format not supported for mesh data." );
			new Exception().printStackTrace();
			System.exit( 1 );
		}

		OBJModel model = new OBJModel( "./res/models/" + fileName );

		triangles = model.getTriangles();
		this.material = new Material();
		axisAlignedBoundingBox = new AABB( this );
		addVertices( triangles );
	}

	private void addVertices( ArrayList<Triangle> triangles )
	{
		axisAlignedBoundingBox = new AABB( this );

		resource = new MeshResource( triangles.size() * 3 );
		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( triangles ), GL_STATIC_DRAW );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createRegularFlippedBuffer( triangles.size() * 3 ), GL_STATIC_DRAW );
	}

	public Mesh( Triangle[] triangles )
	{
		this.triangles = new ArrayList<Triangle>( Arrays.asList( triangles ) );
		this.material = new Material();
		axisAlignedBoundingBox = new AABB( this );
		addVertices( this.triangles );
	}

	public Mesh( ArrayList<Triangle> triangles )
	{
		this.triangles = triangles;
		this.material = new Material();
		axisAlignedBoundingBox = new AABB( this );
		addVertices( triangles );
	}

	//TODO: updateTriangle
	public void updateTriangle( int offset )
	{
		Triangle triangle = triangles.get( offset );

		offset *= 3 * 9;
		System.out.println( "POK: " + offset );
//		resource = new MeshResource( triangles.size() * 3 );
//		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferSubData( GL_ARRAY_BUFFER, offset, Utils.createFlippedBuffer( triangle ) );
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
		for ( Triangle triangle : triangles )
			parent.addComponent( triangle );
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

	public Material getMaterial()
	{
		return material;
	}

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

	public ArrayList<Triangle> getTrianglesInBound( AABB aabb )
	{
		ArrayList<Triangle> tmpTriangles = new ArrayList<Triangle>();

		for ( Triangle triangle : triangles )
		{
			if ( triangle.havePointInBound( aabb ) )
				tmpTriangles.add( triangle );
		}
		return tmpTriangles;
	}
}

