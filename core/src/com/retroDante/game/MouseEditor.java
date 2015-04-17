package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;


/**
 * 
 * Une classe assez simple représentant juste la souris dans l'éditeur
 * 
 * @author florian
 *
 */
public class MouseEditor {
	
	private CanvasInterface m_currentPlaceable;
	private Vector2 m_position; //position de la souris 
	
	
	public MouseEditor()
	{
		update();
	}
	
	
	public <T> void changePlaceable( T element)
	{
		m_currentPlaceable = new Canvas<T>(element);
	}
	
	/**
	 * Update du mouvement de la souris
	 */
	public void update()
	{
		m_position.x = Gdx.input.getX();
		m_position.y = Gdx.input.getY();
	}
	
	/**
	 * Draw de l'element que la souris transporte, s'il existe
	 */
	public void draw()
	{
		if(m_currentPlaceable != null)
		{
			m_currentPlaceable.draw();
		}
	}
	
	
}
