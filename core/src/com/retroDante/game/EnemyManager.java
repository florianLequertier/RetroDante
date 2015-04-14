package com.retroDante.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class EnemyManager implements Drawable, Json.Serializable {

	private List<Enemy> m_container = new ArrayList<Enemy>();
	
	
	public void add(Enemy enemy)
	{
		m_container.add(enemy);
	}
	
	public Enemy remove(int index)
	{
		return m_container.remove(index);
	}
	
	public boolean remove (Enemy enemy)
	{
		return m_container.remove(enemy);
	}
	
	//update 
	public void update(float deltaTime, List<Element2D> others, Vector2 targetPosition)
	{
		List<Enemy> toDelete = new ArrayList<Enemy>();
		
		for(Enemy e : m_container)
		{
			e.update(deltaTime, others, targetPosition);
			if(e.getIsDead())
			{
				toDelete.add(e);
			}
		}
		
		Iterator<Enemy> it = toDelete.iterator();
		while(it.hasNext())
		{
			it.next();
			
			it.remove();
		}
		
	}
	public void update(float deltaTime, Vector2 targetPosition)
	{
		List<Enemy> toDelete = new ArrayList<Enemy>();
		
		for(Enemy e : m_container)
		{
			e.update(deltaTime, targetPosition);
			if(e.getIsDead())
			{
				toDelete.add(e);
			}
		}

		Iterator<Enemy> it = toDelete.iterator();
		while(it.hasNext())
		{
			it.next();
			
			it.remove();
		}
		
	}
	
	//Override element2D :
	public void update(float deltaTime, List<Element2D> others)
	{
		List<Enemy> toDelete = new ArrayList<Enemy>();
		
		for(Enemy e : m_container)
		{
			e.update(deltaTime, others);
			if(e.getIsDead())
			{
				toDelete.add(e);
			}
		}

		Iterator<Enemy> it = toDelete.iterator();
		while(it.hasNext())
		{
			it.next();
			
			it.remove();
		}
		
	}
	public void update(float deltaTime)
	{
		List<Enemy> toDelete = new ArrayList<Enemy>();
		
		for(Enemy e : m_container)
		{
			e.update(deltaTime);
			if(e.getIsDead())
			{
				toDelete.add(e);
			}
		}

		Iterator<Enemy> it = toDelete.iterator();
		while(it.hasNext())
		{
			it.next();
			
			it.remove();
		}
		
	}
	
	
	
	@Override
	public void draw(SpriteBatch batch) {
		
		for(Enemy e : m_container)
		{
			e.draw(batch);
		}
		
	}
	
	
	//serialisation : 
	
	static EnemyManager load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		Json json = new Json();
		EnemyManager enemyManager = json.fromJson(EnemyManager.class, fileString);
		return enemyManager;
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
		json.writeArrayStart("enemyContainer");
			for(Enemy e : m_container)
			{
				json.writeObjectStart();
					e.write(json);
				json.writeObjectEnd();
			}
		json.writeArrayEnd();
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		
		JsonValue tempValue;
		JsonValue enemyData;
		int size = 0;

		tempValue = jsonData.get("enemyContainer");//.getChild("foregrounds");
		size = tempValue.size;
		for(int i=0; i< size; i++)
		{
			enemyData = tempValue.get(i);
			System.out.println(enemyData.toString());
			Enemy enemy = json.fromJson(Enemy.class, enemyData.toString());
			this.add(enemy);

		}
	}
	
}
