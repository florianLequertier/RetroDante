package com.retroDante.game;

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
	
	Attack getAttackInstance()
	{
		return new Attack(m_attackModel);
	}
	
}
