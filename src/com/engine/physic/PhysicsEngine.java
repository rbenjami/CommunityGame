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

package com.engine.physic;

import com.engine.core.helpers.dimensions.Vector3f;

import java.util.ArrayList;

/**
 * Created on 05/05/2014.
 */
public class PhysicsEngine
{
	public final static float NEWTON      = 9.80665f;
	public final static float AIR_DENSITY = 1;

	/**
	 * Update physics
	 */
	public void update( ArrayList<PhysicObject> objectList, float delta )
	{
		if ( objectList == null )
			return;
		for ( PhysicObject object : objectList )
			physics( object, delta );
		for ( int i = 0; i < objectList.size(); i++ )
		{
			for ( int j = i + 1; j < objectList.size(); j++ )
				intersect( objectList.get( i ), objectList.get( j ) );
		}
	}

	public void physics( PhysicObject object, float delta )
	{
		PhysicalProperties properties = object.getPhysicalProperties();

		if ( properties.getFloat( "gravity" ) != 0 )
		{
			float gravity = (float) Math.sqrt( ( 2 * properties.getFloat( "mass" ) * NEWTON ) / ( AIR_DENSITY * properties.getFloat( "surface" ) * properties.getFloat( "dragCoefficient" ) ) );
			gravity *= properties.getFloat( "gravity" );
			object.getVelocity().setY( object.getVelocity().getY() - gravity * delta );
		}
		if ( properties.getFloat( "dragCoefficient" ) != 0 )
		{
			Vector3f dragForce = object.getVelocity().mul( object.getVelocity() ).mul( properties.getFloat( "dragCoefficient" ) * AIR_DENSITY * properties.getFloat( "surface" ) );
			if ( dragForce.length() <= object.getVelocity().length() )
				object.setVelocity( object.getVelocity().add( dragForce ) );
			else
				object.setVelocity( new Vector3f( 0, 0, 0 ) );
		}
	}

	public void intersect( PhysicObject object1, PhysicObject object2 )
	{
		// TODO: do intersect with all colliders
		IntersectData intersectData = Intersect.colliders( object1.getColliders().get( 0 ), object2.getColliders().get( 0 ) );
		if ( intersectData != null && intersectData.isIntersect() )
		{
			Vector3f direction = intersectData.getDirection().normalized();
			Vector3f otherDirection = direction.reflect( object1.getVelocity().normalized() );
			float restitutionCoefficient = ( object1.getPhysicalProperties().getFloat( "restitutionCoefficient" ) + object2.getPhysicalProperties().getFloat( "restitutionCoefficient" ) ) / 2;

			object1.setVelocity( object1.getVelocity().reflect( otherDirection ).mul( restitutionCoefficient ) );
			object2.setVelocity( object2.getVelocity().reflect( direction ).mul( restitutionCoefficient ) );
		}
	}
}
