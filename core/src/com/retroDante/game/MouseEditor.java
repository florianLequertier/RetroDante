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
		m_currentPlaceable = new Canvas<T>(element, type, 0 , 1);
	}
	
	public <T extends Body> void changePlaceable( T element, String type, int layout, int remainActions)
	{
		m_currentPlaceable = new Canvas<T>(element, type, layout, remainActions);
	}
	
	/**
	 * Update du mouvement de la souris
	 */
	public void update()
	{
		m_position.x = Gdx.input.getX();
		m_position.y = -Gdx.input.getY() + Gdx.graphics.getHeight();
		
		if( (m_currentPlaceable != null) && (m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions()) ) 
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
	 * Le Canvas est laché dans la scène.
	 */
	public void dropCanvasOn( List<CanvasInterface> canvasContainer)
	{
		if(m_currentPlaceable != null)
		{
			if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions())// Aucune action n'a encore été traité : On l'attache 
			{
				canvasContainer.add(m_currentPlaceable);
			}
			else if(m_currentPlaceable.getRemainActions() == 0) // Toutes les action efféctuées : On le retire de la souris
			{
				m_currentPlaceable = null;
			}
		}
	}
	
	/**
	 * ajoute l'element présent dans le canvas à un manager
	 * 
	 * @param manager
	 */
	public void attachCanvasOn(Manager<? extends Body> manager)
	{
		if(m_currentPlaceable != null)
		{
			
			if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions()) // Aucune action n'a encore été traité : On l'attache au manager quelque soit son type
			{
				m_currentPlaceable.attachOn(manager);
			}
			else
			{
				if(m_currentPlaceable.getType().equals("trigger")) // si c'est un trigger, on poursuit les autres étapes
				{
					if(m_currentPlaceable.getRemainActions() == 2) //resize
					{
						m_currentPlaceable.resizeAction(m_position);
					}
					else if(m_currentPlaceable.getRemainActions() == 1) 
					{
						m_currentPlaceable.additionnalAction();
					}
				}
			}

		}

	}
	
//	public void attachCanvasOn(Manager<? extends Body> manager, int index)
//	{
//		if(m_currentPlaceable != null)
//		m_currentPlaceable.attachOn(manager, index);
//	}
	
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
	
	public void setCanvasPosition(Vector2 newPosition)
	{
		m_currentPlaceable.setPosition(newPosition);
	}
	
	public Vector2 getCanvasPosition()
	{
		return m_currentPlaceable.getPosition();
	}
	
	/**
	 * return true si le canvas n'est pas null, ie la souris transporte un element à placer dans la scène
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
