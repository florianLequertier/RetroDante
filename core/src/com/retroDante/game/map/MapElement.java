package com.retroDante.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.retroDante.game.Element2D;
import com.retroDante.game.TileSetInfo;
import com.retroDante.game.TileSetManager;

public class MapElement extends Element2D{
	
	public MapElement(Texture tex) 
	{
		super(tex);
		this.makeStaticBody();
		
		m_type = "platform";

	}
	
	public MapElement(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		this.makeStaticBody();
		
		m_type = "platform";

	}
	
	public MapElement() 
	{
		super(TileSetManager.getInstance().get("platform"), 0);
		this.makeStaticBody();
		
		m_type = "platform";

	}
}
