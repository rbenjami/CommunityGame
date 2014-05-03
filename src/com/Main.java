package com;

import com.engine.CoreEngine;
import com.engine.core.helpers.MathHelper;
import com.game.CommunityGame;

/**
 * Created on 27/04/14.
 */
public class Main
{
	public static void main( String[] args )
    {
		CoreEngine engine = new CoreEngine( 850, 550, 60, new CommunityGame() );
		engine.createWindow( "CommunityGame" );
		engine.start();
	}
}
