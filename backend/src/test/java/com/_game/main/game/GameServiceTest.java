package com._game.main.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com._game.main.cards.CardDTO;
import com._game.main.cards.CardEntity;
import com._game.main.cards.CardRepository;
import com._game.main.cards.CardService;

@ExtendWith(MockitoExtension.class)
public class GameServiceTest {
    @Mock
    private CardRepository cardRepository;

    @Mock
    private CardService cardService;

    @InjectMocks
    private GameService gameService;

    /**
     * Test for getAllCardIDs()
     */
    @Test
    void testGetAllCardIDs() throws Exception {
        // mock List<CardEntity>
        List<CardEntity> list = new ArrayList<>();
        list.add(new CardEntity(1L, "2,2,2,3"));
        list.add(new CardEntity(2L, "5,5,5,9"));

        // mock repository responses
        when(cardRepository.findAll()).thenReturn(list);

        // act
        List<Long> result = gameService.getAllCardIDs();

        // verify values
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i), list.get(i).getCardId());
        }
    }

    /**
     * Test for getCardById()
     */
    @Test
    void testGetCardById() throws Exception {
        // Mock DTO and its entity mirror
        List<Integer> nums = Arrays.asList(1, 1, 2, 6);
        CardDTO card = new CardDTO(
            // cardId
            1L,
            // cardNumbers
            nums
        );

        CardEntity ent = new CardEntity(
            // cardId
            1L,
            // cardNumbers
            "1,1,2,6"
        );

        // mock repository responses
        when(cardRepository.findById(any())).thenReturn(Optional.of(ent));
        when(cardService.toDTO(any(CardEntity.class))).thenReturn(card);
        
        // act
        CardDTO result = gameService.getCardById(card.getCardId());

        // verify results
        assertEquals(result.getCardId(), card.getCardId());
        assertEquals(result.getCardNumbers(), card.getCardNumbers());
    }

    /**
     * Test for getRandomCard()
     */
    @Test
    void testGetRandomCard() throws Exception {
        // Mock DTO and Entity
        List<Integer> nums = Arrays.asList(2, 2, 2, 3);
        CardDTO card = new CardDTO(
            // cardId
            1L,
            // cardNumbers
            nums
        );

        CardEntity ent = new CardEntity(
            // cardId
            1L,
            // cardNumbers
            "2,2,2,3"
        );

        // mock repository responses
        when(cardRepository.count()).thenReturn(2L);
        when(cardRepository.findById(any())).thenReturn(Optional.of(ent));
        when(cardService.toDTO(any(CardEntity.class))).thenReturn(card);

        // act
        CardDTO result = gameService.getRandomCard();

        // verify results
        assertEquals(result.getCardId(), card.getCardId());
        assertEquals(result.getCardNumbers(), card.getCardNumbers());
    }
}
