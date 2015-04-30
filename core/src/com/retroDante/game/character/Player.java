package com.retroDante.game.character;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Animator;
import com.retroDante.game.Direction;
import com.retroDante.game.EffectCamera;
import com.retroDante.game.Element2D;
import com.retroDante.game.Force;
import com.retroDante.game.GameCamera;
import com.retroDante.game.GameManager;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;
import com.retroDante.game.attack.Attack;
import com.retroDante.game.attack.AttackEmitter;
import com.retroDante.game.attack.AttackManager;
import com.retroDante.game.attack.BurningHearthquake;

/**
 * 
 * Player représente le personnage controlé par le joueur. 
 * Il etend Character et implement en plus Controllable (cf : Controllable) pour Le controller.
 * Une caméra observe le joueur (design pattern observer) pour pouvoir le suivre. 
 * 
 * @author Florian
 *
 */
public class Player extends Character {

	private GameCamera m_camera;
	private EffectCamera m_effectCamera;
	private AttackEmitter m_weapon;
	private PlayerController m_controller;
	private boolean m_wounded = false;
	private float m_woundedTimer = 0;
	
	/**
	 * Le player est un solidBody 
	 */
	
	public Player(Texture tex) 
	{
		super(tex);
		m_type = "player";
		m_controller = new PlayerController();
		m_weapon = new AttackEmitter("burningHearthquake");
		m_isEnemy = false;
		
	}
	
	public Player(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		m_type = "player";
		m_controller = new PlayerController();
		m_weapon = new AttackEmitter("burningHearthquake");
		m_isEnemy = false;
		
	}
	
