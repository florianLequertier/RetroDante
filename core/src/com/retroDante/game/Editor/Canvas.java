package com.retroDante.game.Editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
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
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Body;
import com.retroDante.game.Element2D;
import com.retroDante.game.Manager;
import com.retroDante.game.map.Map;
import com.retroDante.game.map.MapLayout;
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
	
	public Canvas()
	{
		
		m_element = null;
		m_collider = new Trigger(Color.GREEN);
		m_collider.setDimension(m_element.getDimension());
		m_type = "default";
		m_layout = 0;
		m_remainActions = 0;
		m_maxActions = 0;
	}
	
	public Canvas(T element, String type)
	{
		
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

		m_collider.setPosition(newPos);
		if(m_element != null)
			m_element.setPosition(newPos);
	}

	@Override
	public void draw(Batch batch, boolean onlyButton) 
	{
		if(m_element != null && onlyButton == false)
			m_element.draw(batch);
		
		if(!(m_element instanceof Trigger))
		m_collider.draw(batch);
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
	public void setCollider(Trigger collider)
	{
		m_collider = collider;
	}
	@SuppressWarnings("unchecked")
	@Override 
	public <L extends Body> void setElement(L element)
	{
		m_element = (T)element;
	}
	@Override
	public T getElement()
	{
		return m_element;
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
		System.out.println("TODO : resize action");
		
		Vector2 A = new Vector2(m_element.getPosition());
		Vector2 B = new Vector2(position);//new Vector2( Gdx.input.getX(), Gdx.input.getY() );
		Vector2 dimension = new Vector2(B).mulAdd(new Vector2(A), -1);
		
		if(!decreaseAction)
		{
			m_element.setDimension(dimension);
			//m_button.setSize(dimension.x, dimension.y );
			m_collider.setDimension(dimension);
		}
		else
		{
			dimension = new Vector2(Math.abs(dimension.x), Math.abs(dimension.y));
			
			//cas particuliers : 
			if(B.y < A.y)
			{
				float posX = m_element.getPosition().x;
				float posY = m_element.getPosition().y - Math.abs(B.y - A.y);
				
				m_element.setPosition(new Vector2(posX, posY));
				m_collider.setPosition(new Vector2(posX, posY));
			}
			if(B.x < A.x)
			{
				float posY = m_element.getPosition().y;
				float posX = m_element.getPosition().x - Math.abs(B.x - A.x);
				
				m_element.setPosition(new Vector2(posX, posY));
				m_collider.setPosition(new Vector2(posX, posY));
			}
			
			m_element.setDimension(dimension);
			//m_button.setSize(dimension.x, dimension.y );
			m_collider.setDimension(dimension);
			
			m_remainActions--;
		}

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
	public boolean checkIfDropIsFinished()
	{
		return m_remainActions<=0;
	}
	
	@Override
	public void save(String filePath)
	{
		Json json = new Json();
		String text = json.toJson(this);
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);// internal(filePath);
		file.writeString(text, false);
	}
	
	
	@SuppressWarnings("unchecked")
	public static <T extends Body>  Canvas<T> load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		Json json = new Json();
		Canvas<T> canvas = json.fromJson((Class<Canvas<T>>)(Class<?>)Canvas.class, fileString);
		return canvas;
	}
	
	
	@Override
	public void write(Json json) 
	{
	
		json.writeObjectStart("canvas");
			m_element.write(json);
			json.writeValue("m_type", m_type);
			json.writeValue("m_layout", m_layout);
			json.writeValue("m_remainActions", m_remainActions);
			json.writeValue("m_maxActions", m_maxActions);			
		json.writeObjectEnd();
				
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void read(Json json, JsonValue jsonData) 
	{
		
		m_element = (T) json.fromJson(Body.class, jsonData.toString());
		
		m_type = jsonData.getString("m_type");
		m_layout = jsonData.getInt("m_layout");
		m_remainActions = jsonData.getInt("m_remainActions");
		m_maxActions = jsonData.getInt("m_maxActions");
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
		
		return false;
	}


}
