package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.trigger.DamageTrigger;

public class BurningHearthquake extends Attack implements Cloneable{
	
	int m_step = 0;
	int m_totalStep = 7;
	float m_intitialAmountOfTime;
	
	BurningHearthquake()
	{
		super();
		
		Vector2 position = new Vector2(0,0);
		
		m_trigger = new DamageTrigger(2);
		
		m_trigger.setDimension(new Vector2(32,64));
		m_trigger.setPosition(position);
		
		m_lifeTime = 2;
		
		AnimatedEffect effect = (AnimatedEffect) VisualEffectFactory.getInstance().create("flameColumn");
//				new AnimatedEffect("effect", 0);
//		effect.setLifeTime(m_lifeTime);
//		effect.setPosition(position);
//		effect.setDimension(new Vector2(32,64));
//		effect.setAnimationSpeed(0.05f);
//		effect.setName();
		
		effect.setLifeTime(m_lifeTime);
		effect.setPosition(position);
		m_visual = effect;
		
		m_intitialAmountOfTime = m_lifeTime;
	}
	
	BurningHearthquake(Vector2 position)
	{
		super();
		m_trigger = new DamageTrigger(2);
		
		m_trigger.setDimension(new Vector2(32,64));
		m_trigger.setPosition(position);
		
		m_lifeTime = 2;
		
		AnimatedEffect effect = (AnimatedEffect) VisualEffectFactory.getInstance().create("flameColumn");
		//		new AnimatedEffect("effect", 0);
		//effect.setLifeTime(m_lifeTime);
		//effect.setPosition(position);
		//effect.setDimension(new Vector2(32,64));
		//effect.setAnimationSpeed(0.05f);
		//effect.setName();
		
		effect.setLifeTime(m_lifeTime);
		effect.setPosition(position);
		m_visual = effect;
		
		m_intitialAmountOfTime = m_lifeTime;
	}
	
	BurningHearthquake(BurningHearthquake other)
	{
		super(other);
		m_step = other.m_step;
		m_totalStep = other.m_totalStep;
		m_intitialAmountOfTime = other.m_intitialAmountOfTime;
	}
	
	public Object clone(){
		BurningHearthquake att = null;
		
		att = (BurningHearthquake) super.clone();

		return att;
	}
	
	
	/**
	 * Change la dimension du trigger, rajoute un effet
	 * 
	 */
	@Override
	public void updateVisual(float deltaTime)
	{
		super.updateVisual(deltaTime);
		

		if( (m_lifeTime/m_intitialAmountOfTime) < 1- m_step/( (float)m_totalStep * 2)  && (m_lifeTime/m_intitialAmountOfTime) >= m_step/( (float)m_totalStep ))
		{
			float decal = 30;
			if(m_direction == Direction.Left)
				decal *= -1;
			
			Vector2 currentDimension = m_trigger.getDimension();
				m_trigger.setDimension( currentDimension.add(decal, 0) );
			
			Vector2 currentPosition = m_visual.getPosition();
			AnimatedEffect effect = (AnimatedEffect) VisualEffectFactory.getInstance().create("flameColumn");
//					new AnimatedEffect("effect", 0);
//			effect.setLifeTime(m_lifeTime);
//			effect.setPosition(currentPosition.add(decal * m_step, 0));
//			effect.setDimension(new Vector2(32,64));
//			effect.setAnimationSpeed(0.05f);
			
			effect.setLifeTime(m_lifeTime);
			effect.setPosition(currentPosition.add(decal * m_step, 0));
			m_visual.append(effect);
			
			m_step++;
		}
		
		
	}
	
	
	
}
