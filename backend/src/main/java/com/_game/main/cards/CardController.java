package com._game.main.cards;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
public class CardController {

    @Autowired
    private CardService cardService;

    /**
      * List all cards in program
      * @return String
      */
    @GetMapping("/list")
    public List<CardDTO> listAll() {
        return cardService.getAll();
    }

     /**
      * List random card in program
      * @return String
      */
      @GetMapping("/random")
      public CardDTO getRandomCard() {
          return cardService.getRandomCard();
      }
    
}
