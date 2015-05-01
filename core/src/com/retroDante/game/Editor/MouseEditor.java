package com.retroDante.game.Editor;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.retroDante.game.Body;
import com.retroDante.game.Manager;


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
	private boolean m_isVisible = true;
	private OrthographicCamera m_HUDCamera;
	private Vector2 m_dropPosition = Vector2.Zero;
	
	
	public MouseEditor()
	{
		m_HUDCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		update();
	}
	
	public void setVisibility(boolean newState)
	{
		m_isVisible = newState;
	}
	
	public boolean getVisibility()
	{
		return m_isVisible;
	}
	
	public void setDropPosition(Vector2 dropPosition)
	{
		m_dropPosition = dropPosition;
	}
	
	public Vector2 getDropPosition()
	{
		return m_dropPosition;
	}
	
	public void setCamera( OrthographicCamera camera)
	{
		m_HUDCamera = camera;
	}
	
	public OrthographicCamera getCamera()
	{
		return m_HUDCamera;
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
	
	public void setCanvasLayout(int layoutIndex)
	{
		if(m_currentPlaceable!=null)
		{
			m_currentPlaceable.setLayout(layoutIndex);
		}
	}
	public int getCanvasLayout()
	{
		if(m_currentPlaceable!=null)
		{
			return m_currentPlaceable.getLayout();
		}
		else
			return 0;
	}
	
	/**
	 * Update du mouvement de la souris
	 */
	public void update()
	{
		m_HUDCamera.update();
		
		m_position.x = Gdx.input.getX() - Gdx.graphics.getWidth()*0.5f;
		m_position.y = -Gdx.input.getY() + Gdx.graphics.getHeight()*0.5f;
		
		if( (m_currentPlaceable != null) && (m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions()) ) 
		{
			//m_currentPlaceable.setPosition(m_position);
			m_currentPlaceable.setPosition(m_dropPosition);
		}
	}
	
	/**
	 * Draw de l'element que la souris transporte, s'il existe
	 */
	public void draw(Batch batch)
	{
		//batch.setProjectionMatrix(m_HUDCamera.combined);
		
		if(!batch.isDrawing())
			batch.begin();
		
		if(m_currentPlaceable != null && m_isVisible)
		{
			m_currentPlaceable.draw(batch, false);
		}
		
		batch.end();
	}
	
	/**
	 * Le Canvas est laché dans la scène.
	 */
	public boolean dropCanvasOn( List<CanvasInterface> canvasContainer)
	{
		if(m_currentPlaceable == null)
		return false;
		
//		if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions())// Aucune action n'a encore été traité : On l'attache 
//		{
//			canvasContainer.add(m_currentPlaceable);
//			return true;
//		}
		//else if(m_currentPlaceable.getRemainActions() == 0) // Toutes les action efféctuées : On le retire de la souris
		//{
		//	m_currentPlaceable = null;
		//}
		canvasContainer.add(m_currentPlaceable);
		//screenTable.add(m_currentPlaceable.getButton());
		
		return true;

	}

	Body getPlaceable()
	{
		if(m_currentPlaceable != null)
			return m_currentPlaceable.getElement();
		else 
			return null;
	}
	
	public void deleteCanvas()
	{
		m_currentPlaceable = null;
	}
	
	/**
	 * ajoute l'element présent dans le canvas à un manager
	 * 
	 * @param manager
	 */
	public void attachCanvasOn(Manager<? extends Body> manager)
	{
		if(m_currentPlaceable == null)
			return;

		if(m_currentPlaceable.getRemainActions() == m_currentPlaceable.getMaxActions()) // Aucune action n'a encore été traité : On l'attache au manager quelque soit son type
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
		m_currentPlaceable.applyDropStrategy(worldPosition);
//      if(m_currentPlaceable.applyDropStrategy(worldPosition))
//		{
//			m_currentPlaceable = null;
//		}
	}
	
	public void updateDropStrategy(Vector2 worldPosition)
	{
		if(m_currentPlaceable == null)
			return;
		
		m_currentPlaceable.updateDropStrategy(worldPosition);

	}
	
	public boolean checkIfDropIsFinished()
	{
		if(m_currentPlaceable.checkIfDropIsFinished())
		{
			m_currentPlaceable = null;
			return true;
		}
		else
		{
			return false;
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
	
	/**
	 * Test d'égalité entre le canvas de la souris et celui passé en parametre
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
