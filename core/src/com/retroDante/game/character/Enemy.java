package com.retroDante.game.character;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Animator;
import com.retroDante.game.Direction;
import com.retroDante.game.Element2D;
import com.retroDante.game.Force;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;
import com.retroDante.game.Controllable.KeyStatus;
import com.retroDante.game.attack.Attack;
import com.retroDante.game.attack.AttackEmitter;
import com.retroDante.game.attack.AttackManager;
import com.retroDante.game.attack.BurningHearthquake;


/**
 * 
 * Classe de base pour les ennemis du jeu. Les ennemis sont controllable pas un IAController
 * 
 * @author florian
 *
 */
public class Enemy extends Character{

	private AttackEmitter m_weapon;
	private IAController m_controller;
	
	/**
	 * Le player est un solidBody 
	 */
	
	public Enemy(Texture tex) 
	{
		super(tex);
		m_type = "enemy";
		m_controller = new IAController();
		m_weapon = new AttackEmitter();
		
		m_lifeBar.setScale(new Vector2(0.3f,0.3f));
		
		m_speed = 50;
		m_life = 4;
		
	}
	
	public Enemy(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		m_type = "enemy";
		m_controller = new IAController();
		m_weapon = new AttackEmitter();
		
		m_lifeBar.setScale(new Vector2(0.3f,0.3f));
		
		m_speed = 50;
		m_life = 4;
		
	}
	
	public Enemy(TileSetInfo tileSet, int spriteIndex, float deltaAnim)
	{
		super(tileSet, spriteIndex);
		m_type = "enemy";
		m_controller = new IAController();
		
		m_animator = new Animator(tileSet.getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //cr�� une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(deltaAnim);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();
		
		m_lifeBar.setScale(new Vector2(0.3f,0.3f));
		
		m_animator.changeSpeed(0.02f);
		
		m_speed = 50;
		m_life = 4;

	}
	
	public Enemy()
	{
		super( TileSetManager.getInstance().get("enemy"), 0);
		m_type = "enemy";
		m_controller = new IAController();
		
		m_animator = new Animator(TileSetManager.getInstance().get("enemy").getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //cr�� une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();
		
		m_lifeBar.setScale(new Vector2(0.3f,0.3f));
		
		m_animator.changeSpeed(0.02f);
		
		m_speed = 50;
		m_life = 4;
		
	}
	
	public Enemy(int index)
	{
		super( TileSetManager.getInstance().get("enemy"), index);
		m_type = "enemy";
		m_controller = new IAController();
		
		m_animator = new Animator(TileSetManager.getInstance().get("enemy").getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //cr�� une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();
		
		m_lifeBar.setScale(new Vector2(0.3f,0.3f));
		
		m_animator.changeSpeed(0.02f);
		
		m_speed = 50;
		m_life = 4;
		
	}
	
	//setters/ getters : 
	
	
	public void setController(IAController controller)
	{
		m_controller = controller;
	}
	
	public IAController getController()
	{
		return m_controller;
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
	
	/**
	 * Cr�ation positionnement, puis ajout au manager, de l'attaque produite par l'attackEmitter du joueur.
	 */
	public void attack()
	{
		Attack attack = m_weapon.getAttackInstance();
		attack.setFromEnemy(true);
		if(m_rightDirection)
		{
			attack.setPosition(this.getPosition().add(0, 0));
			attack.setDirection(Direction.Right);
		}
		else
		{
			attack.setPosition(this.getPosition().add(0, 0));
			attack.setDirection(Direction.Left);
		}
			
		
		AttackManager.getInstance().add(attack); // ajout � l'attackManager.
		
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
	
	//overrides Controllers : 
	
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
	
	//update 
	/**
	 * check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
	 * 
	 * @param deltaTime
	 * @param targetPosition
	 */
	public void updateIAController(float deltaTime, Vector2 targetPosition)
	{
		m_controller.setTargetPosition(targetPosition);
		m_controller.setAttackRange(1000);
		m_controller.setOwnLife(m_life);
		m_controller.setOwnPosition(getPosition());
		m_controller.setVisibility(100000);
		m_controller.update(deltaTime);
	}
	public void updateIAController(float deltaTime)
	{
		this.updateIAController(deltaTime, new Vector2(0,0));
	}
	
	public void update(float deltaTime, List<Element2D> others, Vector2 targetPosition)
	{
 
		m_weapon.update(deltaTime);
		updateIAController(deltaTime, targetPosition);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entit�, change l'action a effectuer et l'animation � jouer
		super.update(deltaTime, others);
		
		
	}
	public void update(float deltaTime, Vector2 targetPosition)
	{

		m_weapon.update(deltaTime);
		updateIAController(deltaTime, targetPosition);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entit�, change l'action a effectuer et l'animation � jouer
		super.update(deltaTime);

	}
	
	//Override element2D :
	@Override
	public void update(float deltaTime, List<Element2D> others)
	{

		m_weapon.update(deltaTime);
		updateIAController(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entit�, change l'action a effectuer et l'animation � jouer
		super.update(deltaTime, others);
	}
	@Override
	public void update(float deltaTime)
	{
		m_weapon.update(deltaTime);
		updateIAController(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entit�, change l'action a effectuer et l'animation � jouer
		super.update(deltaTime);
	}
	

	@Override
	public void draw(Batch batch)
	{
			
		//Color screenColor = batch.getColor();
		
		if(m_texRegion.isFlipX() &&  m_rightDirection)
			m_texRegion.flip(true, false);
		else if(!m_texRegion.isFlipX() &&  !m_rightDirection)
			m_texRegion.flip(true, false);

		if(this.m_isInvulnerable)
			batch.setColor(1,1,1,0.5f);
		else
			batch.setColor(new Color(Color.WHITE));

		this.updateTransform();
		batch.draw(m_texRegion, this.getDimension().x, this.getDimension().y, this.getTransform());

		batch.setColor(new Color(Color.WHITE));


		m_lifeBar.setLife((int) this.m_life);
		m_lifeBar.setPosition(this.getPosition().add(0, 64));
		m_lifeBar.draw(batch);
		
	}
	
	
	//loader Json : 
	
	@Override
	public void write(Json json) {
		//json.writeObjectStart("enemy");
			super.write(json);
		//json.writeObjectEnd();
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		//JsonValue playerData = jsonData.get("enemy");
		super.read(json, jsonData);
		System.out.println("enemyData = "+jsonData);
	}
	
	//autres methodes : 
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();
		builder.append("Enemy : \n").append(super.toString());
		
		return builder.toString();
		
	}

		
	
	
}
