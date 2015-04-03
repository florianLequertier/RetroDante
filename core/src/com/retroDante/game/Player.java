package com.retroDante.game;

import com.badlogic.gdx.graphics.Texture;

public class Player extends Element2D{
	
	float m_life;
	float m_speed;
	boolean m_isDead;
	
	/**
	 * Le player est un solidBody 
	 */
	Player(Texture tex) {
		super(tex);
		this.makeSolidBody();
		
		m_life = 100.f;
		m_speed = 10.f;
		m_isDead = false;
		
	}
	
	void kill()
	{
		m_isDead = true;
	}
	

}
