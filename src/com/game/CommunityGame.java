package com.game;

import com.engine.core.*;
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
	Tessellator tessellator3 = new Tessellator( 128, 0.02f, new Color( 85, 105, 255 ) );

	public void init()
	{
		NoiseHelper.setSeed( 0 );

//		SpotLight spotLight = new SpotLight( new Color( 255, 255, 255 ), 1.2f, new Attenuation( 0, 0, 0.1f ), 0.7f );
//		GameObject spotLightObject = new GameObject();
//		spotLightObject.addComponent( spotLight );
//		spotLightObject.getTransform().translate( -6, 0, 6 );
//		spotLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float)Math.toRadians( 90 ) );

		GameObject directionalLightObject = new GameObject();
		DirectionalLight directionalLight = new DirectionalLight( new Color( 255, 255, 255 ), 0.4f );
		directionalLightObject.addComponent( directionalLight );
		directionalLightObject.getTransform().getPos().set( 0, 2, 0 );
		directionalLightObject.getTransform().rotate( new Vector3f( 0, 1, 0 ), (float) Math.toRadians( 90 ) );

		Tessellator tessellator = new Tessellator( 128, 10f, new Color( 132, 255, 69 ) );
		tessellator.calculateTesselator();
		GameObject dirt = new GameObject();
		dirt.addComponent( tessellator.getMesh() );
		dirt.getTransform().translate( 0, 3, 0 );
		dirt.getTransform().getScale().set( 100, 100, 100 );


		Tessellator tessellator2 = new Tessellator( 128, 20f, new Color( 93, 92, 82 ) );
		tessellator2.calculateTesselator();
		GameObject rock = new GameObject();
		rock.addComponent( tessellator2.getMesh() );
		rock.getTransform().getScale().set( 100, 100, 100 );

		tessellator3.calculateTesselator();
		GameObject wather = new GameObject();
		tessellator3.getMesh().getMaterial().setTransparent( true );
		wather.addComponent( tessellator3.getMesh() );
		wather.getTransform().translate( 0, 8, 0 );
		wather.getTransform().getScale().set( 100, 100, 100 );

		addObject( dirt );
		addObject( rock );
		addObject( wather );
//		addObject( pointLightObject );
		addObject( directionalLightObject );
//		addObject( spotLightObject );

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
