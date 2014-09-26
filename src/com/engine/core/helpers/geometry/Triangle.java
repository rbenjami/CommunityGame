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

package com.engine.core.helpers.geometry;

import com.engine.core.components.GameComponent;
import com.engine.core.helpers.AABB;
import com.engine.core.helpers.MathHelper;
import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.core.helpers.dimensions.Vector3f;

import java.awt.*;

/**
 * Created on 20/07/14.
 */
public class Triangle extends GameComponent
{
	public static final int SIZE = 3 * 9;
	private Vector3f point1;
	private Vector3f point2;
	private Vector3f point3;
	private Vector3f normal;

	public Triangle( Vector3f point1, Vector3f point2, Vector3f point3 )
	{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;

		Vector3f v1 = new Vector3f( point1, point2 );
		Vector3f v2 = new Vector3f( point1, point3 );
		normal = v1.cross( v2 ).normalized();
	}

	public static Vector3f[] intersect( Triangle v, Triangle u )
	{
		// Compute plane equation of first triangle
		float e1x = v.getPoint2().getX() - v.getPoint1().getX();
		float e1y = v.getPoint2().getY() - v.getPoint1().getY();
		float e1z = v.getPoint2().getZ() - v.getPoint1().getZ();
		float e2x = v.getPoint3().getX() - v.getPoint1().getX();
		float e2y = v.getPoint3().getY() - v.getPoint1().getY();
		float e2z = v.getPoint3().getZ() - v.getPoint1().getZ();
		Vector3f n1 = new Vector3f( e1y * e2z - e1z * e2y, e1z * e2x - e1x * e2z, e1x * e2y - e1y * e2x );
//		Vector3f n1 = v.getPoint2().sub( v.getPoint1() ).mul( v.getPoint3().sub( v.getPoint1() ) );
		float dv = -n1.dot( v.getPoint1() );

		// Evaluate second triangle with plane equation 1 to determine signed distances to the plane.
		float dv0 = n1.dot( v.getPoint1() ) + dv;
		float dv1 = n1.dot( v.getPoint2() ) + dv;
		float dv2 = n1.dot( v.getPoint3() ) + dv;

		// Coplanarity robustness check.
		if ( Math.abs( dv0 ) < MathHelper.EPSILON )
			dv0 = 0;
		if ( Math.abs( dv1 ) < MathHelper.EPSILON )
			dv1 = 0;
		if ( Math.abs( dv2 ) < MathHelper.EPSILON )
			dv2 = 0;

		float dv0dv1 = dv0 * dv1;
		float dv0dv2 = dv0 * dv2;

		if ( dv0dv1 > 0 && dv0dv2 > 0 ) // same sign on all of them + != 0 ==> no intersection
			return null;

		// Compute plane equation of first triangle
		e1x = u.getPoint2().getX() - u.getPoint1().getX();
		e1y = u.getPoint2().getY() - u.getPoint1().getY();
		e1z = u.getPoint2().getZ() - u.getPoint1().getZ();
		e2x = u.getPoint3().getX() - u.getPoint1().getX();
		e2y = u.getPoint3().getY() - u.getPoint1().getY();
		e2z = u.getPoint3().getZ() - u.getPoint1().getZ();
		Vector3f n2 = new Vector3f( e1y * e2z - e1z * e2y, e1z * e2x - e1x * e2z, e1x * e2y - e1y * e2x );
//		Vector3f n2 = u.getPoint2().sub( u.getPoint1() ).mul( u.getPoint3().sub( u.getPoint1() ) );
		float du = -n2.dot( u.getPoint1() );

		// Evaluate second triangle with plane equation 1 to determine signed distances to the plane.
		float du0 = n2.dot( u.getPoint1() ) + du;
		float du1 = n2.dot( u.getPoint2() ) + du;
		float du2 = n2.dot( u.getPoint3() ) + du;

		// Coplanarity robustness check.
		if ( Math.abs( du0 ) < MathHelper.EPSILON )
			du0 = 0;
		if ( Math.abs( du1 ) < MathHelper.EPSILON )
			du1 = 0;
		if ( Math.abs( du2 ) < MathHelper.EPSILON )
			du2 = 0;

		float du0du1 = du0 * du1;
		float du0du2 = du0 * du2;

		if ( du0du1 > 0 && du0du2 > 0 ) // same sign on all of them + != 0 ==> no intersection
			return null;

		Vector3f ld = n1.cross( n2 );

		//Compute an index to the largest component of line direction.
		float max = Math.abs( ld.getX() );
		int index = 0;
		float b = Math.abs( ld.getY() );
		float c = Math.abs( ld.getZ() );
		if ( b > max )
		{
			max = b;
			index = 1;
		}
		if ( c > max )
		{
			index = 2;
		}

		// This is the simplified projection onto the line of intersection.
		float vp0 = v.getPoint( 1 ).getX();
		float vp1 = v.getPoint( 2 ).getX();
		float vp2 = v.getPoint( 3 ).getX();

		float up0 = u.getPoint( 1 ).getX();
		float up1 = u.getPoint( 2 ).getX();
		float up2 = u.getPoint( 3 ).getX();
		if ( index == 1 )
		{
			vp0 = v.getPoint( 1 ).getY();
			vp1 = v.getPoint( 2 ).getY();
			vp2 = v.getPoint( 3 ).getY();

			up0 = u.getPoint( 1 ).getY();
			up1 = u.getPoint( 2 ).getY();
			up2 = u.getPoint( 3 ).getY();
		}
		else if ( index == 2 )
		{
			vp0 = v.getPoint( 1 ).getZ();
			vp1 = v.getPoint( 2 ).getZ();
			vp2 = v.getPoint( 3 ).getZ();

			up0 = u.getPoint( 1 ).getZ();
			up1 = u.getPoint( 2 ).getZ();
			up2 = u.getPoint( 3 ).getZ();
		}

		// Compute interval for triangle 1.
		TriangleIntersection isectA = compute_intervals_isectline( v, vp0, vp1, vp2, dv0, dv1, dv2, dv0dv1, dv0dv2 );

		if ( isectA == null )
			return coplanarTriangles( n1, v, u ) ? new Vector3f[]{ new Vector3f( 0, 0, 0 ) } : null;

		int smallest1 = 0;
		if ( isectA.s0 > isectA.s1 )
		{
			float cc = isectA.s0;
			isectA.s0 = isectA.s1;
			isectA.s1 = cc;
			smallest1 = 1;
		}

		// Compute interval for triangle 2.
		TriangleIntersection isectB = compute_intervals_isectline( u, up0, up1, up2, du0, du1, du2, du0du1, du0du2 );

		if ( isectB == null )
			return coplanarTriangles( n1, v, u ) ? new Vector3f[]{ new Vector3f( 0, 0, 0 ) } : null;

		int smallest2 = 0;
		if ( isectB.s0 > isectB.s1 )
		{
			float cc = isectB.s0;
			isectB.s0 = isectB.s1;
			isectB.s1 = cc;
			smallest2 = 1;
		}

		if ( isectA.s1 < isectB.s0 || isectB.s1 < isectA.s0 )
			return null;

		// At this point we know that the triangles intersect: there's an intersection line, the triangles are not
		// coplanar, and they overlap.
		Vector3f[] intersectionVertices = new Vector3f[2];

		if ( isectB.s0 < isectA.s0 )
		{
			if ( smallest1 == 0 )
				intersectionVertices[0] = isectA.p0;
			else
				intersectionVertices[0] = isectA.p1;

			if ( isectB.s1 < isectA.s1 )
			{
				if ( smallest2 == 0 )
					intersectionVertices[1] = isectB.p1;
				else
					intersectionVertices[1] = isectB.p0;
			}
			else
			{
				if ( smallest1 == 0 )
					intersectionVertices[1] = isectA.p1;
				else
					intersectionVertices[1] = isectA.p0;
			}
		}
		else
		{
			if ( smallest2 == 0 )
				intersectionVertices[0] = isectB.p0;
			else
				intersectionVertices[0] = isectB.p1;

			if ( isectB.s1 > isectA.s1 )
			{
				if ( smallest1 == 0 )
					intersectionVertices[1] = isectA.p1;
				else
					intersectionVertices[1] = isectA.p0;
			}
			else
			{
				if ( smallest2 == 0 )
					intersectionVertices[1] = isectB.p1;
				else
					intersectionVertices[1] = isectB.p0;
			}
		}
		return intersectionVertices;
	}

//	public boolean isInBound( AABB aabb )
//	{
//		Matrix4f transformation = getTransform().getTransformation();
//		Vector3f p1 = transformation.transform( point1 );
//		Vector3f p2 = transformation.transform( point2 );
//		Vector3f p3 = transformation.transform( point3 );
//		if ( p1.isInBound( aabb ) && p2.isInBound( aabb ) && p3.isInBound( aabb ) )
//			return true;
//		if ( point1.isInBound( aabb ) && point2.isInBound( aabb ) && point3.isInBound( aabb ) )
//			return true;
//		return false;
//	}

