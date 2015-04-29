package com.retroDante.game.character;

import java.util.Map.Entry;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Controller;

/**
 * 
 * Controlleur pour l'IA. Décide de ce que l'entité va faire. 
 * Décision basé sur quelques critères, comme la distance du joueur. 
 * l'IA s'update lors de l'appel à la fonction update().
 * 
 * @author florian
 *
 */
public class IAController extends Controller {
	
	//critère qui determinerons le comportement de l'IA : 
	Vector2 m_targetPosition = new Vector2(0,0); // position du joueur
	Vector2 m_ownPosition = new Vector2(0,0); // position le L'ia
	float m_ownLife = 100; // vie de l'ia
	float m_visibility = 100; //vision de l'IA
	float m_attackRange = 100; //distance de l'attaque de l'ia
	float m_lastTime = 0; //temps correspondant à la derniere action effectuée
	float m_currentTime = 0; 
	
	//state machine pour la gestion des etats de l'ia
	StateMachine<IAController> m_logicMachine;
	
	public IAController()
	{
		
		mappedEvent.put("walk_right", false);

		mappedEvent.put("walk_left", false);

		mappedEvent.put("jump", false);

		mappedEvent.put("attack", false);
		
		m_logicMachine = new DefaultStateMachine<IAController>(this, EnemyState.SLEEP);
		
	}
	
	public void update(float deltaTime)
	{
		m_currentTime += deltaTime;
		m_logicMachine.update();
	}
	
	@Override
	public boolean checkAction(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
			return mappedEvent.get(actionName);
		else
			return false;
	}
	
	@Override
	public boolean checkActionOnce(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
		{
			boolean state = mappedEvent.get(actionName);
			mappedEvent.put(actionName, false); //remet l'evenement à false
			return state;
		}
		else
			return false;
	}
	
	public void putAction(String actionName, boolean actionState)
	{
		mappedEvent.put(actionName, actionState);
	}
	
	public void resetActions()
	{
		for(Entry<String, Boolean> event : mappedEvent.entrySet())
		{
			event.setValue(false);
		}
	}
	
	// getters / setters : 
	public void setTargetPosition(Vector2 targetPosition)
	{
		m_targetPosition = targetPosition;
	}
	
	public Vector2 getTargetPosition()
	{
		return m_targetPosition;
	}
	
	public void setOwnPosition(Vector2 ownPosition)
	{
		m_ownPosition = ownPosition;
	}
	
	public Vector2 getOwnPosition()
	{
		return m_ownPosition;
	}
	
	public void setOwnLife(float life)
	{
		m_ownLife = life;
	}
	
	public float getOwnLife()
	{
		return m_ownLife;
	}
	
	public void setVisibility(float visibility)
	{
		m_visibility = visibility;
	}
	
	public void setAttackRange(float attackRange)
	{
		m_attackRange = attackRange;
	}
	
	public float getAttackRange()
	{
		return m_attackRange;
	}
	
	public float getVisibility()
	{
		return m_visibility;
	}
	
	public StateMachine<IAController> getStateMachine()
	{
		return m_logicMachine;
	}
	
	public float getCurrentTime()
	{
		return m_currentTime;
	}
	
	public float getLastTime()
	{
		return m_currentTime;
	}
	
	public void setLastTime(float time)
	{
		m_lastTime = time;
	}
	

}
