package com.retroDante.game.sound;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;


public final class SoundManager {
	
	private static volatile SoundManager instance = null;
	
	private Map<String, Sound> soundMap = new HashMap<String, Sound>();
	private Map<String, Music> musicMap= new HashMap<String, Music>();
	private Music current;
	
	private SoundManager(){
		current=null;
	}
	
	public final static SoundManager getInstance() {

        if (SoundManager.instance == null) {
          
           synchronized(SoundManager.class) {
             if (SoundManager.instance == null) {
               SoundManager.instance = new SoundManager();
             }
           }
        }
        return SoundManager.instance;
    }
	
	public void starter()
	{
		//add music
		Music intro = (Music) Gdx.audio.newMusic(Gdx.files.internal("asset/Music/Intro.wav"));
		intro.setLooping(true);
		this.addMusic("intro", intro);
		Music superrock = (Music) Gdx.audio.newMusic(Gdx.files.internal("asset/Music/SuperRock.wav"));
		superrock.setLooping(true);
		this.addMusic("superrock", superrock);
		Music descent = (Music) Gdx.audio.newMusic(Gdx.files.internal("asset/Music/Descent.wav"));
		descent.setLooping(true);
		this.addMusic("descent", descent);
		Music survey = (Music) Gdx.audio.newMusic(Gdx.files.internal("asset/Music/The survey.mp3"));
		survey.setLooping(true);
		survey.setVolume(0.6f);
		this.addMusic("survey", survey);
		Music cerberus = (Music) Gdx.audio.newMusic(Gdx.files.internal("asset/Music/Cerberus.mp3"));
		cerberus.setLooping(true);
		this.addMusic("cerberus", cerberus);
		Music death = Gdx.audio.newMusic(Gdx.files.internal("asset/Sound/Death.wav"));
		this.addMusic("death", death);
		
		//add sound
		Sound attack = Gdx.audio.newSound(Gdx.files.internal("asset/Sound/attack.mp3"));
		this.addSound("attack", attack);
		Sound skeleton = Gdx.audio.newSound(Gdx.files.internal("asset/Sound/Skeleton march.mp3"));
		this.addSound("skeleton", skeleton);
		Sound jump = Gdx.audio.newSound(Gdx.files.internal("asset/Sound/Jump groan.wav"));
		this.addSound("jump", jump);
		
	}
	
	public void addSound(String key, Sound sound ){
		soundMap.put(key, sound);
	}
	
	public void addMusic(String key, Music music ){
		musicMap.put(key, music);
	}
	
	public Sound getSound(String key){
		return soundMap.get(key);
	}
	
	public Music getMusic(String key){
		try{
			return musicMap.get(key);
		}
		catch(Exception e)
		{
			  System.out.println("Pointer Null exception");
			  return null;
		}
	}
	public Music getCurrentMusic(String key){
		return current;
	}
	
	public void playSound(String key){
		soundMap.get(key).play();
	}
	
	public void playSound(String key, float volume){
		soundMap.get(key).play(volume);
	}
	
	public void playMusic(String key){
		if(musicMap.get(key)!=null)
		{
			if(current != musicMap.get(key))
			{
				this.stopMusic();
			}
		musicMap.get(key).play();
		current= musicMap.get(key);
		}
		else
			System.out.println("Pointer Null exception");
	}
	
	public void stopMusic(){
		if(current!=null)
			current.stop();
		else
			System.out.println("Pointer Null exception");
	}
	
}
