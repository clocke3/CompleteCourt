package com._game.main.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com._game.main.cards.CardDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class GameControllerTest {

    @Mock
    private GameService gameService;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Test for getAllCardIDs()
     * 
     * @throws Exception
     */
    @Test
    void testGetAllCardIDs() throws Exception {

        // mock list of Long values
        List<Long> cardIds = Arrays.asList(1L, 2L, 3L, 4L, 5L);

        // mock service responses
        when(gameService.getAllCardIDs()).thenReturn(cardIds);

        // act
        mockMvc.perform(get("/game/idList"))
                .andExpect(status().isOk());
    }

    /**
     * Test for getCard()
     * 
     * @throws Exception
     */
    @Test
    void testGetCard() throws Exception {
        // mock DTO and Entity
        List<Integer> cardNumbers = new ArrayList<>(Arrays.asList(3, 3, 7, 8));
        CardDTO randomCard = new CardDTO(
                // cardId
                3L,

                // cardNumbers
                cardNumbers);

        // mock service and repository responses
        when(gameService.getRandomCard()).thenReturn(randomCard);

        // act
        mockMvc.perform(get("/game/card"))
                .andExpect(status().isOk());

    }

    /**
     * Test for getCardById()
     * 
     * @throws Exception
     */
    @Test
    void testGetCardById() throws Exception {
        // mock DTO and Entity
        List<Integer> cardNumbers = new ArrayList<>(Arrays.asList(3, 3, 7, 8));
        CardDTO randomCard = new CardDTO(
                // cardId
                3L,

                // cardNumbers
                cardNumbers);

        // mock service and repository responses
        when(gameService.getCardById(any())).thenReturn(randomCard);

        // act
        mockMvc.perform(get("/game/card/" + randomCard.getCardId().toString()))
                .andExpect(status().isOk());

    }

    /**
     * Test for solveOperation()
     * 
     * @throws Exception
     */
    @Test
    void testSolveOperation() throws Exception {
        // mock DTOs
        List<Integer> cardNumbers = new ArrayList<>(Arrays.asList(3, 3, 7, 8));
        CardDTO randomCard = new CardDTO(
                // cardId
                3L,

                // cardNumbers
                cardNumbers);
        String[] operations = { "3", "+", "3" };

        GameDTO gameDTO = new GameDTO(
                // card
                randomCard,
                // currentOperation
                operations);

        // mock service and repository responses
        String newNumber = String.valueOf(6);
        when(gameService.solveOperation(any((GameDTO.class)))).thenReturn(newNumber);

        // Convert DTO to serialized string
        String requestBody = objectMapper.writeValueAsString(gameDTO);

        // act
        try {
            mockMvc.perform(post("/game/solve")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody))
                    .andExpect(status().isOk());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
