///*
// * Copyright (C) 2014 Repingon Benjamin
// * This file is part of CommunityGame.
// * CommunityGame is free software: you can redistribute it and/or modify
// * it under the terms of the GNU General Public License as published by
// * the Free Software Foundation, either version 2 of the License, or
// * (at your option) any later version.
// * CommunityGame is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
// * GNU General Public License for more details.
// * You should have received a copy of the GNU General Public License
// * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
// */
//
//package com.game;
//
//import com.engine.core.components.DynamicMesh;
//import com.engine.core.helpers.dimensions.Vector3f;
//import com.engine.core.helpers.geometry.Triangle;
//
//import java.util.ArrayList;
//
//public class Chunk
//{
//	private ArrayList<Chunk> renderedChunk;
//	private DynamicMesh mesh;
//	private float radius;
//	private Vector3f center;
//	private Vector3f v1;
//	private Vector3f v2;
//	private Vector3f v3;
//	private Vector3f v4;
//
//	private static int maxLevel = 0;
//	private int levelSubdivisions;
//    private Chunk parent;
//
//    private Chunk son1;
//    private Chunk son2;
//    private Chunk son3;
//    private Chunk son4;
//
//	private ArrayList<Triangle> triangles;
//
//	public Chunk( DynamicMesh mesh, ArrayList<Chunk> renderedChunk, float radius, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
//	{
//		this( mesh, renderedChunk, 0, radius, p1, p2, p3, p4 );
//	}
//
//    public Chunk( DynamicMesh mesh, ArrayList<Chunk> renderedChunk, int levelSubdivisions, float radius, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
//    {
//        this(mesh, renderedChunk, null, levelSubdivisions, radius, p1, p2, p3, p4);
//    }
//
//	public Chunk( DynamicMesh mesh, ArrayList<Chunk> renderedChunk, Chunk parent, int levelSubdivisions, float radius, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
//	{
//		this.renderedChunk = renderedChunk;
//		this.mesh = mesh;
//		this.radius = radius;
//		this.v1 = p1;
//		this.v2 = p2;
//		this.v3 = p3;
//		this.v4 = p4;
//		this.center = p2.add( p3.sub( p2 ).div( 2 ) );
//		this.levelSubdivisions = levelSubdivisions;
//        this.parent = parent;
//
//		triangles = new ArrayList<Triangle>();
//		triangles.add( new Triangle( v1, v2, v3 ) );
//		triangles.add( new Triangle( v3, v2, v4 ) );
//        addToMesh();
//	}
//
//	public ArrayList<Triangle> create( int sub, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
//	{
//		ArrayList<Triangle> triangles = new ArrayList<Triangle>();
//
//		sub--;
//		if ( sub == -1 )
//		{
//			triangles.add( new Triangle( p1, p2, p3 ) );
//			triangles.add( new Triangle( p3, p2, p4 ) );
//			return triangles;
//		}
//
//		Vector3f left = p2.add( p1 ).div( 2 );
//		Vector3f top = p4.add( p2 ).div( 2 );
//		Vector3f right = p3.add( p4 ).div( 2 );
//		Vector3f bot = p1.add( p3 ).div( 2 );
//		Vector3f center = left.add( right );
//
//		left = left.normalized().mul( radius );
//		top = top.normalized().mul( radius );
//		right = right.normalized().mul( radius );
//		bot = bot.normalized().mul( radius );
//		center = center.normalized().mul( radius );
//
//		triangles.addAll( create( sub, p1, left, bot, center ) );
//		triangles.addAll( create( sub, left, p2, center, top ) );
//		triangles.addAll( create( sub, center, top, right, p4 ) );
//		triangles.addAll( create( sub, bot, center, p3, right ) );
//		return triangles;
//	}
//
//	public void subDiv()
//	{
//		if ( levelSubdivisions >= Chunk.maxLevel )
//			return;
//		del();
//		levelSubdivisions++;
//		Vector3f left = v2.add( v1 ).div( 2 );
//		Vector3f top = v4.add( v2 ).div( 2 );
//		Vector3f right = v3.add( v4 ).div( 2 );
//		Vector3f bot = v1.add( v3 ).div( 2 );
//		Vector3f center = left.add( right );
//
//		left = left.normalized().mul( radius );
//		top = top.normalized().mul( radius );
//		right = right.normalized().mul( radius );
//		bot = bot.normalized().mul( radius );
//		center = center.normalized().mul( radius );
//
//		son1 = new Chunk( mesh, renderedChunk, this, levelSubdivisions, radius, v1, left, bot, center );
//		son2 = new Chunk( mesh, renderedChunk, this, levelSubdivisions, radius, left, v2, center, top );
//		son3 = new Chunk( mesh, renderedChunk, this, levelSubdivisions, radius, center, top, right, v4 );
//		son4 = new Chunk( mesh, renderedChunk, this, levelSubdivisions, radius, bot, center, v3, right );
//	}
//
//    public void unDiv()
//    {
//        if ( parent != null )
//        {
//            ArrayList<Chunk> sons = parent.getSons();
//            for (Chunk c : sons)
//                c.del();
//            parent.addToMesh();
//        }
//    }
//
//    public void addToMesh()
//    {
//        this.mesh.addTriangles( triangles );
//        renderedChunk.add( this );
//    }
//
//	public void del()
//	{
//		try
//		{
//			mesh.delTriangles( triangles );
//		}
//		catch ( Exception e )
//		{
//			e.printStackTrace();
//			System.exit( 0 );
//		}
//		renderedChunk.remove( this );
//	}
//
//	public Vector3f getCenter()
//	{
//		return center;
//	}
//
//	public int getLevelSubdivisions()
//	{
//		return levelSubdivisions;
//	}
//
//    public ArrayList<Chunk> getSons()
//    {
//        ArrayList<Chunk> list = new ArrayList<Chunk>();
//
//        list.add(son1);
//        list.add(son2);
//        list.add(son3);
//        list.add(son4);
//        return list;
//    }
//
//    public static void setMaxLevel( int maxLevel )
//	{
//		Chunk.maxLevel = maxLevel;
//	}
//}
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

