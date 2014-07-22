package com.game.entity;

import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 18/07/14.
 */
public class Animals extends Entity
{
	private float test = 0;

	public Animals( Mesh model )
	{
		super( model );
		setName( "" );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		setVelocity( new Vector3f( 0, 0, 0 ) );
		test += 0.01f;
	}
}
