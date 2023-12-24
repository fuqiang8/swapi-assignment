package com.partior.swapiassignment.web;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.partior.swapiassignment.dto.Starship;
import com.partior.swapiassignment.dto.StarshipOutput;
import com.partior.swapiassignment.service.InformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/information")
public class InformationController {
    private final ObjectMapper mapper;
    private final InformationService informationService;

    public InformationController(ObjectMapper mapper, InformationService informationService) {
        this.mapper = mapper;
        this.informationService = informationService;
    }

    @GetMapping
    public ObjectNode getInformation() {
        Starship starship = informationService.getFirstStarshipPilotedBy("Darth Vader");
        JsonNode darthVaderStarship = toStarshipOutput(starship);

        int deathStarCrew = informationService.getStarshipCrew("Death Star");
        boolean isLeiaOnAlderaan = informationService.isPeopleOnPlanet("Leia Organa", "Alderaan");

        ObjectNode obj = mapper.createObjectNode();
        obj.set("starship", darthVaderStarship);
        obj.put("crew", deathStarCrew);
        obj.put("isLeiaOnPlanet", isLeiaOnAlderaan);
        return obj;
    }

    private JsonNode toStarshipOutput(Starship starship) {
        if (starship == null) {
            return mapper.createObjectNode();
        } else {
            var output = new StarshipOutput(starship.name(), starship.starship_class(), starship.model());
            return mapper.convertValue(output, JsonNode.class);
        }
    }
}
