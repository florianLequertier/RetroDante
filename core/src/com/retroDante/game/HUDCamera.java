package com.retroDante.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class HUDCamera extends OrthographicCamera{
	
	HUDCamera()
	{
		super();
	}
	
	HUDCamera(Vector2 viewportDim)
	{
		super(viewportDim.x, viewportDim.y);
	}
	
	HUDCamera(float viewportX, float viewportY)
	{
		super(viewportX, viewportY);
	}
	
}
