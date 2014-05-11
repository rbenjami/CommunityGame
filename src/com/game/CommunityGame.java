package com.game;

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.components.*;
import com.engine.core.helpers.NoiseHelper;
import com.engine.core.helpers.dimensions.Quaternion;
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
		NoiseHelper.setSeed( 42 );

		World world = new World( this );

		PointLight pointLight = new PointLight( new Color( 255, 205, 73 ), 0.2f, new Attenuation( 0, 0, 0.1f ) );
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent( pointLight );
		pointLightObject.getTransform().translate( 10, 13, 10 );
		pointLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 241, 150 ), 0.6f );
		directionalLightObject.addComponent( directionalLight );
		directionalLightObject.getTransform().getPos().set( 0, 2, 0 );
		directionalLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

//		addObject( dirt );
//		addObject( rock );
//		addObject( water );
		addObject( directionalLightObject );
		addObject( pointLightObject );

		GameObject cam = new GameObject();
		cam.addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) );
		cam.getTransform().getPos().set( 10, 12, 2 );
		addObject( cam.addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 5.0f ) ) );

		directionalLight.getTransform().setRot( new Quaternion( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( -45 ) ) );
	}

	public void update( float delta )
	{
//		Vertex3f[] vertices = tessellator3.getMesh().getVertices();
//
//		for ( Vertex3f vertex : vertices )
//		{
//			vertex.setPos( vertex.getX(), vertex.getY(), vertex.getZ() );
//		}
//		tessellator3.getMesh().setVertices( vertices );
	}
}
