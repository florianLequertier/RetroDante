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
	
	public void updateParralax(GameCamera camera)
	{
		int backgroundIndex = m_backgrounds.size();
		for(MapLayout layout : m_backgrounds)
		{
			float newParralaxDecalX = -backgroundIndex * layout.getParralaxFactor() * camera.getCurrentTranslation().x;
			float newParralaxDecalY = -backgroundIndex * layout.getParralaxFactor() * camera.getCurrentTranslation().y;
			
			layout.setParralxDecal( new Vector2(newParralaxDecalX, newParralaxDecalY) );
			
			backgroundIndex--;
		}
		
		
		int foregroundIndex = 0;
		for(MapLayout layout : m_foregrounds)
		{
			float newParralaxDecalX = -foregroundIndex * layout.getParralaxFactor() * camera.getCurrentTranslation().x;
			float newParralaxDecalY = -foregroundIndex * layout.getParralaxFactor() * camera.getCurrentTranslation().y;
			
			layout.setParralxDecal( new Vector2(newParralaxDecalX, newParralaxDecalY) );
			
			foregroundIndex++;
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
		json.writeArrayStart("foregrounds");
			for(MapLayout lay : m_foregrounds)
			{
				lay.write(json);
			}
		json.writeArrayEnd();
		
		json.writeObjectStart("mainground");
			m_mainground.write(json);
		json.writeObjectEnd();
		
		json.writeArrayStart("backgrounds");
			for(MapLayout lay : m_backgrounds)
			{
				lay.write(json);
			}
		json.writeArrayEnd();
		
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		System.out.println(jsonData);
		JsonValue tempValue;
		JsonValue tempLayout;
		
		tempValue = jsonData.child();//.getChild("foregrounds");
		System.out.println("child : "+tempValue.toString());
		int size = tempValue.size;
			for(int i=0; i< size; i++)
			{
				tempLayout = tempValue.get(i);
				for(int j=0; j< tempLayout.size; j++)
				{
					Element2D newElement = json.fromJson(Platform.class, tempLayout.getString(j));
					this.addToForeground(i, newElement);
				}
			}
			
		tempValue = jsonData.get("mainground");//.getChild("foregrounds");
		System.out.println("child : "+tempValue.toString());
		tempLayout = tempValue.get("layout");
		System.out.println("child : "+tempLayout.toString());
		for(int j=0; j< tempLayout.size; j++)
		{
			Element2D newElement = json.fromJson(Platform.class, tempLayout.getString(j));
			this.addToMainground( newElement);
		}
		
		
		
		tempValue = jsonData.next();//.getChild("foregrounds");
		System.out.println("child : "+tempValue.toString());
		size = tempValue.size;
		for(int i=0; i< size; i++)
			{
				tempLayout = tempValue.get(i);
				for(int j=0; j< tempLayout.size; j++)
				{
					Element2D newElement = json.fromJson(Platform.class, tempLayout.getString(j));
					this.addToBackground(i, newElement);
				}
			}
	}

	
}
