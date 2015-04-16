package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class EditorScreen implements Screen {
	
	private Stage m_stage;
	private EditorPicker m_editorPicker;
	
	@Override
	public void show() {
		
		m_stage = new Stage();
		Gdx.input.setInputProcessor(m_stage);
		
		m_editorPicker = new EditorPicker();
		m_stage.addActor(m_editorPicker);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
		m_stage.dispose();		
	}
	
	
	
}
