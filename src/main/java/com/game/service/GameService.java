package com.game.service;

import org.springframework.stereotype.Service;

import com.game.model.Game;
import com.game.model.Player;

@Service
public class GameService {
	
	public Game createGame(Player player) {
		Game game = new Game();
		game.setBoard(new int[3][3]);
		return game;
	}

}
