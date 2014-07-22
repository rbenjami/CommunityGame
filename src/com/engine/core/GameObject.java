package com.engine.core;

import com.engine.CoreEngine;
import com.engine.core.components.GameComponent;
import com.engine.core.components.Mesh;
import com.engine.core.helpers.AABB;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.core.helpers.dimensions.Vector3f;
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
	private Mesh      model;
	private Transform transform;
	private Material  material;
	private Vector3f  velocity;
	private float     rollAngle;
	private String    name;

	public GameObject()
	{
		children = new ArrayList<GameObject>();
		components = new ArrayList<GameComponent>();
		transform = new Transform();
		material = new Material();
		velocity = new Vector3f( 0, 0, 0 );
		rollAngle = 0;
		engine = null;
		model = null;
		name = "";
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

	public Transform getTransform()
	{
		return transform;
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial( Material material )
	{
		this.material = material;
	}

	public Vector3f getVelocity()
	{
		return velocity;
	}

	public void setVelocity( Vector3f velocity )
	{
		this.velocity = velocity;
	}

	public float getRollAngle()
	{
		return rollAngle;
	}

	public void setRollAngle( float rollAngle )
	{
		this.rollAngle = (float) Math.toRadians( rollAngle );
	}

	public AABB getAxisAlignedBoundingBox()
	{
		return getModel().getAxisAlignedBoundingBox();
	}

	public Mesh getModel()
	{
		return model;
	}

	public void setModel( Mesh model )
	{
		this.model = model;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
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
