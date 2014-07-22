package com.game.entity;

import com.engine.core.components.Camera;
import com.engine.core.components.Mesh;
import com.engine.render.Window;

/**
 * Created on 17/07/14.
 */
public class Player extends Entity
{
	public Player( Mesh model )
	{
		super( model );
		setName( "" );
		this.addComponent( new Camera( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.01f, 1000.0f ) );
	}

	@Override
	public void update( float delta )
	{

	}
}
