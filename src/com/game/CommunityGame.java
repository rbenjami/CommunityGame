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

import com.engine.core.*;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Quaternion;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Sphere;
import com.engine.core.helpers.geometry.Triangle;
import com.engine.physic.PhysicObject;
import com.engine.physic.PhysicalProperties;
import com.engine.physic.collider.PlanCollider;
import com.engine.physic.collider.SphereCollider;
import com.game.entity.Entity;
import com.game.entity.Player;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	Player     player;
	Entity     entity;
	GameObject dirt;

	public void init()
	{
		/**
		 * Entity
		 */
		player = new Player( new Mesh( "cube.obj" ) );
		player.getTransform().getPos().set( 0, 5, -6 );
		addObject( player.addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 10.0f ) ) );

		entity = new Entity( new Mesh( "sphere.obj" ) );
		entity.getTransform().getPos().set( 0, 30, 0 );
		entity.getTransform().setScale( new Vector3f( 1, 1, 1 ) );
		entity.addCollider( new SphereCollider( 1 ) );
//
		Entity animals = new Entity( new Mesh( "sphere.obj" ) );
		animals.getTransform().getPos().set( 0.5f, 5, 0 );
		animals.addCollider( new SphereCollider( 1 ) );
		addObject( animals );

		/**
		 * Plane
		 */
		float fieldDepth = 1.0f;
		float fieldWidth = 1.0f;

		Vector3f[] vertices = new Vector3f[]
				{
						new Vector3f( -fieldWidth, 0.0f, -fieldDepth, new Color( 1.0f, 0.0f, 0.0f ) ),
						new Vector3f( -fieldWidth, 0.0f, fieldDepth, new Color( 0.0f, 1.0f, 0.0f ) ),
						new Vector3f( fieldWidth, 0.0f, -fieldDepth, new Color( 0.0f, 0.0f, 1.0f ) ),
						new Vector3f( fieldWidth, 0.0f, fieldDepth, new Color( 1.0f, 1.0f, 0.0f ) )
				};
		Triangle[] triangles = new Triangle[]
				{
						new Triangle( vertices[0], vertices[1], vertices[2] ),
						new Triangle( vertices[2], vertices[1], vertices[3] )
				};


		Mesh mesh = new Mesh( triangles );
		Material material = new Material();
		material.addFloat( "specularPower", 4f );
		material.addFloat( "specularIntensity", 0.85f );
		mesh.setMaterial( material );

		Planet planet1 = new Planet();

		PhysicObject planeObject = new PhysicObject();
		planeObject.addComponent( mesh );
		planeObject.getTransform().setScale( new Vector3f( 100, 100, 100 ) );
		planeObject.addCollider( new PlanCollider( new Vector3f( 0, 1, 0 ), 0 ) );
		planeObject.setPhysicalProperties( new PhysicalProperties().addProperty( PhysicalProperties.RESTITUTION_COEFFICIENT, 0.95f ) );
		addObject( planeObject );

		/**
		 * Light
		 */
		PointLight pointLight = new PointLight( new Color( 255, 205, 73 ), 0.2f, new Attenuation( 0, 0, 0.1f ) );
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent( pointLight );
		pointLightObject.getTransform().translate( 10, 13, 10 );
		pointLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 223, 70 ), 0.05f );
		directionalLightObject.addComponent( directionalLight );
		directionalLightObject.getTransform().getPos().set( 0, 2, 0 );
		directionalLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		GameObject spotLightObject = new GameObject();
		SpotLight spotLight = new SpotLight( new Color( 255, 255, 255 ), 1.2f, new Attenuation( 0, 0, 0.1f ), 0.7f );
		spotLightObject.addComponent( spotLight );
		spotLightObject.getTransform().getPos().set( 5, 1, 5 );
		spotLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		/**
		 * Object
		 */
		PhysicObject planet = new PhysicObject();
		planet.addComponent( new Sphere( 10, 10 ).getMesh() );
		planet.getTransform().setPos( new Vector3f( 10, 30, 0 ) );
		planet.addCollider( new SphereCollider( 10 ) );
		planet.setPhysicalProperties( new PhysicalProperties() );

		dirt = new Tessellator( 256, 10f, new Color( 255, 237, 117 ), 40 );
		dirt.getTransform().translate( 0, -3, 0 );
		dirt.getTransform().getScale().set( 100, 100, 100 );
		dirt.getTransform().setPos( new Vector3f( 0, 5, 0 ) );

//		addObject( entity );
		addObject( dirt );
		addObject( planet );
		addObject( directionalLightObject );
		addObject( pointLightObject );
		addObject( spotLightObject );
		addObject( player );

		directionalLight.getTransform().setRot( new Quaternion( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( -45 ) ) );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
//		System.out.println(entity.getTransform().getPos());
//		if ( dirt.getModel() != null )
//			System.out.println( Intersect.gameObject( player, dirt ) );
	}
}
