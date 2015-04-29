package com.retroDante.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.retroDante.game.Editor.EditorScreen;

/**
 * 
 * gere les pause inGame, la distorsion du temps, ainsi que l'etat global du jeu.
 * 
 * @author florian
 *
 */
public class GameManager implements DataSingleton<GameManager>{
	
	Game m_game;
	float m_distortionFactor;
	boolean m_gamePaused;
	boolean m_timeIsDistorted;
	float m_fps;
	float m_savedTime = 0;
	float m_elapsedTime = 0;
	InputMultiplexer m_inputHandler; 
	private boolean m_followingStroy; // le jeu suit l'histoire ? Ou la partie est une partie customisée ? 
	private StoryChapter m_currentChapter; // chapitre actuel de l'histoire (ne sert que lors du mode histoire)
	private String m_currentFolderName; // le dossier contenant les ressources pour charger la map 
	//StateMachine m_gameState = new ReverseStateMachine();
	
	
	private static GameManager INSTANCE = new GameManager();
	public static GameManager getInstance(){
		return INSTANCE;
	}
	private GameManager()
	{
		m_inputHandler = new InputMultiplexer();
		m_distortionFactor = 1;
		m_gamePaused = false;
		m_timeIsDistorted = false;
		m_fps = 0.016f; //60 images par secondes
	}
	
	public void setGame(Game game)
	{
		m_game = game;
	}
	
	public void distortTime(float distortFactor)
	{
		m_distortionFactor = distortFactor;
		m_timeIsDistorted = true;
	}
	
	public void resetTimeDistortion()
	{
		m_distortionFactor = 1;
		m_timeIsDistorted = false;
	}
	
	public void togglePause()
	{
		m_gamePaused = (m_gamePaused)?false:true ;
	}
	
	public void setPause(boolean state)
	{
		m_gamePaused = state;
	}
	
	public void update(float deltaTime)
	{
		m_elapsedTime += deltaTime;
		m_fps = deltaTime;
	}
	
	public boolean canUpdateFrame()
	{
		if(m_elapsedTime >= m_fps * m_distortionFactor)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * remise à 0 des conditions de jeu lorsque l'on quitte un screen (= un niveau) de jeu. 
	 */
	public void resetGameSession()
	{
		m_distortionFactor = 1;
		m_gamePaused = false;
		m_timeIsDistorted = false;;
		m_savedTime = 0;
		m_elapsedTime = 0;
	}
	
	//getters / setters : 
	
	public void setSavedTime(float time)
	{
		m_savedTime = time;
		m_elapsedTime = 0;
	}
	
	public float getDistortionFactor()
	{
		return m_distortionFactor;
	}
	
	public boolean getGamePaused()
	{
		return m_gamePaused;
	}
	
	public boolean getTimeIsDistorted()
	{
		return m_timeIsDistorted;
	}
	
	/**
	 * 
	 * fonction retournant une nouvelle scene de jeu en fonction du nom passé.
	 * 
	 * @param name
	 * @return
	 */
	public boolean changeScreen(String name)
	{
		
		if(m_game == null)
			return false;
		
		
		if(name == "test")
		{
			this.m_followingStroy = false;
			this.m_currentFolderName = "";
			
			m_game.getScreen().dispose();
			m_game.setScreen(new TestScreen());
		}
		else if(name == "game")
		{
			this.m_followingStroy = true;
			this.m_currentChapter = StoryChapter.Tutorial;
			
			m_game.getScreen().dispose();
			m_game.setScreen(new GameScreen(StoryChapter.Tutorial));
		}
		else if(name == "editor")
		{
			this.m_followingStroy = false;
			this.m_currentFolderName = "";
			
			m_game.getScreen().dispose();
			m_game.setScreen(new EditorScreen());
		}
		else
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new MenuScreen());
		}
		
		return true;

	}
	/**
	 * 
	 * fonction chargeant une nouvelle scene 
	 * 
	 * @param name
	 * @return
	 */
	public boolean changeScreen(String name, String loadFolder)
	{
		
		if(m_game == null || loadFolder == "")
			return false;
		
		this.m_followingStroy = false;
		this.m_currentFolderName = loadFolder;
		
		if(name == "editor")
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new EditorScreen(loadFolder));
		}
		else if(name == "game")
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new GameScreen(loadFolder));
		}
		else
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new MenuScreen());
		}
		
		return true;

	}
	
	/**
	 * 
	 * fonction chargeant une nouvelle scene, correspondant forcement à une partie "histoire" 
	 * 
	 * @param name
	 * @return
	 */
	public boolean changeScreen(StoryChapter chapter)
	{
		
		this.m_followingStroy = true;
		
		if(m_game == null)
			return false;

		m_game.getScreen().dispose();
		m_game.setScreen(new GameScreen(chapter));
		
		return true;

	}
	
	public void quitGame()
	{
		m_game.dispose();
		Gdx.app.exit();
	}
	
	public void setCurrentChapter(StoryChapter chapter)
	{
		m_currentChapter = chapter;
	}
	
	public StoryChapter getCurrentChapter()
	{
		return m_currentChapter;
	}
	
	public void nextChapter()
	{
		if(m_currentChapter.hasNextChapter())
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new GameScreen(m_currentChapter.nextChapter()));
		}
		else
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new MenuScreen());
		}
	}
	
	public void reloadChapter()
	{
		m_game.getScreen().dispose();
		m_game.setScreen(new GameScreen(this.m_currentChapter));
	}
	
	public void reloadLevel()
	{
		m_game.getScreen().dispose();
		
		if(this.m_followingStroy)
			m_game.setScreen(new GameScreen(this.m_currentChapter));
		else
			m_game.setScreen(new GameScreen(this.m_currentFolderName));
	}
	
	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	
}
