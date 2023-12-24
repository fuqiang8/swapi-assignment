package com.partior.swapiassignment.service;

import com.partior.swapiassignment.dto.People;
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
class PeopleServiceTest {
    @InjectMocks
    PeopleService peopleService;
    @Mock
    SWAPIWebClientService webClientService;

    @Test
    void searchExact_shouldReturnStarship_givenExactMatchExists() {
        when(webClientService.searchPeople(anyString()))
                .thenReturn(List.of(
                        new People("name1", null),
                        new People("name2", null)
                ));

        People people = peopleService.searchExact("name2");
        assertEquals("name2", people.name());
    }

    @Test
    void searchExact_shouldReturnNull_givenNoExactMatchExists() {
        when(webClientService.searchPeople(anyString()))
                .thenReturn(List.of(
                        new People("name1", null),
                        new People("name23", null)
                ));

        People people = peopleService.searchExact("name2");
        assertNull(people);
    }

    @Test
    void searchExact_shouldReturnNull_givenEmptyList() {
        when(webClientService.searchPeople(anyString()))
                .thenReturn(List.of());

        People people = peopleService.searchExact("name2");
        assertNull(people);
    }
}