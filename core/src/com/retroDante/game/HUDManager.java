package com.retroDante.game;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class HUDManager {
	
	private Stage m_stage;
	private Skin m_skin;
	private HashMap<String, Table> m_tableComponent = new HashMap<String, Table>();
	//private KeyboardListener m_keyListener;
	
	HUDManager()
	{
		//m_inputHandler = new InputProcessor();
		//Gdx.input.setInputProcessor(m_stage);
		
		//m_keyListener = new KeyboardListener(this);
		
		m_stage = new Stage();
		m_stage.addListener(new InputListener(){
			
			public boolean keyDown(ChangeEvent event, int keycode){
				
				if(keycode == Keys.ESCAPE)
				{
					//gameManager.togglePause();
					tooglePauseMenu();
				}
				
				return false;
			}
		});
		//m_stage.setKeyboardFocus(this);
	
//		if(keycode == Keys.ESCAPE)
//		{
//			//gameManager.togglePause();
//			m_hudManager.tooglePauseMenu();
//		}
		
		m_skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		m_skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		m_skin.add("default", new BitmapFont());

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = m_skin.newDrawable("white", Color.BLUE);
		textButtonStyle.checked = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.over = m_skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = m_skin.getFont("default");
		m_skin.add("default", textButtonStyle);

		
		//initialisation de la map de components stockant tout les HUDs pouvant potentiellement être appelés
		
		//menu de pause, debut : 
			Table pauseMenu = new Table();
			pauseMenu.setFillParent(true);
			pauseMenu.setPosition(0,0);
			pauseMenu.align(Align.center);
			m_tableComponent.put("pauseMenu", pauseMenu);
			//m_stage.addActor(pauseMenu);
			
			
			final TextButton button_quit = new TextButton("Quit", m_skin);
			final TextButton button_resume = new TextButton("Resume", m_skin);
			
			pauseMenu.add(button_quit).space(20).width(100).row();
			pauseMenu.add(button_resume).space(20).width(100).row();
			
			
			button_quit.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					
					GameManager.getInstance().resetGameSession();
					GameManager.getInstance().changeScreen("menu");
					
				}
	
			});
			button_resume.addListener(new ChangeListener() {
				@Override
				public void changed (ChangeEvent event, Actor actor) {
					
					closePauseMenu();
					
				}
	
			});
		
		//menu de pause, fin
	}
	
	public void closePauseMenu()
	{
		GameManager.getInstance().togglePause(); //unpause
		m_stage.getRoot().removeActor(m_tableComponent.get("pauseMenu"));
	}
	
	public void openPauseMenu()
	{
		GameManager.getInstance().togglePause(); //pause
		m_stage.addActor(m_tableComponent.get("pauseMenu"));
	}
	
	public void tooglePauseMenu()
	{
		if(!m_stage.getActors().contains(m_tableComponent.get("pauseMenu"), false)) //si le pauseMenu n'est pas dans le stage
		{
			openPauseMenu();
		}
		else
		{
			closePauseMenu();
		}
	}

	public void draw() {
		
		m_stage.draw();
		
	}
	
	public Stage getStage()
	{
		return m_stage;
	}
	
	public void dispose()
	{
		m_stage.dispose();
		m_skin.dispose();
		m_tableComponent.clear();
	}

}
