package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Attack implements Drawable {
	
	Element2D m_visual;
	DamageTrigger m_trigger;
	float m_lifeTime;
	
	/**
	 * Attack par defaut de 10, sans visuel, 5 seconde de lifeTime, dimensions : 32*32
	 */
	Attack()
	{
		m_trigger = new DamageTrigger(10);
		m_trigger.setDimension(new Vector2(32,32));
		m_visual = null;
		m_lifeTime = 5;
	}
	
	/**
	 * Update de l'attaque, update le visuel (l'element2D), puis test chaque character pass�s pour leur appliquer un dammage s'il y a collision
	 * Important : renvoie false si l'attack doit cesser.
	 * 
	 * @param deltaTime
	 * @param characters
	 */
	public boolean update(float deltaTime, List<Character> characters)
	{
		updateVisual(deltaTime);
		applyDamageOn(characters);
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	
	public void applyDamageOn(List<Character> characters)
	{
		for(Character character : characters)
		{
			if(m_trigger.collideWith(character))
			applyDamageOn(character);
		}
	}
	
	public void applyDamageOn(Character character)
	{
		character.takeDamage(getDamage());
	}
	
	public void updateVisual(float deltaTime)
	{
		if(m_visual != null)
		m_visual.update(deltaTime);
	}
	
	//getters / setters 
	
	public void setDamage(float damageAmount)
	{
		m_trigger.setDamage(damageAmount);
	}
	
	public float getDamage()
	{
		return m_trigger.getDamageAmount();
	}
	
	public void setPosition(Vector2 position)
	{
		if(m_visual != null)
		m_visual.setPosition(position);
		
		m_trigger.setPosition(position);
	}
	
	public Vector2 getPosition()
	{
		return m_trigger.getPosition();
	}
	
	public void setDimension(Vector2 dimension)
	{
		if(m_visual != null)
		m_visual.setDimension(dimension);
		
		m_trigger.setDimension(dimension);
	}
	
	public Vector2 getDimension()
	{
		return m_trigger.getDimension();
	}
	
	//drawable : 
	@Override
	public void draw(SpriteBatch batch) {
		m_visual.draw(batch);
	}
	//pour le debug : 
	public void drawDebug(SpriteBatch batch) {
		m_visual.draw(batch);
		m_trigger.draw(batch); //draw du trigger en plus
	}
	
}