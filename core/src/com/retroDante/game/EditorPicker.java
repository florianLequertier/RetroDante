package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class EditorPicker extends Table {

	private Skin m_skin;
	private ButtonGroup<TextButton> m_typeGroup;
	private ButtonGroup<TextButton> m_elementsGroup;
	private Table m_typePanel;
	private Table m_elementsPanel; //map...
	
	EditorPicker()
	{
		//table : 
		super();
		//skin : 
		setDefaultSkin();
		
		//placeTypeGroup();
		//placeElementGroup();

		m_typePanel = new Table();
		m_elementsPanel = new Table();
		m_typeGroup = new ButtonGroup<TextButton>();
		m_elementsGroup = new ButtonGroup<TextButton>();
		
		
		placeAll();
		
		
		this.setFillParent(false);
		this.setPosition(280,0);
		this.setSize(350,500);
		this.align(Align.center);


		this.debug();
		m_typePanel.debug();
		m_elementsPanel.debug();
		
		

	}
	
	EditorPicker(float x, float y)
	{
		this();
		this.setPosition(x, y);
	}
	
	void placeTypePanel()
	{
		//initialisation du buttonGroup : 
		m_typeGroup.setMaxCheckCount(1);
		
		String localPath = Gdx.files.getLocalStoragePath();
		Texture bg_tex = new Texture(localPath+"/asset/"+"texture/wall.jpg");
		TextureRegion bg_reg = new TextureRegion(bg_tex);
		TextureRegionDrawable bg = new TextureRegionDrawable(bg_reg);
		
		//ajout des boutons à l'horizontal panel : 
		final TextButton button_enemies = new TextButton("11111111", m_skin);
		button_enemies.background(bg);
		final TextButton button_map = new TextButton("2", m_skin);
		button_map.background(bg);
		final TextButton button_triggers = new TextButton("3", m_skin);
		final TextButton button_player = new TextButton("3", m_skin);
		
		m_typePanel.defaults().space(5).maxSize(90, 70).minSize(80, 60);
		m_typePanel.add(button_enemies);
			m_typeGroup.add(button_enemies);
		m_typePanel.add(button_map);
			m_typeGroup.add(button_map);
		m_typePanel.add(button_triggers);
			m_typeGroup.add(button_triggers);
		m_typePanel.add(button_player);
			m_typeGroup.add(button_player);



		button_enemies.addListener(new ChangeListener() {
			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button_enemies.isChecked());
				button_enemies.setText("Good job!");

			}

		});
		button_map.addListener(new ChangeListener() {
			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button_map.isChecked());
				button_map.setText("Good job!");

			}

		});
		button_triggers.addListener(new ChangeListener() {
			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button_triggers.isChecked());
				button_triggers.setText("Good job!");

			}

		});
		button_player.addListener(new ChangeListener() {
			
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button_player.isChecked());
				button_player.setText("Good job!");

			}

		});
		
		this.add(m_typePanel);
	}
	
	void placeElementsPanel()
	{
		//type picker : 
		m_typeGroup = new ButtonGroup<TextButton>();
		m_typeGroup.setMaxCheckCount(1);


		for(int j=0, k=0; j<6; j++)
		{
			for(int i=0; i<4; i++, k++)
			{
				//String name = "element"+Integer.toString(k);
				final TextButton button = new TextButton("elementButton", m_skin, "element1");
				
				m_elementsPanel.add(button).space(5).maxSize(90, 70).minSize(80, 60);
					m_elementsGroup.add(button);
				
				button.addListener(new ChangeListener() {
					@Override
					public void changed (ChangeEvent event, Actor actor) {
						System.out.println("Clicked! Is checked: " + button.isChecked());
						button.setText("Good job!");
					}

				});
				
			}

			m_elementsPanel.row();
		}
		
		//m_elementsPanel.setFillParent(true);
		this.add(m_elementsPanel);
	}
	
	void placeAll()
	{
		placeTypePanel();
		this.row();
		placeElementsPanel();
	}
	
	
	void setDefaultSkin()
	{
		m_skin = new Skin();
		// Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		m_skin.add("white", new Texture(pixmap));

		// Store the default libgdx font under the name "default".
		m_skin.add("default", new BitmapFont());
		
		
		//créé tous les styles des boutons :
		
		//boutons des elements : 
		TileSetInfo tileSet = TileSetManager.getInstance().get("platform");

		TileSetIterator it = (TileSetIterator) tileSet.iterator();
		int index = 0;
		while(it.hasNext())
		{
			TextureRegion tex = it.next();
			String name = "element"+Integer.toString(index);
			m_skin.add( name , tex);
			
			TextButtonStyle textButtonStyle = new TextButtonStyle();
			textButtonStyle.up = m_skin.getDrawable(name);
			textButtonStyle.down = m_skin.newDrawable(name, Color.DARK_GRAY);
			textButtonStyle.checked = m_skin.newDrawable(name, Color.BLUE);
			textButtonStyle.over = m_skin.newDrawable(name, Color.LIGHT_GRAY);
			textButtonStyle.font = m_skin.getFont("default");
			m_skin.add(name, textButtonStyle);
			
			index++;
		}

		
		//boutons par defaut : 
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = m_skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = m_skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = m_skin.getFont("default");
		m_skin.add("default", textButtonStyle);
		
		

	}
	
	
}
