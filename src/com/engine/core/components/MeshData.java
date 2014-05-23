package com.engine.core.components;

import com.engine.core.Utils;
import com.engine.core.helpers.dimensions.Vector3f;

import java.util.ArrayList;

/**
 * Created on 21/05/2014.
 */
public class MeshData
{
	private ArrayList<Vector3f> vertices;
	private ArrayList<Integer>  indices;

	public MeshData()
	{
		this.vertices = new ArrayList<Vector3f>();
		this.indices = new ArrayList<Integer>();
	}

	public void put( Vector3f vertex )
	{
		vertices.add( vertex );
	}

	public void put( int indice )
	{
		indices.add( indice );
	}

	public int[] getIndices()
	{
		return Utils.toIntArray( indices.toArray( new Integer[indices.size()] ) );
	}

	public Vector3f[] getVertices()
	{
		return vertices.toArray( new Vector3f[vertices.size()] );
	}
}
