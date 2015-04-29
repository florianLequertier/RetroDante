package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.character.Character;
import com.retroDante.game.character.Enemy;
import com.retroDante.game.trigger.DamageTrigger;

public class Attack implements Drawable, Cloneable {
	
	VisualEffect m_visual; // visuel de l'attaque
	DamageTrigger m_trigger; // zone de damage de l'attaque
	final float m_maxLifeTime; // delai au bout duquel l'attaque disparait (en secondes), reste fixe.
	float m_lifeTime; // delai au bout duquel l'attaque disparait (en secondes)
	boolean m_fromEnemy; // l'attaque provient elle d'un ennemi
	Direction m_direction; // la direction de l'attaque (Une enumeration de type Direction)
	float m_delay; //le delay entre deux attaques de ce type (en secondes)
	
	/**
	 * Attack par defaut de 10, sans visuel, 5 seconde de lifeTime, dimensions : 32*32
	 */

	Attack(float damage, float delay, float lifeTime, boolean fromEnemy)
	{
		m_trigger = new DamageTrigger(damage);
		m_trigger.setDimension(new Vector2(32,32));
		m_visual = null;
		m_maxLifeTime = 5;
		m_lifeTime = 5;
		m_direction = Direction.Right;
		m_fromEnemy = fromEnemy;  
		m_delay = delay;
	}
	
	Attack(float damage, float delay, boolean fromEnemy)
	{
		this( damage , delay , 5 , fromEnemy ); 
	}
	
	Attack(float damage, boolean fromEnemy)
	{
		this( damage , 1 , 5 , fromEnemy ); 
	}
	
	Attack()
	{
		this( 1 , true );
	}
	
	//constructeur de copie
	Attack(Attack other)
	{
		m_trigger = new DamageTrigger(other.m_trigger.getDamageAmount());
		m_trigger.setDimension(other.getDimension());
		m_visual = VisualEffectFactory.getInstance().create(other.getEffectName());
		m_lifeTime = other.m_lifeTime;
		m_maxLifeTime = other.m_maxLifeTime;
		m_direction = other.m_direction;
		m_fromEnemy = other.m_fromEnemy;; 
	}
	
	//fonction de clonage de l'objet
	public Object clone(){
		Attack att = null;
		try{
			att = (Attack)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace(System.err);
		}
		
		att.m_visual = (VisualEffect) m_visual.clone();
		att.m_trigger = (DamageTrigger) m_trigger.clone();
		
		return att;		
	}
	
	/**
	 * Update de l'attaque, update le visuel (l'element2D), puis test chaque character passés pour leur appliquer un dammage s'il y a collision
	 * Important : renvoie false si l'attack doit cesser.
	 * 
	 * @param deltaTime
	 * @param list
	 */
	public boolean update(float deltaTime, List<? extends Character> list)
	{
		updateTrigger(deltaTime);
		updateVisual(deltaTime);
		applyDamageOn(list);
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	public boolean update(float deltaTime, Character character)
	{
		updateTrigger(deltaTime);
		updateVisual(deltaTime);
		applyDamageOn(character);
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	
	/**
	 * Applique le damage de l'arme au(x) character(s) passés en paramétre
	 * 
	 * @param list
	 */
	public void applyDamageOn(List<? extends Character> list)
	{
		for(Character character : list)
		{
			if(!character.getIsInvulnerable() && character.getIsEnemy() != m_fromEnemy)
			{
				if(m_trigger.collideWith(character))
				{
					character.takeDamage(getDamage());
					System.out.println("une attaque est rentré en collision avec un character");
				}
			}
		}
	}
	public void applyDamageOn(Character character)
	{
		if(!character.getIsInvulnerable() && character.getIsEnemy() != m_fromEnemy)
		{
			if(m_trigger.collideWith(character))
			{
				character.takeDamage(getDamage());
				System.out.println("une attaque est rentré en collision avec un character");
			}
		}
	}
	
	
	public void updateTrigger(float deltaTime)
	{
		m_trigger.update(deltaTime);
	}
	
	public void updateVisual(float deltaTime)
	{
		if(m_visual != null)
		m_visual.update(deltaTime);
	}
	
	
	//getters / setters 
	
	public void setDelay(float delay)
	{
		m_delay = delay;
	}
	
	public float getDelay()
	{
		return m_delay;
	}
	
	public void setDirection(Direction direction)
	{
		m_direction = direction;
		if(direction == Direction.Left)
		{
			float width = m_trigger.getDimension().x;
			float height = m_trigger.getDimension().y;
			m_trigger.setDimension(new Vector2(-width, height));
		}
			
	}
	
	public Direction getDirection()
	{
		return m_direction;
	}
	
	public String getEffectName()
	{
		if(m_visual == null)
			return null;
		else
			return m_visual.getName();
	}
	
	public boolean getFromEnemy()
	{
		return m_fromEnemy;
	}
	
	public void setFromEnemy(boolean isEnemy)
	{
		m_fromEnemy = isEnemy;
	}
	
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
	public void draw(Batch batch) {
		m_visual.draw(batch);
	}
	//pour le debug : 
	public void drawDebug(Batch batch) {
		//m_visual.draw(batch);
		m_trigger.draw(batch); //draw du trigger en plus
	}
	
}
