package com.retroDante.game;

public interface Controllable {
	
	enum KeyStatus{DOWN, UP};
	
	public void listenKey(KeyStatus status, int keyCode);
	public void checkController();
	
}

