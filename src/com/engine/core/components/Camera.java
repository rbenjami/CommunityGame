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

package com.engine.core.components;

import com.engine.CoreEngine;
import com.engine.core.helpers.dimensions.Matrix4f;
import com.engine.core.helpers.dimensions.Vector3f;


/**
 * Created on 16/04/14.
 */
public class Camera extends GameComponent
{
	private Matrix4f projection;
	private float    fov;
	private float    aspect;
	private float    zNear;
	private float    zFar;

	public Camera( float fov, float aspect, float zNear, float zFar )
	{
		this.fov = fov;
		this.aspect = aspect;
		this.zNear = zNear;
		this.zFar = zFar;
		this.projection = new Matrix4f().initPerspective( fov, aspect, zNear, zFar );
	}

	public Matrix4f getViewProjection()
	{
		Matrix4f cameraRotation = getTransform().getTransformedRot().conjugate().toRotationMatrix();
		Vector3f cameraPos = getTransform().getTransformedPos().mul( -1 );
		Matrix4f cameraTranslation = new Matrix4f().initTranslation( cameraPos.getX(), cameraPos.getY(), cameraPos.getZ() );

		return projection.mul( cameraRotation.mul( cameraTranslation ) );
	}

	@Override
	public void addToEngine( CoreEngine engine )
	{
		engine.getRenderEngine().addCamera( this );
	}


	/**
	 * GETTER
	 */
	public Vector3f getPos()
	{
		return getTransform().getPos();
	}

	/**
	 * SETTER
	 */
	public void setPos( Vector3f pos )
	{
		getTransform().setPos( pos );
	}

	public void resizeProjection( float width, float height )
	{
		this.projection = new Matrix4f().initPerspective( fov, width / height, zNear, zFar );
	}
}
