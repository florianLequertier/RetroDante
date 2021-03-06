package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.retroDante.game.attack.AttackManager;
import com.retroDante.game.attack.BurningHearthquake;
import com.retroDante.game.character.Character;
import com.retroDante.game.character.Enemy;
import com.retroDante.game.character.EnemyManager;
import com.retroDante.game.character.Player;
import com.retroDante.game.character.PlayerController;
import com.retroDante.game.map.Map;
import com.retroDante.game.map.MapElement;
import com.retroDante.game.trigger.TeleportTrigger;
import com.retroDante.game.trigger.TriggerManager;

public class TestScreen implements Screen, InputProcessor{

	private Stage m_stage;
	private Batch batch;
    private BitmapFont font;
    GameCamera gameCamera;
    HUDCamera hudCamera;
    Player player;
    PlayerController playerController;
    List<Element2D> m_platformContainer = new ArrayList<Element2D>();
    Map map;
    TriggerManager triggerManager;
    AttackManager attackManager = AttackManager.getInstance(); //singleton
	TileSetManager tileSetManager = TileSetManager.getInstance(); //singleton
	GameManager gameManager = GameManager.getInstance(); //Singleton
	Enemy enemy; //test 
	EnemyManager enemyManager;
	HUDGame m_hudManager;
	InputMultiplexer m_inputHandler;
    
	
	@Override
	public void show() {
		
		m_stage = new Stage();
		batch = m_stage.getBatch();//new SpriteBatch();
		font = new BitmapFont();
		
		
		//camera : 
		gameCamera = new GameCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		batch.setProjectionMatrix(gameCamera.combined);
		hudCamera = new HUDCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		//hudCamera.translate(-Gdx.graphics.getWidth()*0.5f, -Gdx.graphics.getHeight()*0.5f);
		

		//TileSetManager tileSetManager = TileSetManager.getInstance();
		tileSetManager.load(Gdx.files.getLocalStoragePath()+"/asset/"+"textureInfo/tileSetManagerLoader.txt");
		System.out.println(tileSetManager.toString());
		
		//test map save : OK 
		map = Map.createMapTest();
		map.save("test_save_map.txt");
		map = Map.load("test_save_map.txt");
		gameCamera.setParralaxTarget(map); //observe camera
		
		TileSetInfo playerTileSet = tileSetManager.get("player");
    	player = new Player(playerTileSet, 0, 0.2f);//new Texture(Gdx.files.internal("badlogic.jpg"))
    	player.setPosition(new Vector2(200, 400));
    	player.setCamera(gameCamera);
    	playerController = player.getController();
    	
    	//test save player, OK : 
    	
	    	//player.setPosition(new Vector2(100,300));
	    	//player.setLife(10);
	    	//player.save("test_save_player.txt");
	    	//player = Player.load("test_save_player.txt");
	    	//System.out.println(player.toString());
	    	
			//Json json = new Json();
			//String text = json.toJson(player);
			//System.out.println(text);
			//FileHandle file = Gdx.files.local("test_save_player.txt");
			//file.writeString(text, true);
			//json.toJson(player, Gdx.files.internal("test_save_player.txt"));
		
		//fin test save
		
    	//player.addConstantForce(new Force(new Vector2( 0, 9), Force.TypeOfForce.CONSTANT));
    	
    	//platforms : 
    	TileSetInfo platformTileSet = new TileSetInfo("texture/badlogic.jpg", "textureInfo/aaa.txt");
    	Element2D platform = new MapElement(platformTileSet, 0);
    	platform.setPosition(new Vector2(200, 100));
    	m_platformContainer.add(platform);
    	
    	
    	//Triggers : 
    	triggerManager = new TriggerManager();
	    	TeleportTrigger trigger = new TeleportTrigger(new Vector2(0,500));
	    	trigger.setPosition(new Vector2(0,200));
	    	trigger.setDimension(new Vector2(200,200));
    	triggerManager.add(trigger);
    	
    	triggerManager.save("test_save_triggerManager.txt");
    	triggerManager = TriggerManager.load("test_save_triggerManager.txt");
    	
    	
    	gameCamera.setPosition(player.getPosition());
    	gameCamera.update(); //update camera
    	
    	
    	enemy = new Enemy(); //test
    	enemy.setPosition(new Vector2(400, 400));
    	
    	enemyManager = new EnemyManager();
    	enemyManager.add(enemy);
    	
    	enemyManager.save("test_save_enemy.txt");
    	enemyManager = EnemyManager.load("test_save_enemy.txt");
    	
    	
    	//HUD manager : 
    	m_hudManager = new HUDGame(m_stage);
    	//m_stage.addActor(m_hudManager);
    	
    	//input handler : 
    	//m_inputHandler = new InputMultiplexer(m_stage, playerController);
    	InputMultiplexer inputHandler = new InputMultiplexer();
			inputHandler.addProcessor(m_stage);
			inputHandler.addProcessor(playerController);
		Gdx.input.setInputProcessor(inputHandler);
       	//Gdx.input.setInputProcessor(m_inputHandler);
		
	}
	
