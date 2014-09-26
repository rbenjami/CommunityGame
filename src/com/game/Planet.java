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

package com.game;

import com.engine.core.GameObject;
import com.engine.core.components.DynamicMesh;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.quadtree.QuadTree;
import com.engine.physic.collider.SphereCollider;
import com.game.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Planet extends GameObject
{
	float radius = 100;
	private Player      player;
	private DynamicMesh mesh;
	private QuadTree    quadTrees[];

	public Planet( Player player )
	{
		this.player = player;

		mesh = new DynamicMesh();
		quadTrees = new QuadTree[6];

		// TOP
		quadTrees[0] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( -1, 1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( -1, 1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( 1, 1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, 1.0f, 1 ).normalized().mul( radius ) ) );
		// WEST
		quadTrees[1] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( -1, -1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( -1, -1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( -1, 1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( -1, 1.0f, 1 ).normalized().mul( radius ) ) );
		// EST
		quadTrees[2] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( 1, 1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, 1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, 1 ).normalized().mul( radius ) ) );
		// NORTH
		quadTrees[3] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( -1, 1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( -1, -1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( 1, 1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, 1 ).normalized().mul( radius ) ) );
		// SOUTH
		quadTrees[4] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( -1, -1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( -1, 1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, 1.0f, -1 ).normalized().mul( radius ) ) );
		// BOTTOM
		quadTrees[5] = new QuadTree( new Chunk( mesh, 2, radius,
												new Vector3f( -1, -1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( -1, -1.0f, -1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, 1 ).normalized().mul( radius ),
												new Vector3f( 1, -1.0f, -1 ).normalized().mul( radius ) ) );
		addComponent( mesh );
	}

	public boolean isInSphere( int level, SphereCollider sphere, QuadTree node )
	{
		return node.depth < level && sphere.intersect( ( (Chunk) node.object ).getCenter() ) != null;
	}

	public void subDivideChunk( Chunk parent, QuadTree node )
	{
		Vector3f left = parent.getV2().add( parent.getV1() ).div( 2 );
		Vector3f top = parent.getV4().add( parent.getV2() ).div( 2 );
		Vector3f right = parent.getV3().add( parent.getV4() ).div( 2 );
		Vector3f bot = parent.getV1().add( parent.getV3() ).div( 2 );
		Vector3f center = left.add( right );

		left = left.normalized().mul( radius );
		top = top.normalized().mul( radius );
		right = right.normalized().mul( radius );
		bot = bot.normalized().mul( radius );
		center = center.normalized().mul( radius );

		parent.del();

		Chunk child1 = new Chunk( mesh, 2, radius, parent.getV1(), left, bot, center );
		Chunk child2 = new Chunk( mesh, 2, radius, left, parent.getV2(), center, top );
		Chunk child3 = new Chunk( mesh, 2, radius, center, top, right, parent.getV4() );
		Chunk child4 = new Chunk( mesh, 2, radius, bot, center, parent.getV3(), right );

		node.split( child1, child2, child3, child4 );
	}

	@Override
	public void update( float delta )
	{
		SphereCollider spheres[] = new SphereCollider[6];
		spheres[0] = new SphereCollider( player.getTransform().getPos(), 150 );
		spheres[1] = new SphereCollider( player.getTransform().getPos(), 100 );
		spheres[2] = new SphereCollider( player.getTransform().getPos(), 75 );
		spheres[3] = new SphereCollider( player.getTransform().getPos(), 50 );
		spheres[4] = new SphereCollider( player.getTransform().getPos(), 20 );
		spheres[5] = new SphereCollider( player.getTransform().getPos(), 10 );

		List<QuadTree> toDivide = new ArrayList<QuadTree>();
//		List<QuadTree> toRemove = new ArrayList<QuadTree>();
		int limit = 0;
		for ( QuadTree quadTree : quadTrees )
		{
			List<QuadTree> renderedNode = quadTree.getLeaves();
			for ( QuadTree node : renderedNode )
			{
				for ( int i = 0; i < spheres.length; i++ )
				{
					if ( isInSphere( i + 1, spheres[i], node ) )
					{
						toDivide.add( node );
						limit++;
						break;
					}
//					else if ( node.parent != null )
//					{
//						Collections.addAll( toRemove, node.parent );
//					}
				}
				if ( limit == 5 )
					break;
			}
		}
		for ( QuadTree node : toDivide )
		{
			subDivideChunk( (Chunk) node.object, node );
		}
//		for ( QuadTree node : toRemove )
//		{
//			List<QuadTree> list = Arrays.asList( node.children );
//			for ( QuadTree q : list )
//			{
//				try
//				{
//					mesh.delTriangles( ((Chunk)q.object).getTriangles() );
//				}
//				catch ( Exception e )
//				{
//					e.printStackTrace();
//				}
//			}
//			mesh.addTriangles( ( (Chunk) node.object ).getTriangles() );
//		}
	}
}
