package com.retroDante.game;

import com.badlogic.gdx.math.Vector2;

public class Force {
	public static float GRAVITY_CONSTANT =	50f;
	public static float JUMP_CONSTANT = 200f;
	
	private Vector2 m_direction;
	private float m_initialIntensity;
	private float m_intensity;
	public enum TypeOfForce{CONSTANT, LINEAR};
	TypeOfForce m_type;
	float m_elapsedTime = 0.000001f;
	float m_decreaseFactor = -1.0f;
	
	
	static Force Gravity()
	{
		return new Force(new Vector2(0.f, -GRAVITY_CONSTANT));
	}
	
	static Force Jump()
	{
		Force f = new Force(new Vector2(0.f, JUMP_CONSTANT), TypeOfForce.LINEAR);
		f.m_decreaseFactor = -50.f;
		return f;
	}
	
	Force(Vector2 forceVector)
	{
		setIntensity(forceVector.len());
		setInitialIntensity(forceVector.len());
		setDirection(forceVector.nor());
		m_type = TypeOfForce.CONSTANT;
	}
	
	Force(Vector2 forceVector, TypeOfForce type)
	{
		setIntensity(forceVector.len());
		setInitialIntensity(forceVector.len());
		setDirection(forceVector.nor());
		m_type = type;
	}

	public float getIntensity() {
		return m_intensity;
	}

	public void setInitialIntensity(float m_intensity) {
		this.m_initialIntensity = m_intensity;
	}
	
	public float getInitialIntensity() {
		return m_initialIntensity;
	}

	public void setIntensity(float m_intensity) {
		this.m_intensity = m_intensity;
	}

	public Vector2 getDirection() {
		return m_direction;
	}

	public void setDirection(Vector2 m_direction) {
		this.m_direction = m_direction;
	}
	
	
	/**
	 * update des forces, à appeler avant de traiter les forces sur un element
	 * 
	 * @param deltaTime : temps écoulé depuis la derniere frame
	 * @return boolean : si true, rien de particuler, si false il faut supprimer la force dans le rigidbody
	 */
	public boolean update(float deltaTime)
	{
		//ne fait rien si la force est constante : 
		if(m_type == TypeOfForce.CONSTANT)
			return true;
		//save de l'ancienne intensite
		float saveIntensity = m_intensity;
		
		//sinon, décroit l'intensitée de la force : 
		m_elapsedTime += deltaTime;
		m_intensity = m_elapsedTime*m_decreaseFactor + m_initialIntensity;	
		
		//signe opposé : la force a assez diminué 
		if( (saveIntensity < 0 && m_intensity > 0) || (saveIntensity > 0 && m_intensity < 0))
			return false;
		else
			return true;
		
	}
}
