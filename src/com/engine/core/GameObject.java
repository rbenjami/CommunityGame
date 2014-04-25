package com.engine.core;

import java.util.ArrayList;

import static org.lwjgl.opengl.GL20.glUniform4f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4;

/**
 * Created on 24/04/14.
 */
public class GameObject
{
	private Transform             transform;
	private ArrayList<GameObject> gameObjectsList;

	public GameObject()
	{
		transform = new Transform();
	}

	public void render( Shaders shaders )
	{
		shaders.bind();

		Matrix4f worldMatrix = transform.getTransformation();
		Matrix4f MVPMatrix = shaders.getCamera().getViewProjection().mul( worldMatrix );

		glUniform4f( shaders.getBaseColorUnif(), 1.0f, 1.0f, 1.0f, 1.0f );
		glUniformMatrix4( shaders.getModelToCameraMatrixUnif(), true, MVPMatrix.toFloatBuffer() );
		draw();
		shaders.unbind();
	}

	protected void update()
	{

	}

	protected void draw()
	{

	}

	/**
	 * GETTER
	 */
	public Transform getTransform()
	{
		return transform;
	}


	/**
	 * SETTER
	 */
}
