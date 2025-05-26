package com._game.main.card;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com._game.main.cards.CardDTO;
import com._game.main.cards.CardEntity;
import com._game.main.cards.CardRepository;
import com._game.main.cards.CardService;

@ExtendWith(MockitoExtension.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @InjectMocks
    private CardService cardService;
    

    /**
     * Test for getAll()
     */
    @Test
    void testGetAll() throws Exception {
        // mock List<CardEntity>
        List<CardEntity> list = new ArrayList<>();
        list.add(new CardEntity(1L, "2,2,2,3"));
        list.add(new CardEntity(2L, "5,5,5,9"));

        // mock repository responses
        when(cardRepository.findAll()).thenReturn(list);

        // act
        List<CardDTO> result = cardService.getAll();

        // verify results
        assertTrue(result.size() == 2);
        for (int i = 0; i < result.size(); i++) {
            assertEquals(result.get(i).getCardId(), list.get(i).getCardId());
            String resultNumbers = result.get(i).getCardNumbers().stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
            assertEquals(resultNumbers, list.get(i).getCardNumbers());
        }
    }

    /**
     * Test for getRandomCard()
     */
    @Test
    void testGetRandomCard() throws Exception{
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

        // act
        CardDTO result = cardService.getRandomCard();

        // verify results
        assertEquals(result.getCardId(), card.getCardId());
        assertEquals(result.getCardNumbers(), card.getCardNumbers());
    }

    /** 
     * Test for toEntity()
     */
    @Test
    void testToEntity() throws Exception{
        // Mock DTO
        List<Integer> nums = Arrays.asList(2, 2, 2, 3);
        CardDTO card = new CardDTO(
            // cardId
            1L,
            // cardNumbers
            nums
        );

        // act
        CardEntity ent = cardService.toEntity(card);

        // verify results
        assertEquals(ent.getCardId(), card.getCardId());
        String dtoNumbers = card.getCardNumbers().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
        assertEquals(ent.getCardNumbers(), dtoNumbers);

    }

     /** 
     * Test for toDTO()
     */
    @Test
    void testToDTO() throws Exception{
        // Mock Entity
        CardEntity card = new CardEntity(
            // cardId
            1L,
            // cardNumbers
            "2,2,3,2"
        );

        // act
        CardDTO dto = cardService.toDTO(card);

        // verify results
        assertEquals(dto.getCardId(), card.getCardId());
        String dtoNumbers = dto.getCardNumbers().stream()
            .map(String::valueOf)
            .collect(Collectors.joining(","));
        assertEquals(card.getCardNumbers(), dtoNumbers);

    }
}
