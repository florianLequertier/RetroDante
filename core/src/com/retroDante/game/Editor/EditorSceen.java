package com.retroDante.game.Editor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Body;
import com.retroDante.game.Drawable;
import com.retroDante.game.Element2D;
import com.retroDante.game.Manager;
import com.retroDante.game.character.EnemyManager;
import com.retroDante.game.character.Player;
import com.retroDante.game.map.Map;
import com.retroDante.game.map.MapElement;
import com.retroDante.game.trigger.TriggerManager;

public class EditorSceen extends InputAdapter implements Drawable, Json.Serializable{

	private MouseEditor m_mouseEditor; 
	private List<CanvasInterface> m_canvasContainer;
	private Player m_player; 
	private HashMap<String, Manager<? extends Body> > m_managers;
	private EditorCamera m_sceenCamera;
	private int m_lastDroppedIndice;
	
	EditorSceen()
	{
		m_canvasContainer = new ArrayList<CanvasInterface>();
		m_managers = new HashMap<String, Manager<? extends Body> >();
		m_sceenCamera = new EditorCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		m_lastDroppedIndice = -1;
		
		
		initAll();
		
		m_sceenCamera.setParralaxTarget((Map) m_managers.get("map")); //observe camera
	}
	
	EditorSceen(String FolderPath)
	{
		this();
		loadRessources(FolderPath);
	}



	
	/**
	 * Initialise tous les managers de la scene 
	 */
	void initManagers()
	{
		m_managers.put("map", new Map());
		m_managers.put("enemy", new EnemyManager());
		m_managers.put("trigger", new TriggerManager());
	}
	
	/**
	 * initialise la scene
	 */
	void initAll()
	{
		m_player = new Player();
		m_canvasContainer.add( new Canvas<Player>( m_player , "player") );
		
		initManagers();
	}
	
