package com.engine.core;

import com.engine.CoreEngine;
import com.engine.core.components.GameComponent;

import java.util.ArrayList;

/**
 * Created on 24/04/14.
 */
public class GameObject
{
	private ArrayList<GameObject>    children;
	private ArrayList<GameComponent> components;
	private Transform                transform;
	private CoreEngine               engine;

	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		engine = null;
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

	public void renderAll( Shaders shaders, RenderEngine renderEngine )
	{
		render( shaders, renderEngine );

		for ( GameObject child : children )
			child.renderAll( shaders, renderEngine );
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

	public void render( Shaders shaders, RenderEngine renderEngine )
	{
		for ( GameComponent component : components )
			component.render( shaders, renderEngine );
	}

	public ArrayList<GameObject> getAllAttached()
	{
		ArrayList<GameObject> result = new ArrayList<GameObject>();

		for ( GameObject child : children )
			result.addAll( child.getAllAttached() );

		result.add( this );
		return result;
	}

	public Transform getTransform()
	{
		return transform;
	}

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
