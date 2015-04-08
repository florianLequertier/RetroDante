package com.retroDante.game;

import com.badlogic.gdx.graphics.Color;


/**
 * 
 * Trigger declenchant la mort du Character qui le traverse
 * Couleur de debug : rouge (chaque type de trigger possède une couleur differente)
 * 
 * @author Florian
 *
 */
public class KillTrigger extends Trigger {
	
	//private VisualEffect m_effect;
	
	KillTrigger()
	{
		super(Color.RED);
		this.setType("kill");
	}
	
	@Override
	public void triggerEventOn(Character target)
	{
		//m_effect.play(this.getPosition(), false);
		target.kill();
	}
	
}