	public Vector3f getPoint2()
	{
		return point2;
	}

	public Vector3f getPoint1()
	{
		return point1;
	}

	public Vector3f getPoint3()
	{
		return point3;
	}

	/**
	 * GETTER
	 */
	public Vector3f getPoint( int i )
	{
		if ( i == 1 )
			return point1;
		if ( i == 2 )
			return point2;
		if ( i == 3 )
			return point3;
		return null;
	}

	protected static TriangleIntersection compute_intervals_isectline( Triangle t, float vv0, float vv1, float vv2,
																	   float d0, float d1, float d2,
																	   float d0d1, float d0d2 )
	{
		if ( d0d1 > 0 ) // D0, D1 are on the same side, D2 on the other or on the plane
			return intersect( t.getPoint3(), t.getPoint1(), t.getPoint2(), vv2, vv0, vv1, d2, d0, d1 );
		else if ( d0d2 > 0 )
			return intersect( t.getPoint2(), t.getPoint1(), t.getPoint3(), vv1, vv0, vv2, d1, d0, d2 );
		else if ( d1 * d2 > 0 || d0 != 0 )
			return intersect( t.getPoint1(), t.getPoint2(), t.getPoint3(), vv0, vv1, vv2, d0, d1, d2 );
		else if ( d1 != 0 )
			return intersect( t.getPoint2(), t.getPoint1(), t.getPoint3(), vv1, vv0, vv2, d1, d0, d2 );
		else if ( d2 != 0 )
			return intersect( t.getPoint3(), t.getPoint1(), t.getPoint2(), vv2, vv0, vv1, d2, d0, d1 );
		else
			return null; // triangles are coplanar
	}

