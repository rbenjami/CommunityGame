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
