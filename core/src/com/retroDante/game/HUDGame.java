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
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;

/**
 * 
 * @author florian
 *
 */
public class HUDGame extends Table{

	private Skin m_skin;
	private HUDGamePause m_pauseMenu;
	private Cell<Table> m_centerCell;
	
	
	HUDGame(Stage parent)
	{
		super();
		
		
		this.setFillParent(true);
		this.setPosition(0,0);
		this.align(Align.center);
		this.setTouchable(Touchable.enabled);
		this.debug();
		
		parent.addListener(new InputListener(){
			
			@Override
			public boolean keyDown(InputEvent event, int keycode){
				
				if(keycode == Keys.ESCAPE)
				{	
					tooglePauseMenu();
				}
				
				return false;
			}
		});

		
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
		
		
		m_pauseMenu = new HUDGamePause(m_skin, this);
		m_centerCell = this.add().center().space(20);
		
		
		parent.addActor(this);
	
	}
	
	public void closePauseMenu()
	{
		GameManager.getInstance().togglePause(); //unpause
		m_centerCell.clearActor();
		//this.removeActor(m_pauseMenu);
	}
	
	public void openPauseMenu()
	{
		GameManager.getInstance().togglePause(); //pause
		m_centerCell.setActor(m_pauseMenu);
		
		//this.addActor(m_pauseMenu);
	}
	
	public void tooglePauseMenu()
	{
		if(this.findActor("pauseMenu") == null) 
		{
			openPauseMenu();
		}
		else
		{
			closePauseMenu();
		}
	}
	
	/*public void draw(Batch batch) {
		
		this.draw(batch, 1);
		
	} */
	
	public void dispose()
	{
		m_skin.dispose();
	}

}
