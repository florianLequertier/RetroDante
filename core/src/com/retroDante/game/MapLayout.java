package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MapLayout implements Drawable {
	
	List<Element2D> m_container;
	
	MapLayout()
	{
		m_container = new ArrayList<Element2D>();
	}
	
	void addElement(Element2D element)
	{
		m_container.add(element);
	}
	
	void removeElement(Element2D element)
	{
		m_container.remove(element);
	}
	
	List<Element2D> getElements()
	{
		return m_container;
	}
	
	//Override drawable : 
	@Override
	public void draw(SpriteBatch batch) {
		
		for(Element2D element : m_container)
		{
			element.draw(batch);
		}	
		
	}
	
}
