package com.retroDante.game;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class HUDCamera extends OrthographicCamera{
	
	public static int hudCameraWidth = 600;
	public static int hudCameraHeight = 400;
	
	public HUDCamera()
	{
		super(hudCameraWidth, hudCameraHeight);
	}
	
	public HUDCamera(Vector2 viewportDim)
	{
		super(viewportDim.x, viewportDim.y);
	}
	
	public HUDCamera(float viewportX, float viewportY)
	{
		super(viewportX, viewportY);
	}
	
}
