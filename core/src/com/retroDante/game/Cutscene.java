package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class Cutscene implements Screen {

	private float m_timeToLive;
	private Screen m_nextScreen;
	private Stage m_stage;
	private Batch m_batch;
	private Element2D m_backGround;

	
	Cutscene(Screen nextScreen)
	{
		m_timeToLive = 5;
		m_nextScreen = nextScreen;
	}
	
	Cutscene(Screen nextScreen, float ttl)
	{
		m_timeToLive = ttl;
		m_nextScreen = nextScreen;
	}
	
	@Override
	public void show() {
		
		m_stage = new Stage();
		m_batch = m_stage.getBatch();
		
		m_stage.addListener(new InputListener(){
			
			@Override
			public boolean keyDown(InputEvent event, int keycode){
				
				//On passe la cinématique avec echape
				if(keycode == Keys.ESCAPE)
				{
					m_timeToLive = 0;
				}
				
				return false;
			}
			
		});
		
		m_backGround = new Element2D(TileSetManager.getInstance().get("cutscene"), 0);
		m_backGround.setAnimationSpeed(2);
	}

	private void update(float delta)
	{
		//On update que l'animation
		m_backGround.updateAnimation(delta);
		m_timeToLive -= delta;
		if(m_timeToLive <= 0)
		{
			
		}
	}
	
	private void draw()
	{
		m_backGround.draw(m_batch);
	}
	
	@Override
	public void render(float delta) {
		
		//update : 
		update(delta);
		
		//draw : 
		Gdx.gl.glClearColor(0.2f, 0.2f,0.2f, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		draw();

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
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
		m_batch.dispose();
	}
	
	
}
