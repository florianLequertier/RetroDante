package com.retroDante.game.attack;



/**
 * 
 * L'attack emitter permet de stock un type d'attaque, et d'en instancier des copies à la demande (de l'arme, du joueur...)
 * Basé sur le design pattern prototype. 
 * 
 * @author florian
 *
 */
public class AttackEmitter {
	
	private Attack m_attackModel;
	private float m_elapsedTime;
	private float m_attackDelay;
	
	public AttackEmitter()
	{
		m_attackModel = new Attack();
		m_attackDelay = m_attackModel.getDelay();
		m_elapsedTime = 0;
	}
	
	public AttackEmitter(Attack newAttack)
	{
		m_attackModel = newAttack;
		m_attackDelay = m_attackModel.getDelay();
		m_elapsedTime = 0;
	}
	
	public AttackEmitter(String name)
	{
		m_attackModel = AttackFactory.getInstance().create(name);;
		m_attackDelay = m_attackModel.getDelay();
		m_elapsedTime = 0;
	}
	
	public void changeAttack(Attack newAttack)
	{
		m_attackModel = newAttack;
		m_attackDelay = m_attackModel.getDelay();
		m_elapsedTime = 0;
	}
	
	public void changeAttack(String name)
	{
		m_attackModel = AttackFactory.getInstance().create(name);
		m_attackDelay = m_attackModel.getDelay();
		m_elapsedTime = 0;
	}
	
	public Attack getAttackInstance()
	{
		System.out.println("attack delay = "+m_attackDelay);
		System.out.println("elapsed time = "+m_elapsedTime);
		if(m_elapsedTime >= m_attackDelay)
		{
			m_elapsedTime = 0;
		}
		
		Attack instancedAttack = (Attack) m_attackModel.clone(); 
		instancedAttack.play();
		return instancedAttack;
	}
	
	public boolean canAttack()
	{
		return (m_elapsedTime >= m_attackDelay);
	}
	
	public void update(float deltaTime)
	{
		m_elapsedTime += deltaTime;
	}
	
}
