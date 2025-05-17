package com._game.main.game;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com._game.main.cards.CardDTO;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/game")
@SuppressWarnings({ "unchecked", "rawtypes" })
public class GameController {
  @Autowired
  private GameService gameService;

   /**
   * Get all card IDs
   * 
   * @return ResponseEntity<List<Long>>
   */
  @GetMapping("/idList")
  public ResponseEntity<List<Long>> getAllCardIDs() {
    return new ResponseEntity(gameService.getAllCardIDs(), HttpStatus.OK);
  }

  /**
   * Get random card
   * 
   * @return ResponseEntity<CardDTO>
   */
  @GetMapping("/card")
  public ResponseEntity<CardDTO> getCard() {
    return new ResponseEntity(gameService.getRandomCard(), HttpStatus.OK);
  }

  /**
   * Get card by its ID.
   * 
   * @return ResponseEntity<CardDTO>
   */
  @GetMapping("/card/{id}")
  public ResponseEntity<CardDTO> getCardById (@PathVariable Long id) {
    return new ResponseEntity(gameService.getCardById(id), HttpStatus.OK);
  }

  /**
   * Solve operation.
   * @param operation
   * @return ResponseEntity<String>
   */
  @PostMapping("/solve")
  public ResponseEntity<String> solveOperation(@RequestBody GameDTO operation) {
    System.out.println(operation.getCurrentOperation());
    String newNumber = gameService.solveOperation(operation);
    return new ResponseEntity(newNumber, HttpStatus.OK);
  }
}
