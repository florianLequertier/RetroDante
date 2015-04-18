package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
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
	private Vector2 m_position = new Vector2(); //position de la souris 
	
	
	public MouseEditor()
	{
		update();
	}
	
	
	public <T extends Body> void changePlaceable( T element, String type)
	{
		m_currentPlaceable = new Canvas<T>(element, type);
	}
	
	/**
	 * Update du mouvement de la souris
	 */
	public void update()
	{
		m_position.x = Gdx.input.getX();
		m_position.y = -Gdx.input.getY() + Gdx.graphics.getHeight();
		
		if(m_currentPlaceable != null)
		{
			m_currentPlaceable.setPosition(m_position);
		}
	}
	
	/**
	 * Draw de l'element que la souris transporte, s'il existe
	 */
	public void draw(Batch batch)
	{
		if(m_currentPlaceable != null)
		{
			m_currentPlaceable.draw(batch);
		}
	}
	
	/**
	 * Le Canvas est laché dans la scène.
	 */
	public void dropCanvasOn( List<CanvasInterface> canvasContainer)
	{
		if(m_currentPlaceable != null)
		{
			canvasContainer.add(m_currentPlaceable);
			m_currentPlaceable = null;
		}
	}
	
	/**
	 * ajoute l'element présent dans le canvas à un manager
	 * 
	 * @param manager
	 */
	public void attachCanvasOn(Manager<? extends Body> manager)
	{
		m_currentPlaceable.attachOn(manager);
	}
	
	public void attachCanvasOn(Manager<? extends Body> manager, int index)
	{
		m_currentPlaceable.attachOn(manager, index);
	}
	
	public String getCanvasType()
	{
		if(m_currentPlaceable != null)
			return m_currentPlaceable.getType();
		else
		{
			System.out.println("WARRNING : MouseEditor : getCanvasType : aucun canvas attaché à la souris");
			return "error";
		}
	}
	
	
	
	
}
