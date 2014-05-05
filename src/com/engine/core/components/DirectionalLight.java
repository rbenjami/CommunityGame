package com.engine.core.components;

import com.engine.core.helpers.dimensions.Vector3f;
import com.engine.render.Shader;

import java.awt.*;

/**
 * Created on 30/04/14.
 */
public class DirectionalLight extends BaseLight
{
	public DirectionalLight( Color color, float intensity )
	{
		super( color, intensity );

		setShader( new Shader( "directional" ) );
	}

	public Vector3f getDirection()
	{
		return getTransform().getTransformedRot().getForward();
	}
}
