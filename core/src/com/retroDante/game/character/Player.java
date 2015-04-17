package com.retroDante.game.character;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Animator;
import com.retroDante.game.Attack;
import com.retroDante.game.AttackEmitter;
import com.retroDante.game.AttackManager;
import com.retroDante.game.BurningHearthquake;
import com.retroDante.game.Direction;
import com.retroDante.game.Element2D;
import com.retroDante.game.Force;
import com.retroDante.game.GameCamera;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;

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
	private AttackEmitter m_weapon;
	private PlayerController m_controller;
	
	/**
	 * Le player est un solidBody 
	 */
	
	public Player(Texture tex) 
	{
		super(tex);
		m_type = "player";
		m_controller = new PlayerController();
		m_weapon = new AttackEmitter();
		
	}
	
	public Player(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		m_type = "player";
		m_controller = new PlayerController();
		m_weapon = new AttackEmitter();
		
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
		
		m_weapon = new AttackEmitter();

	}
	
	Player()
	{
		super( TileSetManager.getInstance().get("player"), 0);
		m_type = "player";
		m_controller = new PlayerController();
		
		m_animator = new Animator(TileSetManager.getInstance().get("player").getForAnimation(0,1,2,3), "idle", "walk", "jump", "attack"); //créé une list avec les quatres premieres lignes du tileSet (correspondant donc aux 3 premieres animations)
		setAnimationSpeed(1.f);
		m_animator.changeAnimation(0);
		m_animator.play(true);
		
		m_weapon = new AttackEmitter();
		
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
	
//	@Override
//	public void listenKey(KeyStatus status, int keycode) {
//		
//		if(status == KeyStatus.DOWN)
//			m_controller.listenKeyDown(keycode);
//		else if(status == KeyStatus.UP)
//			m_controller.listenKeyUp(keycode);
//	}
	
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
	
	//Override element2D :
	@Override
	public void update(float deltaTime, List<Element2D> others)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
		super.update(deltaTime, others);
	}
	@Override
	public void update(float deltaTime)
	{
		//checkController(); //check le controller avant l'update des forces. Permet de rajouter les forces pour le saut, ou de modifier la vitesse
		updateStateMachine(); //remplace le checkController, gere les etats de l'entité, change l'action a effectuer et l'animation à jouer
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
		
		
		//on bouge la camera si elle est présente : 
		if(m_camera != null)
		{
			m_camera.follow( m_collider.getCenter(new Vector2(0,0)) );// getPosition().add( new Vector2(m_collider.getWidth()*0.5f, m_collider.getHeight()*0.5f) ) );
		}
			
		
		//réinitialisation : 
		m_velocity = Vector2.Zero;
	}
	
	//loader Json : 
	
	static Player load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		//System.out.println(fileString);
		Json json = new Json();
		Player player = json.fromJson(Player.class, fileString);
		return player;
	}
	void save(String filePath)
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
