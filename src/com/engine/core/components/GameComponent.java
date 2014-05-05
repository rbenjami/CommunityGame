package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.GameObject;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

/**
 * Created on 27/04/14.
 */
public abstract class GameComponent
{
	private GameObject parent;

	public void input( float delta ) {}

	public void update( float delta ) {}

	public void render( Shader shader, RenderEngine renderingEngine ) {}

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

