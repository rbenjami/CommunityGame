package com.engine.core.components;

import com.engine.core.Input;
import com.engine.core.helpers.dimensions.Vector3f;

public class FreeMove extends GameComponent
{
	private float speed;
	private int   forwardKey;
	private int   backKey;
	private int   leftKey;
	private int   rightKey;
	private int   lShiftKey;
	private int   spaceKey;

	public FreeMove( float speed )
	{
		this( speed, Input.KEY_W, Input.KEY_S, Input.KEY_A, Input.KEY_D, Input.KEY_LSHIFT, Input.KEY_SPACE );
	}

	public FreeMove( float speed, int forwardKey, int backKey, int leftKey, int rightKey, int lShiftKey, int spaceKey )
	{
		this.speed = speed;
		this.forwardKey = forwardKey;
		this.backKey = backKey;
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.lShiftKey = lShiftKey;
		this.spaceKey = spaceKey;
	}

	@Override
	public void input( float delta )
	{
		float movAmt = speed * delta;

		if ( Input.getKey( forwardKey ) )
			move( getTransform().getRot().getForward(), movAmt );
		if ( Input.getKey( backKey ) )
			move( getTransform().getRot().getForward(), -movAmt );
		if ( Input.getKey( leftKey ) )
			move( getTransform().getRot().getLeft(), movAmt );
		if ( Input.getKey( rightKey ) )
			move( getTransform().getRot().getRight(), movAmt );
		if ( Input.getKey( lShiftKey ) )
			move( getTransform().getRot().getDown(), movAmt );
		if ( Input.getKey( spaceKey ) )
			move( getTransform().getRot().getUp(), movAmt );
	}

	private void move( Vector3f dir, float amt )
	{
		getTransform().setPos( getTransform().getPos().add( dir.mul( amt ) ) );
	}
}
