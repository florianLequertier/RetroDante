package com.retroDante.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.List;


import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;

public class Element2D extends Rigidbody implements Json.Serializable, Drawable{
	
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
	
	
	protected String m_type;
	private Texture m_visual;
	protected TextureRegion m_texRegion;
	protected Animator m_animator;
	private float m_animationSpeed;
	
	
	//constructeurs et factories : 
	
	public Element2D(Texture tex)
	{
		m_animationSpeed = 60;
		m_type = "element2D";
		m_visual = tex;
		m_texRegion = new TextureRegion(m_visual, 0,0, (int)getUnit().x, (int)getUnit().y );
		m_animator = new Animator( new Animation(900, m_texRegion));
	}
	public Element2D(TileSetInfo tileSet, int spriteIndex)
	{
		m_animationSpeed = 60;
		m_type = "element2D";
		m_visual = tileSet.getTexture();
		m_texRegion = tileSet.get(spriteIndex);
		m_animator = new Animator( new Animation(900, m_texRegion));
		setDimension(new Vector2(m_texRegion.getRegionWidth(), m_texRegion.getRegionHeight()));
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
		
		newElement.m_type = "element2D";
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
		
		newElement.m_type = "element2D";
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
	
	public Animator getAnimator()
	{
		return m_animator;
	}
	
	public void setAnimator(Animator animator)
	{
		m_animator = animator;
	}
	
	public Texture getVisual()
	{
		return m_visual;
	}
	
	public void setVisual(Texture texture)
	{
		m_visual = texture;
	}
	
	public void setAnimationSpeed(float newSpeed)
	{
		m_animationSpeed = newSpeed;
		m_animator.changeSpeed(m_animationSpeed);
	}
	
	public float getAnimationSpeed()
	{
		return m_animationSpeed;
	}
	
	
	
	//updates : 
	public void update(float deltaTime)
	{
		updateAnimation(deltaTime);
		updateForces(deltaTime);
		updateMovement(deltaTime);
	}
	public void update(float deltaTime, List<Element2D> others)
	{
		updateAnimation(deltaTime);
		updateForces(deltaTime);
		updateMovement(deltaTime, others);
	}
	
	
	public void updateAnimation(float deltaTime)
	{
		m_texRegion = m_animator.getCurrentFrame();
	}
	
//	public void updateMovement(float deltaTime)
//	{
//		m_velocity.add(getForceResult());//ajout des forces
//		m_velocity.scl( deltaTime);
//		move( m_velocity );
//
//		
//		//réinitialisation : 
//		m_velocity = Vector2.Zero;
//	}

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
//	public Vector2 getPosition()
//	{
//		return new Vector2( m_collider.x, m_collider.y );
//	}
//	
//	public void setPosition(Vector2 pos)
//	{
//		this.m_collider.setPosition( pos );
//	}
	
	@Override
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
	
	public void drawWithParralax(SpriteBatch batch, float decalX, float decalY) {
		
		Vector2 position = new Vector2(this.m_collider.getX()+decalX, this.m_collider.getY() +decalY);
		batch.draw(m_texRegion, position.x, position.y, m_collider.width, m_collider.height );
		
	}
	
	
	//serializable : 
	
	@Override
	public void write(Json json) {
		super.write(json);
		json.writeValue("m_type", m_type);
		json.writeValue("m_animationSpeed", m_animationSpeed);
	}
	@Override
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		m_type = jsonData.getString("m_type");
		setAnimationSpeed( jsonData.getFloat("m_animationSpeed") );
	}
	
	
	
	
}