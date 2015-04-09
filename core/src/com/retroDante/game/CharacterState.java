package com.retroDante.game;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;

public enum CharacterState implements State<Character> {

	IDLE(){
		@Override
		public void enter(Character entity) {
			entity.getAnimator().changeAnimation("idle");
		}
		
	},
	WALK_LEFT(){
		@Override
		public void enter(Character entity) {
			entity.setVelocity( entity.getVelocity().add(-entity.getSpeed(), 0));
			entity.getAnimator().changeAnimation("walk");
		}
		
	},
	WALK_RIGHT(){
		@Override
		public void enter(Character entity) {
			entity.setVelocity( entity.getVelocity().add(entity.getSpeed(), 0));
			entity.getAnimator().changeAnimation("walk");
		}
		
	},
	JUMP()
	{
		@Override
		public void enter(Character entity) {
			entity.addForce(Force.Jump());
			entity.getAnimator().changeAnimation("jump");
		}
		
		@Override
		public void update(Character character){
			
			if(character.checkAction("walk_left"))
			{
				character.flipLeft();
			}
			else if(character.checkAction("walk_right"))
			{
				character.flipRight();
			}
			
			if(character.getIsGrounded())
			{
				character.getStateMachine().changeState(IDLE);
			}	
			
		}
	};
	
	
	@Override
	public void update(Character character)
	{ 
		boolean noAction = true;
		if(character.checkAction("walk_left"))
		{
			character.flipLeft();
			//if(character.getIsGrounded())
				character.getStateMachine().changeState(WALK_LEFT);
			
			noAction = false;
		}
		if(character.checkAction("walk_right"))
		{
			character.flipRight();
			//if(character.getIsGrounded())
				character.getStateMachine().changeState(WALK_RIGHT);
			
			noAction = false;
		}
		if(character.checkActionOnce("jump"))
		{
			if(character.getIsGrounded())
			{
				character.getStateMachine().changeState(JUMP);
			}	
			
			noAction = false;
		}
		
		if(noAction)
		{
			character.getStateMachine().changeState(IDLE);
		}
		
	}
	

	@Override
	public void exit(Character entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onMessage(Character entity, Telegram telegram) {
		// TODO Auto-generated method stub
		return false;
	}

}
