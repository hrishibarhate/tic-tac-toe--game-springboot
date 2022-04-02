package com.game.dto;

import com.game.model.Player;

public class ConnectRequest {

	private Player player;
	private String gameId;
	
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public String getGameId() {
		return gameId;
	}
	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
