package com.engine.core.components;

import com.engine.core.Attenuation;
import com.engine.core.Shader;
import com.engine.core.helpers.ColorHelper;

import java.awt.*;

/**
 * Created on 29/04/14.
 */
public class PointLight extends BaseLight
{
	private static final int COLOR_DEPTH = 256;

	private Attenuation attenuation;
	private float       range;

	public PointLight( Color color, float intensity, Attenuation attenuation )
	{
		super( color, intensity );
		this.attenuation = attenuation;

		float a = attenuation.getExponent();
		float b = attenuation.getLinear();
		float c = attenuation.getConstant() - COLOR_DEPTH * getIntensity() * ColorHelper.max( color );

		this.range = (float) ( ( -b + Math.sqrt( b * b - 4 * a * c ) ) / ( 2 * a ) );

		setShader( new Shader( "point" ) );
	}

	public float getRange()
	{
		return range;
	}

	public void setRange( float range )
	{
		this.range = range;
	}

	public Attenuation getAttenuation()
	{
		return attenuation;
	}
}
