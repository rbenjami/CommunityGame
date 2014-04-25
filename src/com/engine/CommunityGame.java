package com.engine;

import com.engine.core.*;

import java.awt.*;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class CommunityGame extends Game
{
	Vertex3f[] verticles;
	Cube       cube;
	Cube       cube2;
	Cube       plan;
	Shaders    shaders;
	int[]      indices;

	/**
	 * Initialize the resources
	 */
	public void init()
	{
		glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );

		glEnable( GL_CULL_FACE );
		glCullFace( GL_BACK );
		glFrontFace( GL_CW );

		glEnable( GL_DEPTH_TEST );
		glDepthMask( true );
		glDepthRange( 0.0f, 1.0f );
		glEnable( GL_DEPTH_CLAMP );

		cube = new Cube( new Vector3f( 2, 0, -5 ), new Quaternion( 0, 0f, 0, 1 ), new Vector3f( 1, 1, 1 ), new Color( 123, 180, 44 ) );
		cube2 = new Cube( new Vector3f( 0, 5, -5 ), new Quaternion( 0.5f, 0.5f, 0, 1 ), new Vector3f( 1, 1, 1 ), new Color( 150, 107, 73 ) );
		plan = new Cube( new Vector3f( 0, 0, 0 ), new Quaternion( 0, 0, 0, 1 ), new Vector3f( 10, 0, 10 ), new Color( 95, 101, 179 ) );

		shaders = new Shaders();
		shaders.setCamera( new Camera() );
		try
		{
			shaders.createProgram();
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}

	/**
	 * Update logic
	 */
	public void update( long elapsedTime )
	{
		shaders.getCamera().input( elapsedTime );
	}


	/**
	 * Render to screen
	 */
	public void render()
	{
		glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );
		glClearDepth( 1.0f );
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		cube.render( shaders );
		cube2.render( shaders );
		plan.render( shaders );

//		glEnable( GL_BLEND );
//		glBlendFunc( GL_ONE, GL_ONE );
//		glDepthMask( false );
//		glDepthFunc( GL_EQUAL );
//
////		for ( BaseLight light : lights )
////		{
////			activeLight = light;
////			object.renderAll( light.getShader(), this );
////		}
//
//		glDepthFunc( GL_LESS );
//		glDepthMask( true );
//		glDisable( GL_BLEND );
	}

	@Override
	protected void reshape( int width, int height )
	{
		FloatBuffer theMatrixBuffer = new Matrix4f().initPerspective( (float) Math.toRadians( 70.0f ), (float) width / (float) height, 0.5f, 10 ).toFloatBuffer();

		glUseProgram( shaders.getProgram() );
		glUniformMatrix4( shaders.getCameraToClipMatrixUnif(), false, theMatrixBuffer );
		glUseProgram( 0 );
		glViewport( 0, 0, width, height );
	}

	/**
	 * Dispose the resources
	 */
	public void dispose()
	{

	}

	public static void main( String[] args )
	{
		new CommunityGame();
	}

}