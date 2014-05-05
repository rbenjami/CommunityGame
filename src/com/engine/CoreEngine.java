package com.engine;

import com.engine.core.Game;
import com.engine.core.Input;
import com.engine.core.helpers.TimeHelper;
import com.engine.render.RenderEngine;
import com.engine.render.Window;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.glViewport;

/**
 * The basic framework for use with my LWJGL tutorials.
 *
 * @author Sri Harsha Chilakapati
 */
public class CoreEngine
{
	private boolean      isRunning;
	private Game         game;
	private RenderEngine renderEngine;
	private int          width;
	private int          height;
	private double       frameTime;

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
	}

	public void start()
	{
		if ( isRunning )
			return;

		run();
	}

	public void stop()
	{
		if ( !isRunning )
			return;

		isRunning = false;
	}

	protected void reshape( int width, int height )
	{
		glViewport( 0, 0, width, height );
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

				game.update( (float) frameTime );

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

	private void cleanUp()
	{
		Window.dispose();
	}

	public RenderEngine getRenderEngine()
	{
		return renderEngine;
	}
}