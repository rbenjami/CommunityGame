package com.engine.core.components;

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.components.meshLoading.IndexedModel;
import com.engine.core.components.meshLoading.OBJModel;
import com.engine.core.helpers.Bounds;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Mesh extends GameComponent
{
	private MeshResource resource;
	private Material     material;
	private int[]        indices;
	private Vector3f[] vertex;
	private Bounds     boundingBox;

	public Mesh( String fileName )
	{
		loadMesh( fileName );
		this.material = new Material();
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

		OBJModel test = new OBJModel( "./res/models/" + fileName );
		IndexedModel model = test.toIndexedModel();
		model.calcNormals();

		ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();

		for ( int i = 0; i < model.getPositions().size(); i++ )
		{
			vertices.add( new Vector3f( model.getPositions().get( i ) ) );
		}

		Vector3f[] vertexData = new Vector3f[vertices.size()];
		vertices.toArray( vertexData );

		Integer[] indexData = new Integer[model.getIndices().size()];
		model.getIndices().toArray( indexData );
		int[] indices = Utils.toIntArray( indexData );

		this.indices = indices;
		this.vertex = vertexData;
		addVertices( vertexData, indices, true );
	}

	private void addVertices( Vector3f[] vertex, int[] indices, boolean isModel )
	{
		this.indices = indices;
		this.vertex = vertex;
		boundingBox = new Bounds( this );
		calcNormals( vertex, indices );

		resource = new MeshResource( this.indices.length, isModel );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( this.vertex ), GL_STATIC_DRAW );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer( this.indices ), GL_STATIC_DRAW );
	}

	private void calcNormals( Vector3f[] vertex, int[] indices )
	{
		ArrayList<Vector3f> newVertex = new ArrayList<Vector3f>();
		ArrayList<Integer> newIndices = new ArrayList<Integer>();

		for ( int i = 0; i < indices.length; i += 3 )
		{
			int i0 = indices[i];
			int i1 = indices[i + 1];
			int i2 = indices[i + 2];

			Vector3f v1 = new Vector3f( vertex[i0], vertex[i1] );
			Vector3f v2 = new Vector3f( vertex[i0], vertex[i2] );

			Vector3f normal = v1.cross( v2 ).normalized();

			vertex[i0].setNormal( vertex[i0].getNormal().add( normal ) );
			vertex[i1].setNormal( vertex[i1].getNormal().add( normal ) );
			vertex[i2].setNormal( vertex[i2].getNormal().add( normal ) );

			newVertex.add( new Vector3f( vertex[i0] ) );
			newVertex.add( new Vector3f( vertex[i1] ) );
			newVertex.add( new Vector3f( vertex[i2] ) );
			newIndices.add( i );
			newIndices.add( i + 1 );
			newIndices.add( i + 2 );
		}
		Vector3f[] vertexData = new Vector3f[newVertex.size()];
		newVertex.toArray( vertexData );
		this.vertex = vertexData;

		Integer[] indexData = new Integer[newIndices.size()];
		newIndices.toArray( indexData );
		this.indices = Utils.toIntArray( indexData );
	}

	public Mesh( Vector3f[] vertex, int[] indices )
	{
		this( vertex, indices, new Material() );
	}

	public Mesh( Vector3f[] vertex, int[] indices, Material material )
	{
		this.material = material;
		addVertices( vertex, indices, false );
	}

	@Override
	public void render( Shader shader, RenderEngine renderingEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderingEngine );
		draw();
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

	public Vector3f[] getVertex()
	{
		return vertex;
	}

	public boolean intersect( Mesh mesh )
	{
		Bounds bound1 = boundingBox.addPos( getTransform().getPos() );
		Bounds bound2 = mesh.getBoundingBox().addPos( mesh.getTransform().getPos() );

		if ( !bound1.intersect( bound2 ) )
			return false;
		return true;
	}

	public Bounds getBoundingBox()
	{
		return boundingBox;
	}
}
