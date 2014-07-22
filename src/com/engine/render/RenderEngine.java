package com.engine.render;

import com.engine.core.GameObject;
import com.engine.core.MappedValues;
import com.engine.core.components.BaseLight;
import com.engine.core.components.Camera;
import com.engine.core.helpers.dimensions.Transform;
import com.engine.core.helpers.dimensions.Vector3f;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL32.GL_DEPTH_CLAMP;

public class RenderEngine extends MappedValues
{
	private ArrayList<BaseLight> lights;
	private BaseLight            activeLight;

	private Shader ambientShader;
	private Camera camera;

	/**
	 * Initialize the resources
	 */
	public RenderEngine()
	{
		super();
		lights = new ArrayList<BaseLight>();

		float ambient = 0.1f;
		addVector3f( "ambient", new Vector3f( ambient, ambient, ambient ) );

		ambientShader = new Shader( "ambient" );

		glClearColor( 0.4f, 0.4f, 0.4f, 0.0f );

		glFrontFace( GL_CW );
		glCullFace( GL_BACK );
		glEnable( GL_CULL_FACE );
		glEnable( GL_DEPTH_TEST );

		glEnable( GL_DEPTH_CLAMP );

		glEnable( GL_TEXTURE_2D );
	}

	/**
	 * Update logic
	 */
	public void update( long elapsedTime )
	{
		getCamera().input( elapsedTime );
	}

	public Camera getCamera()
	{
		return camera;
	}

	/**
	 * SETTER
	 */
	public void setCamera( Camera camera )
	{
		this.camera = camera;
	}

	/**
	 * Render to screen
	 */
	public void render( GameObject object )
	{
		glClear( GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT );

		object.renderAll( ambientShader, this );

		glEnable( GL_BLEND );
		glBlendFunc( GL_ONE, GL_ONE );
		glDepthMask( false );
		glDepthFunc( GL_EQUAL );

		for ( BaseLight light : lights )
		{
			activeLight = light;
			object.renderAll( light.getShader(), this );
		}

		glDepthFunc( GL_LESS );
		glDepthMask( true );
		glDisable( GL_BLEND );
	}

	/**
	 * Dispose the resources
	 */
	public void dispose()
	{

	}

	public void addCamera( Camera camera )
	{
		this.camera = camera;
	}

	public void addLight( BaseLight light )
	{
		lights.add( light );
	}

	public void updateUniformStruct( Transform transform, Shader shader, String uniformName, String uniformType )
	{
		throw new IllegalArgumentException( uniformType + " is not a supported type in RenderingEngine" );
	}

	/**
	 * GETTER
	 */
	public BaseLight getActiveLight()
	{
		return activeLight;
	}

}