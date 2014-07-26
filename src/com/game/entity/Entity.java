package com.game.entity;

import com.engine.core.GameObject;
import com.engine.core.Material;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.dimensions.Quaternion;
import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 17/07/14.
 */
public class Entity extends GameObject
{
	public Entity( Mesh model )
	{
		this.setModel( model );
		this.addComponent( model );
		Material material = new Material();
		material.addFloat( "gravity", 1 );
		material.addFloat( "mass", 1 );
		material.addFloat( "dragCoefficient", 0.47f );
		material.addFloat( "surface", 0.5f );
		setName( "Roger" );
		setMaterial( material );
	}

	@Override
	public void update( float delta )
	{
		super.update( delta );
		if ( !getVelocity().equals( Vector3f.NULL ) )
		{
			getTransform().translate( getVelocity() );
			getTransform().lookAt( getTransform().getPos().add( getVelocity() ), new Vector3f( 0, 1, 0 ).rotate( new Quaternion( getVelocity().normalized(), getRollAngle() ) ) );
		}
	}

	/**
	 * GETTER
	 */

	/**
	 * SETTER
	 */
}
