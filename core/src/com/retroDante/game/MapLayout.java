package com.retroDante.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class MapLayout implements Drawable, Json.Serializable {
	
	List<Element2D> m_container;
	Vector2 m_parralxDecal = new Vector2(0,0);
	float m_parralaxFactor = 1;
	
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
	
	public void setParralxDecal(Vector2 newParralaxDecal)
	{
		m_parralxDecal = newParralaxDecal;
	}
	
	public void setParralaxFactor(float newParralaxFactor)
	{
		m_parralaxFactor = newParralaxFactor;
	}
	
	public float getParralaxFactor()
	{
		return m_parralaxFactor;
	}
	
	//Override drawable : 
	@Override
	public void draw(SpriteBatch batch) {
		
		for(Element2D element : m_container)
		{
			element.draw(batch);
		}	
	}
	
	public void drawWithParralax(SpriteBatch batch) {
		
		for(Element2D element : m_container)
		{
			element.drawWithParralax(batch, 0, 0);
		}	
	}
	
	//serialisation : 
	@Override
	public void write(Json json) {
		
		json.writeArrayStart("layout");
			for(Element2D e : m_container)
			{
				json.writeObjectStart();
					e.write(json);
				json.writeObjectEnd();
			}
		json.writeArrayEnd();
			
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		
	}


}
