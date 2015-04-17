package com.retroDante.game;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

/**
 * 
 * Element de base du jeu. Peut etre transformé. 
 * 
 * @author florian
 *
 */
public abstract class Body {
	
	private Vector2 m_dimension = new Vector2(1,1);
	private Vector2 m_scaleFactor = new Vector2(1,1);
	private Vector2 m_position = new Vector2(0,0);
	private float m_rotation = 0;
	private Affine2 m_transform = new Affine2();
	
	Body()
	{
		m_dimension = new Vector2(1,1);
		m_scaleFactor = new Vector2(1,1);
		m_position = new Vector2(0,0);
		m_rotation = 0;
		m_transform = new Affine2();
		m_transform.idt();
	}
	
	public void updateTransform()
	{
		m_transform = m_transform.idt().translate(m_position).rotate(m_rotation).scale(m_scaleFactor);
	}
	
	public void setDimension(Vector2 dimension)
	{
		m_dimension = dimension;
		updateTransform();
	}
	
	public void move(Vector2 deltaPos)
	{
		m_position.add(deltaPos);
		updateTransform();
		
	}
	public void setPosition(Vector2 newPosition)
	{
		m_position = newPosition;
		updateTransform();
	}
	public Vector2 getPosition()
	{
		return m_position;
	}
	public void setScale(Vector2 newScale)
	{
		m_scaleFactor = newScale;
		updateTransform();
	}
	public void scale(Vector2 deltaScale)
	{
		m_scaleFactor.add(deltaScale);
		updateTransform();
	}
	public Vector2 getScale()
	{
		return m_scaleFactor;
	}
	public void setRotation(float newRotation)
	{
		m_rotation = newRotation;
		updateTransform();
	}
	public void rotate(float deltaRotation)
	{
		m_rotation += deltaRotation;
		updateTransform();
	}
	public float getRotation()
	{
		return m_rotation;
	}
	
	public Affine2 getTransform()
	{
		return m_transform;
	}
	
	public Vector2 getDimension()
	{
		return m_dimension;
	}
	
	public abstract void draw(Batch batch);
	
}
