package com.engine.core.components;

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.NoiseHelper;
import com.engine.core.helpers.QuadTree.Node;
import com.engine.core.helpers.QuadTree.QuadTree;
import com.engine.core.helpers.TimeHelper;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

/**
 * Created on 21/05/2014.
 */
public class GeoSphere extends GameComponent
{
	private float        radius;
	private int          maxLevels;
	private QuadTree     dataTree;
	private Material     material;
	private MeshResource resource;

	public GeoSphere( float radius, int maxLevels )
	{
		this.maxLevels = maxLevels;
		this.radius = radius;
		this.material = Material.ROCK;
		updateGeometry();
	}

	int debug = 0;

	public void updateGeometry()
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
		System.out.println( start );

		if ( maxLevels > 1 )
		{
			for ( int i = 0; i < dataTree.size(); i++ )
			{
				subivideNode( rootNodes.get( i ) , maxLevels );
			}
		}
		//DEBUG
		System.out.println( "Vertices: " + debug );
		System.out.println( TimeHelper.getTime() - start );
	}

	private void subivideNode( Node node, int levels )
	{
		Vector3f pt0 = node.getVector1();
		Vector3f pt1 = node.getVector2();
		Vector3f pt2 = node.getVector3();
		Vector3f va = createMidpoint( pt0, pt2, this.radius );
		Vector3f vb = createMidpoint( pt0, pt1, this.radius );
		Vector3f vc = createMidpoint( pt1, pt2, this.radius );
		node.setLeft( new Node( new Vector3f( pt0 ), vb, va, node ) );
		node.setBot( new Node( vb, new Vector3f( pt1 ), vc, node ) );
		node.setMid( new Node( va, vb, vc, node ) );
		node.setRight( new Node( va, vc, new Vector3f( pt2 ), node ) );
		debug += 4;
		if ( levels-- > 0 )
		{
			subivideNode( node.getLeft(), levels );
			subivideNode( node.getBot(), levels );
			subivideNode( node.getMid(), levels );
			subivideNode( node.getRight(), levels );
		}
	}

	private int fillValueNode( Vector3f[] vertices, Node node, int levels, int actual )
	{
		if ( node == null )
			return actual;
		if ( levels-- > 0 )
		{
			actual = fillValueNode( vertices, node.getLeft(), levels, actual );
			actual = fillValueNode( vertices, node.getBot(), levels, actual );
			actual = fillValueNode( vertices, node.getMid(), levels, actual );
			actual = fillValueNode( vertices, node.getRight(), levels, actual );
		}
		if ( levels == 0 )
		{
			debug += 3;
			vertices[actual] = node.getVector1();
			vertices[actual + 1] = node.getVector2();
			vertices[actual + 2] = node.getVector3();
			return actual + 3;
		}
		return actual;
	}

	private Vector3f createMidpoint( Vector3f a, Vector3f b, float radius )
	{
		return a.add( b ).div( 2 ).normalized().mul( radius );
	}

	private void updateVertices( int levels )
	{
		Vector3f[] vertices = new Vector3f[27280];
		System.out.println( vertices.length );
		debug = 0;
		for (int i = 0; i < dataTree.size(); i++ )
			fillValueNode( vertices, dataTree.getRootNodes().get( i ), levels, i * levels * 3 );
		System.out.println( debug );
		int[] indices = new int[vertices.length];
		for ( int i = 0; i < vertices.length; i++ )
			indices[i] = i;

		calcNormals( vertices, indices );

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

			if ( vertices[i0] == null )
				break ;

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
		updateVertices( maxLevels );
		draw();
	}

	/**
	 * GETTER
	 */
}
