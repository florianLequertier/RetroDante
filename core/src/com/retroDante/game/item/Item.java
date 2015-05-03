package com.retroDante.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Animator;
import com.retroDante.game.Element2D;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;
import com.retroDante.game.trigger.Trigger;
import com.retroDante.game.visualEffect.VisualEffect;

public abstract class Item extends Element2D {
	
	protected Trigger m_trigger;
	protected VisualEffect m_visualEffect;
	protected boolean m_isAlive = true;
	
	public Item(Texture tex)
	{
		super(tex);
		this.makeStaticBody();
		this.setIsSolid(false);

	}
	public Item(TileSetInfo tileSet, int spriteIndex)
	{
		super(tileSet, spriteIndex);
		this.makeStaticBody();
		this.setIsSolid(false);
		
		m_texRegion = tileSet.get(0);
		m_animator = new Animator(tileSet.getForAnimation(0)); //créé une list avec les trois premiere ligne du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(0.5f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
	}
	public Item()
	{
		super(TileSetManager.getInstance().get("item"), 0);
		this.makeStaticBody();
		this.setIsSolid(false);
		
		m_texRegion = TileSetManager.getInstance().get("item").get(0);
		m_animator = new Animator(TileSetManager.getInstance().get("item").getForAnimation(0)); //créé une list avec les trois premiere ligne du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(0.5f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
	}
	
	/**
	 * Déclenchement de l'effet de l'item quand le joueur marche dessus
	 * return true si le joueur a bien touché le trigger
	 * 
	 * @return
	 */
	public boolean OnItemEnter(com.retroDante.game.character.Character character)
	{
		
		if(this.m_trigger.collideWith(character))
		{
			this.m_trigger.triggerEventOn(character);
			return true;
		}
		else 
			return false;
		
	}
	
	public boolean isAlive()
	{
		return m_isAlive;
	}
	
	//Override transformations 
	@Override
	public void setPosition(Vector2 position)
	{
		
		super.setPosition(position);
		
		if(this.m_trigger != null)
			m_trigger.setPosition(position);
		
		if(this.m_visualEffect != null)
			m_visualEffect.setPosition(position);
		
	}
	
	
	@Override
	public void setDimension(Vector2 dimension)
	{
		super.setDimension(dimension);
		
		if(this.m_trigger != null)
			m_trigger.setDimension(dimension);
		
		if(this.m_visualEffect != null)
			m_visualEffect.setDimension(dimension);
	}
	
	@Override
	public void setScale(Vector2 scaleFactor)
	{
		super.setScale(scaleFactor);
		
		if(this.m_trigger != null)
			m_trigger.setScale(scaleFactor);
		
		if(this.m_visualEffect != null)
			m_visualEffect.setScale(scaleFactor);
	}
	
	
	@Override
	public void update(float deltaTime)
	{
		super.update(deltaTime);
		
		if(this.m_visualEffect != null)
			this.m_visualEffect.update(deltaTime);
		
		if(this.m_trigger != null)
			this.m_trigger.update(deltaTime);
		
	}

	public void draw(Batch batch)
	{
		super.draw(batch);
		
		if(this.m_visualEffect != null)
		{
			m_visualEffect.draw(batch);
		}
	}
	
	public void drawDebug(Batch batch)
	{
		super.draw(batch);
		
		if(this.m_visualEffect != null)
		{
			m_visualEffect.draw(batch);
		}
		
		if(this.m_trigger != null)
		{
			m_trigger.draw(batch);
		}
	}
	
}
