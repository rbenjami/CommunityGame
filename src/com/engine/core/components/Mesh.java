package com.engine.core.components;

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh extends GameComponent
{
	private MeshResource resource;
	private Material     material;
	private Vector3f[]   vertices;
	private int[]        indices;

	public Mesh( MeshData data )
	{
		this.material = Material.ROCK;
		addVertices( data.getVertices(), data.getIndices() );
	}

	public Mesh( Vector3f[] vertices, int[] indices )
	{
		this.material = Material.ROCK;
		addVertices( vertices, indices );
	}

	private void addVertices( Vector3f[] vertices, int[] indices )
	{
		calcNormals( vertices, indices );

		this.vertices = vertices;
		this.indices = indices;
		resource = new MeshResource( indices.length );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( vertices ), GL_STATIC_DRAW );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer( indices ), GL_STATIC_DRAW );
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

	private void calcNormals( Vector3f[] vertices, int[] indices )
	{
		for ( int i = 0; i < indices.length; i += 3 )
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];

			Vector3f v1 = new Vector3f( vertices[i0], vertices[i1] );
			Vector3f v2 = new Vector3f( vertices[i0], vertices[i2] );

			Vector3f normal = v1.cross( v2 ).normalized();

			vertices[i0].setNormal( vertices[i0].getNormal().add( normal ) );
			vertices[i1].setNormal( vertices[i1].getNormal().add( normal ) );
			vertices[i2].setNormal( vertices[i2].getNormal().add( normal ) );
		}
//		for ( Vector3f vertex : vertices )
//			vertex.setNormal( vertex.getNormal().normalized() );
	}

	@Override
	public void render( Shader shader, RenderEngine renderingEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderingEngine );
		draw();
	}

	public void setMaterial( Material material )
	{
		this.material = material;
	}

	public Material getMaterial()
	{
		return material;
	}

	public Vector3f[] getVertices()
	{
		return vertices;
	}
}
