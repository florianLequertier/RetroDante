package com.retroDante.game;

import com.badlogic.gdx.graphics.Texture;

public class Platform extends Element2D{
	
	Platform(Texture tex) 
	{
		super(tex);
		this.makeStaticBody();

	}
	
	Platform(TileSetInfo tileSet, int spriteIndex) 
	{
		super(tileSet, spriteIndex);
		this.makeStaticBody();

	}
}
