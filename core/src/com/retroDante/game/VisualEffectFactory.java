package com.retroDante.game;

import com.badlogic.gdx.math.Vector2;

public class VisualEffectFactory implements Factory<VisualEffect>{
	
	private VisualEffectFactory(){}
	private static VisualEffectFactory INSTANCE = new VisualEffectFactory();
	public static VisualEffectFactory getInstance()
	{
		return INSTANCE;
	}
	
	public VisualEffect create(String name)
	{
		if(name == "flameColumn")
		{
			AnimatedEffect effect = new AnimatedEffect("effect", 0);
			effect.setLifeTime(2);
			effect.setPosition(new Vector2(0,0));
			effect.setDimension(new Vector2(32,64));
			effect.setAnimationSpeed(0.05f);
			effect.setName("flameColumn");
			
			return effect;
		}
		else
		{
			System.out.println("ERREUR : VisualEffectFactory : le nom "+name+" ne correspond a aucun effet");
			return null;
		}
	}
	
}