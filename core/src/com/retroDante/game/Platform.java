package com.retroDante.game;

import com.badlogic.gdx.graphics.Texture;

public class Platform extends Element2D{
	
	Platform(Texture tex) 
	{
		super(tex);
		this.makeStaticBody();
		
		m_type = "platform";

	}
	
	Platform(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		this.makeStaticBody();
		
		m_type = "platform";

	}
	
	Platform() 
	{
		super(TileSetManager.getInstance().get("platform"), 0);
		this.makeStaticBody();
		
		m_type = "platform";

	}
}
