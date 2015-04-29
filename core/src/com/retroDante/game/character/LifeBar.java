package com.retroDante.game.character;

import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Affine2;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Animator;
import com.retroDante.game.Body;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;

public class LifeBar extends Body{

	protected String m_type;
	private Texture m_visual;
	protected TextureRegion m_texRegion;
	private int m_life;
	private int m_maxLife;
	
	LifeBar() 
	{
		this(TileSetManager.getInstance().get("hud"), 0);

	}
	
	LifeBar(int life) 
	{
		this(TileSetManager.getInstance().get("hud"), 0);
		m_life = life;
		m_maxLife = life;
	}
	LifeBar(float life) 
	{
		this(TileSetManager.getInstance().get("hud"), 0);
		m_life = (int)life;
		m_maxLife = (int)life;
	}
	
	LifeBar(TileSetInfo tileSet, int spriteIndex) 
	{
		super();
		
		m_type = "character";
		m_visual = tileSet.getTexture();
		m_texRegion = tileSet.get(spriteIndex);
		
		m_life = 10;
		m_maxLife = 10;
		
		//body : 
		this.setDimension(new Vector2(32,32));
	}
	
	
	public void setLife(int life)
	{
		m_life = life;
	}
	
	public void setMaxLife(int maxLife)
	{
		m_maxLife = maxLife;
	}
	
	public int getLife()
	{
		return m_life;
	}
	
	public int getMaxLife()
	{
		return m_maxLife;
	}

	@Override
	public int getConstructorStep() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setConstructorStep(int constructorStep) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Batch batch) {
		
		if(!batch.isDrawing())
			batch.begin();
		
		Vector2 savedPos = this.getPosition();
		int decal = (int) (this.getDimension().x * (this.getScale().x+0.1f)); 
		//transfo.translate(-this.m_life*0.5f*decal,0);
		for(int i=0; i<this.m_life; i++)
		{
			//transfo.translate(decal, 0);
			this.move(new Vector2(decal, 0));
			batch.draw(m_texRegion, this.getDimension().x, this.getDimension().y, this.getTransform() );
		}
		
		this.setPosition(savedPos);
		
	}
	
	
	
	
}
