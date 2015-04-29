package com.retroDante.game.character;

import com.badlogic.gdx.ai.fsm.State;
import com.badlogic.gdx.ai.msg.Telegram;
import com.retroDante.game.Force;

public enum CharacterState implements State<Character> {

	IDLE(){
		@Override
		public void enter(Character entity) {
			entity.getAnimator().changeAnimation("idle");
			entity.getAnimator().playAt(0.4f);
		}
		
		@Override
		public void update(Character character)
		{
			
			if(character.checkAction("walk_left") )
			{
				character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
					character.flipLeft();
					//if(character.getIsGrounded())
						character.getStateMachine().changeState(WALK_LEFT);
			}
			else if(character.checkAction("walk_right") ) 
			{
				character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
					character.flipRight();
					//if(character.getIsGrounded())
						character.getStateMachine().changeState(WALK_RIGHT);
			}
			else if(character.checkActionOnce("jump") ) 
			{
					if(character.getIsGrounded())
					{
						character.getStateMachine().changeState(JUMP);
					}
			}
			else if(character.checkActionOnce("attack") ) 
			{
					character.getStateMachine().changeState(ATTACK);
			}
		}
		
	},
	WALK_LEFT(){
		@Override
		public void enter(Character entity) {
			entity.setVelocity( entity.getVelocity().add(-entity.getSpeed(), 0));
			entity.getAnimator().changeAnimation("walk");
			entity.getAnimator().playAt(0.5f);
		}
		
		@Override
		public void update(Character character)
		{
			character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
			
			
			if(character.checkAction("walk_right") ) 
			{
				character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
					character.flipRight();
					//if(character.getIsGrounded())
						character.getStateMachine().changeState(WALK_RIGHT);
			}
			else if(character.checkActionOnce("jump") ) 
			{
					if(character.getIsGrounded())
					{
						character.getStateMachine().changeState(JUMP);
					}
			}
			else if(character.checkActionOnce("attack") ) 
			{
					character.getStateMachine().changeState(ATTACK);
			}
			else if( !character.checkAction("walk_left") )
			{
				character.getStateMachine().changeState(IDLE);
			}
		}
		
	},
	WALK_RIGHT(){
		@Override
		public void enter(Character entity) {
			entity.setVelocity( entity.getVelocity().add(entity.getSpeed(), 0));
			entity.getAnimator().changeAnimation("walk");
			entity.getAnimator().playAt(0.5f);
		}
		
		@Override
		public void update(Character character)
		{
			character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
			
			

			if(character.checkAction("walk_left") ) 
			{
				character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
					character.flipLeft();
					//if(character.getIsGrounded())
						character.getStateMachine().changeState(WALK_LEFT);
			}
			else if(character.checkActionOnce("jump") ) 
			{
					if(character.getIsGrounded())
					{
						character.getStateMachine().changeState(JUMP);
					}
			}
			else if(character.checkActionOnce("attack") ) 
			{
					character.getStateMachine().changeState(ATTACK);
			}
			else if( !character.checkAction("walk_right"))
			{
				character.getStateMachine().changeState(IDLE);
			}
		}
		
	},
	JUMP()
	{
		@Override
		public void enter(Character entity) {
			entity.addForce(Force.Jump());
			entity.getAnimator().changeAnimation("jump");
			entity.getAnimator().playAt(0.8f);
		}
		
		@Override
		public void update(Character character){
			
			if(character.checkAction("walk_left"))
			{
				character.flipLeft();
				character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
			}
			else if(character.checkAction("walk_right"))
			{
				character.flipRight();
				character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
			}
			if(character.getIsGrounded())
			{
				character.getStateMachine().changeState(IDLE);
			}
			
		}
	},
	ATTACK()
	{
		@Override
		public void enter(Character entity) {
			
			if(entity.canAttack())
			{
				entity.attack();
				
				entity.getAnimator().changeAnimation("attack");
				entity.getAnimator().playAt(0.5f);
			}

		}
		
		@Override
		public void update(Character character){
			
			if(character.checkAction("attack"))
			{
				if(character.canAttack())
				{
					character.attack();
					
					character.getAnimator().changeAnimation("attack");
					character.getAnimator().playAt(0.5f);
				}

			}
			
			if(character.checkAction("walk_left"))
			{
				character.flipLeft();
				character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
			}
			else if(character.checkAction("walk_right"))
			{
				character.flipRight();
				character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
			}
			
			if(character.getAnimator().isAnimationFinished())
			{
				if(character.getIsGrounded())
				{
					character.getStateMachine().changeState(IDLE);
				}
				else
				{
					character.getStateMachine().changeState(IDLE);
					character.getAnimator().changeAnimation("jump");
					character.getAnimator().playAt(0.8f);
				}
			}
	
		}
	};
	
	
	@Override
	public void update(Character character)
	{ /*
		boolean noAction = true;
		if(character.checkAction("walk_left") )
		{
			character.setVelocity( character.getVelocity().add(-character.getSpeed(), 0));
			if( !character.getCurrentState().toString().equals("WALK_LEFT") )
			{
				character.flipLeft();
				//if(character.getIsGrounded())
					character.getStateMachine().changeState(WALK_LEFT);
			}
			noAction = false;
		}
		if(character.checkAction("walk_right") ) 
		{
			character.setVelocity( character.getVelocity().add(character.getSpeed(), 0));
			if( !character.getCurrentState().toString().equals("WALK_RIGHT") && !character.getCurrentState().toString().equals("JUMP"))
			{
				character.flipRight();
				//if(character.getIsGrounded())
					character.getStateMachine().changeState(WALK_RIGHT);
			}
			noAction = false;
		}
		if(character.checkActionOnce("jump") ) 
		{
			if( !character.getCurrentState().toString().equals("JUMP"))
			{
				if(character.getIsGrounded())
				{
					character.getStateMachine().changeState(JUMP);
				}
			}
			noAction = false;
		}
		if(character.checkActionOnce("attack") ) 
		{
			if( !character.getCurrentState().toString().equals("ATTACK"))
			{
				character.getStateMachine().changeState(ATTACK);
			}
			noAction = false;
		}
		
		if(noAction && !character.getCurrentState().toString().equals("IDLE"))
		{
			character.getStateMachine().changeState(IDLE);
		}
		System.out.println(character.getCurrentState().toString());
		*/
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
