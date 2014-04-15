package com.engine;

import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

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
		try
		{
			instance = this;
			Display.setDisplayMode( new DisplayMode( 850, 550 ) );
			Display.create();
			Display.setVSyncEnabled( true );
			Display.setResizable( true );
			gameLoop();
		}
		catch ( LWJGLException e )
		{
			e.printStackTrace();
			System.exit( -1 );
		}
	}

	// The gameloop. Runs at 60 fps
	private void gameLoop()
	{
		long lastFrame = getCurrentTime();
		long thisFrame = getCurrentTime();
		long startTime = getCurrentTime();

		init();

		while ( !Display.isCloseRequested() )
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

			Display.update();

			if ( Display.wasResized() )
			{
				reshape( Display.getWidth(), Display.getHeight() );
			}
			lastFrame = thisFrame;
		}

		end();
	}

	protected void reshape( int width, int height )
	{
//		perspectiveMatrix[0] = fFrustumScale / (w / (float)h);
//		perspectiveMatrix[5] = fFrustumScale;
//
//		glUseProgram(theProgram);
//		glUniformMatrix4fv(perspectiveMatrixUnif, 1, GL_FALSE, perspectiveMatrix);
//		glUseProgram(0);
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
		Display.destroy();
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