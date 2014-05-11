package com.engine.core;

/**
 * Created on 05/05/2014.
 */
public class Material extends MappedValues
{
	private boolean isTransparent = false;

	public Material()
	{
		super();
		addFloat( "specularIntensity", 0.8f );
		addFloat( "specularPower", 1 );
	}

	public void setTransparent( boolean isTransparent )
	{
		this.isTransparent = isTransparent;
	}

	public boolean isTransparent()
	{
		return isTransparent;
	}
}