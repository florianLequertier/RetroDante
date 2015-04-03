package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Controllable.KeyStatus;

public class Player extends Element2D implements Controllable{
	
	float m_life;
	float m_speed;
	boolean m_isDead;
	boolean m_isGrounded;
	PlayerController m_controller;
	
	/**
	 * Le player est un solidBody 
	 */
	Player(Texture tex) 
	{
		super(tex);
		this.makeSolidBody();
		
		m_life = 100.f;
		m_speed = 10.f;
		m_isDead = false;
		
		m_controller = new PlayerController();
		
	}
	
	Player(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		this.makeSolidBody();
		
		m_life = 100.f;
		m_speed = 10.f;
		m_isDead = false;
		
		m_controller = new PlayerController();
		
	}
	
	Player(TileSetInfo tileSet, int spriteIndex, float deltaAnim)
	{
		super(tileSet, spriteIndex);
		this.makeSolidBody();
		m_animator = new Animator(tileSet.getForAnimation(0,1,2)); //créé une list avec les trois premiere ligne du tileSet (correspondant donc aux 3 premieres animations)
		
		m_life = 100.f;
		m_speed = 10.f;
		m_isDead = false;
		
		m_controller = new PlayerController();
		
	}
	
	
	void kill()
	{
		m_isDead = true;
	}
	
	@Override
	public void listenKey(KeyStatus status, int keycode) {
		
		if(status == KeyStatus.DOWN)
			m_controller.listenKeyDown(keycode);
		else if(status == KeyStatus.UP)
			m_controller.listenKeyUp(keycode);
	}
	
	@Override
	public void checkController()
	{
		if(m_controller.checkAction("walk_left"))
		{
			this.m_velocity.x -= m_speed;
		}
		if(m_controller.checkAction("walk_right"))
		{
			this.m_velocity.x += m_speed;
		}
		if(m_controller.checkActionOnce("jump"))
		{
			if(m_isGrounded)
			this.addForce(Force.Jump());
		}
	}
	
	@Override
	public void update(float deltaTime, List<Element2D> others)
	{
		checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		super.update(deltaTime, others);

	}
	@Override
	public void update(float deltaTime)
	{
		checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		super.update(deltaTime);
	}
	@Override
	public void updateMovement(float deltaTime, List<Element2D> others)
	{
		m_velocity.add(getForceResult());//ajout des forces
		m_velocity.scl( deltaTime);
		
		m_isGrounded = false;
		Vector2 footPosition = new Vector2(m_collider.x + m_collider.width/2.f , m_collider.y - 10);

		//collision ? 
		boolean collideX = false;
		boolean collideY = false;
		
		//collision ? 
		for(Element2D e : others)
		{
			
			if(!collideX)
			{
				//celon x : 
				move( new Vector2(m_velocity.x, 0.f) );
				if(this.collideWith(e))
				{
					collideX = true;
				}
				move( new Vector2(-m_velocity.x, 0.f) );
			}
			if(!collideY)
			{
				//celon y : 
				move( new Vector2(0.f, m_velocity.y) );
				if(this.collideWith(e))
				{
					collideY = true;
					if(e.contains(footPosition)) //test pour savoir si le joueur a quelque chose sous ses pieds
						m_isGrounded = true;
				}
				move( new Vector2(0.f, -m_velocity.y) );
			}
			if(collideX && collideY)
				break;
			
		}
		if(!collideX)
			move( new Vector2(m_velocity.x, 0.f) );
		if(!collideY)
			move( new Vector2(0.f, m_velocity.y) );
		
		
		
		//réinitialisation : 
		m_velocity = Vector2.Zero;
	}
	
	

}
