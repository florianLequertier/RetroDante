package com.retroDante.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TileSetInfo implements Iterable {
	
	String m_name;
	int m_spriteNumber;
	int m_spriteByLine[];
	int m_lineNumber;
	Texture m_texture;
	TextureRegion[][] m_textureRegion;
	
	TileSetInfo(String TexturePath, String DescriptionPath)
	{
		String localPath = Gdx.files.getLocalStoragePath();
		
		m_texture = new Texture(localPath+"/asset/"+TexturePath);
		m_spriteNumber = 0;
		
		Scanner lecteur;
		try {
			
			lecteur = new Scanner(new File(localPath+"/asset/"+DescriptionPath));
			
			m_name = lecteur.nextLine();
			m_lineNumber = lecteur.nextInt();
			m_spriteByLine = new int[m_lineNumber];
			
			m_textureRegion = new TextureRegion[m_lineNumber][];
			int posX = 0;
			int posY = 0;
			for(int i= 0; i<m_lineNumber; i++)
			{
				int nbSprite = lecteur.nextInt();
				m_spriteByLine[i] = nbSprite;
				int height = lecteur.nextInt();
				
				
				m_textureRegion[i] = new TextureRegion[nbSprite];
				for(int j=0; j<nbSprite; j++)
				{
					int width = lecteur.nextInt();
					m_textureRegion[i][j] = new TextureRegion(m_texture, posX, posY, width, height);
					posX += width;
					m_spriteNumber++;
				}

				
				posY += height;
				posX = 0;
			}
			lecteur.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("mauvaise initialisation du fichier ! ");
			e.printStackTrace();
		}
		
	}
	
	public TextureRegion get(int index)
	{
		if(index >= m_spriteNumber)
		{
			while(index >= m_spriteNumber)
			{
				index -= m_spriteNumber;
			}
		}
		else if(index < 0 )
		{
			while(index < 0)
			{
				index += m_spriteNumber;
			}
		}
		
		
		int indexRow = 0;
		int indexColl = 0;
		
		int result = index;
		while(result > 0)
		{
			result -= m_spriteByLine[indexRow];
			
			if(result >= 0)
			{
				index -= m_spriteByLine[indexRow];
				indexRow++;
			}
		}
		indexColl = index;
		
		return m_textureRegion[indexRow][indexColl];
	}
	
	public List<Animation> getForAnimation(int... lineIndex)
	{
		boolean outOfBounds = false;
		
		for(int i=0; i<lineIndex.length; i++)
		{
			if(i>m_textureRegion.length)
				outOfBounds = true;
		}
		
		List<Animation> animList = new ArrayList<Animation>();
		
		if(outOfBounds)
		{
			animList.add( new Animation( 60, new Array<TextureRegion>(m_textureRegion[0])) );
			return animList;
		}
		
		for(int i=0; i< lineIndex.length; i++)
		{
			animList.add( new Animation( 60, new Array<TextureRegion>(m_textureRegion[lineIndex[i]])) );
		}
		return animList;
	}
	
	public Texture getTexture()
	{
		return m_texture;
	}
	
	/**
	 * total number of textureRegion
	 * 
	 * @return
	 */
	public int length()
	{
		int length = 0;
		for(int i=0; i<m_textureRegion.length; i++)
		{
			length+=m_textureRegion[i].length;
		}
		
		return length;
	}
	
	
	public String toString()
	{
		StringBuilder builder = new StringBuilder();

		builder.append("name : ").append(m_name).append(", nombre de sprite : ").append(m_spriteNumber);
		
		
		return builder.toString();
	}

	@Override
	public Iterator<TextureRegion> iterator() {		
		return new TileSetIterator(this);
	}
}
