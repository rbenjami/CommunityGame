package com.game;

import com.engine.core.*;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.Window;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	public void init()
	{
		/**
		 * Plane
		 */
		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vector3f[] vertices = new Vector3f[]
				{
						new Vector3f( -fieldWidth, 0.0f, -fieldDepth, new Color( 1.0f, 0.0f, 0.0f ) ),
						new Vector3f( -fieldWidth, 0.0f, fieldDepth * 3, new Color( 0.0f, 1.0f, 0.0f ) ),
						new Vector3f( fieldWidth * 3, 0.0f, -fieldDepth, new Color( 1.0f, 0.0f, 1.0f ) ),
						new Vector3f( fieldWidth * 3, 0.0f, fieldDepth * 3, new Color( 1.0f, 1.0f, 0.0f ) )
				};

		int indices[] = { 0, 1, 2,
				2, 1, 3 };

		Mesh mesh = new Mesh( vertices, indices );

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
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 243, 149 ), 0.6f );
		directionalLightObject.addComponent( directionalLight );

		/**
		 * Object
		 */
		GameObject planet = new GameObject();
		planet.addComponent( new GeoSphere( 5, 5 ).getMesh() );
		planet.getTransform().translate( 0, 10, 0 );

//		Tessellator tessellator = new Tessellator( 128, 10f, new Color( 255, 237, 117 ), 40 );
//		tessellator.calculateTesselator();
//		tessellator.getMesh().setMaterial( Material.ROCK );
//		GameObject dirt = new GameObject();
//		dirt.addComponent( tessellator.getMesh() );
//		dirt.getTransform().translate( 0, -3, 0 );
//		dirt.getTransform().getScale().set( 100, 100, 100 );

//		addObject( dirt );
		addObject( planet );
		addObject( directionalLightObject );
		addObject( pointLightObject );

		GameObject cam = new GameObject();
		cam.addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) );
		cam.getTransform().getPos().set( 10, 12, 2 );
		addObject( cam.addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 10.0f ) ) );

		directionalLight.getTransform().rotate( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( -25 ) );
	}
}
