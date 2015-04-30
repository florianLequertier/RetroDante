package com.retroDante.game.attack;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.retroDante.game.Drawable;
import com.retroDante.game.character.Character;
import com.retroDante.game.character.Enemy;


/**
 * 
 * Singleton stockant toutes les attack du niveau. 
 * Singleton car doit pouvoir être accessible à plusieurs endroit où il aurait été delicat de le passer en parametre. Il est logique qu'il ne puisse y en avoir qu'un par niveau.
 * 
 * @author florian
 *
 */
public class AttackManager implements Drawable {

	List<Attack> m_attackContainer = new ArrayList<Attack>();
	
	static public AttackManager getInstance()
	{
		return INSTANCE;
	}
	private	AttackManager()	{}
	private static AttackManager INSTANCE = new AttackManager();
	
	
	public void add(Attack attack)
	{
		m_attackContainer.add(attack);
	}
	
	public void delete(int index)
	{
		
	}
	
	public void clear()
	{
		m_attackContainer.clear();
	}
	
	/**
	 * 
	 * test la collision avec tous les characters passés en argument. Applique des dommages si collision.
	 * 
	 * @param deltaTime
	 * @param list
	 */
	public void updateOnEnemies(float deltaTime, List<Enemy> list)
	{
		Iterator<Attack> it = m_attackContainer.iterator();
		while(it.hasNext())
		{
			Attack attack = it.next();
			
			if(!attack.update(deltaTime, list)) //retourne false si l'attack est arrivé à son terme et doit cesser
				it.remove();
		}
	}
	
	public void updateOnPlayer(float deltaTime, Character character)
	{
		Iterator<Attack> it = m_attackContainer.iterator();
		while(it.hasNext())
		{
			Attack attack = it.next();
			
			if(!attack.update(deltaTime, character)) //retourne false si l'attack est arrivé à son terme et doit cesser
				it.remove();
		}
	}
	
	
	//drawable : 
	@Override
	public void draw(Batch batch) {
		
		for(Attack attack : m_attackContainer)
		{
			attack.draw(batch);
		}
	}
	
	//pour le debug : 
	public void drawDebug(Batch batch) {

		for(Attack attack : m_attackContainer)
		{
			attack.drawDebug(batch); //draw aussi les triggers
		}

	}
	
}
