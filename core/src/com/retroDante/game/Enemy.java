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
	
	Enemy(Texture tex) 
	{
		super(tex);
		m_type = "enemy";
		m_controller = new IAController();
		m_weapon = new AttackEmitter();
		
	}
	
	Enemy(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		m_type = "enemy";
		m_controller = new IAController();
		m_weapon = new AttackEmitter();
		
	}
	
	Enemy(TileSetInfo tileSet, int spriteIndex, float deltaAnim)
	{
		super(tileSet, spriteIndex);
		m_type = "enemy";
		m_controller = new IAController();
		
		m_animator = new Animator(tileSet.getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //créé une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(deltaAnim);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();

	}
	
	Enemy()
	{
		super( TileSetManager.getInstance().get("player"), 0);
		m_type = "enemy";
		m_controller = new IAController();
		
		m_animator = new Animator(TileSetManager.getInstance().get("player").getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //créé une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();
		
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
	 * Création positionnement, puis ajout au manager, de l'attaque produite par l'attackEmitter du joueur.
	 */
	public void attack()
	{
		Attack attack = m_weapon.getAttackInstance();
		if(m_rightDirection)
		{
			attack.setPosition(this.getPosition().add(70, 0));
			attack.setDirection(Direction.Right);
		}
		else
		{
			attack.setPosition(this.getPosition().add(-6, 0));
			attack.setDirection(Direction.Left);
		}
			
		
		AttackManager.getInstance().add(attack); // ajout à l'attackManager.
		if(attack instanceof BurningHearthquake)
		{
			System.out.println("ATTACK REUSSIE");
		}
		else
		{
			System.out.println("ATTACK NON REUSSIE");
		}
		System.out.println("ATTACK !!!!!");
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
	public void update(float deltaTime, List<Element2D> others, Vector2 targetPosition)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		m_controller.setTargetPosition(targetPosition);

		m_controller.setAttackRange(100);
		m_controller.setOwnLife(m_life);
		m_controller.setOwnPosition(getPosition());
		m_controller.setVisibility(10000000);
		m_controller.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime, others);
	}
	public void update(float deltaTime, Vector2 targetPosition)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		m_controller.setAttackRange(100);
		m_controller.setOwnLife(m_life);
		m_controller.setOwnPosition(getPosition());
		m_controller.setVisibility(10000000);
		m_controller.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime);
	}
	
	//Override element2D :
	@Override
	public void update(float deltaTime, List<Element2D> others)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		m_controller.setAttackRange(100);
		m_controller.setOwnLife(m_life);
		m_controller.setOwnPosition(getPosition());
		m_controller.setVisibility(10000000);
		m_controller.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime, others);
	}
	@Override
	public void update(float deltaTime)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		m_controller.setAttackRange(100);
		m_controller.setOwnLife(m_life);
		m_controller.setOwnPosition(getPosition());
		m_controller.setVisibility(10000000);
		m_controller.update(deltaTime);
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime);
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
