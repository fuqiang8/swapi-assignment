package com.partior.swapiassignment.web;

import com.partior.swapiassignment.dto.InformationOutput;
import com.partior.swapiassignment.service.InformationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/information")
public class InformationController {
    private final InformationService informationService;

    public InformationController(InformationService informationService) {
        this.informationService = informationService;
    }

    @GetMapping
    public InformationOutput getInformation() {
        int deathStarCrew = informationService.getStarshipCrew("Death Star");

        return new InformationOutput(deathStarCrew);
    }
}
