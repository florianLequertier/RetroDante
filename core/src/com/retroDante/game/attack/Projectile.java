package com.retroDante.game.attack;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Force;
import com.retroDante.game.character.Character;

public class Projectile extends Attack {

	Projectile()
	{
		super();
		m_trigger.addConstantForce(new Force( new Vector2(1,0) ) );
	}
	
	Projectile(Vector2 initialForce)
	{
		super();
		m_trigger.addConstantForce(new Force( initialForce ) );
	}
	
	@Override
	public boolean update(float deltaTime, List<? extends Character> characters)
	{
		updateTrigger(deltaTime);
		updateVisual(deltaTime);
		
		//On bouge de trigger avant d'entamer les test de collision
		Vector2 newPosition = m_trigger.getPosition();
		m_visual.setPosition(newPosition);
		
		applyDamageOn(characters);
		
		
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	
	@Override
	public boolean update(float deltaTime, Character character)
	{
		updateTrigger(deltaTime);
		updateVisual(deltaTime);
		
		//On bouge de trigger avant d'entamer les test de collision
		Vector2 newPosition = m_trigger.getPosition();
		m_visual.setPosition(newPosition);
		
		applyDamageOn(character);
		
		
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	
}
