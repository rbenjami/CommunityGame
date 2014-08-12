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

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.Tessellator;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;
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
	GameObject planet;
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
		entity.getTransform().setScale( new Vector3f( 1, 1, 1 ) );
		entity.getTransform().getPos().set( 5, 50, 10 );

		/**
		 * Plane
		 */
		float fieldDepth = 1.0f;
		float fieldWidth = 1.0f;

		Vector3f[] vertices = new Vector3f[]
				{
						new Vector3f( -fieldWidth, 0.0f, -fieldDepth, new Color( 1.0f, 0.0f, 0.0f ) ),
						new Vector3f( -fieldWidth, 0.0f, fieldDepth, new Color( 0.0f, 1.0f, 0.0f ) ),
						new Vector3f( fieldWidth, 0.0f, -fieldDepth, new Color( 1.0f, 0.0f, 1.0f ) ),
						new Vector3f( fieldWidth, 0.0f, fieldDepth, new Color( 1.0f, 1.0f, 0.0f ) )
				};
		Triangle[] triangles = new Triangle[]
				{
						new Triangle( vertices[0], vertices[1], vertices[2], 0 ),
						new Triangle( vertices[2], vertices[1], vertices[3], 1 )
				};


		Mesh mesh = new Mesh( triangles );

//		GameObject planeObject = new GameObject();
//		planeObject.addComponent( mesh );
//		planeObject.getTransform().translate( 0, -1, 0 );
//		addObject( planeObject );

		/**
		 * Light
		 */
		PointLight pointLight = new PointLight( new Color( 255, 205, 73 ), 0.2f, new Attenuation( 0, 0, 0.1f ) );
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent( pointLight );
		pointLightObject.getTransform().translate( -1, 0, 0 );
		pointLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 243, 149 ), 0.8f );
		directionalLightObject.addComponent( directionalLight );

		/**
		 * Object
		 */
//		planet = new GameObject();
//		planet.addComponent( new GeoSphere( 50, 7 ) );
//		planet.getTransform().translate( -50, 10, 0 );

		dirt = new Tessellator( 256, 10f, new Color( 255, 237, 117 ), 40 );
		dirt.getTransform().translate( 0, -3, 0 );
		dirt.getTransform().getScale().set( 100, 100, 100 );

		addObject( entity );
		addObject( dirt );
//		addObject( planet );
		addObject( directionalLightObject );
		addObject( pointLightObject );

		directionalLight.getTransform().rotate( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( -42 ) );
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
