package com._game.main.card;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com._game.main.cards.CardDTO;
import com._game.main.cards.CardService;

@SpringBootTest
@AutoConfigureMockMvc
class CardControllerTest {
    @Mock
    private CardService cardService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test for listAll()
     */
    @Test
    void testListAll() throws Exception {
        // mock list of CardDTO
        List<CardDTO> list = new ArrayList<>();
        List<Integer> numList = Arrays.asList(1, 2, 10, 2);
        list.add(new CardDTO(1L, numList));
        list.add(new CardDTO(2L, numList));

        // mock service response
        when(cardService.getAll()).thenReturn(list);

        // act
        mockMvc.perform(get("/cards/list"))
                .andExpect(status().isOk());
    }

    /**
     * Test for getRandomCard()
     */
    @Test
    void testGetRandomCard() throws Exception {
        // mock DTO
        List<Integer> numList = Arrays.asList(1, 2, 10, 2);
        CardDTO card = new CardDTO(
            // cardId
            1L,

            // cardNumbers
            numList
        );

         // mock service response
        when(cardService.getRandomCard()).thenReturn(card);

        // act
        mockMvc.perform(get("/cards/random"))
            .andExpect(status().isOk());
    }
}
