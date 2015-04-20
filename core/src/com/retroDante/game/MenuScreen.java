package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
	
	private Stage m_stage;
	private Batch m_batch;
	private Texture m_background;
	private ShapeRenderer  m_shapeRenderer;
	private Skin m_skin;
	
	MenuScreen()
	{
		m_stage = new Stage();
		m_batch = m_stage.getBatch();
		m_background = null;
		
		Gdx.input.setInputProcessor(m_stage);
		
		m_shapeRenderer = new ShapeRenderer(); 
		
		
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


		Table table = new Table();
		table.setFillParent(true);
		table.setPosition(0,0);
		table.align(Align.center);
		m_stage.addActor(table);
		
		
		final TextButton button01 = new TextButton("Play", m_skin);
		TextButton button02 = new TextButton("Editor", m_skin);
		TextButton button03 = new TextButton("test02", m_skin);
		
		table.add(button01).space(20).width(100).row();
		table.add(button02).space(20).width(100).row();
		table.add(button03).space(20).width(100).row();
		
		
		button01.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button01.setChecked(false);
				
				GameManager.getInstance().changeScreen("test");
			}

		});
		button02.addListener(new ChangeListener() {
			@Override
			public void changed (ChangeEvent event, Actor actor) {
				button01.setChecked(false);
				
				GameManager.getInstance().changeScreen("editor");
			}

		});
		
	}
	
	@Override
	public void show() {
		
		String localPath = Gdx.files.getLocalStoragePath();
		m_background = new Texture(localPath+"/asset/"+"texture/wall.jpg");
		
	}

	@Override
	public void render(float delta) {
		
		 Gdx.gl.glClearColor(0.8f, 0.8f, 0.8f, 1);
	     Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	         
	     m_batch.begin();
	        m_batch.draw(m_background, 0, 0,Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	     m_batch.end();
	         
	     m_stage.act(Gdx.graphics.getDeltaTime());
	     m_stage.draw(); 
	     
		
	}

	@Override
	public void resize(int width, int height) {
		m_stage.getViewport().update(width, height, true);
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		m_shapeRenderer.dispose();
		m_stage.dispose();		
	}

}
