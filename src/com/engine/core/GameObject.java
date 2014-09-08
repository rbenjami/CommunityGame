/*
 * Copyright (C) 2014 Repingon Benjamin
 * This file is part of CommunityGame.
 * CommunityGame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * CommunityGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
 */

package com.engine.core;

import com.engine.CoreEngine;
import com.engine.core.components.GameComponent;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.physic.PhysicObject;
import com.engine.render.RenderEngine;
import com.engine.render.Shader;

import java.util.ArrayList;

/**
 * Created on 24/04/14.
 */
public class GameObject
{
	private ArrayList<GameObject>    children;
	private ArrayList<GameComponent> components;
	private CoreEngine               engine;
	private Transform                transform;

	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
	}

	public Transform getTransform()
	{
		return transform;
	}

	public void addChild( GameObject child )
	{
		children.add( child );
		child.setEngine( engine );
		child.getTransform().setParent( transform );
	}

	public GameObject addComponent( GameComponent component )
	{
		components.add( component );
		component.setParent( this );

		return this;
	}

	public void inputAll( float delta )
	{
		input( delta );

		for ( GameObject child : children )
			child.inputAll( delta );
	}

	public void updateAll( float delta )
	{
		update( delta );

		for ( GameObject child : children )
			child.updateAll( delta );
	}

	public void renderAll( Shader shader, RenderEngine renderEngine )
	{
		render( shader, renderEngine );

		for ( GameObject child : children )
			child.renderAll( shader, renderEngine );
	}

	public void input( float delta )
	{
		transform.update();

		for ( GameComponent component : components )
			component.input( delta );
	}

	public void update( float delta )
	{
		for ( GameComponent component : components )
			component.update( delta );
	}

	public void render( Shader shader, RenderEngine renderEngine )
	{
		for ( GameComponent component : components )
			component.render( shader, renderEngine );
	}

	/**
	 * GETTER
	 */
	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for ( GameObject child : children )
			result.addAll( child.getAllAttached() );

		result.add( this );
		return result;
	}

	public ArrayList<PhysicObject> getAllPhysicObjects()
	{
		ArrayList<PhysicObject> result = new ArrayList<PhysicObject>();

		for ( GameObject child : children )
			result.addAll( child.getAllPhysicObjects() );

		if ( this instanceof PhysicObject )
			result.add( (PhysicObject) this );
		return result;
	}

	/**
	 * SETTER
	 */
	public void setEngine( CoreEngine engine )
	{
		if ( this.engine != engine )
		{
			this.engine = engine;

			for ( GameComponent component : components )
				component.addToEngine( engine );

			for ( GameObject child : children )
				child.setEngine( engine );
		}
	}
}