	protected static boolean coplanarTriangles( Vector3f n, Triangle t1, Triangle t2 )
	{
		// First project onto an axis-aligned plane that maximizes the are of the triangles.
		int i0;
		int i1;

		float[] a = new float[]{ Math.abs( n.getX() ), Math.abs( n.getY() ), Math.abs( n.getZ() ) };
		if ( a[0] > a[1] ) // X > Y
		{
			if ( a[0] > a[2] )
			{ // X is greatest
				i0 = 1;
				i1 = 2;
			}
			else
			{ // Z is greatest
				i0 = 0;
				i1 = 1;
			}
		}
		else // X < Y
		{
			if ( a[2] > a[1] )
			{ // Z is greatest
				i0 = 0;
				i1 = 1;
			}
			else
			{ // Y is greatest
				i0 = 0;
				i1 = 2;
			}
		}

		// Test all edges of triangle 1 against the edges of triangle 2.
		float[] v0 = new float[]{ t1.getPoint1().getX(), t1.getPoint1().getY(), t1.getPoint1().getZ() };
		float[] v1 = new float[]{ t1.getPoint2().getX(), t1.getPoint2().getY(), t1.getPoint2().getZ() };
		float[] v2 = new float[]{ t1.getPoint3().getX(), t1.getPoint3().getY(), t1.getPoint3().getZ() };

		float[] u0 = new float[]{ t2.getPoint1().getX(), t2.getPoint1().getY(), t2.getPoint1().getZ() };
		float[] u1 = new float[]{ t2.getPoint2().getX(), t2.getPoint2().getY(), t2.getPoint2().getZ() };
		float[] u2 = new float[]{ t2.getPoint3().getX(), t2.getPoint3().getY(), t2.getPoint3().getZ() };

		boolean tf = triangleEdgeTest( v0, v1, u0, u1, u2, i0, i1 );
		if ( tf )
			return true;

		tf = triangleEdgeTest( v1, v2, u0, u1, u2, i0, i1 );
		if ( tf )
			return true;

		tf = triangleEdgeTest( v2, v0, u0, u1, u2, i0, i1 );
		if ( tf )
			return true;

		// Finally, test whether one triangle is contained in the other one.
		tf = pointInTri( v0, u0, u1, u2, i0, i1 );
		if ( tf )
			return true;

		return pointInTri( u0, v0, v1, v2, i0, i1 );
	}

	protected static TriangleIntersection intersect( Vector3f v0, Vector3f v1, Vector3f v2, float vv0, float vv1, float vv2, float d0, float d1, float d2 )
	{
		TriangleIntersection intersection = new TriangleIntersection();

		float tmp = d0 / ( d0 - d1 );
		intersection.s0 = vv0 + ( vv1 - vv0 ) * tmp;
		Vector3f diff = v1.sub( v0 );
		diff = diff.mul( tmp );
		intersection.p0 = diff.add( v0 );

		tmp = d0 / ( d0 - d2 );
		intersection.s1 = vv0 + ( vv2 - vv0 ) * tmp;
		diff = v2.sub( v0 );
		diff = diff.mul( tmp );
		intersection.p1 = diff.add( v0 );

		return intersection;
	}

