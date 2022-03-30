package com.game.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.game.exception.InvalidGameException;
import com.game.exception.InvalidParamException;
import com.game.model.Game;
import com.game.model.GameStatus;
import com.game.model.Player;
import com.game.repo.GameStorage;

@Service
public class GameService {

	public Game createGame(Player player) {
		Game game = new Game();
		game.setBoard(new int[3][3]);
		game.setGameId(UUID.randomUUID().toString());
		game.setPlayer1(player);
		game.setGameStatus(GameStatus.NEW);

		GameStorage.getInstance().setGames(game);
		return game;
	}

	public Game connectToGame(Player player2, String gameId) throws InvalidParamException, InvalidGameException {
		if (!GameStorage.getInstance().getGames().containsKey(gameId)) {
			throw new InvalidParamException("Game with provided Id doesnt exists.");
		}

		Game game = GameStorage.getInstance().getGames().get(gameId);
		if (game.getPlayer2() != null) {
			throw new InvalidGameException("Game is not valid");
		}

		game.setPlayer2(player2);
		game.setGameStatus(GameStatus.IN_PROGRESS);

		GameStorage.getInstance().setGames(game);

		return game;
	}

}
