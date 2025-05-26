package com._game.main.cards;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<CardDTO> getAll() {
       return cardRepository.findAll().stream()
            .map(this::toDTO)
            .toList();
    }

    public CardDTO getRandomCard() {
        Random r = new Random();
        Long count = cardRepository.count();
        Long randomLong = r.nextLong(count + 1);

        Optional<CardEntity> entity = cardRepository.findById(randomLong);
        if (entity.isPresent()){
            return toDTO(entity.get());
        } else {
            throw new NoSuchElementException("no dto found");
        }
    }


    /**
     * Convert CardDTO to a CardEntity 
     * @param dto
     * @return CardEntity
     */
    public CardEntity toEntity(CardDTO dto) {
        return new CardEntity(
            // id
            dto.getCardId(),
            // cardNumbers
            convertDTOCardsToString(dto.getCardNumbers())
        );
    }

    /**
     * Convert a CardEntity to a CardDTO
     * @param entity
     * @return CardDTO
     */
   public CardDTO toDTO(CardEntity entity) {
        return new CardDTO(
            // id
            entity.getCardId(),
            // cardNumbers
            convertStringToDTOCards(entity.getCardNumbers())
        );
    }

     /**
     * Convert String to List<Integer>.
     * 
     * @param card
     * @return
     */
    private List<Integer> convertStringToDTOCards(String string) {
        return Arrays.stream(string.split(","))
                .map(String::trim)
                .map(Integer::parseInt)
                .toList();
    }

    /**
     * Convert List<Integer> into String.
     * @param card
     * @return
     */
    private String convertDTOCardsToString(List<Integer> list) {
        return list.stream()
            .map(String::valueOf)
                      .collect(Collectors.joining(","));
    }
}
