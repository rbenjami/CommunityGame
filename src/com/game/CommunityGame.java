package com.game;

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.Tessellator;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.geometry.Triangle;
import com.game.entity.Animals;
import com.game.entity.Entity;
import com.game.entity.Player;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	Player     player;
	Animals    animals;
	Entity     entity;
	GameObject planet;
	GameObject dirt;

	public void init()
	{
		/**
		 * Entity
		 */
		player = new Player( new Mesh( "monkey.obj" ) );
		player.getTransform().getPos().set( 10, 60, 2 );
		addObject( player.addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 10.0f ) ) );

		animals = new Animals( new Mesh( "monkey.obj" ) );
		animals.getTransform().getPos().set( 20, 10, 2 );
		entity = new Entity( new Mesh( "monkey.obj" ) );
		entity.getTransform().getPos().set( 10, 120, 2 );
//		Mesh mesh = new Mesh( "monkey.obj" );
//		mesh.getTransform().setPos( new Vector3f( 2, 0, 0 ) );
//		entity.addComponent( mesh );

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
						new Triangle( vertices[0], vertices[1], vertices[2] ),
						new Triangle( vertices[2], vertices[1], vertices[3] )
				};


		Mesh mesh = new Mesh( triangles );

		GameObject planeObject = new GameObject();
		planeObject.addComponent( mesh );
		planeObject.getTransform().translate( 0, -1, 0 );
		addObject( planeObject );

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

		dirt = new Tessellator( 64, 10f, new Color( 255, 237, 117 ), 40 );
		dirt.getTransform().translate( 0, -3, 0 );
		dirt.getTransform().getScale().set( 100, 100, 100 );

		addObject( animals );
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
