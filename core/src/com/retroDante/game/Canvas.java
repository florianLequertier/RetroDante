package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Button.ButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.retroDante.game.trigger.TeleportTrigger;
import com.retroDante.game.trigger.Trigger;

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
	//private Button m_button;
	private Trigger m_collider;
	private int m_layout;
	private int m_remainActions = 0;
	private int m_maxActions = 0;
	
	public Canvas(T element, String type)
	{
//		m_button = new Button(m_skin);
//		m_button.setSize(100, 100);
//		m_button.addListener(new InputListener() {
//			@Override
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				System.out.println("appuie sur un button de la scene");
//				return false;
//			}
//		});
		
		m_element = element;
		m_collider = new Trigger(Color.GREEN);
		m_collider.setDimension(m_element.getDimension());
		m_type = type;
		m_layout = 0;
		m_remainActions = 0;
		m_maxActions = 0;
	}
	
	public Canvas(T element, String type, int layout, int remainActions)
	{
//		m_button = new Button(m_skin);
//		m_button.setSize(100, 100);
//		m_button.addListener(new InputListener() {
//			@Override
//			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
//				System.out.println("appuie sur un button de la scene");
//				return false;
//			}
//		});
		
		m_element = element;
		m_collider = new Trigger(Color.GREEN);
		m_collider.setDimension(m_element.getDimension());
		m_type = type;
		m_layout = layout;
		m_remainActions = remainActions;
		m_maxActions = remainActions;
	}

	@Override
	public void setPosition(Vector2 newPos) 
	{

		//m_button.setPosition(newPos.x, newPos.y);
		m_collider.setPosition(newPos);
		if(m_element != null)
			m_element.setPosition(newPos);
	}

	@Override
	public void draw(Batch batch, boolean onlyButton) 
	{
		if(m_element != null && onlyButton == false)
			m_element.draw(batch);
		
		//m_button.draw(batch, 1);
		//m_collider.draw(batch);
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
	public Trigger getCollider()
	{
		return m_collider;
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
	
	@Override
	public <T extends Body> void removeOn(Manager<T> manager)
	{
		if(m_element != null)
		{
			if(m_type.equals("map") )
				manager.remove((T)m_element, m_layout);
			else
				manager.remove((T)m_element);
			
			m_remainActions = m_maxActions;
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
		return m_collider.getPosition();
	}
	
	@Override
	public void resizeAction(Vector2 position, boolean decreaseAction)
	{
		Vector2 A = m_element.getPosition();
		Vector2 B = position;//new Vector2( Gdx.input.getX(), Gdx.input.getY() );
		Vector2 dimension = B.mulAdd(A, -1);
		
		m_element.setDimension(dimension);
		//m_button.setSize(dimension.x, dimension.y );
		m_collider.setDimension(dimension);
		
		if(decreaseAction)
		m_remainActions--;
	}
	@Override
	public void additionnalAction(boolean decreaseAction, Vector2 worldPosition)
	{
		System.out.println("TODO : additionnalAction");
		
		if(m_element instanceof TeleportTrigger )
		{
			TeleportTrigger trigger = (TeleportTrigger)m_element;
			trigger.setTeleportDestination(worldPosition);
		}
		
		
		if(decreaseAction)
		m_remainActions--;
	}
	
	@Override
	public Body getElement()
	{
		return m_element;
	}
	
	/**
	 * return true si la strategie de drop a fini de dropper l'objet. On peut alors enlever l'objet à la souris
	 */
	@Override
	public boolean applyDropStrategy(Vector2 worldPosition) {
		
		if(m_type.equals("trigger")) // si c'est un trigger, on poursuit les autres étapes
		{
			if(m_remainActions == this.m_maxActions - 1) //resize
			{
				this.resizeAction(worldPosition, true);
			}
			else if(m_remainActions == this.m_maxActions - 2) 
			{
				this.additionnalAction(true, worldPosition);
			}
		}
		
		return m_remainActions==0;
		
	}
	
	/**
	 * update de la strategy de drop lors du mouvement de la souris
	 * 
	 * @param worldPosition
	 */
	@Override
	public void updateDropStrategy(Vector2 worldPosition)
	{
		if(m_type.equals("trigger")) // si c'est un trigger, on poursuit les autres étapes
		{
			if(m_remainActions == this.m_maxActions - 1) //resize
			{
				this.resizeAction(worldPosition, false);
			}
			else if(m_remainActions == this.m_maxActions - 2) 
			{
				this.additionnalAction(false, worldPosition);
			}
		}
	}
	
	
	@Override
	public boolean equals(Object other)
	{
		if(other == this)
			return true;
		
		if(other instanceof Canvas)
		{
			Canvas<? extends Body> canvas = (Canvas<? extends Body>)other;
			if(this.m_element.equals(canvas.m_element))
				return true;
		}
		/*else if(other instanceof CanvasInterface)
		{
			CanvasInterface canvasInter = (CanvasInterface)other;
			if(this.m_element.equals(canvasInter.m_element))
				return true;
		}*/

		
		return false;
	}


}
