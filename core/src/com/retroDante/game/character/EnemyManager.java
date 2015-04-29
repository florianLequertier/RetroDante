package com.retroDante.game.character;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Drawable;
import com.retroDante.game.Element2D;
import com.retroDante.game.Manager;

public class EnemyManager extends Manager<Enemy> implements  Json.Serializable {

	private List<Enemy> m_container = new ArrayList<Enemy>();
	
	
	public EnemyManager()
	{
		
	}
	
	
	//Override Manager : 
	@Override
	public void add(Enemy enemy, int index )
	{
		m_container.add(enemy);
	}
	
	@Override
	public boolean remove (Enemy enemy, int index)
	{
		return m_container.remove(enemy);
	}

	public Enemy remove(int index)
	{
		return m_container.remove(index);
	}
	
	public List<Enemy> getContainer()
	{
		return m_container;
	}

	
	//update 
	public void update(float deltaTime, List<Element2D> others, Vector2 targetPosition)
	{
		List<Integer> toDelete = new ArrayList<Integer>();//new ArrayList<Enemy>();
		
		int index = 0;
		for(Enemy e : m_container)
		{
			e.update(deltaTime, others, targetPosition);
			if(e.getIsDead())
			{
				toDelete.add(index);
			}
			
			index++;
		}
		
		Iterator<Integer> it = toDelete.iterator();
		while(it.hasNext())
		{
			int indexToDelete = it.next();
			
			m_container.remove(indexToDelete);
			//it.remove();
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
	public void draw(Batch batch) {
		
		for(Enemy e : m_container)
		{
			e.draw(batch);
		}
		
	}
	
	
	//serialisation : 
	
	public static EnemyManager load(String filePath)
	{
		FileHandle file = Gdx.files.absolute(Gdx.files.getLocalStoragePath()+"/asset/"+filePath);
		String fileString = file.readString();
		Json json = new Json();
		EnemyManager enemyManager = json.fromJson(EnemyManager.class, fileString);
		return enemyManager;
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
	
	
	@Override
	public String toString()
	{
		return "nombre d'enemies dans le enemyManager = "+m_container.size();
	}
	
}
