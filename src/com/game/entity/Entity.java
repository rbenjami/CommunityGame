package com.game.entity;

import com.engine.core.GameObject;
import com.engine.core.components.Mesh;

/**
 * Created on 17/07/14.
 */
public class Entity extends GameObject
{
	private Mesh model;

	public Entity( Mesh model )
	{
		this.model = model;
		this.addComponent( model );
	}

	/**
	 * GETTER
	 */
	public Mesh getModel()
	{
		return model;
	}
}
