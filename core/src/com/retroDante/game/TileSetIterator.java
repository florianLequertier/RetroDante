package com.retroDante.game;

import java.util.Iterator;
import java.util.NoSuchElementException;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class TileSetIterator implements Iterator<TextureRegion>{
	
	TileSetInfo m_owner;
	int m_index;
	
	TileSetIterator( TileSetInfo owner)
	{
		m_owner = owner;
		m_index = 0;
	}
	
	@Override
	public boolean hasNext() {
		System.out.println("nombre de tiles dans le set : "+m_owner.length());
		return ( m_index != m_owner.length() );
	}

	@Override
	public TextureRegion next() {
		
		if(hasNext())
		{
			return m_owner.get(m_index++);
		}
		else{
			throw new NoSuchElementException("Il n'y a plus d'element dans la liste");
		}

	}

	
	
}
