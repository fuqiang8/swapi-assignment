package com.partior.swapiassignment.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.partior.swapiassignment.dto.Starship;
import com.partior.swapiassignment.dto.StarshipOutput;
import com.partior.swapiassignment.service.InformationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InformationControllerTest {
    @InjectMocks
    InformationController informationController;
    @Spy
    ObjectMapper objectMapper;
    @Mock
    InformationService informationService;

    @Test
    void getInformation_shouldReturnObjectNodeWithQueriedResponseValues_givenAllQueriesReturnedValues() {
        when(informationService.getFirstStarshipPilotedBy(anyString()))
                .thenReturn(new Starship("starship", "", "", ""));
        when(informationService.getStarshipCrew(anyString()))
                .thenReturn(123);
        when(informationService.isPeopleOnPlanet(anyString(), anyString()))
                .thenReturn(true);

        ObjectNode node = informationController.getInformation();
        JsonNode starshipNode = node.get("starship");
        var output = objectMapper.convertValue(starshipNode, StarshipOutput.class);
        assertEquals("starship", output.getName());
        assertEquals(123, node.get("crew").asInt());
        assertTrue(node.get("isLeiaOnPlanet").asBoolean());
    }

    @Test
    void getInformation_shouldReturnStarshipValueAsEmptyJsonObject_givenPeopleHasNoStarship() {
        when(informationService.getFirstStarshipPilotedBy(anyString()))
                .thenReturn(null);

        ObjectNode node = informationController.getInformation();
        JsonNode starshipNode = node.get("starship");
        assertEquals("{}", starshipNode.toString());
    }
}