package com.game;

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.Tessellator;
import com.engine.core.components.*;
import com.engine.core.helpers.dimensions.Quaternion;
import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.core.helpers.dimensions.Vertex3f;
import com.engine.render.Window;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	public void init()
	{
//		float fieldDepth = 10.0f;
//		float fieldWidth = 10.0f;

//		Vertex3f[] vertices = new Vertex3f[]
//				{
//						new Vertex3f( new Vector3f( -fieldWidth, 0.0f, -fieldDepth ), new Color( 0.0f, 0.011764706f, 1.0f ) ),
//						new Vertex3f( new Vector3f( -fieldWidth, 0.0f, fieldDepth ), new Color( 1.0f, 0.79607844f, 0.0f ) ),
//						new Vertex3f( new Vector3f( fieldWidth, 0.0f, -fieldDepth ), new Color( 1.0f, 0.0f, 0.0f ) ),
//						new Vertex3f( new Vector3f( fieldWidth, 0.0f, fieldDepth ), new Color( 0.043137256f, 1.0f, 0.0f ) )
//				};
//
//		int indices[] = { 0, 1, 2,
//				2, 1, 3 };

//		Mesh plan = new Mesh( vertices, indices );

//		GameObject planeObject = new GameObject();
//		planeObject.addComponent( plan );
//		planeObject.getTransform().getPos().set( 0, -1, 0 );
//		planeObject.getTransform().getScale().set( 5, 5, 5 );
//		planeObject.getTransform().rotate( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( 1 ) );

//		Cube x = new Cube( new Color( 255, 0, 0 ) );
//		GameObject objectX = new GameObject().addComponent( x );
//		objectX.getTransform().getScale().set( 10f, 0.1f, 0.1f );
//		Cube y = new Cube( new Color( 0, 255, 0 ) );
//		GameObject objectY = new GameObject().addComponent( y );
//		objectY.getTransform().getScale().set( 0.1f, 10f, 0.1f );
//		Cube z = new Cube( new Color( 0, 0, 255 ) );
//		GameObject objectZ = new GameObject().addComponent( z );
//		objectZ.getTransform().getScale().set( 0.1f, 0.1f, 10f );

		PointLight pointLight = new PointLight( new Color( 255, 216, 54 ), 0.8f, new Attenuation( 0, 0, 0.1f ) );
		GameObject pointLightObject = new GameObject();
		pointLightObject.addComponent( pointLight );
		pointLight.getTransform().getPos().set( 6, 5, 6 );

//		SpotLight spotLight = new SpotLight(new Color( 255, 4, 0 ), 0.4f, new Attenuation(0,0,0.1f), 0.7f);
//		GameObject spotLightObject = new GameObject();
//		spotLightObject.addComponent( spotLight );
//		spotLightObject.getTransform().getPos().set( 0, 0, 0 );

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 218, 58 ), 0.4f );
		directionalLightObject.addComponent( directionalLight );
		directionalLightObject.getTransform().getPos().set( 0, 2, 0 );
		directionalLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		Tessellator tessellator = new Tessellator( 64, 1.9f, new Color( 132, 255, 69 ) );
		tessellator.calculateTesselator();
		GameObject dirt = new GameObject();
		dirt.addComponent( tessellator.getMesh() );
		dirt.getTransform().getScale().set( 100, 100, 100 );

		Tessellator tessellator2 = new Tessellator( 64, 2.9f, new Color( 93, 92, 82 ) );
		tessellator2.calculateTesselator();
		GameObject rock = new GameObject();
		rock.addComponent( tessellator2.getMesh() );
		rock.getTransform().getScale().set( 100, 100, 100 );

		addObject( dirt );
		addObject( rock );
//		addObject( c );
//		addObject( planeObject );
		addObject( pointLightObject );
		addObject( directionalLightObject );
//		addObject( spotLightObject );
//		addObject( objectX );
//		addObject( objectY );
//		addObject( objectZ );

		addObject( new GameObject().addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 5.0f ) ).addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) ) );

		directionalLight.getTransform().setRot( new Quaternion( new Vector3f( 1, 0, 0 ), (float) Math.toRadians( -45 ) ) );
	}
}
