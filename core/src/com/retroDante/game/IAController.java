package com.retroDante.game;

import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ai.fsm.DefaultStateMachine;
import com.badlogic.gdx.ai.fsm.StateMachine;
import com.badlogic.gdx.math.Vector2;

/**
 * 
 * Controlleur pour l'IA. D�cide de ce que l'entit� va faire. 
 * D�cision bas� sur quelques crit�res, comme la distance du joueur. 
 * l'IA s'update lors de l'appel � la fonction update().
 * 
 * @author florian
 *
 */
public class IAController extends Controller {
	
	//crit�re qui determinerons le comportement de l'IA : 
	Vector2 m_targetPosition; // position du joueur
	Vector2 m_ownPosition; // position le L'ia
	float m_ownLife; // vie de l'ia
	float m_visibility; //vision de l'IA
	float m_attackRange; //distance de l'attaque de l'ia
	float m_lastTime; //temps correspondant � la derniere action effectu�e
	float m_currentTime; 
	
	//state machine pour la gestion des etats de l'ia
	StateMachine<IAController> m_logicMachine;
	
	IAController()
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
	boolean checkAction(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
			return mappedEvent.get(actionName);
		else
			return false;
	}
	
	@Override
	boolean checkActionOnce(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
		{
			boolean state = mappedEvent.get(actionName);
			mappedEvent.put(actionName, false); //remet l'evenement � false
			return state;
		}
		else
			return false;
	}
	
	public void putAction(String actionName, boolean actionState)
	{
		mappedEvent.put(actionName, actionState);
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
	
	public void setAttackRenge(float attackRange)
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
