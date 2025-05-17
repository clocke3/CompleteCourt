package com._game.main.game;

import com._game.main.cards.CardDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO {
    @NotNull
    private CardDTO card;

    @NotBlank
    private String[] currentOperation;
}
