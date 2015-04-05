package com.retroDante.game;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Map implements Drawable{

	List<MapLayout> m_backgrounds;
	List<MapLayout> m_foregrounds;
	MapLayout m_mainground;
	
	//constructeur : 
	Map()
	{
		m_backgrounds = new ArrayList<MapLayout>();
		m_foregrounds = new ArrayList<MapLayout>();
		m_mainground = new MapLayout();
	}
	
	//factories : 
	static public Map createMapTest()
	{
		Map map = new Map();
		Platform p = new Platform();
		p.setPosition(new Vector2(400, 200));
			map.addToMainground(p);
		p = new Platform();
		p.setPosition(new Vector2(100, 20));
			map.addToMainground(p);
		p = new Platform();
		p.setPosition(new Vector2(10, 200));
			map.addToMainground(p);
			
		
		return map;
	}
	
	//ajout à la map : 
	void addToForeground(int index, Element2D element)
	{
		try
		{
			MapLayout currentLayout = m_foregrounds.get(index);
			currentLayout.addElement(element);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("mauvais ajout dans map : index incorrect");
		}
	}
	
	void addToBackground(int index, Element2D element)
	{
		try
		{
			MapLayout currentLayout = m_backgrounds.get(index);
			currentLayout.addElement(element);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("mauvais ajout dans map : index incorrect");
		}
	}
	
	void addToMainground(Element2D element)
	{
		m_mainground.addElement(element);
	}
	
	//suppression de la map : 
	void removeToForeground(int index, Element2D element)
	{
		try
		{
			MapLayout currentLayout = m_foregrounds.get(index);
			currentLayout.removeElement(element);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("mauvais ajout dans map : index incorrect");
		}
	}
	
	void removeToBackground(int index, Element2D element)
	{
		try
		{
			MapLayout currentLayout = m_backgrounds.get(index);
			currentLayout.removeElement(element);
		}
		catch(IndexOutOfBoundsException e)
		{
			System.out.println("mauvais ajout dans map : index incorrect");
		}
	}
	
	void removeToMainground(int index, Element2D element)
	{
		m_mainground.removeElement(element);
	}
	
	List<Element2D> getColliders()
	{
		return m_mainground.getElements();
	}
	
	//Override drawable : 
	@Override
	public void draw(SpriteBatch batch) {
		
		for(MapLayout layout : m_backgrounds)
		{
			layout.draw(batch);
		}
		m_mainground.draw(batch);
		for(MapLayout layout : m_foregrounds)
		{
			layout.draw(batch);
		}
	}

	
}
