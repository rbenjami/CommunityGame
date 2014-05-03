package com.engine.core.components;

import com.engine.core.Attenuation;
import com.engine.core.Shader;
import com.engine.core.helpers.dimensions.Vector3f;

import java.awt.*;

/**
 * Created on 30/04/14.
 */
public class SpotLight extends PointLight
{
	private float cutoff;

	public SpotLight( Color color, float intensity, Attenuation attenuation, float cutoff )
	{
		super( color, intensity, attenuation );
		this.cutoff = cutoff;

		setShader( new Shader( "spot" ) );
	}

	public Vector3f getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}

	public float getCutoff()
	{
		return cutoff;
	}

	public void setCutoff( float cutoff )
	{
		this.cutoff = cutoff;
	}
}
