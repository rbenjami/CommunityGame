package com.game.entity;

import com.engine.core.Material;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 18/07/14.
 */
public class Animals extends Entity
{
	public Animals( Mesh model )
	{
		super( model );
		Material material = new Material();
		material.addFloat( "gravity", 0 );
		material.addFloat( "mass", 0 );
		material.addFloat( "dragCoefficient", 0.47f );
		material.addFloat( "surface", 1f );
		setMaterial( material );
		setName( "" );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		setVelocity( new Vector3f( 0, 0, 0 ) );
	}
}
