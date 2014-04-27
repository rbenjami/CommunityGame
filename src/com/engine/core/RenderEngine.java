package com.engine.core;

import com.engine.core.components.Camera;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderEngine
{
	Shaders ambientShaders;
	Camera  camera;

	/**
	 * Initialize the resources
	 */
	public RenderEngine()
	{
		glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );

		glFrontFace( GL_CW );
		glCullFace( GL_BACK );
		glEnable( GL_CULL_FACE );
		glEnable( GL_DEPTH_TEST );

		glEnable( GL_DEPTH_CLAMP );

		glEnable( GL_TEXTURE_2D );

		ambientShaders = new Shaders( "ambient" );
	}

	/**
	 * Update logic
	 */
	public void update( long elapsedTime )
	{
		getCamera().input( elapsedTime );
	}


	/**
	 * Render to screen
	 */
	public void render( GameObject object )
	{
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		object.renderAll( ambientShaders, this );

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

//	@Override
//	protected void reshape( int width, int height )
//	{
//		FloatBuffer theMatrixBuffer = new Matrix4f().initPerspective( (float) Math.toRadians( 70.0f ), (float) width / (float) height, 0.5f, 10 ).toFloatBuffer();
//
//		glUseProgram( shaders.getProgram() );
//		glUniformMatrix4( shaders.getCameraToClipMatrixUnif(), false, theMatrixBuffer );
//		glUseProgram( 0 );
//		glViewport( 0, 0, width, height );
//	}

	/**
	 * Dispose the resources
	 */
	public void dispose()
	{

	}

	public void setCamera( Camera camera )
	{
		this.camera = camera;
	}

	public Camera getCamera()
	{
		return camera;
	}

	public void addCamera( Camera camera )
	{
		this.camera = camera;
	}
}