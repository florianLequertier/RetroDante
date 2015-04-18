package com.retroDante.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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

public class EditorSceen extends Table{

	private MouseEditor m_mouseEditor; 
	private List<CanvasInterface> m_canvasContainer;
	private Player m_player; 
	private HashMap<String, Manager<? extends Body> > m_managers;
	
	EditorSceen()
	{
		m_canvasContainer = new ArrayList<CanvasInterface>();
		m_managers = new HashMap<String, Manager<? extends Body> >();
		
		this.setFillParent(true);
		this.setPosition(0,0);
		this.align(Align.center);
		this.debug();
		this.setTouchable(Touchable.enabled);
		
		this.addListener( new ClickListener(){
			@Override
			public boolean touchDown(InputEvent event, float x, float y, int pointer, int button){
				
				if(m_mouseEditor != null)
					dropCanvasOnSceen();
				
				return false;
			}
			
		});
		
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
	
	public void setMouse(MouseEditor mouse)
	{
		m_mouseEditor = mouse;
	}
	
	public void dropCanvasOnSceen()
	{
		if(m_mouseEditor != null)
		{

			String canvasType = m_mouseEditor.getCanvasType();
			if(m_managers.containsKey(canvasType))
			{
				m_mouseEditor.attachCanvasOn(m_managers.get(canvasType));
			}
			else
			{
				System.out.println("ERROR : EditorSceen : dropCanvasOnSceen : aucun manager ne correspond au type de canvas : "+canvasType+" le'element du canvas ne peut pas s'attacher à un manager");
			}
			
			m_mouseEditor.dropCanvasOn(m_canvasContainer);
		}
		else
		{
			System.out.println("WARNING : EditorSceen : dropCanvasOnSceen : le pointeur vers la mouseEditor n'a pas été initialisé.");
		}
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
		m_player.save(folderPath+"/player.txt");
		
		for(Entry<String, Manager<? extends Body> > entry : m_managers.entrySet() )
		{
			String filePath = folderPath+"/"+entry.getKey()+".txt";
			entry.getValue().save(filePath);
		}
	}
	
	@Override
	public void draw(Batch batch, float alpha)
	{
		this.validate();
		
		for(CanvasInterface canvas : m_canvasContainer)
		{
			canvas.draw(batch);
		}
		
		super.draw(batch, alpha);
	}
}
