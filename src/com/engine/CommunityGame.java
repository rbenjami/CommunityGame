package com.engine;

import com.engine.core.Mesh;
import com.engine.core.Vertex3f;

import java.awt.Color;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class CommunityGame extends Game
{
	Vertex3f[] verticles;
	Mesh mesh;
	Shaders shaders;
	int[] indices;

	/**
	 * Initialize the resources
	 */
	public void init()
	{
		glClearColor( 0.0f, 0.0f, 0.0f, 0.0f );

		glFrontFace( GL_CW );
		glCullFace( GL_BACK );
		glEnable( GL_CULL_FACE );
		glEnable( GL_DEPTH_TEST );

		glEnable( GL_DEPTH_CLAMP );

		glEnable( GL_TEXTURE_2D );

		verticles = new Vertex3f[]{
				new Vertex3f( -0.25f,  -0.25f, -1.25f, new Color( 255, 0, 0 ) ),
				new Vertex3f( -0.25f, 0.25f, -1.25f, new Color( 241, 235, 0 ) ),
				new Vertex3f( 0.25f,  0.25f, -1.25f, new Color( 0, 250, 250 ) ),
				new Vertex3f( 0.25f, -0.25f, -2.5f, new Color( 250, 0, 250 ) ),
//				new Vertex3f( 1f, 0.5f, -0.5f, new Color( 0, 250, 0 ) ),
//				new Vertex3f( 1f, -0.5f, -0.5f, new Color( 250, 250, 250 ) ),
		};

		indices = new int[]{
				0, 1, 2,
				2, 3, 0,
//				3, 2, 4,
//				4, 5, 3
		};

		mesh = new Mesh( verticles, indices );
		shaders = new Shaders();
		shaders.addShader( GL_FRAGMENT_SHADER, Shaders.loadShader( "shader.frag" ) );
		shaders.addShader( GL_VERTEX_SHADER, Shaders.loadShader( "shaderPerspective.vs" ) );
	}

	/**
	 * Update logic
	 */
	public void update( long elapsedTime )
	{

	}

	/**
	 * Render to screen
	 */
	public void render()
	{
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		mesh.render( shaders );

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