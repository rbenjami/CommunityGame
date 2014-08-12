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

package com.engine.core.helpers.quadtree;

import com.engine.core.helpers.dimensions.Vector3f;

/**
 * Created on 24/05/2014.
 */
public class Node
{
	private Vector3f vector1;
	private Vector3f vector2;
	private Vector3f vector3;
	private Node     parent;
	private Node bot   = null;
	private Node mid   = null;
	private Node right = null;
	private Node left  = null;

	public Node( Vector3f vector1, Vector3f vector2, Vector3f vector3, Node parent )
	{
		this.vector1 = vector1;
		this.vector2 = vector2;
		this.vector3 = vector3;
		this.parent = parent;
	}

	/**
	 * GETTER
	 */
	public Vector3f getVector1()
	{
		return vector1;
	}

	/**
	 * SETTER
	 */
	public void setVector1( Vector3f vector1 )
	{
		this.vector1 = vector1;
	}

	public Vector3f getVector2()
	{
		return vector2;
	}

	public void setVector2( Vector3f vector2 )
	{
		this.vector2 = vector2;
	}

	public Vector3f getVector3()
	{
		return vector3;
	}

	public void setVector3( Vector3f vector3 )
	{
		this.vector3 = vector3;
	}

	public Node getBottom()
	{
		return bot;
	}

	public Node getMiddle()
	{
		return mid;
	}

	public Node getRight()
	{
		return right;
	}

	public void setRight( Node right )
	{
		this.right = right;
	}

	public Node getLeft()
	{
		return left;
	}

	public void setLeft( Node left )
	{
		this.left = left;
	}

	public void setBot( Node bot )
	{
		this.bot = bot;
	}

	public void setMid( Node mid )
	{
		this.mid = mid;
	}
}
