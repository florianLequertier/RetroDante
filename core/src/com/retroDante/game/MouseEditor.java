package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Table;


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
	
	public <T extends Body> void changePlaceable(CanvasInterface canvas)
	{
		m_currentPlaceable = canvas;
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
	 * Le Canvas est lach� dans la sc�ne.
	 */
	public boolean dropCanvasOn( List<CanvasInterface> canvasContainer)
	{
		if(m_currentPlaceable == null)
		return false;
		
//		if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions())// Aucune action n'a encore �t� trait� : On l'attache 
//		{
//			canvasContainer.add(m_currentPlaceable);
//			return true;
//		}
		//else if(m_currentPlaceable.getRemainActions() == 0) // Toutes les action eff�ctu�es : On le retire de la souris
		//{
		//	m_currentPlaceable = null;
		//}
		canvasContainer.add(m_currentPlaceable);
		//screenTable.add(m_currentPlaceable.getButton());
		
		return true;

	}
	
	/**
	 * ajoute l'element pr�sent dans le canvas � un manager
	 * 
	 * @param manager
	 */
	public void attachCanvasOn(Manager<? extends Body> manager)
	{
		if(m_currentPlaceable == null)
			return;

		if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions()) // Aucune action n'a encore �t� trait� : On l'attache au manager quelque soit son type
		{
			m_currentPlaceable.attachOn(manager);
		}
		
	}
	
	/**
	 * Retire l'element du manager
	 * 
	 * @param manager
	 */
	public void removeCanvasOn(Manager<? extends Body> manager)
	{
		if(m_currentPlaceable == null)
			return;

		m_currentPlaceable.removeOn(manager);	
	}
	
	public void applyDropStrategy(Vector2 worldPosition)
	{
		if(m_currentPlaceable == null)
			return;
		
		if(m_currentPlaceable.applyDropStrategy(worldPosition))
		{
			m_currentPlaceable = null;
		}
	}
	
	public void updateDropStrategy(Vector2 worldPosition)
	{
		if(m_currentPlaceable == null)
			return;
		
		m_currentPlaceable.updateDropStrategy(worldPosition);

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
	
	/**
	 * Test d'�galit� entre le canvas de la souris et celui pass� en parametre
	 * 
	 * @param other
	 * @return
	 */
	public boolean testCanvasEquality(CanvasInterface other)
	{
		if(m_currentPlaceable == null)
			return false;
		
		if(m_currentPlaceable.equals(other))
		{
			//System.out.println("canvas egaux");
			return true;
		}
		else
		{
			return false;
		}
	}
	
	
	
}
