package com.game;

import com.engine.core.Attenuation;
import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.components.*;
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
		Cube cube = new Cube( new Color( 123, 180, 44 ) );

//		GameObject c = new GameObject().addComponent( cube );
//		c.getTransform().getPos().set( 0, 5, 0 );

		PointLight pointLight2 = new PointLight( new Color( 255, 201, 62 ), 0.03f, new Attenuation( 10, 0, 0 ) );
		GameObject light2 = new GameObject().addComponent( pointLight2 );
		light2.getTransform().getPos().set( 0, 5, 5 );

		Test test = new Test( new Vector3f( 0, 0, 0 ) );
//		Test test2 = new Test( new Vector3f( 16, 0, 0 ) );

		addObject( test );
//		addObject( test2 );
//		addObject( c );
		addObject( light2 );

		addObject( new GameObject().addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 0.01f ) ).addComponent( new Camera() ) );
	}
}
