package com.retroDante.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;


public class Rigidbody {
	
	protected Vector2 m_velocity = new Vector2(0,0);
	protected Rectangle m_collider = new Rectangle(0,0,32,32);
	
	private List<Force> m_tabForce = new ArrayList<Force>();
	private List<Force> m_tabConstantForce = new ArrayList<Force>();
	private boolean m_blockCursor;
	private boolean m_isSolid;
	
	
	//constructeurs et factories : 
	
	//default : blockCursor == true && isSolid == true
	/**
	 * Constructeur par defaut : 
	 * blockCursor == true;
	 * isSolid == true;
	 * 
	 */
	Rigidbody()
	{
		setBlockCursor(true);
		setIsSolid(true);
	}
	
	/**
	 * Cette fonction est une factory renvoyant un nouveau rigidbody avec : 
	 * isSolid = true, blockCursor = false, et une force de gravit�e.
	 * 
	 * Utile pour la plupart des �l�ments non "static" du jeu (joueur, ennemis...)
	 * 
	 */
	static Rigidbody SolidBody()
	{
		Rigidbody r = new Rigidbody();
		r.setIsSolid(true);
		r.addConstantForce(Force.Gravity());
		
		return r;
	}
	
	/**
	 * Cette fonction est une factory renvoyant un nouveau rigidbody avec : 
	 * isSolid = true, blockCursor = false
	 * 
	 * Utile pour la plupart des �l�ments "static" du jeu (plateformes, sol, fond, ...)
	 * 
	 */
	static Rigidbody StaticBody()
	{
		Rigidbody r = new Rigidbody();
		r.setIsSolid(true);
		
		return r;
	}
	
	/**
	 * Cette fonction fixe les param�tre de rigidbody pour qu'il devienne un SolidBody :
	 * isSolid = true, blockCursor = false, et une force de gravit�e.
	 * 
	 * Utile pour la plupart des �l�ments non "static" du jeu (joueur, ennemis...)
	 * 
	 */
	public void makeSolidBody()
	{
		this.setBlockCursor(false);
		this.setIsSolid(true);
		this.addConstantForce(Force.Gravity());
	}
	
	/**
	 * Cette fonction fixe les param�tre de rigidbody pour qu'il devienne un SolidBody :
	 * isSolid = true, blockCursor = false
	 * 
	 * Utile pour la plupart des �l�ments "static" du jeu (plateformes, sol, fond, ...)
	 * 
	 */
	public void makeStaticBody()
	{
		this.setBlockCursor(false);
		this.setIsSolid(true);
	}
	
	
	//getters et setters : 
	

	public boolean getIsSolid() 
	{
		return m_isSolid;
	}

	public boolean getBlockCursor() 
	{
		return m_blockCursor;
	}
	
	public Vector2 getPosition() {
		return new Vector2(m_collider.x, m_collider.y);
	}

	public void setPosition(Vector2 position) {
		this.m_collider.setPosition(position);
	}

	public Vector2 getVelocity() {
		return m_velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.m_velocity = velocity;
	}

	public Vector2 getDimension() {
		return new Vector2(m_collider.width, m_collider.height);
	}

	public void setDimension(Vector2 dimension) {
		Vector2 position = new Vector2(this.m_collider.x,this.m_collider.y);
		m_collider = new Rectangle(position.x, position.y, dimension.x, dimension.y);
	}

	public boolean isBlockCursor() {
		return m_blockCursor;
	}

	public void setBlockCursor(boolean blockCursor) {
		this.m_blockCursor = blockCursor;
	}

	public boolean isIsSolid() {
		return m_isSolid;
	}

	public void setIsSolid(boolean isSolid) {
		this.m_isSolid = isSolid;
	}
	
	
	
	// gestion des forces : 
	
	public void addForce(Force newForce)
	{
		m_tabForce.add(newForce);
	}
	
	public void addConstantForce(Force newForce)
	{
		m_tabConstantForce.add(newForce);
	}
	
	public Vector2 getForceResult()
	{
		
		int nbOfForces = m_tabForce.size() + m_tabConstantForce.size();
		if(nbOfForces == 0)
			return Vector2.Zero;
		
		float resultant_x = 0.f;
		float resultant_y = 0.f;
		
		for(Force f : m_tabForce)
		{
			resultant_x += f.getIntensity() * f.getDirection().x;
			resultant_y += f.getIntensity() * f.getDirection().y;
		}
		
		for(Force f : m_tabConstantForce)
		{
			resultant_x += f.getIntensity() * f.getDirection().x;
			resultant_y += f.getIntensity() * f.getDirection().y;
		}
		
		
		return new Vector2( (resultant_x / (float)nbOfForces), (resultant_y / (float)nbOfForces) );
	}
	
	public void updateForces(float deltaTime)
	{
		Iterator<Force> it = m_tabForce.iterator();
		while(it.hasNext())
		{
			Force f = it.next();
			if(!f.update(deltaTime))
			{
				it.remove();
			}
		}
	}
	
	//Fonctions de collision :
		
		/**
		 * 
		 * @param other : autre rigidbody 
		 * @return renvoi true s'il y a collision
		 */
		public boolean collideWith(Rigidbody other)
		{
			if(this.m_collider.overlaps(other.m_collider))
				return true;
			else
				return false;			
		}
		
		/**
		 * 
		 * @param other : point � tester 
		 * @return renvoi true si le point est dans le rigidbody
		 */
		public boolean collideWith(Vector2 point)
		{
			if(this.m_collider.contains(point))
				return true;
			else
				return false;
		}
		
		public boolean contains(Rigidbody other)
		{
			if(this.m_collider.contains(other.m_collider))
				return true;
			else
				return false;
		}
		
		public boolean contains(Vector2 point)
		{
			if(this.m_collider.contains(point))
				return true;
			else
				return false;
		}
		
		public boolean containedIn(Rigidbody other)
		{
			if( other.m_collider.contains(this.m_collider))
				return true;
			else
				return false;
		}

		
		
	
}
