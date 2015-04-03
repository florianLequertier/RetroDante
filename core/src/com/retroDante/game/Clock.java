package com.retroDante.game;

public class Clock {

	private double m_startTime = 0.0;
	private double m_currentTime = 0.0;
	
	Clock()
	{
		m_startTime = System.currentTimeMillis()*0.001;
		m_currentTime = m_startTime;
	}
	
	private void update()
	{
		m_currentTime = System.currentTimeMillis()*0.001;
	}
	
	public double getElapsedTime()
	{
		update();
		return m_currentTime - m_startTime;
	}
	
	public double restart()
	{
		double tmp = getElapsedTime();
		m_startTime = System.currentTimeMillis()*0.001;
		m_currentTime = m_startTime;
		return tmp;
	}
}
