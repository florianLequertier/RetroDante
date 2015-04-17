package com.retroDante.game.trigger;

import com.retroDante.game.Factory;
import com.retroDante.game.character.Enemy;
import com.retroDante.game.character.EnemyFactory;

public class TriggerFactory implements Factory<Trigger> {
	
	private TriggerFactory()
	{}
	private static TriggerFactory INSTANCE = new TriggerFactory();
	public static TriggerFactory getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public Trigger create(String name) {

		if( name.equals("damageTrigger") )
		{
			return new DamageTrigger();
		}
		else if(name.equals("killTrigger"))
		{
			return new KillTrigger();
		}
		else if(name.equals("teleportTrigger"))
		{
			return new KillTrigger();
		}
		else
		{
			return new DamageTrigger();
		}

	}
	
}