	EditorCamera getCamera()
	{
		return m_sceenCamera;
	}
	
	
	//events : 
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button)
	{
		
		if(m_mouseEditor != null)
			dropCanvasOnSceen();
		
		return true;
	}
	
	public void update(float delta)
	{
		if(m_mouseEditor != null && m_mouseEditor.hasCanvas())
		{
			Vector2 positionDrop = new Vector2( m_sceenCamera.position.x - m_sceenCamera.viewportWidth*0.5f + Gdx.input.getX(), m_sceenCamera.position. y- m_sceenCamera.viewportHeight*0.5f -Gdx.input.getY() + Gdx.graphics.getHeight());
			if(	m_lastDroppedIndice!=-1 && m_mouseEditor.testCanvasEquality(  m_canvasContainer.get(m_lastDroppedIndice)) )
			{
				m_mouseEditor.updateDropStrategy(positionDrop);
			}
		}
	}
	
	public void setMouse(MouseEditor mouse)
	{
		m_mouseEditor = mouse;
	}
	
	public void dropCanvasOnSceen()
	{
		
		Vector2 positionDrop = new Vector2( m_sceenCamera.position.x - m_sceenCamera.viewportWidth*0.5f + Gdx.input.getX(), m_sceenCamera.position. y- m_sceenCamera.viewportHeight*0.5f -Gdx.input.getY() + Gdx.graphics.getHeight());
		Vector2 mousePosition = new Vector2(  Gdx.input.getX(), -Gdx.input.getY() + Gdx.graphics.getHeight());
		
		
		if(m_mouseEditor == null)
		{
			System.out.println("WARNING : EditorSceen : dropCanvasOnSceen : le pointeur vers la mouseEditor n'a pas �t� initialis�.");
			return;
		}
		
		if(m_mouseEditor.hasCanvas())
		{
			
			String canvasType = m_mouseEditor.getCanvasType();
			if(m_managers.containsKey(canvasType))
			{
				if(	m_lastDroppedIndice==-1 || !m_mouseEditor.testCanvasEquality( m_canvasContainer.get(m_lastDroppedIndice)) )
				{
					if(canvasType.equals("map") ) // traitement de faveur pour la map � cause du parralax
					{
						m_mouseEditor.setCanvasPosition( positionDrop.mulAdd( ((Map)m_managers.get(canvasType)).getParralaxDecalOfPlane(m_mouseEditor.getCanvasLayout() ), -1 ) );
					}
					else
					{
						m_mouseEditor.setCanvasPosition( positionDrop);
					}
					
					m_mouseEditor.attachCanvasOn(m_managers.get(canvasType)); // Le canvas s'attache au manager
					if(m_mouseEditor.dropCanvasOn(m_canvasContainer)) //On drop le canvas dans canvasContainer
					{
						m_lastDroppedIndice = m_canvasContainer.size()-1;
					}
					
					m_mouseEditor.checkIfDropIsFinished(); // Regarde s'il faut maintenir le canvas actif ou si la souris peut briser le lien qu'elle a sur le canvas actif. 
					//m_mouseEditor.applyDropStrategy(positionDrop);
				}
				else
				{
					m_mouseEditor.applyDropStrategy(positionDrop); // Effectue des modifications (si besoin) sur le canvas actif (scale, ...)
					m_mouseEditor.checkIfDropIsFinished();
				}

			}
			else if(canvasType == "player")
			{
				m_player = (Player) m_mouseEditor.getPlaceable();
				m_mouseEditor.setCanvasPosition( positionDrop );
				m_mouseEditor.dropCanvasOn(m_canvasContainer);
				m_mouseEditor.applyDropStrategy(positionDrop);
				m_mouseEditor.checkIfDropIsFinished();
				m_lastDroppedIndice = -1;
			}
			else
			{
				System.out.println("ERROR : EditorSceen : dropCanvasOnSceen : aucun manager ne correspond au type de canvas : "+canvasType+" le'element du canvas ne peut pas s'attacher � un manager");
			}
			
			
			//System.out.println("canvas dropp� � la position : "+ positionDrop.toString() +" dans le manager "+canvasType+" ce manager contient alors "+m_managers.get(canvasType).toString() );
		}
		else if(!m_mouseEditor.hasCanvas())
		{
			Iterator<CanvasInterface> it = m_canvasContainer.iterator();
			while(it.hasNext())
			{
				CanvasInterface canvas = it.next();
				
				if(canvas.getCollider().contains(positionDrop))
				{
					System.out.println("La souris est dans un element de l'editeur.");
					
					m_mouseEditor.changePlaceable(canvas);
					String canvasType = m_mouseEditor.getCanvasType();
					m_mouseEditor.setCanvasPosition( mousePosition );
					
					if(m_canvasContainer.remove(canvas))
					{
						System.out.println("canvas removed");
					}
					
					
					if(canvasType == "player")
					{
						m_player = null;
						m_lastDroppedIndice = -1;
						
					}
					else
					{
						m_mouseEditor.removeCanvasOn(m_managers.get(canvasType));
						m_lastDroppedIndice = -1;
					}
	
					break;
				}
				
			}
		}

	}
	
	/**
	 * set la camera de la scene
	 * 
	 * @param camera
	 */
	public void setCamera(EditorCamera camera)
	{
		m_sceenCamera = camera;
	}
	
	/**
	 * 
	 * Sauvegarde de tous les managers dans le dossier -> filePath, les managers sont sauvegard�s dans un fichier appel� par le nom du manager (enemy, map, trigger...). 
	 * exemple :
	 * 
	 * argument : map01
	 * sauvegarde de : 
	 * 		map01/player.txt
	 * 		map01/enemy.txt
	 * 		map01/map.txt
	 * 		map01/trigger.txt
	 * 
	 * 
	 * @param filePath
	 */
	public void save(String folderPath)
	{
		m_player.save("editorSave/"+folderPath+"/player.txt");
		
		for(Entry<String, Manager<? extends Body> > entry : m_managers.entrySet() )
		{
			String filePath = "editorSave/"+folderPath+"/"+entry.getKey()+".txt";
			entry.getValue().save(filePath);
		}
		
		
		
		String filePath = "editorSave/editableMap/"+folderPath+"/canvasContainer.txt";
		Json json = new Json();
		String text = json.toJson(this);
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);// internal(filePath);
		file.writeString(text, false);


	}
	
	private void loadRessources(String folderPath)
	{
		m_player = Player.load(folderPath+"/player.txt");	
		m_managers.put("map", Map.load(folderPath+"/map.txt"));
		m_managers.put("enemy", EnemyManager.load(folderPath+"/enemy.txt"));
		m_managers.put("trigger", TriggerManager.load(folderPath+"/trigger.txt"));
		/*
		String filePath = "editorSave/editableMap/"+folderPath+"/canvasContainer.txt";
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		
		Json json = new Json();
		JsonValue jsonData = new JsonValue(fileString);
		json.fromJson(EditorSceen.class, fileString);
		*/
	}
	
	@Override
	public void write(Json json) {
		
		//json.writeObjectStart("editorSceen");
			//json.writeValue("canvasNumber", m_canvasContainer.size());
			json.writeObjectStart("canvasContainer");
				for(CanvasInterface canvas : m_canvasContainer)
				{
					canvas.write(json);
				}
			json.writeObjectEnd();
		//json.writeObjectEnd();
		
	}
	
	@Override
	public void read(Json json, JsonValue jsonData) {
		
		//JsonValue EditorSceenValue = jsonData.get("editorSceen");
		//int canvasNumber = jsonData.getInt("canvasNumber");
		JsonValue canvasValue;
		JsonValue canvasContainer;
		
		canvasContainer = jsonData.get("canvasContainer");
		System.out.println(canvasContainer.toString());
		//System.out.println("canvasContainer");
		
		for(int i = 0; i<canvasContainer.size; i++)
		{
			canvasValue = canvasContainer.get(i);
			@SuppressWarnings("unchecked")
			Canvas<? extends Body> canvas = json.fromJson((Class<Canvas<?>>)(Class<?>)Canvas.class, canvasValue.toString());
			if(m_managers.containsKey(canvas.getType()))
			{
				canvas.attachOn(m_managers.get(canvas.getType()));
			}
			else if(canvas.getType() == "player")
			{
				m_player = (Player)canvas.getElement();
			}
			else
			{
				System.out.println("ERROR : EditorSceen : read : le type de canvas est incorrect");
			}
			
			canvas.read(json, canvasValue);
		}
				
	}
	
	
