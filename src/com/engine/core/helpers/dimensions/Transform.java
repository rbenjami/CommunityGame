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

package com.engine.core.helpers.dimensions;

/**
 * Created on 24/04/14.
 */
public class Transform
{
	private Transform parent;
	private Matrix4f  parentMatrix;

	private Vector3f   pos;
	private Quaternion rot;
	private Vector3f   scale;

	private Vector3f   oldPos;
	private Quaternion oldRot;
	private Vector3f   oldScale;

	public Transform()
	{
		pos = new Vector3f( 0, 0, 0 );
		rot = new Quaternion( 0, 0, 0, 1 );
		scale = new Vector3f( 1, 1, 1 );

		parentMatrix = new Matrix4f().initIdentity();
	}

	public Transform( Vector3f pos, Quaternion rot, Vector3f scale )
	{
		this.pos = pos;
		this.rot = rot;
		this.scale = scale;

		parentMatrix = new Matrix4f().initIdentity();
	}

	public void update()
	{
		if ( oldPos != null )
		{
			oldPos.set( pos );
			oldRot.set( rot );
			oldScale.set( scale );
		}
		else
		{
			oldPos = new Vector3f( 0, 0, 0 ).set( pos ).add( 1.0f );
			oldRot = new Quaternion( 0, 0, 0, 0 ).set( rot ).mul( 0.5f );
			oldScale = new Vector3f( 0, 0, 0 ).set( scale ).add( 1.0f );
		}
	}

	public boolean hasChanged()
	{
		if ( parent != null && parent.hasChanged() )
			return true;

		if ( !pos.equals( oldPos ) )
			return true;

		if ( !rot.equals( oldRot ) )
			return true;

		if ( !scale.equals( oldScale ) )
			return true;

		return false;
	}

	public void translate( float x, float y, float z )
	{
		pos = pos.add( new Vector3f( x, y, z ) );
	}

	public void translate( Vector3f vec )
	{
		pos = pos.add( vec );
	}

	public void rotate( Vector3f axis, float angle )
	{
		rot = new Quaternion( axis, angle ).mul( rot ).normalized();
	}

	public void lookAt( Vector3f point, Vector3f up )
	{
		rot = getLookAtRotation( point, up );
	}

	public Quaternion getLookAtRotation( Vector3f point, Vector3f up )
	{
		return new Quaternion( new Matrix4f().initRotation( point.sub( pos ).normalized(), up ) );
	}

	public Matrix4f getTransformation()
	{
		Matrix4f translationMatrix = new Matrix4f().initTranslation( pos.getX(), pos.getY(), pos.getZ() );
		Matrix4f rotationMatrix = rot.toRotationMatrix();
		Matrix4f scaleMatrix = new Matrix4f().initScale( scale.getX(), scale.getY(), scale.getZ() );

		return getParentMatrix().mul( translationMatrix.mul( rotationMatrix.mul( scaleMatrix ) ) );
	}

	private Matrix4f getParentMatrix()
	{
		if ( parent != null && parent.hasChanged() )
			parentMatrix = parent.getTransformation();

		return parentMatrix;
	}

	public Vector3f getTransformedPos()
	{
		return getParentMatrix().transform( pos );
	}

	public Quaternion getTransformedRot()
	{
		Quaternion parentRotation = new Quaternion( 0, 0, 0, 1 );

		if ( parent != null )
			parentRotation = parent.getTransformedRot();

		return parentRotation.mul( rot );
	}

	public Vector3f getPos()
	{
		return pos;
	}

	public void setPos( Vector3f pos )
	{
		this.pos = pos;
	}

	public Quaternion getRot()
	{
		return rot;
	}

	public void setRot( Quaternion rotation )
	{
		this.rot = rotation;
	}

	public Vector3f getScale()
	{
		return scale;
	}

	public void setScale( Vector3f scale )
	{
		this.scale = scale;
	}

	public void setParent( Transform parent )
	{
		this.parent = parent;
	}
}
