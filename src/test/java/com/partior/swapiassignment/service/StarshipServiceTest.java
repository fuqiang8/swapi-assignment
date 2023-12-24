package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.Starship;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StarshipServiceTest {
    @InjectMocks
    StarshipService starshipService;
    @Mock
    SWAPIWebClientService webClientService;

    @Test
    void searchExact_shouldReturnStarship_givenExactMatchExists() {
        when(webClientService.searchStarships(anyString()))
                .thenReturn(List.of(
                        new Starship("starship1", "", "",""),
                        new Starship("starship12", "", "","")
                ));

        Starship starship = starshipService.searchExact("starship1");
        assertEquals("starship1", starship.name());
    }

    @Test
    void searchExact_shouldReturnNull_givenNoExactMatchExists() {
        when(webClientService.searchStarships(anyString()))
                .thenReturn(List.of(
                        new Starship("starship12", "", "","1"),
                        new Starship("starship123", "", "","")
                ));

        Starship starship = starshipService.searchExact("starship1");
        assertNull(starship);
    }

    @Test
    void searchExact_shouldReturnNull_givenEmptyList() {
        when(webClientService.searchStarships(anyString()))
                .thenReturn(List.of());

        Starship starship = starshipService.searchExact("starship1");
        assertNull(starship);
    }
}