//	@Override
//	public void draw(Batch batch, float alpha)
//	{
//	
//		Matrix4 tempProj = batch.getProjectionMatrix();
//		
//		batch.setProjectionMatrix(m_sceenCamera.combined);
//		
//		this.validate();
//		/*
//		for(CanvasInterface canvas : m_canvasContainer)
//		{
//			canvas.draw(batch, true);
//		}*/
//		
//		super.draw(batch, alpha);
//		
//		batch.setProjectionMatrix(tempProj);
//	}

	@Override
	public void draw(Batch batch) {
		
		//if(batch.isDrawing())
		//batch.end();
		
		//Matrix4 tempProj = batch.getProjectionMatrix();
		
		
		batch.setProjectionMatrix(m_sceenCamera.combined);
		
		if(!batch.isDrawing())
		batch.begin();
			
			for(Entry<String, Manager<? extends Body> > entity : m_managers.entrySet() )
			{
				if(entity.getValue() instanceof Map)
				{
					((Map) entity.getValue()).drawWithParralax(batch);
				}
				else
				{
					entity.getValue().draw(batch);
				}
			}
			
			if(m_player != null)
				m_player.draw(batch);
			
			for(CanvasInterface canvas : m_canvasContainer)
			{
				canvas.draw(batch, true);
			}
		
		batch.end();
		
		//batch.setProjectionMatrix(tempProj);
		
	}
	
	
}
