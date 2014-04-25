package com.engine.core;

/**
 * Created on 13/04/14.
 */
public class Color3f
{
	private float red;
	private float green;
	private float blue;

	public Color3f( float red, float green, float blue )
	{
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public Color3f( int red, int green, int blue )
	{
		if ( red <= 1 && green <= 1 && blue <= 1 )
		{
			this.red = red;
			this.green = green;
			this.blue = blue;
		}
		else
		{
			this.red = red / 256;
			this.green = green / 256;
			this.blue = blue / 256;
		}
	}

	public float max()
	{
		return Math.max( red, Math.max( green, blue ) );
	}

	public Color3f add( Color3f c )
	{
		float r = red + c.getRed();
		float g = green + c.getGreen();
		float b = blue + c.getBlue();

		if ( r > 1 )
			r = 1;
		if ( g > 1 )
			g = 1;
		if ( b > 1 )
			b = 1;
		return new Color3f( r, g, b );
	}

	/**
	 * GETTER
	 */
	public float getRed()
	{
		return red;
	}

	public float getGreen()
	{
		return green;
	}

	public float getBlue()
	{
		return blue;
	}


	/**
	 * SETTER
	 */
	public void setRed( float red )
	{
		this.red = red;
	}

	public void setGreen( float green )
	{
		this.green = green;
	}

	public void setBlue( float blue )
	{
		this.blue = blue;
	}
}
