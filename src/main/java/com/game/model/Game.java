package com.game.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Game {
	
	private String gameId;
	Player player1;
	Player player2;
	private GameStatus gameStatus;
	private int[][] board;
	private String winner;

}
