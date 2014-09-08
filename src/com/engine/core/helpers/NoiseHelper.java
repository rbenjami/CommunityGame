/*
 * Copyright (C) 2014 Repingon Benjamin
 * This file is part of CommunityGame.
 * CommunityGame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * CommunityGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
 */

package com.engine.core.helpers;

import java.util.Random;

/**
 * Created on 10/05/2014.
 */
public class NoiseHelper
{
	public static float[][] generatePerlinNoise( int width, int height, int octaveCount, long seed )
	{
		return generatePerlinNoise( generateWhiteNoise( width, height, seed ), octaveCount );
	}

	private static float[][] generatePerlinNoise( float[][] baseNoise, int octaveCount )
	{
		int width = baseNoise.length;
		int height = baseNoise[0].length;

		float[][][] smoothNoise = new float[octaveCount][][];

		float persistance = 0.5f;

		//generate smooth noise
		for ( int i = 0; i < octaveCount; i++ )
		{
			smoothNoise[i] = generateSmoothNoise( baseNoise, i );
		}

		float[][] perlinNoise = new float[width][height];
		float amplitude = 1.0f;
		float totalAmplitude = 0.0f;

		//blend noise together
		for ( int octave = octaveCount - 1; octave >= 0; octave-- )
		{
			amplitude *= persistance;
			totalAmplitude += amplitude;

			for ( int i = 0; i < width; i++ )
			{
				for ( int j = 0; j < height; j++ )
				{
					perlinNoise[i][j] += smoothNoise[octave][i][j] * amplitude;
				}
			}
		}

		for ( int i = 0; i < width; i++ )
		{
			for ( int j = 0; j < height; j++ )
			{
				perlinNoise[i][j] /= totalAmplitude;
			}
		}

		return perlinNoise;
	}

	private static float[][] generateWhiteNoise( int width, int height, long seed )
	{
		Random random = new Random( seed );
		float[][] noise = new float[width][height];

		for ( int i = 0; i < width; i++ )
		{
			for ( int j = 0; j < height; j++ )
				noise[i][j] = (float) random.nextDouble() % 1;
		}
		return noise;
	}

	private static float[][] generateSmoothNoise( float[][] baseNoise, int octave )
	{
		int width = baseNoise.length;
		int height = baseNoise[0].length;

		float[][] smoothNoise = new float[width][height];

		int samplePeriod = 1 << octave; // calculates 2 ^ k
		float sampleFrequency = 1.0f / samplePeriod;

		for ( int i = 0; i < width; i++ )
		{
			//calculate the horizontal sampling indices
			int sample_i0 = ( i / samplePeriod ) * samplePeriod;
			int sample_i1 = ( sample_i0 + samplePeriod ) % width; //wrap around
			float horizontal_blend = ( i - sample_i0 ) * sampleFrequency;

			for ( int j = 0; j < height; j++ )
			{
				//calculate the vertical sampling indices
				int sample_j0 = ( j / samplePeriod ) * samplePeriod;
				int sample_j1 = ( sample_j0 + samplePeriod ) % height; //wrap around
				float vertical_blend = ( j - sample_j0 ) * sampleFrequency;

				//blend the top two corners
				float top = interpolate( baseNoise[sample_i0][sample_j0], baseNoise[sample_i1][sample_j0], horizontal_blend );

				//blend the bottom two corners
				float bottom = interpolate( baseNoise[sample_i0][sample_j1], baseNoise[sample_i1][sample_j1], horizontal_blend );

				//final blend
				smoothNoise[i][j] = interpolate( top, bottom, vertical_blend );
			}
		}

		return smoothNoise;
	}

	private static float interpolate( float x0, float x1, float alpha )
	{
		return x0 * ( 1 - alpha ) + alpha * x1;
	}
}
