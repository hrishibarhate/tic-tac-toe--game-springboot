package com.game.service;

import org.springframework.stereotype.Service;

import com.game.model.Game;
import com.game.model.Player;

@Service
public class GameService {
	
	public Game createGame(Player player) {
		Game game = new Game();
		
		return game;
	}

}