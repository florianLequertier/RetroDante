package com.retroDante.game.character;

import com.retroDante.game.Factory;


/**
 * 
 * Une Factory singleton renvoyant un nouvel ennemy en fonction du nom de celui-ci 
 * 
 * @author florian
 *
 */
public class EnemyFactory implements Factory<Enemy> {
	
	private EnemyFactory()
	{}
	private static EnemyFactory INSTANCE = new EnemyFactory();
	public static EnemyFactory getInstance()
	{
		return INSTANCE;
	}
	
	
	@Override
	public Enemy create(String name) {

		if( name.equals("enemy01") )
		{
			return new Enemy();
		}
		else
		{
			return new Enemy();
		}

	}
	
	
	
}
