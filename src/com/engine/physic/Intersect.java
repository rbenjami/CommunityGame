package com.engine.physic;

import com.engine.core.GameObject;
import com.engine.core.helpers.dimensions.Vector3f;

import java.util.ArrayList;

/**
 * Created on 17/07/14.
 */
public class Intersect
{
	public static Vector3f gameObjects( ArrayList<GameObject> objectList, GameObject object )
	{
		Vector3f res;
		for ( GameObject obj : objectList )
		{
			if ( obj != object && obj.getModel() != null && ( res = gameObject( object, obj ) ) != null )
				return res;
		}
		return null;
	}

	public static Vector3f gameObject( GameObject object1, GameObject object2 )
	{
		if ( object1.getModel() == null || object2.getModel() == null )
			return null;
		if ( !object1.getAxisAlignedBoundingBox().intersectBounds( object2.getAxisAlignedBoundingBox() ) )
			return null;

		return new Vector3f( 0, 0, 0 );
	}

//	public static Vector3f meshes( Mesh mesh1, Mesh mesh2 )
//	{
//
//	}
}
