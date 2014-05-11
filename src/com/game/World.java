package com.game;

import com.engine.core.Game;
import com.engine.core.helpers.NoiseHelper;

import java.util.ArrayList;

/**
 * Created on 10/05/2014.
 */
public class World
{
	private ArrayList<Chunk> chunkList;
	private Game             game;

	public World( Game game )
	{
		this.game = game;
		this.chunkList = new ArrayList<Chunk>();
		generateWorld();
	}

	private void generateWorld()
	{
		int x;
		int y;

		y = 0;
		while ( y < 1 )
		{
			x = 0;
			while ( x < 1 )
			{
				chunkList.add( new Chunk( x * 100, y * 100 ) );
				x++;
			}
			y++;
		}
		for ( Chunk chunk : chunkList )
			chunk.addToGame( this.game );
	}
}
