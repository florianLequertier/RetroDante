package com.retroDante.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.ButtonGroup;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener.ChangeEvent;

public class EditorPicker extends Table {

	private Skin m_skin;
	ButtonGroup<TextButton> m_typeGroup;
	ButtonGroup<TextButton> m_elementGroup;
	HorizontalGroup 
	
	EditorPicker()
	{
		//table : 
		super();
		//skin : 
		setDefaultSkin();
		placeTypeGroup();
		placeElementGroup();
		
		this.setFillParent(true);
		this.setPosition(0,0);
		this.align(Align.center);

	}
	
	EditorPicker(float x, float y)
	{
		this();
		this.setPosition(x, y);
	}
	
	void placeElementGroup()
	{
		//type picker : 
		m_typeGroup = new ButtonGroup<TextButton>();
		m_typeGroup.setMaxCheckCount(1);
		
		
		for(int j=0; j<5; j++)
		{
			for(int i=0; i<5; i++)
			{
				final TextButton button = new TextButton("elementButton", m_skin);
				m_typeGroup.add(button);
				this.add(button).space(10).width(100).height(80);
				button.addListener(new ChangeListener() {
					@Override
					public void changed (ChangeEvent event, Actor actor) {
						System.out.println("Clicked! Is checked: " + button.isChecked());
						button.setText("Good job!");
					}

				});
			}
			
			this.row();
		}

	}
	
	void placeTypeGroup()
	{
		//type picker : 
		m_typeGroup = new ButtonGroup<TextButton>();
		m_typeGroup.setMaxCheckCount(1);
		
		final TextButton button01 = new TextButton("1", m_skin);
		final TextButton button02 = new TextButton("2", m_skin);
		final TextButton button03 = new TextButton("3", m_skin);
		
		this.add(button01).space(10).width(40);
		this.add(button02).space(10).width(40);
		this.add(button03).space(10).width(40);
		
		
		button01.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button01.isChecked());
				button01.setText("Good job!");

			}

		});
		button02.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button01.isChecked());
				button01.setText("Good job!");

			}

		});
		button03.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				System.out.println("Clicked! Is checked: " + button01.isChecked());
				button01.setText("Good job!");

			}

		});
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

		// Configure a TextButtonStyle and name it "default". Skin resources are stored by type, so this doesn't overwrite the font.
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = m_skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = m_skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = m_skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = m_skin.getFont("default");
		m_skin.add("default", textButtonStyle);


	}
	
	
}
