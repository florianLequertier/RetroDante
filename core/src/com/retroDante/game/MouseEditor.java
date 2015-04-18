package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;


/**
 * 
 * Une classe assez simple repr�sentant juste la souris dans l'�diteur
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
			m_currentPlaceable.draw(batch, false);
		}
	}
	
	/**
	 * Le Canvas est lach� dans la sc�ne.
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
	 * ajoute l'element pr�sent dans le canvas � un manager
	 * 
	 * @param manager
	 */
	public void attachCanvasOn(Manager<? extends Body> manager)
	{
		if(m_currentPlaceable != null)
		m_currentPlaceable.attachOn(manager);
	}
	
	public void attachCanvasOn(Manager<? extends Body> manager, int index)
	{
		if(m_currentPlaceable != null)
		m_currentPlaceable.attachOn(manager, index);
	}
	
	public String getCanvasType()
	{
		if(m_currentPlaceable != null)
			return m_currentPlaceable.getType();
		else
		{
			System.out.println("WARRNING : MouseEditor : getCanvasType : aucun canvas attach� � la souris");
			return "error";
		}
	}
	
	public void setCanvasPosition(Vector2 newPosition)
	{
		m_currentPlaceable.setPosition(newPosition);
	}
	
	public Vector2 getCanvasPosition()
	{
		return m_currentPlaceable.getPosition();
	}
	
	/**
	 * return true si le canvas n'est pas null, ie la souris transporte un element � placer dans la sc�ne
	 * 
	 * @return
	 */
	public boolean hasCanvas()
	{
		if(m_currentPlaceable != null)
			return true;
		else
			return false;
	}
	
	
	
	
}
