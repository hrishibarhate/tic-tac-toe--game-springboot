package com.game.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.game.dto.ConnectRequest;
import com.game.exception.InvalidGameException;
import com.game.exception.InvalidParamException;
import com.game.exception.NotFoundException;
import com.game.model.Game;
import com.game.model.GamePlay;
import com.game.model.Player;
import com.game.service.GameService;

@RestController
@RequestMapping("/game")
public class GameController {
	
	//private static final Logger log = Logger.getLogger(GameController.class);
	
	private final GameService gameService = new GameService();
	
	private final SimpMessagingTemplate simpMessagingTemplate = null;

    @PostMapping("/start")
    public ResponseEntity<Game> start(@RequestBody Player player) {
        //log.info("start game request: {}", player);
        return ResponseEntity.ok(gameService.createGame(player));
    }

    @PostMapping("/connect")
    public ResponseEntity<Game> connect(@RequestBody ConnectRequest request) throws InvalidParamException, InvalidGameException {
        //log.info("connect request: {}", request);
        return ResponseEntity.ok(gameService.connectToGame(request.getPlayer(), request.getGameId()));
    }
    
    @PostMapping("/connect/random")
    public ResponseEntity<Game> connectRandom(@RequestBody Player player) throws NotFoundException {
        //log.info("connect random {}", player);
        return ResponseEntity.ok(gameService.connectToRandomGame(player));
    }

    @PostMapping("/gameplay")
    public ResponseEntity<Game> gamePlay(@RequestBody GamePlay request) throws NotFoundException, InvalidGameException {
        //log.info("gameplay: {}", request);
        Game game = gameService.gamePlay(request);
        simpMessagingTemplate.convertAndSend("/topic/game-progress/" + game.getGameId(), game);
        return ResponseEntity.ok(game);
    }
	
}
