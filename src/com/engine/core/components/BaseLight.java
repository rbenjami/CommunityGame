package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.render.Shader;

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

	public void setColor( Color color )
	{
		this.color = color;
	}

	public void setIntensity( float intensity )
	{
		this.intensity = intensity;
	}

	public Matrix4f getDepthMVP()
	{
		Matrix4f depthProjectionMatrix = new Matrix4f().initOrthographic( -10, 10, -10, 10, -10, 20 );
		Matrix4f worldMatrix = getTransform().getTransformation();

		return depthProjectionMatrix.mul( worldMatrix );
	}

	public Shader getShader()
	{
		return shader;
	}

	public Color getColor()
	{
		return color;
	}

	public float getIntensity()
	{
		return intensity;
	}
}