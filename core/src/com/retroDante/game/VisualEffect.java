package com.retroDante.game;

import com.badlogic.gdx.math.Vector2;
import java.util.Iterator;

public abstract class VisualEffect implements Drawable, Iterable<VisualEffect> {
	
	protected String m_name;
	protected VisualEffect m_child;
	protected boolean m_isActive;
	protected boolean m_isLooping;
	protected float m_lifeTime;
	protected float m_remainingTime;
	
	
	VisualEffect()
	{
		m_name = "noEffect";
		m_child = null;
		m_isActive = true;
		m_isLooping = false;
		m_lifeTime = 5;
		m_remainingTime = m_lifeTime;
	}
	
	
	abstract public void setPosition(Vector2 position);
	abstract public Vector2 getPosition();
	abstract public void setDimension(Vector2 dimension);
	abstract public void getDimension();
	
	abstract public void play();
	public void update(float deltaTime)
	{
		
		if(m_child != null)
			m_child.update(deltaTime);
			
		m_remainingTime -= deltaTime;
		if(m_remainingTime <= 0)
		{
			if(m_isLooping)
				m_remainingTime = m_lifeTime;
			else
				m_isActive = false;
		}
			
	}
	
	
	//setters /getters : 
	
	public void setName(String name)
	{
		m_name = name;
	}
	
	public String getName()
	{
		return m_name;
	}
	
	public float getRemainingTime()
	{
		return m_remainingTime;
	}
	
	public void setRemainingTime(float time)
	{
		m_remainingTime = time;
	}
	
	public float getLifeTime()
	{
		return m_lifeTime;
	}
	
	public void setLifeTime(float lifeTime)
	{
		m_lifeTime = lifeTime;
	}
	
	public boolean getIsActive()
	{
		return m_isActive;
	}
	
	public void setIsActive(boolean state)
	{
		m_isActive = state;
	}
	
	public boolean getIsLooping()
	{
		return m_isLooping;
	}
	
	public void setIsLooping(boolean state)
	{
		m_isLooping = state;
	}
	
	public void setChild(VisualEffect effect)
	{
		m_child = effect;
	}
	
	public VisualEffect getChild()
	{
		return m_child;
	}
	
	public boolean hasChild()
	{
		if(m_child == null)
			return false;
		else 
			return true;
	}
	
	public Iterator<VisualEffect> iterator()
	{
		return new VisualEffectIterator(this);
	}
	
	/**
	 * Ajoute un effet à la fin de la liste
	 * 
	 * @param effect
	 */
	public void append(VisualEffect effect)
	{
		
		if(this.getChild() == null)
		{
			this.setChild(effect);
			
			return;
		}
		
		
		Iterator<VisualEffect> it = this.iterator();
		
		VisualEffect current = null;
		while(it.hasNext())
		{
			current = it.next();	
		}
		if(current.hasChild())
		{
			current = current.getChild();
			if(!current.hasChild())
			{
				current.setChild(effect);
				System.out.println("ajout d'un enfant");
			}

		}
	}
	
	
	
}
