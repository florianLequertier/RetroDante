package com.retroDante.game.character;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javafx.scene.input.MouseButton;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.retroDante.game.Controller;


public class PlayerController extends Controller implements InputProcessor {

	Map<Integer, String> mappedKey = new HashMap<Integer, String>(); // associe à chaque key, un evenement
	Map<Integer, String> mappedButton = new HashMap<Integer, String>(); // associe à chaque button, un evenement
	
	PlayerController()
	{
		
		mappedKey.put(Keys.D, "walk_right");
		mappedKey.put(Keys.RIGHT, "walk_right");
			mappedEvent.put("walk_right", false); //initialisation de l'evenement lié aux touches D ou right
		
		mappedKey.put(Keys.Q, "walk_left");
		mappedKey.put(Keys.LEFT, "walk_left");
			mappedEvent.put("walk_left", false);
		
		mappedKey.put(Keys.SPACE, "jump");
			mappedEvent.put("jump", false);
			
		mappedButton.put(Buttons.LEFT, "attack");
			mappedEvent.put("attack", false);
		
	}
	
	@Override
	public boolean checkAction(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
			return mappedEvent.get(actionName);
		else
			return false;
	}
	
	@Override
	public boolean checkActionOnce(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
		{
			boolean state = mappedEvent.get(actionName);
			mappedEvent.put(actionName, false); //remet l'evenement à false
			return state;
		}
		else
			return false;
	}
	
	
//	/**
//	 * 
//	 * ecoute l'evennement de type button down dans le screen
//	 * 
//	 * @param keycode
//	 */
//	void listenButtonDown(int buttoncode)
//	{
//		String eventName = mappedButton.get(buttoncode);
//		
//		if(eventName != null)
//		{
//			mappedEvent.put(eventName, true);
//		}
//	}
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type button up dans le screen
//	 * 
//	 * @param keycode
//	 */
//	void listenButtonUp(int buttoncode)
//	{
//		String eventName = mappedButton.get(buttoncode);
//		
//		if(eventName != null)
//		{
//			mappedEvent.put(eventName, false);
//		}
//	}
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type key down dans le screen
//	 * 
//	 * @param keycode
//	 */
//	void listenKeyDown(int keycode)
//	{
//		String eventName = mappedKey.get(keycode);
//		
//		if(eventName != null)
//		{
//			mappedEvent.put(eventName, true);
//		}
//	}
//	
//	/**
//	 * 
//	 * ecoute l'evennement de type key up dans le screen
//	 * 
//	 * @param keycode
//	 */
//	void listenKeyUp(int keycode)
//	{
//		String eventName = mappedKey.get(keycode);
//		
//		if(eventName != null)
//		{
//			mappedEvent.put(eventName, false);
//		}
//	}

	@Override
	public boolean keyDown(int keycode) {
		String eventName = mappedKey.get(keycode);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, true);
		}
		
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		String eventName = mappedKey.get(keycode);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, false);
		}
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		
		String eventName = mappedButton.get(button);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, true);
		}
		
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		
		String eventName = mappedButton.get(button);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, true);
		}
		
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
