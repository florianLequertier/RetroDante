package com.retroDante.game.trigger;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Drawable;
import com.retroDante.game.Manager;
import com.retroDante.game.character.Character;

public class TriggerManager extends Manager<Trigger> implements Json.Serializable {
	
	List<Trigger> m_triggerContainer = new ArrayList<Trigger>();
	
	public TriggerManager()
	{
		
	}
	
	//Override Manager : 
	@Override
	public void add(Trigger trigger, int index)
	{
		m_triggerContainer.add(trigger);
	}
	@Override
	public boolean remove(Trigger trigger, int index)
	{
		return m_triggerContainer.remove(trigger);
	}
	
	
	public void update(float deltaTime, List<Character> targets)
	{
		for(Trigger currentTrigger : m_triggerContainer)
		{
			for(Character currentCharacter : targets)
			{
				if(currentCharacter.containedIn(currentTrigger)) //rencontre du trigger et du character
				{
					currentTrigger.triggerEventOn(currentCharacter);
				}
			}
		}
	}
	public void update(float deltaTime, Character currentCharacter)
	{
		for(Trigger currentTrigger : m_triggerContainer)
		{
			if(currentCharacter.containedIn(currentTrigger)) //rencontre du trigger et du character
			{
				currentTrigger.triggerEventOn(currentCharacter);
			}
		}
	}

	//à utiliser pour le debugging, ou lors de la création de map. 
	@Override
	public void draw(Batch batch) {
		
		for(Trigger t : m_triggerContainer)
		{
			t.draw(batch);
		}
		
	}
	
	//serialisation : 
	public static TriggerManager load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		//System.out.println(fileString);
		Json json = new Json();
		TriggerManager triggerManager = json.fromJson(TriggerManager.class, fileString);
		return triggerManager;
	}
	@Override
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
		
			json.writeArrayStart("triggerContainer");
				for(Trigger t : m_triggerContainer)
				{
					json.writeObjectStart();
						t.write(json);
					json.writeObjectEnd();
				}
			json.writeArrayEnd();
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		
		JsonValue triggerContainer =  jsonData.get("triggerContainer");
		for(int i=0; i<triggerContainer.size; i++)
		{
			JsonValue triggerValue = triggerContainer.get(i);
			
			String typeOfTrigger = triggerValue.getString("type");
			
			Trigger newTrigger = null;
			
			if ( typeOfTrigger.equals("teleport") )
			{
				newTrigger = json.fromJson(TeleportTrigger.class, triggerValue.toString());
			}
			else if ( typeOfTrigger.equals("damage") )
			{
				newTrigger = json.fromJson(DamageTrigger.class, triggerValue.toString());
			}
			else if ( typeOfTrigger.equals("kill") )
			{
				newTrigger = json.fromJson(KillTrigger.class, triggerValue.toString());
			}
			else if ( typeOfTrigger.equals("nextLevel") )
			{
				newTrigger = json.fromJson(NextLevel.class, triggerValue.toString());
			}
			else
			{
				System.out.println("attention ! Probleme dans TriggerManager : \n mauvais type de trigger lors du chargement");
			}

			
			this.add(newTrigger);
		}
			
	}
	
	@Override
	public String toString()
	{
		return "nombre de triggers dans triggerManager = "+m_triggerContainer.size();
	}
	
}