	protected static boolean triangleEdgeTest( float[] v0, float[] v1, float[] u0, float[] u1, float[] u2, int i0,
											   int i1 )
	{
		float ax = v1[i0] - v0[i0];
		float ay = v1[i1] - v0[i1];

		// Test edge u0:u1 against v0:v1
		boolean tf = edgeEdgeTest( v0, u0, u1, i0, i1, ax, ay );
		if ( tf )
			return true;

		// Test edge u1:u2 against v0:v1
		tf = edgeEdgeTest( v0, u1, u2, i0, i1, ax, ay );
		if ( tf )
			return true;

		// Test edge u2:u0 against v0:v1
		return edgeEdgeTest( v0, u2, u0, i0, i1, ax, ay );
	}

	protected static boolean pointInTri( float[] v0, float[] u0, float[] u1, float[] u2, int i0, int i1 )
	{
		float a = u1[i1] - u0[i1];
		float b = -( u1[i0] - u0[i0] );
		float c = -a * u0[i0] - b * u0[i1];
		float d0 = a * v0[i0] + b * v0[i1] + c;

		a = u2[i1] - u1[i1];
		b = -( u2[i0] - u1[i0] );
		c = -a * u1[i0] - b * u1[i1];
		float d1 = a * v0[i0] + b * v0[i1] + c;

		a = u0[i1] - u2[i1];
		b = -( u0[i0] - u2[i0] );
		c = -a * u2[i0] - b * u2[i1];
		float d2 = a * v0[i0] + b * v0[i1] + c;

		return d0 * d1 > 0 && d0 * d2 > 0;
	}

	protected static boolean edgeEdgeTest( float[] v0, float[] u0, float[] u1, int i0, int i1, float ax, float ay )
	{
		float bx = u0[i0] - u1[i0];
		float by = u0[i1] - u1[i1];
		float cx = v0[i0] - u0[i0];
		float cy = v0[i1] - u0[i1];

		float f = ay * bx - ax * by;
		float d = by * cx - bx * cy;

		if ( ( f > 0 && d >= 0 && d <= f ) || ( f < 0 && d <= 0 && d >= f ) )
		{
			float e = ax * cy - ay * cx;
			if ( f > 0 )
			{
				if ( e >= 0 && e <= f )
					return true;
			}
			else
			{
				if ( e <= 0 && e >= f )
					return true;
			}
		}

		return false;
	}

	public void setPoint3( Vector3f point3 )
	{
		this.point3 = point3;
	}

	public void setPoint1( Vector3f point1 )
	{
		this.point1 = point1;
	}

	public void setPoint2( Vector3f point2 )
	{
		this.point2 = point2;
	}

	public Triangle transform( Transform transform )
	{
		Matrix4f transformation = transform.getTransformation();
		Vector3f p1 = transformation.transform( point1 );
		Vector3f p2 = transformation.transform( point2 );
		Vector3f p3 = transformation.transform( point3 );
		set( p1, p2, p3 );
		return this;
	}

	public void set( Vector3f point1, Vector3f point2, Vector3f point3 )
	{
		this.point1 = point1;
		this.point2 = point2;
		this.point3 = point3;

		Vector3f v1 = new Vector3f( point1, point3 );
		Vector3f v2 = new Vector3f( point1, point2 );
		this.normal = v1.cross( v2 ).normalized();
	}

	public boolean havePointInBound( AABB aabb )
	{
		Matrix4f transformation = getTransform().getTransformation();
		Vector3f p1 = transformation.transform( point1 );
		Vector3f p2 = transformation.transform( point2 );
		Vector3f p3 = transformation.transform( point3 );

		if ( p1.isInBound( aabb ) || p2.isInBound( aabb ) || p3.isInBound( aabb ) )
			return true;
		return false;
	}

	/**
	 * SETTER
	 */
	public void set( Triangle triangle )
	{
		this.point1 = triangle.getPoint1();
		this.point2 = triangle.getPoint2();
		this.point3 = triangle.getPoint3();
		this.normal = triangle.getNormal();
	}

	public Vector3f getNormal()
	{
		return normal;
	}

	public void setNormal( Vector3f normal )
	{
		this.normal = normal;
	}

	public void setColor( Color color )
	{
		point1.setColor( color );
		point2.setColor( color );
		point3.setColor( color );
	}

//	public boolean equals( Triangle obj )
//	{
//		return obj.getPoint1().equals( point1 ) && obj.getPoint2().equals( point2 ) && obj.getPoint3().equals( point3 );
//	}

	protected static class TriangleIntersection
	{
		public Vector3f p0; // the first point of the line
		public Vector3f p1; // the second point of the line
		public float    s0; // the distance along the line to the first intersection with the triangle
		public float    s1; // the distance along the line to the second intersection with the triangle
	}
}
