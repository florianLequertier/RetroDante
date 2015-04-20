package com.retroDante.game;

public enum StoryChapter {
	
	Inferno02("storyMaps/story02"),
	Inferno01("storyMaps/story01", Inferno02),
	Tutorial("storyMaps/story00", Inferno01);
	
	String m_folderName;
	StoryChapter m_nextChapter = null;
	
	
	StoryChapter(String folderName)
	{
		m_folderName = folderName;
	}
	
	StoryChapter(String folderName, StoryChapter nextChapter)
	{
		m_nextChapter = nextChapter;
		m_folderName = folderName;
	}

	
	StoryChapter nextChapter()
	{
		return m_nextChapter;
	}
	
	boolean hasNextChapter()
	{
		return m_nextChapter != null;
	}
	
	public String getFolderName()
	{
		return m_folderName;
	}
}
