package com.engine.core.helpers;

/**
 * Created on 01/05/2014.
 */
public class TimeHelper
{
	private static final long SECOND = 1000000000L;

	public static double getTime()
	{
		return (double) System.nanoTime() / (double) SECOND;
	}
}
