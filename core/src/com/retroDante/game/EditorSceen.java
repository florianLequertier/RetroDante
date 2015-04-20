package com.retroDante.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.retroDante.game.character.EnemyManager;
import com.retroDante.game.character.Player;
import com.retroDante.game.map.Map;
import com.retroDante.game.trigger.TriggerManager;

public class EditorSceen extends InputAdapter implements Drawable{

	private MouseEditor m_mouseEditor; 
	private List<CanvasInterface> m_canvasContainer;
	private Player m_player; 
	private HashMap<String, Manager<? extends Body> > m_managers;
	private OrthographicCamera m_sceenCamera;
	private int m_lastDroppedIndice;
	
	EditorSceen()
	{
		m_canvasContainer = new ArrayList<CanvasInterface>();
		m_managers = new HashMap<String, Manager<? extends Body> >();
		m_sceenCamera = new OrthographicCamera();
		m_lastDroppedIndice = -1;
		
//		this.setFillParent(true);
//		this.setPosition(0,0);
//		//this.setSize(500,500);
//		this.align(Align.center);
//		this.debug();
//		this.setTouchable(Touchable.enabled);
//		this.defaults().space(5).maxSize(90, 70).minSize(80, 60);
			

		
//		this.addListener( new InputListener(){
//			@Override
//			public boolean keyDown(InputEvent event, int keycode){
//				
//				if(keycode == Keys.Q || keycode == Keys.A)
//					m_sceenCamera.translate(new Vector2(-1,0) );
//				if(keycode == Keys.Z || keycode == Keys.W)
//					m_sceenCamera.translate(new Vector2(0,1) );
//				if(keycode == Keys.D)
//					m_sceenCamera.translate(new Vector2(1,0) );
//				if(keycode == Keys.S)
//					m_sceenCamera.translate(new Vector2(0,-1) );
//				
//				System.out.println("La camera est maintenant en ("+m_sceenCamera.position.x+", "+m_sceenCamera.position.y+")" );
//				
//				return false;
//            }
//			
//		});
		
		initAll();
	}

	
	/**
	 * Initialise tous les managers de la scene 
	 */
	void initManagers()
	{
		m_managers.put("enemy", new EnemyManager());
		m_managers.put("map", new Map());
		m_managers.put("trigger", new TriggerManager());
	}
	
	/**
	 * initialise la scene
	 */
	void initAll()
	{
		m_player = new Player();
		initManagers();
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
			System.out.println("WARNING : EditorSceen : dropCanvasOnSceen : le pointeur vers la mouseEditor n'a pas été initialisé.");
			return;
		}
		
		if(m_mouseEditor.hasCanvas())
		{
			
			String canvasType = m_mouseEditor.getCanvasType();
			if(m_managers.containsKey(canvasType))
			{
				if(	m_lastDroppedIndice==-1 || !m_mouseEditor.testCanvasEquality( m_canvasContainer.get(m_lastDroppedIndice)) )
				{
					m_mouseEditor.setCanvasPosition( positionDrop );
					m_mouseEditor.attachCanvasOn(m_managers.get(canvasType));
					if(m_mouseEditor.dropCanvasOn(m_canvasContainer))
					{
						m_lastDroppedIndice = m_canvasContainer.size()-1;
					}
				}
				else
				{
					m_mouseEditor.applyDropStrategy(positionDrop);
				}

			}
			else
			{
				System.out.println("ERROR : EditorSceen : dropCanvasOnSceen : aucun manager ne correspond au type de canvas : "+canvasType+" le'element du canvas ne peut pas s'attacher à un manager");
			}
			
			
			System.out.println("canvas droppé à la position : "+ positionDrop.toString() +" dans le manager "+canvasType+" ce manager contient alors "+m_managers.get(canvasType).toString() );
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
					
					m_mouseEditor.removeCanvasOn(m_managers.get(canvasType));
					m_lastDroppedIndice = -1;
					
					
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
	public void setCamera(OrthographicCamera camera)
	{
		m_sceenCamera = camera;
	}
	
	/**
	 * 
	 * Sauvegarde de tous les managers dans le dossier -> filePath, les managers sont sauvegardés dans un fichier appelé par le nom du manager (enemy, map, trigger...). 
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
		
		Matrix4 tempProj = batch.getProjectionMatrix();
		
		batch.setProjectionMatrix(m_sceenCamera.combined);
			m_player.draw(batch);
			for(Entry<String, Manager<? extends Body> > entity : m_managers.entrySet() )
			{
				entity.getValue().draw(batch);
			}
			
			for(CanvasInterface canvas : m_canvasContainer)
			{
				canvas.draw(batch, true);
			}
			
		batch.setProjectionMatrix(tempProj);
		
	}
	
	
}
