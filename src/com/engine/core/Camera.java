package com.engine.core;

import com.engine.Window;
import org.lwjgl.input.Keyboard;


/**
 * Created on 16/04/14.
 */
public class Camera extends GameObject
{
	private static final Vector3f yAxis = new Vector3f( 0, 1, 0 );

	private Matrix4f projection;

	private boolean mouseLocked = false;
	private float sensitivity;
	private int   unlockMouseKey;
	private float speed;

	public Camera()
	{
		this.speed = 0.01f;
		this.sensitivity = 0.3f;
		this.unlockMouseKey = Input.KEY_ESCAPE;
		this.projection = new Matrix4f().initPerspective( (float) Math.toRadians( 70.0f ), (float) Window.getWidth() / (float) Window.getHeight(), 0.5f, 10 );
	}

	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul( -1 );
		Matrix4f cameraTranslation = new Matrix4f().initTranslation( cameraPos.getX(), cameraPos.getY(), cameraPos.getZ() );

		return projection.mul( cameraRotation.mul( cameraTranslation ) );
	}

	public void input( float delta )
	{
		float movAmt = speed * delta;

		if ( Input.getKey( Keyboard.KEY_W ) )
			move( getTransform().getRot().getForward(), -movAmt );
		if ( Input.getKey( Keyboard.KEY_S ) )
			move( getTransform().getRot().getForward(), movAmt );
		if ( Input.getKey( Keyboard.KEY_A ) )
			move( getTransform().getRot().getLeft(), movAmt );
		if ( Input.getKey( Keyboard.KEY_D ) )
			move( getTransform().getRot().getRight(), movAmt );
		if ( Input.getKey( Keyboard.KEY_LSHIFT ) )
			move( getTransform().getRot().getDown(), movAmt );
		if ( Input.getKey( Keyboard.KEY_SPACE ) )
			move( getTransform().getRot().getUp(), movAmt );

		System.out.println( getTransform().getPos() );

		Vector2f centerPosition = new Vector2f( Window.getWidth() / 2, Window.getHeight() / 2 );

		if ( Input.getKey( unlockMouseKey ) )
		{
			Input.setCursor( true );
			mouseLocked = false;
		}
		if ( Input.getMouseDown( 0 ) )
		{
			Input.setMousePosition( centerPosition );
			Input.setCursor( false );
			mouseLocked = true;
		}

		if ( mouseLocked )
		{
			Vector2f deltaPos = Input.getMousePosition().sub( centerPosition );

			boolean rotY = deltaPos.getX() != 0;
			boolean rotX = deltaPos.getY() != 0;

			if ( rotY )
				getTransform().rotate( yAxis, (float) Math.toRadians( -deltaPos.getX() * sensitivity ) );
			if ( rotX )
				getTransform().rotate( getTransform().getRot().getRight(), (float) Math.toRadians( deltaPos.getY() * sensitivity ) );

			if ( rotY || rotX )
				Input.setMousePosition( centerPosition );
		}
	}

	private void move( Vector3f dir, float amt )
	{
		getTransform().setPos( getTransform().getPos().add( dir.mul( amt ) ) );
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
