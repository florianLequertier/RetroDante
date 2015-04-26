package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.retroDante.game.character.Character;
import com.retroDante.game.character.Enemy;
import com.retroDante.game.character.EnemyManager;
import com.retroDante.game.character.Player;
import com.retroDante.game.character.PlayerController;
import com.retroDante.game.map.Map;
import com.retroDante.game.map.MapElement;
import com.retroDante.game.trigger.TeleportTrigger;
import com.retroDante.game.trigger.TriggerManager;

public class GameScreen  implements Screen, InputProcessor{

	private Stage m_stage;
	private Batch batch;
    private BitmapFont font;
    GameCamera gameCamera;
    HUDCamera hudCamera;
    Player player;
    PlayerController playerController;
    Map map;
    TriggerManager triggerManager;
	EnemyManager enemyManager;
	HUDGame m_hudManager;
	InputMultiplexer m_inputHandler;
	AttackManager attackManager = AttackManager.getInstance(); //singleton
	TileSetManager tileSetManager = TileSetManager.getInstance(); //singleton
	GameManager gameManager = GameManager.getInstance(); //Singleton
    private String folderPath; //chemin vers le dossier source des ressources de cette map.
	
    
	GameScreen(String loadPath)
	{
		folderPath = loadPath;
	}
	
	GameScreen(StoryChapter chapter)
	{
		gameManager.setCurrentChapter(chapter);
		folderPath = chapter.getFolderName();
	}
	
	/**
	 * charge les ressources de la map. 
	 * La map charg� es tune map custom (cr�� par le joueur). Ce n'es tpas une map Story (map sp�ciales suivant l'histoire du jeu)
	 */
	private void loadRessourcesFromCustom()
	{
		//Initialisation map : 
		map = Map.load(folderPath+"/map.txt");
		//updateCamera : 
		gameCamera.setParralaxTarget(map); //observe camera

		//Initialisation Player : 
		player = Player.load(folderPath+"/player.txt");
		player.setCamera(gameCamera);
		playerController = player.getController();
		//updateCamera : 
		gameCamera.setPosition(player.getPosition());
		gameCamera.update(); //update camera


		//Initialisation triggers : 
		triggerManager = TriggerManager.load(folderPath+"/trigger.txt");  	


		//Initialisation Ennemis : 
		enemyManager = EnemyManager.load(folderPath+"/enemy.txt");  
	}
	
	@Override
	public void show() {
		
		//Initialisation stage : 
		m_stage = new Stage();
		
		//Initialisation batch : 
		batch = m_stage.getBatch();
		
		//Initialisation font : 
		font = new BitmapFont();
		
		//Initialisation cameras (gameCamera / hudCamera) : 
		gameCamera = new GameCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		batch.setProjectionMatrix(gameCamera.combined);
		hudCamera = new HUDCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() );
		

		//Initialisation tileSetManager : 
		tileSetManager.load(Gdx.files.getLocalStoragePath()+"/asset/"+"textureInfo/tileSetManagerLoader.txt");
		
		//load Map Ressources (mapElements, triggers, player, enemies,...)
		this.loadRessourcesFromCustom();
		
		 	
    	
    	//Initialisation HUD : 
    	m_hudManager = new HUDGame(m_stage);
    	
    	//input handler : 
    	InputMultiplexer inputHandler = new InputMultiplexer();
			inputHandler.addProcessor(m_stage);
			inputHandler.addProcessor(playerController);
		Gdx.input.setInputProcessor(inputHandler);
		
	}
	
	private void update(float delta)	
	{
		
		List<Element2D> listCollider = new ArrayList<Element2D>();
		listCollider.addAll(map.getColliders());
		
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
		attackManager.update(delta, listCharacter);
		
		
	}
	
	private void draw(Batch batch)
	{
		
		//////////////////////////////////////////////////
		//////////////// dessin du jeu //////////////////
		
		batch.setProjectionMatrix(gameCamera.combined);
		
		batch.begin();
			
			 //arriere plan : 
		     map.drawBackgroungWithParralax(batch);
		     
		     
		     //plan du milieu : 
		     map.drawMaingroundWithParralax(batch);
		     player.draw(batch);
		     enemyManager.draw(batch);
		     attackManager.draw(batch);
		     
	
		     //plan avant : 
		     map.drawForegroundWithParralax(batch);
		     
		     
		     //�l�ments annexe : 
		     batch.setProjectionMatrix(hudCamera.combined);
		     if(gameManager.getGamePaused())
		     {
		    	 font.setUseIntegerPositions(true);
		    	 font.setScale(2, 2);
		    	 font.draw(batch, "PAUSE", -45, 100);
		     }
		     
		     batch.setProjectionMatrix(gameCamera.combined);
			 triggerManager.draw(batch); //for debug ou creation de la map
		     
		 batch.end();
		 
		 
		 
		 
		//////////////////////////////////////////////////
		//////////////// dessin du HUD ///////////////////

		m_stage.act(Gdx.graphics.getDeltaTime());
		m_stage.draw();
		
	}
	
	
	
	
	
	/**
	 * Rendu de la fenetre
	 */
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
	
		//pause du jeu : 
		if(keycode == Keys.ESCAPE)
		{
			m_hudManager.tooglePauseMenu();
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
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