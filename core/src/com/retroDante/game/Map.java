package com.retroDante.game;


import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Map implements Drawable, Json.Serializable{

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
		//mainground : 
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
		
		//background : 
		p = new Platform();
		p.setPosition(new Vector2(70, 450));
			map.addToBackground(-1, p);
		p = new Platform();
		p.setPosition(new Vector2(70, 350));
			map.addToBackground(-2, p);
		p = new Platform();
		p.setPosition(new Vector2(70, 250));
			map.addToBackground(-3, p);
		p.setPosition(new Vector2(70, 150));
			map.addToBackground(-4, p);
			
		
		return map;
	}
	
	//ajout � la map : 
	void addToForeground(int index, Element2D element)
	{
		for(MapLayout layout : m_foregrounds)
		{
			if(layout.getIndex() == index)
			{
				layout.addElement(element);
				return;
			}
		}
		MapLayout newLayout = new MapLayout();
		newLayout.setIndex(index);
		newLayout.addElement(element);
		m_foregrounds.add(newLayout);
	}
	
	void addToBackground(int index, Element2D element)
	{
		for(MapLayout layout : m_backgrounds)
		{
			if(layout.getIndex() == index)
			{
				layout.addElement(element);
				return;
			}
		}
		MapLayout newLayout = new MapLayout();
		newLayout.setIndex(index);
		newLayout.addElement(element);
		m_backgrounds.add(newLayout);
	}
	
	void addToMainground(Element2D element)
	{
		m_mainground.addElement(element);
	}
	
	//suppression de la map : 
	void removeToForeground(int index, Element2D element)
	{
		for(MapLayout layout : m_foregrounds)
		{
			if(layout.getIndex() == index)
			{
				layout.removeElement(element);
				return;
			}
		}

	}
	
	void removeToBackground(int index, Element2D element)
	{
		for(MapLayout layout : m_backgrounds)
		{
			if(layout.getIndex() == index)
			{
				layout.removeElement(element);
				return;
			}
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
	
	//parrallax : 
	public void drawWithParralax(SpriteBatch batch) {
		
		for(MapLayout layout : m_backgrounds)
		{
			layout.drawWithParralax(batch);
		}
		m_mainground.draw(batch);
		for(MapLayout layout : m_foregrounds)
		{
			layout.drawWithParralax(batch);
		}
	} 
	public void drawBackgroungWithParralax(SpriteBatch batch) {
		
		for(MapLayout layout : m_backgrounds)
		{
			layout.drawWithParralax(batch);
		}
		
	}
	public void drawMaingroundWithParralax(SpriteBatch batch) {
		
		m_mainground.draw(batch);
		
	}
	public void drawForegroundWithParralax(SpriteBatch batch) {

		for(MapLayout layout : m_foregrounds)
		{
			layout.drawWithParralax(batch);
		}
		
	}
	
	
	
	public void updateParralax(GameCamera camera)
	{
		int maxIndex = MapLayout.getMaxIndex();
		
		for(MapLayout layout : m_backgrounds)
		{
			
			float newParralaxDecalX = -( layout.getIndex()/(float)maxIndex ) * layout.getParralaxFactor() * camera.getCurrentTranslation().x;
			float newParralaxDecalY = -( layout.getIndex()/(float)maxIndex ) * layout.getParralaxFactor() * camera.getCurrentTranslation().y;
			
			layout.setParralaxDecal( layout.getParralaxDecal().add(new Vector2(newParralaxDecalX, newParralaxDecalY)) );

		}
		
		
		for(MapLayout layout : m_foregrounds)
		{
			float newParralaxDecalX = ( layout.getIndex()/(float)maxIndex ) * layout.getParralaxFactor() * camera.getCurrentTranslation().x;
			float newParralaxDecalY = ( layout.getIndex()/(float)maxIndex ) * layout.getParralaxFactor() * camera.getCurrentTranslation().y;
			
			layout.setParralaxDecal( new Vector2(newParralaxDecalX, newParralaxDecalY) );

		}
	}
	
	//serialisation : 
	
	static Map load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		Json json = new Json();
		Map map = json.fromJson(Map.class, fileString);
		return map;
	}
	void save(String filePath)
	{
		Json json = new Json();
		String text = json.toJson(this);
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);// internal(filePath);
		file.writeString(text, false);
	}
	
	@Override
	public void write(Json json) {
	
		json.writeObjectStart("foregrounds");
			for(MapLayout lay : m_foregrounds)
			{
				lay.write(json);
			}
		json.writeObjectEnd();
		
		json.writeObjectStart("mainground");
			m_mainground.write(json);
		json.writeObjectEnd();
		
		json.writeObjectStart("backgrounds");
			for(MapLayout lay : m_backgrounds)
			{
				lay.write(json);
			}
		json.writeObjectEnd();
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		System.out.println(jsonData);
		JsonValue tempValue;
		JsonValue tempLayout;
		JsonValue tempElement;
		JsonValue tempContainer;
		int index = 0;
		int size = 0;
		
		tempValue = jsonData.get("foregrounds");//.getChild("foregrounds");
		size = tempValue.size;
		for(int i=0; i< size; i++)
			{
				tempLayout = tempValue.get(i);
				index = tempLayout.getInt("m_index");
				tempContainer = tempLayout.get("container");
				
				for(int j=0; j< tempContainer.size; j++)
				{
					tempElement = tempContainer.get(j);
					
					Element2D newElement = json.fromJson(Platform.class, tempElement.toString());
					this.addToBackground(index, newElement);
				}
			}
			
		tempValue = jsonData.get("mainground");//.getChild("foregrounds");
		tempLayout = tempValue.get("layout");
		index = tempLayout.getInt("m_index");
		tempContainer = tempLayout.get("container");
		for(int j=0; j< tempContainer.size; j++)
		{
			tempElement = tempContainer.get(j);

			Element2D newElement = json.fromJson(Platform.class, tempElement.toString());
			this.addToMainground( newElement);
		}
		
		
		
		tempValue = jsonData.get("backgrounds");//.getChild("foregrounds");
		size = tempValue.size;
		for(int i=0; i< size; i++)
			{
				tempLayout = tempValue.get(i);
				index = tempLayout.getInt("m_index");
				tempContainer = tempLayout.get("container");
				
				for(int j=0; j< tempContainer.size; j++)
				{
					tempElement = tempContainer.get(j);
					
					Element2D newElement = json.fromJson(Platform.class, tempElement.toString());
					this.addToBackground(index, newElement);
				}
			}
	}

	
}