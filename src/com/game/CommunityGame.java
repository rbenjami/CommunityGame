package com.game;

import com.engine.core.*;
import com.engine.core.components.Camera;
import com.engine.core.components.Cube;
import com.engine.core.components.FreeLook;
import com.engine.core.components.FreeMove;
import com.engine.core.dimensions_helpers.Vector3f;

import java.awt.*;

/**
 * Created on 27/04/14.
 */
public class CommunityGame extends Game
{
	public void init()
	{
		Cube cube = new Cube( new Color( 123, 180, 44 ) );
		Cube cube2 = new Cube( new Color( 150, 107, 73 ) );
		Cube plan = new Cube( new Color( 95, 101, 179 ) );

		GameObject planeObject = new GameObject();
		planeObject.addComponent( plan );
		planeObject.getTransform().getPos().set( 0, 0, 5 );
		planeObject.getTransform().setScale( new Vector3f( 10, 0, 10 ) );
		addObject( planeObject );

		GameObject c = new GameObject().addComponent( cube );
		c.getTransform().getPos().set( 0, 5, 0 );
		GameObject c2 = new GameObject().addComponent( cube2 );
		c2.getTransform().getPos().set( 5, 0, 0 );

		addObject( c );
		addObject( c2 );

		addObject( new GameObject().addComponent( new FreeLook( 0.5f ) ).addComponent( new FreeMove( 0.01f ) ).addComponent( new Camera() ) );
	}
}
