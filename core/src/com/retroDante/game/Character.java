package com.retroDante.game;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Controllable.KeyStatus;

/**
 * 
 * Character repr�sente toutes les entit�es mobiles, animables, tuables, bref, les protagonnistes du jeu (joueur, ennemis, ... )
 * 
 * @author Florian
 *
 */
public class Character extends Element2D implements Json.Serializable {
	
	
	float m_life;
	boolean m_isDead;
	float m_speed;
	boolean m_isGrounded;
	
	/**
	 * Le character est un solidBody 
	 */
	
	Character(Texture tex) 
	{
		super(tex);
		this.makeSolidBody();
		m_type = "character";
		
		m_life = 100.f;
		m_speed = 60.f;
		m_isDead = false;
		
	}
	
	Character(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		this.makeSolidBody();
		m_type = "character";
		
		m_life = 100.f;
		m_speed = 60.f;
		m_isDead = false;
		
	}
	
	Character(TileSetInfo tileSet, int spriteIndex, float deltaAnim)
	{
		super(tileSet, spriteIndex);
		this.makeSolidBody();
		m_type = "character";
		
		m_life = 100.f;
		m_speed = 60.f;
		m_isDead = false;
		
		m_animator = new Animator(tileSet.getForAnimation(0,1,2)); //cr�� une list avec les trois premiere ligne du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(deltaAnim);
		m_animator.changeAnimation(0);
		m_animator.play(true);
			
	}
	
	Character()
	{
		super( TileSetManager.getInstance().get("player"), 0);
		this.makeSolidBody();
		m_type = "character";
		
		m_life = 100.f;
		m_speed = 60.f;
		m_isDead = false;

		m_animator = new Animator(TileSetManager.getInstance().get("player").getForAnimation(0,1,2)); //cr�� une list avec les trois premiere ligne du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
	}
	
	//setters/ getters : 
	
	public void sendDammage(float damageAmount)
	{
		m_life -= damageAmount;
	}
	
	public void setLife(float life)
	{
		m_life = life;
	}
	
	public float getLife()
	{
		return m_life;
	}
	
	public void kill()
	{
		m_isDead = true;
	}
	
	//ne percute pas les murs : 
	
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
			
		
		//r�initialisation : 
		m_velocity = Vector2.Zero;
	}
	
	//loader Json : 
	
	@Override
	public void write(Json json) {
			super.write(json);
			json.writeValue("m_life", m_life);
			json.writeValue("m_speed", m_speed);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		m_speed = jsonData.getFloat("m_speed");
		m_life = jsonData.getFloat("m_life");
	}
	
	//autres methodes : 
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("character : \n").append("\n vie : ").append(m_life).append("\n vitesse : ").append(m_speed).append("\n position : ").append(getPosition().toString()).append("\n vitesse d'animation :  ").append(getAnimationSpeed());
		
		return builder.toString();
		
	}
		
	
	
}
