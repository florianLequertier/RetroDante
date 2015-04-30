package com.retroDante.game.attack;

import com.retroDante.game.Factory;


/**
 * 
 * Une factory, singleton, permettant de creer une Attaque (attack) à partir de son nom
 * 
 * @author Alexflo
 *
 */
public class AttackFactory implements Factory<Attack> {

	private AttackFactory(){}
	private static AttackFactory INSTANCE = new AttackFactory();
	public static AttackFactory getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Attack create(String name) {
		
		if(name == "slice")
		{
			return new Attack();
		}
		else if(name == "burningHearthquake")
		{
			return new BurningHearthquake();
		}
		else
		{
			System.out.println("ERREUR : attackFactory : aucune attaque trouvée pour le nom donné ("+name+")");
			return null;
		}
	}
	
	@Override
	public Attack create(int index) {
		
		if(index == 0)
		{
			return new Attack();
		}
		else if(index == 1)
		{
			return new BurningHearthquake();
		}
		else
		{
			System.out.println("ERREUR : attackFactory : aucune attaque trouvée pour l'index donné ("+index+")");
			return null;
		}
		

	}


	
	
}
