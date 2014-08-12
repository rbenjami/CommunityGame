/*
 * Copyright (C) 2014 Repingon Benjamin
 * This file is part of CommunityGame.
 * CommunityGame is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 * CommunityGame is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with CommunityGame. If not, see <http://www.gnu.org/licenses/
 */

package com.engine.render;

import com.engine.core.helpers.dimensions.Vector2f;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

/**
 * Created on 24/04/14.
 */
public class Window
{
	public static void createWindow( int width, int height, String title )
	{
		Display.setTitle( title );
		try
		{
			Display.setDisplayMode( new DisplayMode( width, height ) );
			Display.setVSyncEnabled( true );
			Display.setResizable( true );
			Display.create();
			Keyboard.create();
			Mouse.create();
		}
		catch ( LWJGLException e )
		{
			e.printStackTrace();
		}
	}

	public static void render()
	{
		Display.update();
	}

	public static void dispose()
	{
		Display.destroy();
		Keyboard.destroy();
		Mouse.destroy();
	}

	public static boolean isCloseRequested()
	{
		return Display.isCloseRequested();
	}

	public static String getTitle()
	{
		return Display.getTitle();
	}

	public Vector2f getCenter()
	{
		return new Vector2f( getWidth() / 2, getHeight() / 2 );
	}

	public static int getWidth()
	{
		return Display.getDisplayMode().getWidth();
	}

	public static int getHeight()
	{
		return Display.getDisplayMode().getHeight();
	}
}
