package com.retroDante.game;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public class TileSetManager {
	
	private Map<String,TileSetInfo> m_tileSetContainer = new HashMap<String,TileSetInfo>();
	
	public void add(String name, TileSetInfo tileSet)
	{
		m_tileSetContainer.put(name, tileSet);
	}
	
	public void remove(String name)
	{
		m_tileSetContainer.remove(name);
	}
	
	public boolean contains(String name)
	{
		return m_tileSetContainer.containsKey(name);
	}
	
	public TileSetInfo get(String name)
	{
		return m_tileSetContainer.get(name);
	}
	
	
	public void load(String fileName)
	{
		Scanner lecteur;
		try {
			
			lecteur = new Scanner(new File(fileName));
			
			int nbLine = lecteur.nextInt();
			lecteur.nextLine();
			for(int i=0; i< nbLine; i++)
			{
				String name = lecteur.nextLine();
				String texPath = lecteur.nextLine();
				String infoPath = lecteur.nextLine();
				
				TileSetInfo tileSet = new TileSetInfo(texPath, infoPath);
				m_tileSetContainer.put(name, tileSet);
				
				lecteur.nextLine();
			}
			lecteur.close();
			
		} catch (FileNotFoundException e) {
			System.out.println("mauvaise initialisation du fichier ! ");
			e.printStackTrace();
		}
	}
	
}