import com.engine.core.components.DynamicMesh;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

import java.util.ArrayList;

public class Chunk
{
	static int pok = 0;
	private DynamicMesh         mesh;
	private float               radius;
	private Vector3f            center;
	private Vector3f            v1;
	private Vector3f            v2;
	private Vector3f            v3;
	private Vector3f            v4;
	private ArrayList<Triangle> triangles;

	public Chunk( DynamicMesh mesh, int subdivisions, float radius, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
	{
		this( mesh, subdivisions, radius, p1, p2, p3, p4, p2.add( p1 ).div( 2 ).add( p3.add( p4 ).div( 2 ) ).normalized().mul( radius ) );
	}

	public Chunk( DynamicMesh mesh, int subdivisions, float radius, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4, Vector3f center )
	{
		this.mesh = mesh;
		this.radius = radius;
		this.v1 = p1;
		this.v2 = p2;
		this.v3 = p3;
		this.v4 = p4;
		this.center = center;

		triangles = new ArrayList<Triangle>();
		create( subdivisions, p1, p2, p3, p4 );
		mesh.addTriangles( triangles );
	}

	public void create( int subdivisions, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
	{
		if ( subdivisions <= 0 )
		{
			if ( pok % 2 == 0 )
			{
				triangles.add( new Triangle( p3, p1, p4 ) );
				triangles.add( new Triangle( p4, p1, p2 ) );
			}
			else
			{
				triangles.add( new Triangle( p1, p2, p3 ) );
				triangles.add( new Triangle( p3, p2, p4 ) );
			}
			pok++;
		}
		else
			subDiv( --subdivisions, p1, p2, p3, p4 );
	}

	public void subDiv( int subdivisions, Vector3f p1, Vector3f p2, Vector3f p3, Vector3f p4 )
	{
		Vector3f left = p2.add( p1 ).div( 2 );
		Vector3f top = p4.add( p2 ).div( 2 );
		Vector3f right = p3.add( p4 ).div( 2 );
		Vector3f bot = p1.add( p3 ).div( 2 );
		Vector3f center = left.add( right );

		left = left.normalized().mul( radius );
		top = top.normalized().mul( radius );
		right = right.normalized().mul( radius );
		bot = bot.normalized().mul( radius );
		center = center.normalized().mul( radius );

		create( subdivisions, p1, left, bot, center );
		create( subdivisions, left, p2, center, top );
		create( subdivisions, center, top, right, p4 );
		create( subdivisions, bot, center, p3, right );
	}

	public void del()
	{
		try
		{
			mesh.delTriangles( triangles );
		}
		catch ( Exception e )
		{
			e.printStackTrace();
			System.exit( 0 );
		}
	}

	public ArrayList<Triangle> getTriangles()
	{
		return triangles;
	}

	public Vector3f getCenter()
	{
		return center;
	}

	public Vector3f getV1()
	{
		return v1;
	}

	public Vector3f getV2()
	{
		return v2;
	}

	public Vector3f getV3()
	{
		return v3;
	}

	public Vector3f getV4()
	{
		return v4;
	}
}