	private void update(float delta)	
	{
		
		List<Element2D> listCollider = new ArrayList<Element2D>();
		listCollider.addAll(map.getColliders());
		listCollider.addAll(m_platformContainer);
		
		player.update(delta, listCollider);
		//enemy.update(delta, listCollider, player.getPosition()); //test
		enemyManager.update(delta, listCollider, player.getPosition());
		
		List<Character> listCharacter = new ArrayList<Character>();
			listCharacter.add(player);
		triggerManager.update(delta, listCharacter);
		
		if(Gdx.input.isKeyPressed(Input.Keys.E))
		{
			System.out.println("coucou");
			attackManager.add(new BurningHearthquake(new Vector2(100,100)));
		}
		//attackManager.updateOnEnemies(delta, listCharacter);
		
		
	}
	
	private void draw(Batch batch)
	{
		batch.setProjectionMatrix(gameCamera.combined);
		
		batch.begin();
		
		//font.draw(batch, "Bienvenue dans inGameScreen",50,Gdx.graphics.getHeight()-50);
			
			//arriere plan : 
		     map.drawBackgroungWithParralax(batch);
		     
		     
		     //plan du milieu : 
		     
		     for(Element2D e : m_platformContainer)
		     {
		    	 e.draw(batch);
		     }
		     
		     //map.draw(batch);
		     //map.drawWithParralax(batch); //version avec parralax
		     map.drawMaingroundWithParralax(batch);
		     player.draw(batch);
		     //enemy.draw(batch); //test
		     enemyManager.draw(batch);
		     
		     attackManager.draw(batch);
		     //attackManager.drawDebug(batch);
		     
	
		     //plan avant : 
		     map.drawForegroundWithParralax(batch);
		     
		     //HUD : 
		     batch.setProjectionMatrix(hudCamera.combined);
		     if(gameManager.getGamePaused())
		     {
		    	 font.setUseIntegerPositions(true);
		    	 font.setScale(2, 2);
		    	 font.draw(batch, "PAUSE", -45, 100);//Gdx.graphics.getWidth() *0.5f - 130,Gdx.graphics.getHeight()*0.5f + 100);
		     }
		     //m_hudManager.act(Gdx.graphics.getDeltaTime());
		     //m_hudManager.draw(batch);
		     
		     batch.setProjectionMatrix(gameCamera.combined);
			 triggerManager.draw(batch); //for debug ou creation de la map
		     
			 
			 
			 
		 batch.end();
		 
		m_stage.act(Gdx.graphics.getDeltaTime());
		m_stage.draw();
		 //le HUD poss�de son propre batch : 
		 //m_hudManager.draw();
		     
		
		 
		
		 
		

	}

	@Override
	public void render(float delta) {
		
		//update du gameManager prioritaire
		gameManager.update(delta);
		
		//evite un bug lors du deplacement de la fenetre
		if(delta > 0.5)
			return;
		
		//updateCamera
		gameCamera.update();
		
		//update de la logique :
		if(gameManager.getGamePaused() == false)
		{
			if(!gameManager.getTimeIsDistorted())
			{
				if(gameManager.canUpdateFrame())
				{
					update(delta);
				}
			}
			else
			{
				update(delta);
			}
		}

		
		
		//update du rendu : 
		Gdx.gl.glClearColor(0.2f, 0.2f,0.2f, 1f);
	    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	        
	    //commande de dessin : 
		draw(batch);
		

	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
		attackManager.clear(); // AttackManager �tant dans le stack, il ne faut pas oublier d'enlever les elements qu'elle contient lorsque l'on quitte le niveau.
		tileSetManager.clear(); // idem
		
		m_hudManager.dispose();
	}

	@Override
	public boolean keyDown(int keycode) {
		//playerController.listenKeyDown(keycode);
		//player.listenKey(KeyStatus.DOWN, keycode);
		
		
		//pause du jeu : 
		if(keycode == Keys.ESCAPE)
		{
			//gameManager.togglePause();
			m_hudManager.tooglePauseMenu();
		}
		
		
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		//playerController.listenKeyUp(keycode);
		//player.listenKey(KeyStatus.UP, keycode);
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		//playerController.listenButtonDown(button);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		//playerController.listenButtonUp(button);
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
	
}
