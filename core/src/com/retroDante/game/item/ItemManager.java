package com.retroDante.game.item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.retroDante.game.Drawable;
import com.retroDante.game.character.Character;

public class ItemManager implements Drawable {

	List<Item> m_itemContainer = new ArrayList<Item>();
	
	static public ItemManager getInstance()
	{
		return INSTANCE;
	}
	private	ItemManager()	{}
	private static ItemManager INSTANCE = new ItemManager();
	
	
	public void add(Item item)
	{
		m_itemContainer.add(item);
	}
	
	public void delete(int index)
	{
		m_itemContainer.remove(index);
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
	
}
