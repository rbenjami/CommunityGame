package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.GameObject;
import com.engine.core.RenderEngine;
import com.engine.core.Shaders;
import com.engine.core.Transform;

/**
 * Created on 27/04/14.
 */
public abstract class GameComponent
{
	private GameObject parent;

	public void input( float delta ) {}

	public void update( float delta ) {}

	public void render( Shaders shader, RenderEngine renderingEngine ) {}

	public void setParent( GameObject parent )
	{
		this.parent = parent;
	}

	public Transform getTransform()
	{
		return parent.getTransform();
	}

	public void addToEngine( CoreEngine engine ) {}
}