	public Player(TileSetInfo tileSet, int spriteIndex, float deltaAnim)
	{
		super(tileSet, spriteIndex);
		m_type = "player";
		m_controller = new PlayerController();
		
		m_animator = new Animator(tileSet.getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //créé une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(deltaAnim);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter("burningHearthquake");
		
		m_animator.changeSpeed(0.02f);
		
		m_isEnemy = false;

	}
	
	public Player()
	{
		super( TileSetManager.getInstance().get("player"), 0);
		m_type = "player";
		m_controller = new PlayerController();
		
		m_animator = new Animator(TileSetManager.getInstance().get("player").getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //créé une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter("burningHearthquake");
		
		m_animator.changeSpeed(0.02f);
		
		m_isEnemy = false;
		
	}
	
	//setters/ getters : 
	
	
	public void setController(PlayerController controller)
	{
		m_controller = controller;
	}
	
	public PlayerController getController()
	{
		return m_controller;
	}
	
	public void setCamera(GameCamera newCamera)
	{
		m_camera = newCamera;
	}
	
	public void setEffectCamera(EffectCamera effectCamera)
	{
		m_effectCamera = effectCamera;
	}
	
	public void removeCamera()
	{
		m_camera = null;
	}
	
	// gestion des armes : 
	
	public void changeWeapon(String weaponName)
	{
		m_weapon.changeAttack(weaponName);
	}
	
	public void changeWeapon(Attack newAttack)
	{
		m_weapon.changeAttack(newAttack);
	}
	
	
	//gestion de la mort du joueur : 
	@Override
	public void kill()
	{
		m_isDead = true;
		GameManager.getInstance().reloadLevel();
	}
	
	/**
	 * Création positionnement, puis ajout au manager, de l'attaque produite par l'attackEmitter du joueur.
	 */
	public void attack()
	{
		Attack attack = m_weapon.getAttackInstance();
		attack.setFromEnemy(false);
		if(m_rightDirection)
		{
			attack.setPosition(this.getPosition().add(32, 0));
			attack.setDirection(Direction.Right);
		}
		else
		{
			attack.setPosition(this.getPosition().add(32, 0));
			attack.setDirection(Direction.Left);
		}
			
		
		AttackManager.getInstance().add(attack); // ajout à l'attackManager.

		System.out.println("ATTACK !!!!!");
	}
	
	@Override
	public boolean canAttack()
	{
		if(m_weapon == null)
			return false;
		else
			return m_weapon.canAttack();
	}
	
	@Override
	public void takeDamage(float damageAmount)
	{
		super.takeDamage(damageAmount);
		if(damageAmount > 0)
		{
			m_wounded = true;
			m_woundedTimer = 0;
			
			if(m_effectCamera != null)
			m_effectCamera.activateEffect();
		}
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
	public boolean checkAction(String stateName)
	{
		return m_controller.checkAction(stateName);
	}
	
	@Override
	public boolean checkActionOnce(String stateName)
	{
		return m_controller.checkActionOnce(stateName);
	}
	
	private void updateWoundedEffect(float deltaTime)
	{
		m_woundedTimer += deltaTime;
		if(m_woundedTimer > 1)
		{
			m_woundedTimer = 0;
			m_wounded = false;
		}
		
	}
	
	//Override element2D :
	@Override
	public void update(float deltaTime, List<Element2D> others)
	{
		updateWoundedEffect(deltaTime);
		m_weapon.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime, others);
		
		if(this.m_isDead)
		{
			this.kill();
		}
	}
	@Override
	public void update(float deltaTime)
	{
		updateWoundedEffect(deltaTime);
		m_weapon.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime);
		
		if(this.m_isDead)
		{
			this.kill();
		}
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
		
		
		//on bouge la camera si elle est présente : 
		if(m_camera != null)
		{
			m_camera.follow( m_collider.getCenter(new Vector2(0,0)) );
		}
			
		
		//réinitialisation : 
		m_velocity = Vector2.Zero;
	}
	
	/**
	 * dessine uniquement le HUD du joueur (barre de vie,...)
	 * 
	 * @param batch
	 */
	public void drawHUD(Batch batch)
	{
		if(m_lifeBar != null)
		{
			m_lifeBar.setLife((int) this.m_life);
			m_lifeBar.setPosition(new Vector2(-Gdx.graphics.getWidth()*0.5f, Gdx.graphics.getHeight()*0.5f - 32));
			m_lifeBar.draw(batch);
		}
	}
	
	// Ajoute un effet quand le joueur est touché : quand le joueur est invulnérable, sa sprite devient transparente, quand il est touché, l'écran devient rouge
	@Override
	public void draw(Batch batch) {

		
		if(m_texRegion.isFlipX() &&  m_rightDirection)
		m_texRegion.flip(true, false);
		else if(!m_texRegion.isFlipX() &&  !m_rightDirection)
		m_texRegion.flip(true, false);
		
		if(this.m_isInvulnerable)
			batch.setColor(1,1,1,0.5f);
		else
			batch.setColor(1,1,1,1);
		
		this.updateTransform();
		batch.draw(m_texRegion, this.getDimension().x, this.getDimension().y, this.getTransform());
		
		/*
		if(this.m_wounded)
		{
			
			Color sreenColor = Color.WHITE;
			
			if(this.m_woundedTimer > 0.5f)
			{
				sreenColor = Color.WHITE;
				sreenColor.mul(1.f, this.m_woundedTimer*2.f - 1.f, this.m_woundedTimer*2.f - 1.f, 1.f);
			}
			else
			{
				sreenColor = Color.WHITE;
				sreenColor.mul(1.f, 1.f - this.m_woundedTimer*2.f, 1.f - this.m_woundedTimer*2.f, 1.f);
			}
			
			batch.setColor(sreenColor);
		}
		else*/
			batch.setColor(1,1,1,1);

		
	}
	
	//loader Json : 
	
	public static Player load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		//System.out.println(fileString);
		Json json = new Json();
		Player player = json.fromJson(Player.class, fileString);
		return player;
	}
	public void save(String filePath)
	{
		Json json = new Json();
		String text = json.toJson(this);
		System.out.println(text);
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);// internal(filePath);
		file.writeString(text, false);
	}
	
	@Override
	public void write(Json json) {
		json.writeObjectStart("player");
			super.write(json);
		json.writeObjectEnd();
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		JsonValue playerData = jsonData.get("player");
		super.read(json, playerData);
		System.out.println("playerData = "+playerData);
	}
	
	//autres methodes : 
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Player : \n").append(super.toString());
		
		return builder.toString();
		
	}
		
	

}
