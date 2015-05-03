package com.retroDante.game.item;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.retroDante.game.Animator;
import com.retroDante.game.Element2D;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;
import com.retroDante.game.trigger.DamageTrigger;
import com.retroDante.game.trigger.TriggerFactory;

public class ItemLife extends Item {
	
	
	
	public ItemLife(Texture tex)
	{
		super(tex);
		m_trigger = TriggerFactory.getInstance().create("damageTrigger");
		((DamageTrigger)m_trigger).setDamage(-1);
		this.setDimension(Element2D.getUnit().scl(2));
		this.setPosition(new Vector2(Vector2.Zero));

	}
	public ItemLife(TileSetInfo tileSet, int spriteIndex)
	{
		super(tileSet, spriteIndex);
		m_trigger = TriggerFactory.getInstance().create("damageTrigger");
		((DamageTrigger)m_trigger).setDamage(-1);
		this.setDimension(Element2D.getUnit().scl(2));
		this.setPosition(new Vector2(Vector2.Zero));

	}
	public ItemLife()
	{
		super(TileSetManager.getInstance().get("item"), 0);
		m_trigger = TriggerFactory.getInstance().create("damageTrigger");
		((DamageTrigger)m_trigger).setDamage(-1);
		this.setDimension(Element2D.getUnit().scl(2));
		this.setPosition(new Vector2(Vector2.Zero));

	}
	
	public void update(float deltaTime)
	{
		super.update(deltaTime);
	}
	
	
}
