package com.retroDante.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class EditorScreen implements Screen {
	
	private Stage m_stage;
	private EditorPicker m_editorPicker;
	private TileSetManager m_tileSetManager = TileSetManager.getInstance();
	private Batch m_batch;
	private MouseEditor m_mouse;
	private EditorSceen m_sceen;
	
	@Override
	public void show() {
		
		m_tileSetManager.load(Gdx.files.getLocalStoragePath()+"/asset/"+"textureInfo/tileSetManagerLoader.txt");
		System.out.println(m_tileSetManager.toString());
		
		m_stage = new Stage();
		Gdx.input.setInputProcessor(m_stage);
		m_batch = m_stage.getBatch();
		
		m_mouse = new MouseEditor();
		

		
		m_sceen = new EditorSceen();
			m_stage.addActor(m_sceen);
			m_sceen.setMouse(m_mouse);
			
		m_editorPicker = new EditorPicker();
			m_stage.addActor(m_editorPicker);
			m_editorPicker.setMouse(m_mouse);
		
		
	}
	
	
	private void update(float delta)
	{
		m_mouse.update();
	}
	
	private void draw()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		
		m_stage.act(Gdx.graphics.getDeltaTime());
		m_stage.draw();
		
		m_batch.begin();
			m_mouse.draw(m_batch);
		m_batch.end();
	}

	@Override
	public void render(float delta) {
		
		//update de la logique :
		update(delta);
		
		//dessin : 
		draw();
		
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
		m_tileSetManager.clear();
		m_stage.dispose();		
	}
	
	
	
}
