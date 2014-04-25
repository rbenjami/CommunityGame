package com.engine;

import com.engine.core.Input;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;

import static org.lwjgl.opengl.GL11.glViewport;

/**
 * The basic framework for use with my LWJGL tutorials.
 *
 * @author Sri Harsha Chilakapati
 */
public class Game
{
	// Single instance is allowed
	private static Game instance;
	// Delta time
	private static long delta;
	private static long elapsedTime;

	/**
	 * A basic game.
	 */
	public Game()
	{
		instance = this;
		Window.create( 850, 550 );
		gameLoop();
	}

	// The gameloop. Runs at 60 fps
	private void gameLoop()
	{
		long lastFrame = getCurrentTime();
		long thisFrame = getCurrentTime();
		long startTime = getCurrentTime();

		init();

		while ( !Window.isCloseRequested() )
		{
			elapsedTime = (long) ( ( System.nanoTime() - startTime ) / 1000000.0 );
			thisFrame = getCurrentTime();

			delta = thisFrame - lastFrame;

			update( delta );
			render();

			if ( Display.wasResized() )
			{
				resized();
			}

			Display.sync( 60 );
//			System.out.println( "FPS: " + ( thisFrame - lastFrame ) );

			Input.update();
			Display.update();

			if ( Display.wasResized() )
			{
				reshape( Window.getWidth(), Window.getHeight() );
			}
			lastFrame = thisFrame;
		}

		end();
	}

	protected void reshape( int width, int height )
	{
		glViewport( 0, 0, width, height );
	}

	/**
	 * @return Current time in milliseconds.
	 */
	public static long getCurrentTime()
	{
		return Sys.getTime() * 1000 / Sys.getTimerResolution();
	}

	protected final float getElapsedTime()
	{
		return elapsedTime;
	}

	/**
	 * Properly terminate the game.
	 */
	public static void end()
	{
		instance.dispose();
		instance = null;
		Window.dispose();
		System.exit( 0 );
	}

	/**
	 * Load any resources here.
	 */
	public void init()
	{
	}

	/**
	 * Update the logic of the game.
	 *
	 * @param elapsedTime Time elapsed since last frame.
	 */
	public void update( long elapsedTime )
	{
	}

	/**
	 * Render to screen.
	 */
	public void render()
	{
	}

	/**
	 * Display is resized
	 */
	public void resized()
	{
	}

	/**
	 * Dispose created resources.
	 */
	public void dispose()
	{
	}

}