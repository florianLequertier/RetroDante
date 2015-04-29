package com.retroDante.game.character;

import java.util.Random;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.badlogic.gdx.math.Vector2;

public enum EnemyState implements State<IAController>{

	SLEEP() //entitée "endormie"
	{
		@Override
		public void enter(IAController entity) {
			entity.resetActions();
			entity.putAction("idle", true); 
		}
		
		@Override
		public void update(IAController character){
			
			float distanceToTarget = character.getTargetPosition().mulAdd(character.getOwnPosition(), -1).len2();
			
			if(distanceToTarget < character.getVisibility())
			{
				character.getStateMachine().changeState(CHASE);
			}
			
		}
		
	},
	AWAKE() //entitée en eveil 
	{
		@Override
		public void enter(IAController entity) {
			Random rand = new Random();
			
			if(rand.nextFloat() > 0.5)
			{
				entity.resetActions();
				//entity.putAction("walk_right", false); //on marche vers la gauche 
				entity.putAction("walk_left", true); //on marche vers la gauche 
			}
			else
			{
				//entity.putAction("walk_left", false);
				entity.putAction("walk_right", true);
			}
			
		}
		
		@Override
		public void update(IAController character){
			
			float distanceToTarget = character.getTargetPosition().mulAdd(character.getOwnPosition(), -1).len2();
			
			if(distanceToTarget < character.getVisibility())
			{
				character.getStateMachine().changeState(CHASE);
			}
			
			Random rand = new Random();
			if( character.getCurrentTime() > character.getLastTime() + (rand.nextFloat()*3 + 1)) // l'IA va changer de direction toutes les ( 1 < x < 4 ) secondes
			{
				if(character.checkAction("walk_right"))
				{
					character.resetActions();
					//character.putAction("walk_right", false); 
					character.putAction("walk_left", true); //on marche vers la gauche 
				}
				else
				{
					character.resetActions();
					//character.putAction("walk_left", false);
					character.putAction("walk_right", true);
				}
			}
			
		}
	},
	CHASE(){

		@Override
		public void update(IAController character){
			
			Vector2 vectorToTarget = character.getTargetPosition().mulAdd(character.getOwnPosition(), -1);
			float distanceToTarget = vectorToTarget.len2();
			
			if(distanceToTarget > character.getVisibility())
			{
				character.getStateMachine().changeState(AWAKE);
			}
			else
			{
				if(vectorToTarget.x < 0) // vers la gauche ? 
				{
					character.resetActions();
					//character.putAction("walk_right", false); 
					character.putAction("walk_left", true); //on marche vers la gauche 
				}
				else
				{
					character.resetActions();
					//character.putAction("walk_left", false);
					character.putAction("walk_right", true);
				}
				
				if(distanceToTarget <= character.getAttackRange())
				{
					character.getStateMachine().changeState(ATTACK);
				}
			}
			
		}
		
	},
	ATTACK(){
		@Override
		public void enter(IAController entity) {
			entity.resetActions();
			entity.putAction("attack", true);
			//entity.putAction("walk_left", false);
			//entity.putAction("walk_right", false);
		}
		
		@Override
		public void update(IAController character){
			
			Vector2 vectorToTarget = character.getTargetPosition().mulAdd(character.getOwnPosition(), -1);
			float distanceToTarget = vectorToTarget.len2();
			
			if(distanceToTarget > character.getAttackRange())
			{
				character.getStateMachine().changeState(CHASE);
			}
			else
			{
				character.resetActions();
				//character.putAction("attack", false);
			}
			
		}
		
	};
	
	
	
	
	@Override
	public void enter(IAController entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(IAController entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exit(IAController entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMessage(IAController entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
