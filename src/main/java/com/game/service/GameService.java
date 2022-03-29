package com.game.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.game.model.Game;
import com.game.model.GameStatus;
import com.game.model.Player;

@Service
public class GameService {
	
	public Game createGame(Player player) {
		Game game = new Game();
		game.setBoard(new int[3][3]);
		game.setGameId(UUID.randomUUID().toString());
		game.setPlayer1(player);
		game.setGameStatus(GameStatus.NEW);
		return game;
	}

}
