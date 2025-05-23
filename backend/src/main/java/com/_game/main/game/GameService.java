package com._game.main.game;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com._game.main.cards.CardDTO;
import com._game.main.cards.CardEntity;
import com._game.main.cards.CardRepository;
import com._game.main.cards.CardService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class GameService {

    @Autowired
    private CardService cardService;

    @Autowired
    private CardRepository cardRepository;

    /**
     * Get all unique card IDs in database.
     * @return
     */
    public List<Long> getAllCardIDs() {
        return cardRepository.findAll().stream().map(e -> e.getCardId()).toList();
    }

    /**
     * Get a CardDTO by its ID.
     * 
     * @param id
     * @return CardDTO
     */
    public CardDTO getCardById(final Long id) {
        Optional<CardEntity> card = cardRepository.findById(id);
        if(card.isPresent()){
            return cardService.toDTO(card.get());
        } else {
            throw new NoSuchElementException("No dto found");
        }
    }

    /**
     * Get a random Card.
     * @return CardDTO
     */
    public CardDTO getRandomCard() {
        Random r = new Random();
        Long count = cardRepository.count();
        Long randomLong = r.nextLong(count);

        Optional<CardEntity> entity = cardRepository.findById(randomLong);
        if (entity.isPresent()) {
            return cardService.toDTO(entity.get());
        } else {
            throw new NoSuchElementException("No dto found");
        }
    }

    /**
     * Solve operation.
     * @param round
     * @return new number found from operation in string
     */
    public String solveOperation(GameDTO round) {
        // parse operation by characters
        String[] operation = round.getCurrentOperation();

        // do operation
        Integer firstNumber = Integer.parseInt(operation[0]);
        Integer secondNumber = Integer.parseInt(operation[2]);
        System.out.println(firstNumber);
        System.out.println(secondNumber);
        String oper = operation[1];
        Integer newNumber = doMath(firstNumber, secondNumber, oper);
        return String.valueOf(newNumber);
    }

    /**
     * Handle simple operation.
     * @param num1
     * @param num2
     * @param oper
     * @return Integer
     */
    private Integer doMath(final Integer num1, final Integer num2, final String oper) {
        Integer result = 0;
        switch (oper) {
            case "+" -> result = num1 + num2;
            case "-" -> result = num1 - num2;
            case "x" -> result = num1 * num2;
            case "/" -> result = num1 / num2;
            default -> {
            }
        }
        return result;
    }
}
