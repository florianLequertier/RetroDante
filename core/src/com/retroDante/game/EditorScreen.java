package com.retroDante.game;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
	private EditorCamera m_camera;
	
	@Override
	public void show() {
		
		m_tileSetManager.load(Gdx.files.getLocalStoragePath()+"/asset/"+"textureInfo/tileSetManagerLoader.txt");
		System.out.println(m_tileSetManager.toString());
		
		m_stage = new Stage();
		
		m_camera = new EditorCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
					
			
		m_mouse = new MouseEditor();
		
		m_sceen = new EditorSceen();
			//m_stage.addActor(m_sceen);
			m_sceen.setMouse(m_mouse);
			m_sceen.setCamera(m_camera);
		
		m_editorPicker = new EditorPicker();
			m_stage.addActor(m_editorPicker);
			m_editorPicker.setMouse(m_mouse);
			
		m_batch = m_stage.getBatch();
			InputMultiplexer inputHandler = new InputMultiplexer();
				inputHandler.addProcessor(m_stage);
				inputHandler.addProcessor(m_camera);
				inputHandler.addProcessor(m_sceen);
				Gdx.input.setInputProcessor(inputHandler);
		
		
	}
	
	
	private void update(float delta)
	{
		m_mouse.update();
		m_camera.updateMovements();
		m_sceen.update(delta);
	}
	
	private void draw()
	{
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		m_batch.begin();
			m_mouse.draw(m_batch);
			m_sceen.draw(m_batch);
		m_batch.end();
		
		m_stage.act(Gdx.graphics.getDeltaTime());
		m_stage.draw();
		
		
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
