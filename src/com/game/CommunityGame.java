package com.game;

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.Window;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Quaternion;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.dimensions.Vertex3f;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	public void init()
	{

		float fieldDepth = 10.0f;
		float fieldWidth = 10.0f;

		Vertex3f[] vertices = new Vertex3f[]{
				new Vertex3f( new Vector3f( -fieldWidth, 0.0f, -fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
				new Vertex3f( new Vector3f( -fieldWidth, 0.0f, fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
				new Vertex3f( new Vector3f( fieldWidth, 0.0f, -fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
				new Vertex3f( new Vector3f( fieldWidth, 0.0f, fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) )
		};

		int indices[] = { 0, 1, 2,
				2, 1, 3 };

		Mesh pok = new Mesh( vertices, indices );

		GameObject planeObject = new GameObject();
		planeObject.addComponent( pok );
		planeObject.getTransform().getPos().set( 0, 0, 0 );
		planeObject.getTransform().getScale().set( 10, 10, 10 );
		planeObject.getTransform().rotate( new Vector3f( 1, 0, 0 ), (float)Math.toRadians( 1 ) );

		Cube cube = new Cube( new Color( 123, 180, 44 ) );

//		GameObject c = new GameObject().addComponent( cube );
//		c.getTransform().getPos().set( 0, 5, 0 );

		PointLight pointLight2 = new SpotLight( new Color( 255, 201, 62 ), 0.006f, new Attenuation( 1, 0, 0 ), 0.8f );
		GameObject light2 = new GameObject().addComponent( pointLight2 );
		light2.getTransform().getPos().set( 0, 10, -32 );

		Test test = new Test( new Vector3f( 0, -1, 0 ) );
//		test.getTransform().rotate( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( 0 ) );
//		Test test2 = new Test( new Vector3f( 16, 0, 0 ) );

		addObject( test );
//		addObject( test2 );
//		addObject( c );
//		addObject( planeObject );
		addObject( light2 );

        addObject(new GameObject().addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 20.0f ) ).addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) ) );


//		float fieldDepth = 10.0f;
//		float fieldWidth = 10.0f;
//
//		Vertex3f[] vertices = new Vertex3f[]{
//				new Vertex3f( new Vector3f( -fieldWidth, 0.0f, -fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( -fieldWidth, 0.0f, fieldDepth * 3 ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( fieldWidth * 3, 0.0f, -fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( fieldWidth * 3, 0.0f, fieldDepth * 3 ), new Color( 1.0f, 0.0f, 0.0f ) )
//		};
//
//		int indices[] = { 0, 1, 2,
//				2, 1, 3 };
//
//		Vertex3f[] vertices2 = new Vertex3f[]{
//				new Vertex3f( new Vector3f( -fieldWidth / 10, 0.0f, -fieldDepth / 10 ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( -fieldWidth / 10, 0.0f, fieldDepth / 10 * 3 ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( fieldWidth / 10 * 3, 0.0f, -fieldDepth / 10 ), new Color( 1.0f, 0.0f, 0.0f ) ),
//				new Vertex3f( new Vector3f( fieldWidth / 10 * 3, 0.0f, fieldDepth / 10 * 3 ), new Color( 1.0f, 0.0f, 0.0f ) )
//		};
//
//		int indices2[] = { 0, 1, 2,
//				2, 1, 3 };
//
//		Mesh mesh2 = new Mesh( vertices2, indices2, true );
//
//		Mesh mesh = new Mesh( vertices, indices, true );
//
//		GameObject planeObject = new GameObject();
//		planeObject.addComponent( mesh );
//		planeObject.getTransform().getPos().set( 0, -10, 5 );
//
//		GameObject pointLightObject = new GameObject();
//		pointLightObject.addComponent( new PointLight( new Color( 0, 255, 0 ), 0.4f, new Attenuation( 0, 0, 1 ) ) );
//
//		SpotLight spotLight = new SpotLight( new Color( 0, 255, 255 ), 0.4f,
//											 new Attenuation( 0, 0, 0.1f ), 0.7f );
//
//		GameObject spotLightObject = new GameObject();
//		spotLightObject.addComponent( spotLight );
//
//		spotLightObject.getTransform().getPos().set( 5, 0, 5 );
//		spotLightObject.getTransform().setRot( new Quaternion( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90.0f ) ) );
//
//		addObject( planeObject );
//		addObject( pointLightObject );
//		addObject( spotLightObject );
//
//
//		GameObject testMesh1 = new GameObject().addComponent( mesh2 );
//		GameObject testMesh2 = new GameObject().addComponent( mesh2 );
//
//		testMesh1.getTransform().getPos().set( 0, 2, 0 );
//		testMesh1.getTransform().setRot( new Quaternion( new Vector3f( 0, 1, 0 ), 0.4f ) );
//
//		testMesh2.getTransform().getPos().set( 0, 0, 5 );
//
//		testMesh1.addChild( testMesh2 );
////		testMesh2.addChild(
//		addObject(
//				new GameObject().addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 20.0f ) ).addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) ) );
//
//		addObject( testMesh1 );
	}
}
