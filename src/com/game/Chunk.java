package com.game;

import com.engine.core.Game;
import com.engine.core.GameObject;
import com.engine.core.Material;
import com.engine.core.Tessellator;
import com.engine.core.helpers.dimensions.Vector3f;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created on 10/05/2014.
 */
public class Chunk
{
	private ArrayList<GameObject> gameObjectsList = new ArrayList<GameObject>();

	public Chunk( int x, int y )
	{
		Tessellator tessellator = new Tessellator( 128, 10f, new Color( 255, 237, 117 ) );
		tessellator.calculateTesselator();
		tessellator.getMesh().setMaterial( Material.ROCk );
		GameObject dirt = new GameObject();
		dirt.addComponent( tessellator.getMesh() );
		dirt.getTransform().translate( x, 3, y );
		dirt.getTransform().getScale().set( 100, 100, 100 );
		gameObjectsList.add( dirt );

		Tessellator tessellator2 = new Tessellator( 129, 30f, new Color( 93, 92, 82 ) );
		tessellator2.calculateTesselator();
		tessellator2.getMesh().setMaterial( Material.ROCk );
		GameObject rock = new GameObject();
		rock.addComponent( tessellator2.getMesh() );
		rock.getTransform().translate( x, -5, y );
		rock.getTransform().getScale().set( 100, 100, 100 );
		gameObjectsList.add( rock );

		Tessellator tessellator3 = new Tessellator( 128, 1f, new Color( 60, 149, 255 ) );
		tessellator3.calculateTesselator();
		tessellator3.getMesh().setMaterial( Material.WATER );
		GameObject water = new GameObject();
		water.addComponent( tessellator3.getMesh() );
		water.getTransform().translate( x, 8, y );
		water.getTransform().getScale().set( 100, 100, 100 );
		gameObjectsList.add( water );
	}

	public void addToGame( Game game )
	{
		for ( GameObject gameObject : gameObjectsList )
			game.addObject( gameObject );
	}
}
