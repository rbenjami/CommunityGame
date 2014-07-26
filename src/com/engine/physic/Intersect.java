package com.engine.physic;

import com.engine.core.GameObject;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 17/07/14.
 */
public class Intersect
{
	public static Vector3f[] gameObjects( ArrayList<GameObject> objectList, GameObject object )
	{
		Vector3f[] res;
		for ( GameObject obj : objectList )
		{
			if ( obj != object && !object.getVelocity().isNull() && obj.getModel() != null && ( res = gameObject( object, obj ) ) != null )
				return res;
		}
		return null;
	}

	public static Vector3f[] gameObject( GameObject object1, GameObject object2 )
	{
		if ( object1.getModel() == null || object2.getModel() == null )
			return null;
		if ( !object1.getAxisAlignedBoundingBox().intersectBounds( object2.getAxisAlignedBoundingBox() ) )
			return null;

		Vector3f[] res;

		ArrayList<Triangle> obj1Triangles = object1.getModel().getTrianglesInBound( object2.getModel().getAxisAlignedBoundingBox() );
		ArrayList<Triangle> obj2Triangles = object2.getModel().getTrianglesInBound( object1.getModel().getAxisAlignedBoundingBox() );

		System.out.println( obj1Triangles.size() + " " + obj2Triangles.size() );
		for ( Triangle t1 : obj1Triangles )
		{
			for ( Triangle t2 : obj2Triangles )
			{
				if ( ( res = Triangle.intersect( t1, t2 ) ) != null )
				{
					t1.setColor( new Color( 255, 0, 0 ) );
					t2.setColor( new Color( 255, 0, 0 ) );
					System.out.println( res[0] );
					return res;
				}
			}
		}
		return null;
	}
}
