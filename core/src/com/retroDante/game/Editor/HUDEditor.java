package com.retroDante.game.Editor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.retroDante.game.GameManager;

public class HUDEditor extends Table{
	
	private Skin m_skin;
	private HUDEditorPause m_pauseMenu;
	private Cell<Table> m_centerCell;
	private EditorSceen m_sceenPtr;
	private EditorPicker m_editorPicker;
	
	
	HUDEditor(Stage parent, EditorSceen sceenPtr, MouseEditor mouse)
	{
		super();
		
		m_sceenPtr = sceenPtr;
		
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
		
		TextFieldStyle textFieldStyle = new TextFieldStyle();
		textFieldStyle.font = m_skin.getFont("default");
		textFieldStyle.focusedBackground = m_skin.newDrawable("white", Color.LIGHT_GRAY);
		textFieldStyle.background = m_skin.newDrawable("white", Color.DARK_GRAY);
		textFieldStyle.disabledBackground = m_skin.newDrawable("white", Color.DARK_GRAY);
		textFieldStyle.fontColor = Color.WHITE;
		textFieldStyle.selection = m_skin.newDrawable("white", Color.BLUE);
		textFieldStyle.cursor = m_skin.newDrawable("white", Color.WHITE);
		m_skin.add("default", textFieldStyle);
		
		
		m_pauseMenu = new HUDEditorPause(m_skin, this);
		m_centerCell = this.add().center().space(10);
		
		//Cell<Table> leftCell =  this.add().left().space(10);
		m_editorPicker = new EditorPicker();
		//leftCell.setActor(m_editorPicker);
		this.addActor(m_editorPicker);
		m_editorPicker.setMouse(mouse);
		
		m_editorPicker.setScreenPointer(sceenPtr);
		
		parent.addActor(this);
	
	}
	
	public void onResize()
	{
		m_editorPicker.setPosition(Gdx.graphics.getWidth() - 350, Gdx.graphics.getHeight() - 500);
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
	
	/**
	 * appel la fonction save de l'editorScreen
	 */
	public void callSave(String folderName)
	{
		m_sceenPtr.save(folderName);
	}
	
	/*public void draw(Batch batch) {
		
		this.draw(batch, 1);
		
	} */
	
	public void dispose()
	{
		m_skin.dispose();
	}
}
