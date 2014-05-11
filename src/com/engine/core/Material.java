package com.engine.core;

/**
 * Created on 05/05/2014.
 */
public class Material
{
	private float specularIntensity;
	private float specularPower;
	private float transparency;

	public Material( float specularIntensity, float specularPower , float transparency )
	{
		this.specularIntensity = specularIntensity;
		this.specularPower = specularPower;
		this.transparency = transparency;
	}

	public static final Material WATER = new Material( 1, 6, 0.5f);
	public static final Material ROCk = new Material( 1, 4, 1f);


	/**
	 * GETTER
	 */
	public boolean isTransparency()
	{
		return this.transparency < 1;
	}

	public float getSpecularIntensity()
	{
		return specularIntensity;
	}

	public float getSpecularPower()
	{
		return specularPower;
	}
}