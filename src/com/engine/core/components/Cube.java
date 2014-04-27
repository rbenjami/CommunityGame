package com.engine.core.components;

import com.engine.core.RenderEngine;
import com.engine.core.Shaders;
import com.engine.core.dimensions_helpers.Vector3f;
import com.engine.core.dimensions_helpers.Vertex3f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 16/04/14.
 */
public class Cube extends GameComponent
{
	private ArrayList<Vertex3f> verticles;
	private int[]               indices;
	private Mesh                mesh;

	public Cube( Color color )
	{
		Vertex3f rbf = new Vertex3f( -1, -1, 1, color );
		Vertex3f rtf = new Vertex3f( -1, 1, 1, color );
		Vertex3f ltf = new Vertex3f( 1, 1, 1, color );
		Vertex3f lbf = new Vertex3f( 1, -1, 1, color );
		Vertex3f rbb = new Vertex3f( -1, -1, -1, color );
		Vertex3f rtb = new Vertex3f( -1, 1, -1, color );
		Vertex3f ltb = new Vertex3f( 1, 1, -1, color );
		Vertex3f lbb = new Vertex3f( 1, -1, -1, color );

		verticles = new ArrayList<>();
		verticles.add( rbf );
		verticles.add( rtf );
		verticles.add( lbf );
		verticles.add( ltf );
		verticles.add( ltb );
		verticles.add( lbb );
		verticles.add( rbb );
		verticles.add( rtb );

		indices = new int[]
				{
						0, 2, 1,
						2, 3, 1,
						3, 2, 4,
						2, 5, 4,
						5, 2, 6,
						2, 0, 6,
						0, 1, 6,
						1, 7, 6,
						7, 1, 3,
						7, 3, 4,
						4, 6, 7,
						4, 5, 6
				};

		mesh = new Mesh( verticles, indices );
	}

	@Override
	public void render( Shaders shader, RenderEngine renderEngine )
	{
		shader.bind();
		shader.updateUniforms( getTransform(), renderEngine );
		mesh.draw();
	}

	public void update( float delta )
	{
	}
}
