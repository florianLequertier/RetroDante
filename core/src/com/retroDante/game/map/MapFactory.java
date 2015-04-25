package com.retroDante.game.map;

import com.retroDante.game.Factory;
import com.retroDante.game.trigger.DamageTrigger;
import com.retroDante.game.trigger.KillTrigger;
import com.retroDante.game.trigger.Trigger;
import com.retroDante.game.trigger.TriggerFactory;

public class MapFactory implements Factory<MapElement>{
	
	private MapFactory()
	{}
	private static MapFactory INSTANCE = new MapFactory();
	public static MapFactory getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public MapElement create(String name) {

		if( name.equals("default") )
		{
			return new MapElement();
		}
		else
		{
			return new MapElement();
		}

	}
	
	@Override
	public MapElement create(int index) {

		if( index > 0 )
		{
			return new MapElement(index);
		}
		else
		{
			return new MapElement(0);
		}

	}
	
	
}
