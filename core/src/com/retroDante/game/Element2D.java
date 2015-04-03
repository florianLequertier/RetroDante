package com.retroDante.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.List;






import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class Element2D extends Rigidbody implements Drawable{
	
	/*Vector2 m_position = new Vector2(0,0);
	Vector2 m_velocity = new Vector2(0,0);
	private Vector2 m_dimension = new Vector2(32,32);*/
	
	static private Vector2 Unit = new Vector2(32,32);
	static public Vector2 getUnit()
	{
		return Unit;
	}
	static public void setUnit(Vector2 unit)
	{
		Unit = unit;
	}
	static public void setUnit(int unit)
	{
		Unit = new Vector2(unit, unit);
	}
	
	
	private Texture m_visual;
	private TextureRegion m_texRegion;
	private Animator m_animator;
	
	
	//constructeurs et factories : 
	
	Element2D(Texture tex)
	{
		m_visual = tex;
		m_texRegion = new TextureRegion(m_visual, 0,0, (int)getUnit().x, (int)getUnit().y );
		m_animator = new Animator( new Animation(900, m_texRegion));
	}
	
	/**
	 * Cette fonction est une factory créant un element "static" (n'usant pas la gravitée, mais étant solide) :
	 * isSolid = true, blockCursor = false
	 * 
	 * Utile pour la plupart des éléments "static" du jeu (plateformes, sol, fond, ...)
	 * 
	 */
	public static Element2D StaticElement(Texture tex)
	{
		Element2D newElement = new Element2D(tex);
		newElement.makeStaticBody();
		
		newElement.m_visual = tex;
		newElement.m_texRegion = new TextureRegion(newElement.m_visual, 0,0, (int)getUnit().x, (int)getUnit().y );
		newElement.m_animator = new Animator( new Animation(900, newElement.m_texRegion));
		
		return newElement;
	}
	
	
	/**
	 * Cette fonction est une factory créant un element "solide" (usant la gravitée, et étant solide) :
	 * isSolid = true, blockCursor = false, plus gravitée
	 * 
	 */
	public static Element2D SolidElement(Texture tex)
	{
		Element2D newElement = new Element2D(tex);
		newElement.makeSolidBody();
		
		String path = "img/image02.png";
		newElement.m_visual = tex;
		newElement.m_texRegion = new TextureRegion(newElement.m_visual, 0,0, (int)getUnit().x, (int)getUnit().y );
		newElement.m_animator = new Animator( new Animation(900, newElement.m_texRegion));
		
		return newElement;
	}
	
	/**
	 * rends le rigidbody de base static
	 */
	public void makeStaticElement()
	{
		this.makeStaticBody();
	}
	
	/**
	 * rends le rigidbody de base solide
	 */
	public void makeSolidElement()
	{
		this.makeSolidBody();
	}
	
	
	
	
	
	
	//setters et getters : 
	
	public Texture getVisual()
	{
		return m_visual;
	}
	
	public void setVisual(Texture texture)
	{
		m_visual = texture;
	}
	
	
	
	
	//updates : 
	public void update(float deltaTime)
	{
		updateForces(deltaTime);
		updateMovement(deltaTime);
	}
	
	public void updateMovement(float deltaTime)
	{
		m_velocity.add(getForceResult());//ajout des forces
		m_velocity.mulAdd(m_velocity, deltaTime);
		move( m_velocity );

		
		//réinitialisation : 
		m_velocity = Vector2.Zero;
	}

	public void update(float deltaTime, List<Element2D> others)
	{
		updateForces(deltaTime);
		updateMovement(deltaTime, others);
	}
	
	public void updateMovement(float deltaTime, List<Element2D> others)
	{
		m_velocity.add(getForceResult());//ajout des forces
		m_velocity.scl( deltaTime);
		
		//collision ? 
		
		//celon x : 
		move( new Vector2(m_velocity.x, 0.f) );
		//collision ? 
		for(Element2D e : others)
		{
			if(this.collideWith(e))
			{
				move( new Vector2(-m_velocity.x, 0.f) );
				break;
			}
		}
		//celon y : 
		move( new Vector2(0.f, m_velocity.y) );
		//collision ? 
		for(Element2D e : others)
		{
			if(this.collideWith(e))
			{
				move( new Vector2(0.f, -m_velocity.y) );
				break;
			}
		}
		
		//réinitialisation : 
		m_velocity = Vector2.Zero;
	}
	
	
		
		
	//Transformable : 
	public Vector2 getPosition()
	{
		return new Vector2( m_collider.x, m_collider.y );
	}
	
	public void setPosition(Vector2 pos)
	{
		this.m_collider.setPosition( pos );
	}
	
	public void move(Vector2 deltaPos)
	{
		Vector2 position = new Vector2(this.m_collider.getX() + deltaPos.x, this.m_collider.getY() + deltaPos.y);
		this.m_collider.setPosition( position );
	}
	

	

	
	//Drawable : 
	
	@Override
	public void draw(SpriteBatch batch) {
		Vector2 position = new Vector2(this.m_collider.getX(), this.m_collider.getY());
		batch.draw(m_texRegion, position.x, position.y, m_collider.width, m_collider.height );
	}
	
	
	
	
}