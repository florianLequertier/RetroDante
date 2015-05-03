package com.retroDante.game.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Drawable;
import com.retroDante.game.Manager;
import com.retroDante.game.character.Character;
import com.retroDante.game.character.Enemy;
import com.retroDante.game.character.EnemyManager;

public class ItemManager extends Manager<Item> implements  Json.Serializable {

	List<Item> m_itemContainer = new ArrayList<Item>();
	
	static public ItemManager getInstance()
	{
		return INSTANCE;
	}
	public	ItemManager(){}
	private static ItemManager INSTANCE = new ItemManager();

	
	@Override
	public void add(Item element, int index) 
	{
		m_itemContainer.add(element);
	}
	@Override
	public boolean remove(Item element, int index) 
	{
		m_itemContainer.remove(index);
		return false;
	}
	
	public void clear()
	{
		m_itemContainer.clear();
	}
	
		
	public void update(float deltaTime, Character character)
	{
		Iterator<Item> it = m_itemContainer.iterator();
		while(it.hasNext())
		{
			Item item = it.next();
			
			item.update(deltaTime);
			item.OnItemEnter(character);
			
			if(!item.isAlive()) 
				it.remove();
		}
	}
	
	
	//drawable : 
	@Override
	public void draw(Batch batch) {
		
		for(Item item : m_itemContainer)
		{
			item.draw(batch);
		}
	}
	
	//pour le debug : 
	public void drawDebug(Batch batch) {

		for(Item item : m_itemContainer)
		{
			item.drawDebug(batch); //draw aussi les triggers
		}

	}
	
	
	//serialisation : 
	
	public static ItemManager load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		Json json = new Json();
		ItemManager itemManager = json.fromJson(ItemManager.class, fileString);
		return itemManager;
	}

	@Override
	public void save(String filePath)
	{
		Json json = new Json();
		String text = json.toJson(this);
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);// internal(filePath);
		file.writeString(text, false);
	}

	@Override
	public void write(Json json) {
		json.writeArrayStart("itemContainer");
		for(Item item : m_itemContainer)
		{
			json.writeObjectStart();
			item.write(json);
			json.writeObjectEnd();
		}
		json.writeArrayEnd();
	}

	@Override
	public void read(Json json, JsonValue jsonData) {

		JsonValue tempValue;
		JsonValue itemData;
		int size = 0;

		tempValue = jsonData.get("itemContainer");//.getChild("foregrounds");
		size = tempValue.size;
		for(int i=0; i< size; i++)
		{
			itemData = tempValue.get(i);
			System.out.println(itemData.toString());
			ItemLife item = json.fromJson(ItemLife.class, itemData.toString());
			this.add(item);

		}
	}


	
}
