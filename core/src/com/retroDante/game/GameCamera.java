package com.retroDante.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class GameCamera extends OrthographicCamera{
	
	GameCamera()
	{
		super();
	}
	
	GameCamera(Vector2 viewportDim)
	{
		super(viewportDim.x, viewportDim.y);
	}
	
	GameCamera(float viewportX, float viewportY)
	{
		super(viewportX, viewportY);
	}
	
}
