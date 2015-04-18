package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

/**
 * 
 * Un Canvas est un bouton stockant un objet. Il permet de placer des elements dans l'editor.
 * On peut detecter la souris grace aux propriétés du bouton et le draw dessine l'element stocké dans le canvas. 
 * La methode save sauvegarde l'objet stocké dans le canvas. 
 * 
 * @author florian
 *
 * @param <T>
 */
public class Canvas<T extends Body> implements CanvasInterface {
	
	private static final Skin m_skin;
	
	static{
		
		m_skin = new Skin();
		
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		m_skin.add("white", new Texture(pixmap));

		
		//boutons par defaut : 
		ButtonStyle buttonStyle = new ButtonStyle();
		buttonStyle.up = m_skin.newDrawable("white", 0 , 0 , 0 , 0);
		buttonStyle.down = m_skin.newDrawable("white", 100, 20, 240, 80);
		buttonStyle.checked = m_skin.newDrawable("white", 0 , 0 , 0 , 0);
		buttonStyle.over = m_skin.newDrawable("white", 100, 20, 240, 20);
		m_skin.add("default", buttonStyle);
		
	} //skin unique pour chaque canvas
	
	private String m_type;
	private T m_element;
	private Button m_button;
	private int m_layout;
	private int m_remainActions = 0;
	private int m_maxActions = 0;
	
	Canvas(T element, String type, int layout, int remainActions)
	{
		m_button = new Button(m_skin);
		m_element = element;
		m_type = type;
		m_layout = layout;
		m_remainActions = remainActions;
		m_maxActions = remainActions;
	}

	@Override
	public void setPosition(Vector2 newPos) 
	{

		m_button.setPosition(newPos.x, newPos.y);
		if(m_element != null)
			m_element.setPosition(newPos);
	}

	@Override
	public void draw(Batch batch, boolean onlyButton) 
	{
		if(m_element != null && onlyButton == false)
			m_element.draw(batch);
		
		m_button.draw(batch, 1);
	}
	
	@Override
	public void setType(String type)
	{
		m_type = type;
	}
	@Override
	public String getType()
	{
		return m_type;
	}
	@Override
	public int getLayout()
	{
		return m_layout;
	}
	@Override
	public void setLayout(int layout)
	{
		m_layout = layout;
	}
	@Override 
	public int getRemainActions()
	{
		return m_remainActions;
	}
	@Override
	public void setRemainActions(int remainActions)
	{
		m_remainActions = remainActions;
	}
	@Override 
	public int getMaxActions()
	{
		return m_maxActions;
	}
	@Override
	public void setMaxActions(int maxActions)
	{
		m_maxActions = maxActions;
	}
	

	@Override
	public <T extends Body> void attachOn(Manager<T> manager)
	{
		if(m_element != null)
		{
			if(m_type.equals("map") )
				manager.add((T)m_element, m_layout);
			else
				manager.add((T)m_element);
			
			m_remainActions--;
		}
		
		
		System.out.println("attache au manager de l'element du canvas de type : "+m_type);
	}
	
//	@Override
//	public <T extends Body> void attachOn(Manager<T> manager, int index)
//	{
//		if(m_element != null)
//		manager.add((T)m_element, index);
//	}
	
	@Override 
	public Vector2 getPosition()
	{
		return new Vector2(m_button.getX(), m_button.getY());
	}
	
	@Override
	public void resizeAction(Vector2 position)
	{
		Vector2 A = m_element.getPosition();
		Vector2 B = position;//new Vector2( Gdx.input.getX(), Gdx.input.getY() );
		Vector2 dimension = A.mulAdd(B, -1);
		
		m_element.setDimension(dimension);
		
		m_remainActions--;
	}
	@Override
	public void additionnalAction()
	{
		System.out.println("TODO : additionnalAction");
		m_remainActions--;
	}
	
	@Override
	public void dropOnSceen()
	{
		
	}
}
