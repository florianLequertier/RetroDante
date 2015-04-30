package com.retroDante.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.visualEffect.RedTintEffect;

public class EffectCamera  extends OrthographicCamera{
	
	
	RedTintEffect m_tintEffect = new RedTintEffect();
	
	public EffectCamera()
	{
		super(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}
	
	public EffectCamera(Vector2 viewportDim)
	{
		super(viewportDim.x, viewportDim.y);
	}
	
	public EffectCamera(float viewportX, float viewportY)
	{
		super(viewportX, viewportY);
	}
	
	public void activateEffect()
	{
		if(m_tintEffect != null)
		{
			m_tintEffect.play();
		}
	}
	
	public void update(float deltaTime)
	{
		update();
		m_tintEffect.update(deltaTime);
	}
	
	public void draw(Batch batch)
	{
		batch.setProjectionMatrix(this.combined);
		if(m_tintEffect != null)
			m_tintEffect.draw(batch);
	}
	
}
