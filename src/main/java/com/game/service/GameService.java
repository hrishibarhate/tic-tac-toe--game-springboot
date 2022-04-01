package com.game.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.game.exception.InvalidGameException;
import com.game.exception.InvalidParamException;
import com.game.exception.NotFoundException;
import com.game.model.Game;
import com.game.model.GamePlay;
import com.game.model.GameStatus;
import com.game.model.Player;
import com.game.model.TicToe;
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

	public Game connectToRandomGame(Player player2) throws NotFoundException {
		Game game = GameStorage.getInstance().getGames().values().stream()
				.filter(it -> it.getGameStatus().equals(GameStatus.NEW)).findFirst()
				.orElseThrow(() -> new NotFoundException("Game not Found"));

		game.setPlayer2(player2);
		game.setGameStatus(GameStatus.IN_PROGRESS);
		GameStorage.getInstance().setGames(game);

		return game;
	}

	public Game gamePlay(GamePlay gamePlay) throws NotFoundException, InvalidGameException {
		if (!GameStorage.getInstance().getGames().containsKey(gamePlay.getGameId())) {
			throw new NotFoundException("Game not Found");
		}
		Game game = GameStorage.getInstance().getGames().get(gamePlay.getGameId());

		if (game.getGameStatus().equals(GameStatus.FINISHED)) {
			throw new InvalidGameException("Game has already finished.");
		}

		int[][] board = game.getBoard();
		board[gamePlay.getCoordinateX()][gamePlay.getCoordinateY()] = gamePlay.getType().getValue();

		Boolean xWinner = checkWinner(game.getBoard(), TicToe.X);
		Boolean oWinner = checkWinner(game.getBoard(), TicToe.O);

		if (xWinner) {
			game.setWinner(TicToe.X);
		} else if (oWinner) {
			game.setWinner(TicToe.O);
		}

		return game;
	}

	private Boolean checkWinner(int[][] board, TicToe ticToe) {
		// first converting 2 dimensional array into 1 dimensional.
		int[] boardArray = new int[9];
		int counterIndex = 0;

		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				boardArray[counterIndex] = board[i][j];
				counterIndex++;
			}
		}

		int[][] winCombinations = { { 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, { 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 },
				{ 0, 4, 8 }, { 2, 4, 6 } };
		for (int i = 0; i < winCombinations.length; i++) {
			int counter = 0;
			for (int j = 0; j < winCombinations[i].length; j++) {
				if (boardArray[winCombinations[i][j]] == ticToe.getValue()) {
					counter++;
					if (counter == 3) {
						return true;
					}
				}
			}
		}
		return false;
	}

}
