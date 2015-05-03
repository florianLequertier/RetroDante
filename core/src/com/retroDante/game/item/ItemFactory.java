package com.retroDante.game.item;

import com.retroDante.game.Factory;

public class ItemFactory implements Factory<Item> {

	private ItemFactory(){}
	private static ItemFactory INSTANCE = new ItemFactory();
	public static ItemFactory getInstance()
	{
		return INSTANCE;
	}
	
	@Override
	public Item create(String name) {
		
		if(name == "itemLife")
		{
			return new ItemLife();
		}
		else
		{
			System.out.println("ERREUR : attackFactory : aucune attaque trouvée pour le nom donné ("+name+")");
			return null;
		}
	}
	
	@Override
	public Item create(int index) {
		
		if(index == 0)
		{
			return new ItemLife();
		}
		else
		{
			System.out.println("ERREUR : attackFactory : aucune attaque trouvée pour l'index donné ("+index+")");
			return null;
		}
		

	}


	
}
