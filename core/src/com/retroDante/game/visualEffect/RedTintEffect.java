package com.retroDante.game.visualEffect;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;

public class RedTintEffect extends VisualEffect {

	TextureRegion m_visual;
	
	public RedTintEffect()
	{
		super();
		
		m_name = "redTint";
		m_child = null;
		m_isActive = true;
		m_isLooping = false;
		m_lifeTime = 1;
		m_remainingTime = m_lifeTime;
		
		
		TileSetManager test01 = TileSetManager.getInstance();
		TileSetInfo test02 = TileSetManager.getInstance().get("postprodEffect");
		
		m_visual = TileSetManager.getInstance().get("postprodEffect").get(0);
	}
	
	public void setVisual(TextureRegion visual)
	{
		m_visual = visual;
	}
	
	@Override
	public void play() 
	{
		m_remainingTime = m_lifeTime;
		m_isActive = true;
	}

	@Override
	public void draw(Batch batch) 
	{
		if(this.m_isActive && m_visual != null)
		{
			float timer = m_lifeTime - m_remainingTime;
			
			Color sreenColor = Color.WHITE;
			
			if(timer > 0.5f)
			{
				sreenColor = Color.WHITE;
				sreenColor.mul(1.f, 1.f, 1.f, timer*2.f - 1.f);
			}
			else
			{
				sreenColor = Color.WHITE;
				sreenColor.mul(1.f, 1.f, 1.f, 1.f - timer*2.f);
			}
			
			batch.setColor(sreenColor);
			
				batch.draw(m_visual, -Gdx.graphics.getWidth()*0.5f, -Gdx.graphics.getHeight()*0.5f, Gdx.graphics.getWidth() + 20, Gdx.graphics.getHeight() + 20 );
			
			batch.setColor(1,1,1,1);
		}
			
	}
	
	
	@Override
	public int getConstructorStep() 
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setConstructorStep(int constructorStep) 
	{
		// TODO Auto-generated method stub
		
	}

	
	
}
