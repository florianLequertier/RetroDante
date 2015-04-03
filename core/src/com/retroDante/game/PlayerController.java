package com.retroDante.game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.badlogic.gdx.Input.Keys;


public class PlayerController extends Controller {

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
		
	}
	
	boolean checkAction(String actionName)
	{
		if(mappedEvent.containsKey(actionName))
			return mappedEvent.get(actionName);
		else
			return false;
	}
	
	boolean checkActionOnce(String actionName)
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
	
	void listenKeyDown(int keycode)
	{
		String eventName = mappedKey.get(keycode);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, true);
		}
	}
	
	void listenKeyUp(int keycode)
	{
		String eventName = mappedKey.get(keycode);
		
		if(eventName != null)
		{
			mappedEvent.put(eventName, false);
		}
	}
	
}
