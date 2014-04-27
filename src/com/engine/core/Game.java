package com.engine.core;

import com.engine.CoreEngine;

/**
 * Created on 27/04/14.
 */
public abstract class Game
{
	private GameObject root;

	public void init() {}

	public void input( float delta )
	{
		getRootObject().inputAll( delta );
	}

	public void update( float delta )
	{
		getRootObject().updateAll( delta );
	}

	public void render( RenderEngine renderingEngine )
	{
		renderingEngine.render( getRootObject() );
	}

	public void addObject( GameObject object )
	{
		getRootObject().addChild( object );
	}

	private GameObject getRootObject()
	{
		if ( root == null )
			root = new GameObject();

		return root;
	}

	public void setEngine( CoreEngine engine )
	{
		getRootObject().setEngine( engine );
	}
}