package com.engine.core.components;

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.IntersectHelper;
import com.engine.core.helpers.TimeHelper;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.quadtree.Node;
import com.engine.core.helpers.quadtree.QuadTree;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

/**
 * Created on 21/05/2014.
 */
public class GeoSphere extends GameComponent
{
	int DEBUG = 0;
	private float        radius;
	private int          maxLevels;
	private QuadTree     dataTree;
	private Material     material;
	private MeshResource resource;
	private Vector3f[]   vertices;
	private int[]        indices;

	public GeoSphere( float radius, int maxLevels )
	{
		this.maxLevels = maxLevels;
		this.radius = radius;
		this.material = new Material();
		createBase();
	}

	public void createBase()
	{
		ArrayList<Node> rootNodes = new ArrayList<Node>();
		float y = 0.4472f * this.radius;
		float a = 0.8944f * this.radius;
		float b = 0.2764f * this.radius;
		float c = 0.7236f * this.radius;
		float d = 0.8507f * this.radius;
		float e = 0.5257f * this.radius;

		rootNodes.add( new Node( new Vector3f( 0, this.radius, 0 ), new Vector3f( a, y, 0 ), new Vector3f( b, y, -d ), null ) );
		rootNodes.add( new Node( new Vector3f( 0, this.radius, 0 ), new Vector3f( b, y, -d ), new Vector3f( -c, y, -e ), null ) );
		rootNodes.add( new Node( new Vector3f( 0, this.radius, 0 ), new Vector3f( -c, y, -e ), new Vector3f( -c, y, e ), null ) );
		rootNodes.add( new Node( new Vector3f( 0, this.radius, 0 ), new Vector3f( -c, y, e ), new Vector3f( b, y, d ), null ) );
		rootNodes.add( new Node( new Vector3f( 0, this.radius, 0 ), new Vector3f( b, y, d ), new Vector3f( a, y, 0 ), null ) );

		rootNodes.add( new Node( new Vector3f( b, y, -d ), new Vector3f( a, y, 0 ), new Vector3f( c, -y, -e ), null ) );
		rootNodes.add( new Node( new Vector3f( -c, y, -e ), new Vector3f( b, y, -d ), new Vector3f( -b, -y, -d ), null ) );
		rootNodes.add( new Node( new Vector3f( -c, y, e ), new Vector3f( -c, y, -e ), new Vector3f( -a, -y, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( b, y, d ), new Vector3f( -c, y, e ), new Vector3f( -b, -y, d ), null ) );
		rootNodes.add( new Node( new Vector3f( a, y, 0 ), new Vector3f( b, y, d ), new Vector3f( c, -y, e ), null ) );

		rootNodes.add( new Node( new Vector3f( -b, -y, -d ), new Vector3f( b, y, -d ), new Vector3f( c, -y, -e ), null ) );
		rootNodes.add( new Node( new Vector3f( -a, -y, 0 ), new Vector3f( -c, y, -e ), new Vector3f( -b, -y, -d ), null ) );
		rootNodes.add( new Node( new Vector3f( -b, -y, d ), new Vector3f( -c, y, e ), new Vector3f( -a, -y, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( c, -y, e ), new Vector3f( b, y, d ), new Vector3f( -b, -y, d ), null ) );
		rootNodes.add( new Node( new Vector3f( c, -y, -e ), new Vector3f( a, y, 0 ), new Vector3f( c, -y, e ), null ) );

		rootNodes.add( new Node( new Vector3f( -b, -y, -d ), new Vector3f( c, -y, -e ), new Vector3f( 0, -this.radius, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( -a, -y, 0 ), new Vector3f( -b, -y, -d ), new Vector3f( 0, -this.radius, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( -b, -y, d ), new Vector3f( -a, -y, 0 ), new Vector3f( 0, -this.radius, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( c, -y, e ), new Vector3f( -b, -y, d ), new Vector3f( 0, -this.radius, 0 ), null ) );
		rootNodes.add( new Node( new Vector3f( c, -y, -e ), new Vector3f( c, -y, e ), new Vector3f( 0, -this.radius, 0 ), null ) );

		this.dataTree = new QuadTree( rootNodes );

		//DEBUG
		double start = TimeHelper.getTime();

		if ( maxLevels > 1 )
		{
			for ( int i = 0; i < dataTree.size(); i++ )
				subdivideNode( rootNodes.get( i ), maxLevels );
		}

		//DEBUG
//		System.out.println( TimeHelper.getTime() - start );
	}

	private void subdivideNode( Node node, int levels )
	{
		Vector3f pt0 = node.getVector1();
		Vector3f pt1 = node.getVector2();
		Vector3f pt2 = node.getVector3();
		Vector3f va = createMidpoint( pt0, pt2, this.radius );
		Vector3f vb = createMidpoint( pt0, pt1, this.radius );
		Vector3f vc = createMidpoint( pt1, pt2, this.radius );
		node.setLeft( new Node( pt0, vb, va, node ) );
		node.setBot( new Node( new Vector3f( vb ), pt1, vc, node ) );
		node.setMid( new Node( new Vector3f( va ), new Vector3f( vb ), new Vector3f( vc ), node ) );
		node.setRight( new Node( new Vector3f( va ), new Vector3f( vc ), pt2, node ) );
		if ( --levels > 0 )
		{
			subdivideNode( node.getLeft(), levels );
			subdivideNode( node.getBottom(), levels );
			subdivideNode( node.getMiddle(), levels );
			subdivideNode( node.getRight(), levels );
		}
	}

	private Vector3f createMidpoint( Vector3f a, Vector3f b, float radius )
	{
		return a.add( b ).div( 2 ).normalized().mul( radius );
	}

	private void addToArray( ArrayList<Vector3f> vertices, Node node )
	{
		vertices.add( node.getVector1() );
		vertices.add( node.getVector2() );
		vertices.add( node.getVector3() );
	}

	private void fillValueNode( ArrayList<Vector3f> vertices, Node node, int levels, Vector3f pos, Vector3f ray )
	{
		if ( node == null )
			return;
		if ( levels-- >= 0 && IntersectHelper.rayTriangle( node, pos, ray, -0.01f * levels ) )
		{
			DEBUG += 4;
			fillValueNode( vertices, node.getLeft(), levels, pos, ray );
			fillValueNode( vertices, node.getBottom(), levels, pos, ray );
			fillValueNode( vertices, node.getMiddle(), levels, pos, ray );
			fillValueNode( vertices, node.getRight(), levels, pos, ray );
		}
		else
			addToArray( vertices, node );
	}

	private void updateVertices( int levels, Vector3f pos, Vector3f ray )
	{
		ArrayList<Vector3f> tmpVertices = new ArrayList<Vector3f>();

		DEBUG = 0;
		for ( int i = 0; i < dataTree.size(); i++ )
			fillValueNode( tmpVertices, dataTree.getRootNodes().get( i ), levels, pos, ray );

//		System.out.println( DEBUG );
		Vector3f[] vertices = new Vector3f[tmpVertices.size()];
		tmpVertices.toArray( vertices );

		this.indices = new int[vertices.length];
		for ( int i = 0; i < vertices.length; i++ )
			indices[i] = i;

		calcNormals( vertices, indices );

		resource = new MeshResource( indices.length );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( vertices ), GL_DYNAMIC_DRAW );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer( indices ), GL_DYNAMIC_DRAW );
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

			vertices[i0].setNormal( normal );
			vertices[i1].setNormal( normal );
			vertices[i2].setNormal( normal );
		}
	}

	@Override
	public void render( Shader shader, RenderEngine renderEngine )
	{
		Vector3f playerToPlanet = getTransform().getPos().sub( renderEngine.getCamera().getPos() );
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderEngine );
		updateVertices( maxLevels, renderEngine.getCamera().getPos().normalized(), playerToPlanet );
		draw();
	}

	/**
	 * GETTER
	 */
}
