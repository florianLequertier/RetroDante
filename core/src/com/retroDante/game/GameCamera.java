package com.retroDante.game;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.retroDante.game.map.Map;

public class GameCamera extends OrthographicCamera{
	
	Map m_parralaxTarget = null;
	float m_maxSpeed = 2.f;
	float m_currentSpeed = 0;
	Vector2 m_currentTranslation;
	
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
	
	void setParralaxTarget(Map map)
	{
		m_parralaxTarget = map;
	}
	
	public Vector2 getCurrentTranslation()
	{
		return m_currentTranslation;
	}
	
	public void setPosition(Vector2 newPos) //ne prend pas en compte le parralax
	{
		super.translate(newPos);
	}
	public void setPosition(Vector3 newPos) //ne prend pas en compte le parralax
	{
		super.translate(newPos);
	}
	
	public void setCurrentSpeed(float speed)
	{
		m_currentSpeed = speed;
	}
	
	public void setMaxSpeed(float speed)
	{
		m_maxSpeed = speed;
	}
	
	public float getCurrentSpeed()
	{
		return m_currentSpeed;
	}
	
	public float getMaxSpeed()
	{
		return m_maxSpeed;
	}
	
	public void follow(Vector2 target)
	{
		Vector2 camPos = new Vector2(this.position.x, this.position.y );
		Vector2 fromTo = target.mulAdd(camPos, -1);
		float distance = fromTo.len2();
		distance = MathUtils.clamp(distance, 0, 2000);
		distance *= 0.0005;
		distance *= m_maxSpeed;
		if(distance < 0.2){ distance = 0; }
		fromTo.nor();
		m_currentSpeed = MathUtils.lerp(0, m_maxSpeed, distance);
		translate(fromTo);
	}
	
	@Override
	public void translate(float x, float y)
	{
		m_currentTranslation = new Vector2(x * m_currentSpeed, y * m_currentSpeed);
		super.translate(m_currentTranslation);
		if(m_parralaxTarget != null)
		{
			m_parralaxTarget.updateParralax(this);
		}
	}
	
	@Override
	public void translate(Vector2 vec)
	{
		this.translate(vec.x, vec.y);
	}
	
}
