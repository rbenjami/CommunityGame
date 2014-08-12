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

package com.engine;

import com.engine.core.Game;
import com.engine.core.Input;
import com.engine.core.helpers.TimeHelper;
import com.engine.physic.PhysicsEngine;
import com.engine.render.RenderEngine;
import com.engine.render.Window;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.*;

/**
 * The basic framework for use with my LWJGL tutorials.
 *
 * @author Sri Harsha Chilakapati
 */
public class CoreEngine
{
	private boolean       isRunning;
	private Game          game;
	private RenderEngine  renderEngine;
	private PhysicsEngine physicsEngine;
	private int           width;
	private int           height;
	private double        frameTime;

	public CoreEngine( int width, int height, double framerate, Game game )
	{
		this.isRunning = false;
		this.game = game;
		this.width = width;
		this.height = height;
		this.frameTime = 1.0 / framerate;
		game.setEngine( this );
	}

	public void createWindow( String title )
	{
		Window.createWindow( width, height, title );
		this.renderEngine = new RenderEngine();
		this.physicsEngine = new PhysicsEngine();
	}

	public void start()
	{
		if ( isRunning )
			return;

		run();
	}

	private void run()
	{
		isRunning = true;

		int frames = 0;
		long frameCounter = 0;

		game.init();

		double lastTime = TimeHelper.getTime();
		double unprocessedTime = 0;

		while ( isRunning )
		{
			if ( Display.wasResized() )
			{
				reshape( Display.getWidth(), Display.getHeight() );
			}
			boolean render = false;

			double startTime = TimeHelper.getTime();
			double passedTime = startTime - lastTime;
			lastTime = startTime;

			unprocessedTime += passedTime;
			frameCounter += passedTime;

			while ( unprocessedTime > frameTime )
			{
				render = true;

				unprocessedTime -= frameTime;

				if ( Window.isCloseRequested() )
					stop();

				game.input( (float) frameTime );
				Input.update();
				debug();

				game.update( (float) frameTime );

				game.physic( physicsEngine, (float) frameTime );

				if ( frameCounter >= 1.0 )
				{
					System.out.println( frames );
					frames = 0;
					frameCounter = 0;
				}
			}
			if ( render )
			{
				game.render( renderEngine );
				Window.render();
				frames++;
			}
			else
			{
				try
				{
					Thread.sleep( 1 );
				}
				catch ( InterruptedException e )
				{
					e.printStackTrace();
				}
			}
		}

		cleanUp();
	}

	protected void reshape( int width, int height )
	{
		glViewport( 0, 0, width, height );
		renderEngine.getCamera().resizeProjection( width, height );
	}

	public void stop()
	{
		if ( !isRunning )
			return;

		isRunning = false;
	}

	private void debug()
	{
		if ( Input.getKey( Input.KEY_F5 ) )
			glPolygonMode( GL_FRONT_AND_BACK, GL_LINE );
		else if ( Input.getKey( Input.KEY_F6 ) )
			glPolygonMode( GL_FRONT_AND_BACK, GL_POINT );
		else
			glPolygonMode( GL_FRONT_AND_BACK, GL_FILL );
	}

	private void cleanUp()
	{
		Window.dispose();
	}

	public RenderEngine getRenderEngine()
	{
		return renderEngine;
	}

	public PhysicsEngine getPhysicsEngine()
	{
		return physicsEngine;
	}

	public Game getGame()
	{
		return game;
	}
}