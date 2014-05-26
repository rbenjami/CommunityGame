package com.engine.core.helpers.quadtree;

import java.util.ArrayList;

/**
 * Created on 24/05/2014.
 */
public class QuadTree
{
	private ArrayList<Node> rootNodes;
	private int             size;

	public QuadTree( ArrayList<Node> rootNodes )
	{
		this.rootNodes = rootNodes;
		this.size = rootNodes.size();
	}

	public int size()
	{
		return size;
	}

	/**
	 * GETTER
	 */
	public ArrayList<Node> getRootNodes()
	{
		return rootNodes;
	}
}
