package com.engine.core;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 16/04/14.
 */
public class Cube extends GameObject
{
	private ArrayList<Vertex3f> verticles;
	private int[]               indices;
	private Mesh                mesh;

	public Cube( Vector3f pos, Color color )
	{
		this( pos, new Quaternion( 0, 0, 0, 1 ), new Vector3f( 1, 1, 1 ), color );
	}

	public Cube( Vector3f pos, Quaternion rot, Vector3f scale, Color color )
	{
		this.getTransform().setPos( pos );
		this.getTransform().setRot( rot );
		this.getTransform().setScale( scale );

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

		mesh = new Mesh( verticles, indices );
	}

	@Override
	public void update()
	{

	}

	@Override
	protected void draw()
	{
		mesh.draw();
	}

	public void rotate( Vector3f axis, float angle )
	{
		getTransform().rotate( axis, (float) Math.toRadians( angle ) );
	}
}
