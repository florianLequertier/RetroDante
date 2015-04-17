package com.retroDante.game.trigger;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.retroDante.game.character.Character;

/**
 * 
 * Trigger permettant de teleporter le joueur à un endroit donné quand celui-ci entre dans le trigger.
 * Couleur de debug : bleu (chaque type de trigger possède une couleur differente)
 * 
 * @author Florian
 *
 */

public class TeleportTrigger extends Trigger {
	
	//private VisualEffect m_effect;
	private Vector2 m_teleportDestination;
	
	public TeleportTrigger()
	{
		super(Color.BLUE);
		this.setType("teleport"); 
		m_teleportDestination = new Vector2(0,0);
	}
	
	public TeleportTrigger(Vector2 teleportDestination)
	{
		super(Color.BLUE);
		this.setType("teleport");
		m_teleportDestination = teleportDestination;
	}
	
	
	@Override
	public void triggerEventOn(Character target)
	{
		//m_effect.play(target.getPosition(), false);
		target.setPosition(m_teleportDestination);
	}
	
	//serialisation : 
	@Override
	public void write(Json json) {
			super.write(json);
			json.writeValue("teleportDestination_x", m_teleportDestination.x);
			json.writeValue("teleportDestination_y", m_teleportDestination.y);
	}

	@Override
	public void read(Json json, JsonValue jsonData) {
		super.read(json, jsonData);
		float destX = jsonData.getFloat("teleportDestination_x");
		float destY = jsonData.getFloat("teleportDestination_y");
		m_teleportDestination = new Vector2(destX, destY);
	}
	
	
}
