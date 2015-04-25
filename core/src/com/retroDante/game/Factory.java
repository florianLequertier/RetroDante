package com.retroDante.game;

public interface Factory<Type> {
	
	Type create(String name);
	Type create(int index);
}
