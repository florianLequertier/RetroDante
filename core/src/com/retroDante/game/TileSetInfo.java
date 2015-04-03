package com.retroDante.game;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class TileSetInfo {
	
	String m_name;
	int m_lineNumber;
	Texture m_texture;
	TextureRegion[][] m_textureRegion;
	
	TileSetInfo(String TexturePath, String DescriptionPath)
	{
		m_texture = new Texture(Gdx.files.internal(TexturePath));
		
		Scanner lecteur;
		try {
			
			lecteur = new Scanner(new File(DescriptionPath));
			
			m_name = lecteur.nextLine();
			m_lineNumber = lecteur.nextInt();
			
			m_textureRegion = new TextureRegion[m_lineNumber][];
			int posX = 0;
			int posY = 0;
			for(int i= 0; i<m_lineNumber; i++)
			{
				int nbSprite = lecteur.nextInt();
				int height = lecteur.nextInt();
				
				
				m_textureRegion[i] = new TextureRegion[nbSprite];
				for(int j=0; j<nbSprite; j++)
				{
					int width = lecteur.nextInt();
					m_textureRegion[i][j] = new TextureRegion(m_texture, posX, posY, width, height);
					posX += width;
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
	
	TextureRegion get(int index)
	{
		if(index >= m_textureRegion.length * m_textureRegion[0].length)
		{
			while(index >= m_textureRegion.length * m_textureRegion[0].length)
			{
				index -= m_textureRegion.length * m_textureRegion[0].length;
			}
		}
		
		int indexRow = index / m_textureRegion[0].length;
		int indexColl = index % m_textureRegion[0].length;
		
		return m_textureRegion[indexRow][indexColl];
	}
	
	List<Animation> getForAnimation(int... lineIndex)
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
	
	Texture getTexture()
	{
		return m_texture;
	}
}
