package com.engine.core.components;

import com.engine.core.Material;
import com.engine.core.Utils;
import com.engine.core.helpers.quadtree.Node;
import com.engine.core.helpers.quadtree.QuadTree;
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
	private Vector3f[]   vertices;
	private int[]        indices;

	public GeoSphere( float radius, int maxLevels )
	{
		this.maxLevels = maxLevels;
		this.radius = radius;
		this.material = Material.ROCK;
		createBase();
	}

	int debug = 0;

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
		System.out.println( start );

		if ( maxLevels > 1 )
		{
			for ( int i = 0; i < dataTree.size(); i++ )
			{
				subdivideNode( rootNodes.get( i ), maxLevels );
			}
		}
		//DEBUG
		System.out.println( "Vertices: " + debug );
		System.out.println( TimeHelper.getTime() - start );
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
		debug += 4;
		if ( --levels > 0 )
		{
			subdivideNode( node.getLeft(), levels );
			subdivideNode( node.getBot(), levels );
			subdivideNode( node.getMid(), levels );
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

//	private void fillValueNode( ArrayList<Vector3f> vertices, Node node, int levels, Vector3f pos, Vector3f ray )
//	{
//		if ( node == null )
//			return ;
//		if ( levels-- > 0 && intersectTriangle( node, pos, ray ) )
//		{
//			fillValueNode( vertices, node.getLeft(), levels, pos, ray );
//			fillValueNode( vertices, node.getBot(), levels, pos, ray );
//			fillValueNode( vertices, node.getMid(), levels, pos, ray );
//			fillValueNode( vertices, node.getRight(), levels, pos, ray );
//		}
//		else
//			addToArray( vertices, node );
//	}

	private void fillValueNode( ArrayList<Vector3f> vertices, Node node, int levels, Vector3f pos, Vector3f ray, boolean forced )
	{
		if ( node == null )
			return ;
		if ( levels-- > 0)
		{
			if ( forced || intersectTriangle( node.getLeft(), pos, ray ) )
				fillValueNode( vertices, node.getLeft(), levels, pos, ray, false );
			else
				addToArray( vertices, node.getLeft() );
			if ( forced || intersectTriangle( node.getBot(), pos, ray ) )
				fillValueNode( vertices, node.getBot(), levels, pos, ray, false );
			else
				addToArray( vertices, node.getBot() );
			if ( forced || intersectTriangle( node.getMid(), pos, ray ) )
				fillValueNode( vertices, node.getMid(), levels, pos, ray, false );
			else
				addToArray( vertices, node.getMid() );
			if ( forced || intersectTriangle( node.getRight(), pos, ray ) )
				fillValueNode( vertices, node.getRight(), levels, pos, ray, false );
			else
				addToArray( vertices, node.getRight() );
		}
		else
			addToArray( vertices, node );
	}

	private void updateVertices( int levels, Vector3f pos, Vector3f ray )
	{
		ArrayList<Vector3f> tmpVertices = new ArrayList<Vector3f>();

//		this.vertices = new Vector3f[20 * (int)Math.pow( 4, levels - 1 ) * 3];
		for ( int i = 0; i < dataTree.size(); i++ )
			fillValueNode( tmpVertices, dataTree.getRootNodes().get( i ), levels, pos, ray, false );

		Vector3f[] vertices = new Vector3f[tmpVertices.size()];
		tmpVertices.toArray( vertices );

		this.indices = new int[vertices.length];
		for ( int i = 0; i < vertices.length; i++ )
			indices[i] = i;

		calcNormals( vertices, indices );

		resource = new MeshResource( indices.length );

		glBindBuffer( GL_ARRAY_BUFFER, resource.getVbo() );
		glBufferData( GL_ARRAY_BUFFER, Utils.createFlippedBuffer( vertices ), GL_DYNAMIC_DRAW  );

		glBindBuffer( GL_ELEMENT_ARRAY_BUFFER, resource.getIbo() );
		glBufferData( GL_ELEMENT_ARRAY_BUFFER, Utils.createFlippedBuffer( indices ), GL_DYNAMIC_DRAW  );
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

	boolean intersectTriangle(Node node, Vector3f orig, Vector3f ray)
	{
		Vector3f v0 = node.getVector1();
		Vector3f v1 = node.getVector2();
		Vector3f v2 = node.getVector3();

		// compute plane's normal
		Vector3f A, B;
		A = v1.sub( v0 );
		B = v2.sub( v0 );
		// no need to normalize
		Vector3f N = A .cross( B ); // N

		//
		// Step 1: finding P
		//

		// check if ray and plane are parallel ?
		float NdotRayDirection = N.dot( ray );
//		if (NdotRayDirection == 0)
//			return false; // they are parallel so they don't intersect !
		// compute d parameter using equation 2
		float d = N.dot( v0 );
		// compute t (equation 3)
		float t = -(N.dot( orig ) + d) / NdotRayDirection;
		// check if the triangle is in behind the ray
		if (t < 0)
			return false; // the triangle is behind 
		// compute the intersection point using equation 1
		Vector3f P = orig.add( ray.mul( t ) );

		//
		// Step 2: inside-outside test
		//

		Vector3f C; // Vector3f perpendicular to triangle's plane

		// edge 0
		Vector3f edge0 = v1.sub( v0 );
		Vector3f VP0 = P.sub( v0 );
		C = edge0.cross( VP0 );
		if (N.dot( C ) < 0)
			return false; // P is on the right side

		// edge 1
		Vector3f edge1 = v2.sub( v1 );
		Vector3f VP1 = P.sub( v1 );
		C = edge1.cross( VP1 );
		if (N.dot( C ) < 0)
			return false; // P is on the right side

		// edge 2
		Vector3f edge2 = v0.sub( v2 );
		Vector3f VP2 = P.sub( v2 );
		C = edge2.cross( VP2 );
		if (N.dot( C ) < 0)
			return false; // P is on the right side;

		return true; // this ray hits the triangle
	}

	boolean  hasChanged = true;

	@Override
	public void render( Shader shader, RenderEngine renderEngine )
	{
		Vector3f playerToPlanet = getTransform().getPos().sub( renderEngine.getCamera().getPos() );
//		Vector3f planetToPlayer = renderEngine.getCamera().getPos().sub( getTransform().getPos() );
		System.out.println( playerToPlanet.length() );
		shader.bind();
		shader.updateUniforms( getTransform(), material, renderEngine );
//		if ( hasChanged )
//		{
//			hasChanged = false;
			updateVertices( maxLevels, renderEngine.getCamera().getPos().normalized(), playerToPlanet );
//		}
		draw();
	}

	/**
	 * GETTER
	 */
}
