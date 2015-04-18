package com.retroDante.game.map;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Drawable;
import com.retroDante.game.Element2D;
import com.retroDante.game.Manager;

public class MapLayout implements Drawable, Json.Serializable {
	
	List<Element2D> m_container;
	Vector2 m_parralaxDecal = new Vector2(0,0);
	float m_parralaxFactor = 1;
	int m_index = 0;
	static int s_maxIndex = 0;
	static int getMaxIndex() { return s_maxIndex;}
	
	
	MapLayout()
	{
		m_container = new ArrayList<Element2D>();
	}
	
	public void setIndex(int newIndex)
	{
		m_index = newIndex;
		int absIndex = Math.abs( m_index );
		if( absIndex > s_maxIndex)
		{
			s_maxIndex = absIndex;
			System.out.println("maxIndex : "+s_maxIndex);
		}
	}


	public void add(Element2D element)
	{
		m_container.add(element);
	}

	public boolean remove(Element2D element)
	{
		return m_container.remove(element);
	}
	
	//getters / setters  :
	
	public int getIndex()
	{
		return m_index;
	}
	
	List<Element2D> getElements()
	{
		return m_container;
	}
	
	public void setParralaxDecal(Vector2 newParralaxDecal)
	{
		m_parralaxDecal = newParralaxDecal;
	}
	
	public Vector2 getParralaxDecal()
	{
		return m_parralaxDecal;
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
	public void draw(Batch batch) {
		
		for(Element2D element : m_container)
		{
			element.draw(batch);
		}	
	}
	
	public void drawWithParralax(SpriteBatch batch) {
		
		for(Element2D element : m_container)
		{
			element.drawWithParralax(batch, m_parralaxDecal.x, m_parralaxDecal.y);
		}	
	}
	
	//serialisation : 
	@Override
	public void write(Json json) {
		
		json.writeObjectStart("layout");
			json.writeValue("m_index", m_index); //new 
			json.writeArrayStart("container");
				for(Element2D e : m_container)
				{
					json.writeObjectStart();
						e.write(json);
					json.writeObjectEnd();
				}
			json.writeArrayEnd();
		json.writeObjectEnd();
			
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		// TODO Auto-generated method stub
		
	}


}
