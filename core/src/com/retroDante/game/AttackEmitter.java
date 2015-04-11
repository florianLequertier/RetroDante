package com.retroDante.game;


/**
 * 
 * L'attack emitter permet de stock un type d'attaque, et d'en instancier des copies à la demande (de l'arme, du joueur...)
 * Basé sur le design pattern prototype. 
 * 
 * @author florian
 *
 */
public class AttackEmitter {
	
	Attack m_attackModel;
	
	AttackEmitter()
	{
		m_attackModel = new BurningHearthquake();
	}
	
	void changeAttack(Attack newAttack)
	{
		m_attackModel = newAttack;
	}
	
	void changeAttack(String name)
	{
		m_attackModel = AttackFactory.getInstance().create("burningHearthquake");
	}
	
	Attack getAttackInstance()
	{
		return (Attack) m_attackModel.clone(); 
	}
	
}
