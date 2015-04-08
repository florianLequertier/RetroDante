package com.retroDante.game;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

/**
 * 
 * Trigger declenchant la mort du Character qui le traverse
 * Couleur de debug : orange (chaque type de trigger possède une couleur differente)
 * 
 * @author Florian
 *
 */
public class DamageTrigger extends Trigger {
	
	//private VisualEffect m_effect;
	private float m_damageAmount;
	
	DamageTrigger()
	{
		super(Color.ORANGE);
		this.setType("damage");
		
		m_damageAmount = 30;
	}
	
	DamageTrigger(float damageAmount)
	{
		super(Color.ORANGE);
		this.setType("damage");
		
		m_damageAmount = damageAmount;
	}
	
	@Override
	public void triggerEventOn(Character target)
	{
		//m_effect.play(target.getPosition(), false);
		target.sendDammage(m_damageAmount);
	}
	
	//serialisation : 
	@Override
	public void write(Json json) {
			super.write(json);
			json.writeValue("damageAmount", m_damageAmount);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		m_damageAmount = jsonData.getFloat("damageAmount");
	}
	
}
