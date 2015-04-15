package com.retroDante.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;

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
	boolean changeScreen(String name)
	{
		
		if(m_game == null)
			return false;
		
		
		if(name == "test")
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new TestScreen());
		}
		else
		{
			m_game.getScreen().dispose();
			m_game.setScreen(new MenuScreen());
		}
		
		return true;

	}
	
	
	@Override
	public void clear() {
		
	}
	
	
	
}
