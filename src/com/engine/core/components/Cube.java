package com.engine.core.components;

import com.engine.core.RenderEngine;
import com.engine.core.Shader;
import com.engine.core.helpers.dimensions.Vertex3f;

import java.awt.*;

/**
 * Created on 16/04/14.
 */
public class Cube extends GameComponent
{
	private Mesh mesh;

	public Cube( Color color )
	{
		Vertex3f[] verticles = new Vertex3f[]
				{
						new Vertex3f( 1, -1, 1, color ),
						new Vertex3f( 1, 1, 1, color ),
						new Vertex3f( -1, -1, 1, color ),
						new Vertex3f( -1, 1, 1, color ),
						new Vertex3f( -1, 1, -1, color ),
						new Vertex3f( -1, -1, -1, color ),
						new Vertex3f( 1, -1, -1, color ),
						new Vertex3f( 1, 1, -1, color )
				};

		int[] indices = new int[]
				{
						0, 1, 2,
						2, 1, 3,
						3, 4, 2,
						2, 4, 5,
						5, 6, 2,
						2, 6, 0,
						0, 6, 1,
						1, 6, 7,
						7, 3, 1,
						7, 4, 3,
						4, 7, 6,
						4, 6, 5
				};

		mesh = new Mesh( verticles, indices, true );
	}

	@Override
	public void render( Shader shader, RenderEngine renderEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), renderEngine );
		mesh.draw();
	}

	public void update( float delta )
	{
	}
}
