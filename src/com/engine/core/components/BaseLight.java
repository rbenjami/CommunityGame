package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.Shader;

import java.awt.*;

/**
 * Created on 29/04/14.
 */
public class BaseLight extends GameComponent
{
	private Color  color;
	private float  intensity;
	private Shader shader;

	public BaseLight( Color color, float intensity )
	{
		this.color = color;
		this.intensity = intensity;
	}

	@Override
	public void addToEngine( CoreEngine engine )
	{
		engine.getRenderEngine().addLight( this );
	}

	public void setShader( Shader shader )
	{
		this.shader = shader;
	}

	public Shader getShader()
	{
		return shader;
	}

	public Color getColor()
	{
		return color;
	}

	public void setColor( Color color )
	{
		this.color = color;
	}

	public float getIntensity()
	{
		return intensity;
	}

	public void setIntensity( float intensity )
	{
		this.intensity = intensity;
	}
}