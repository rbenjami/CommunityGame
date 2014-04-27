package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.Window;
import com.engine.core.dimensions_helpers.Matrix4f;
import com.engine.core.dimensions_helpers.Vector3f;


/**
 * Created on 16/04/14.
 */
public class Camera extends GameComponent
{
	private Matrix4f projection;

	public Camera()
	{
		this.projection = new Matrix4f().initPerspective( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.5f, 1000 );
	}

	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul( -1 );
		Matrix4f cameraTranslation = new Matrix4f().initTranslation( cameraPos.getX(), cameraPos.getY(), cameraPos.getZ() );

		System.out.println( getTransform().getPos() );
		return projection.mul( cameraRotation.mul( cameraTranslation ) );
	}

	@Override
	public void addToEngine( CoreEngine engine )
	{
		engine.getRenderEngine().addCamera( this );
	}


	/**
	 * GETTER
	 */
	public Vector3f getPos()
	{
		return getTransform().getPos();
	}

	/**
	 * SETTER
	 */
	public void setPos( Vector3f pos )
	{
		getTransform().setPos( pos );
	}
}
