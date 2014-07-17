package com.engine.core;

import com.engine.CoreEngine;
import com.engine.render.RenderEngine;
import com.game.entity.Entity;

import java.util.ArrayList;

/**
 * Created on 27/04/14.
 */
public abstract class Game
{
	private GameObject root;
	private ArrayList<Entity> entityList = new ArrayList<Entity>();

	public void init() {}

	public void input( float delta )
	{
		getRootObject().inputAll( delta );
	}

	private GameObject getRootObject()
	{
		if ( root == null )
			root = new GameObject();

		return root;
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

	public void addEntity( Entity entity )
	{
		root.addComponent( entity.getModel() );
		entityList.add( entity );
	}

	public void setEngine( CoreEngine engine )
	{
		getRootObject().setEngine( engine );
	}
}