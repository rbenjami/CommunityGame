package com.engine.core.helpers;

import java.awt.*;

/**
 * Created on 29/04/14.
 */
public class ColorHelper
{
	public static float max( Color color )
	{
		return Math.max( color.getRed(), Math.max( color.getGreen(), color.getBlue() ) );
	}
}
