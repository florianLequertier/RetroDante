package com.retroDante.game.trigger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.Drawable;
import com.retroDante.game.Rigidbody;
import com.retroDante.game.character.Character;

/**
 * Trigger : classe abstraite (design pattern structure), représentant la classe de base des triggers. 
 * Un trigger est un élement non visible en jeu, mais possédant un collider. L'entrée dans ce collider par un element permet d'activer une action sur l'élement.
 * De manière générale, un trigger va réagir à un l'entrée d'un Character. 
 * 
 * @author florian
 *
 */
public abstract class Trigger extends Rigidbody implements Json.Serializable, Drawable, Cloneable {
	
	//pour un affichage de debug : 
	private ShapeRenderer m_visual = new ShapeRenderer();
	private Color m_color;
	private String m_type; //type de trigger.
	//private VisualEffect m_effect;
	
	Trigger()
	{
		this.makeStaticBody();
		m_color = Color.BLUE;
	}
	
	Trigger(Color color)
	{
		this.makeStaticBody();
		m_color = color;
	}
	
	public Object clone(){
		Trigger tri = null;
		try{
			tri = (Trigger)super.clone();
		}catch(CloneNotSupportedException e){
			e.printStackTrace(System.err);
		}
		
		return tri;		
	}
	
	public String getType()
	{
		return m_type;
	}
	
	public void setType(String newType)
	{
		m_type = newType;
	}
	
	public void setColor(Color color)
	{
		m_color = color;
	}
	
	public Color getColor()
	{
		return m_color;
	}
	
	public void triggerEventOn(Character target)
	{
		//m_effect.play(target.getPosition(), false);
		System.out.println("trigger de l'event sur une cible");
	}
	
	@Override 
	public void draw(SpriteBatch batch)
	{
		batch.end();
		
		m_visual.setProjectionMatrix( batch.getProjectionMatrix() );
		 m_visual.begin(ShapeType.Line);
			 m_visual.setColor(m_color);
			 m_visual.rect(m_collider.x, m_collider.y, m_collider.width, m_collider.height);
		 m_visual.end();
		 
		 batch.begin();
	}
	@Override 
	public void draw(Batch batch)
	{
		batch.end();
		
		m_visual.setProjectionMatrix( batch.getProjectionMatrix() );
		 m_visual.begin(ShapeType.Line);
			 m_visual.setColor(m_color);
			 m_visual.rect(m_collider.x, m_collider.y, m_collider.width, m_collider.height);
		 m_visual.end();
		 
		 batch.begin();
	}
	
	//serialisation : 
	@Override
	public void write(Json json) {
			super.write(json);
			json.writeValue("type", m_type);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		m_type = jsonData.getString("type");
	}
	
	
	
}
