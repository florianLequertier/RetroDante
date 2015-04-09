package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.math.Vector2;

public class Projectile extends Attack {

	Projectile()
	{
		super();
		m_visual.addConstantForce(new Force( new Vector2(1,0) ) );
	}
	
	Projectile(Vector2 initialForce)
	{
		super();
		m_visual.addConstantForce(new Force( initialForce ) );
	}
	
	@Override
	public boolean update(float deltaTime, List<Character> characters)
	{
		updateVisual(deltaTime);
		
		//On bouge de trigger avant d'entamer les test de collision
		Vector2 newPosition = m_visual.getPosition();
		m_trigger.setPosition(newPosition);
		
		applyDamageOn(characters);
		
		
		m_lifeTime -= deltaTime;
		if(m_lifeTime <= 0)
			return false;
		
		return true;
	}
	
}
