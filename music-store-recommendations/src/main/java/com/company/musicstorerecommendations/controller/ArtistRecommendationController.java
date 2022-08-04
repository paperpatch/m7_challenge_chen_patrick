package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exceptions.NotFoundException;
import com.company.musicstorerecommendations.model.ArtistRecommendation;
import com.company.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArtistRecommendationController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/artistRecommendation")
    @ResponseStatus(HttpStatus.OK)
    public List<ArtistRecommendation> getAllArtistRecommendations() {
        return service.getAllArtistRecommendations();
    }

    @GetMapping("/artistRecommendation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ArtistRecommendation> getArtistRecommendationById(@PathVariable int id) {
        if (service.getArtistRecommendationById(id) == null) {
            throw new NotFoundException("Artist recommendation with that ID does not exists.");
        }
        return service.getArtistRecommendationById(id);
    }

    @PostMapping("/artistRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public ArtistRecommendation createArtistRecommendation(@Valid @RequestBody ArtistRecommendation artistRecommendation) {
        return service.createArtistRecommendation(artistRecommendation);
    }

    @PutMapping("/artistRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtistRecommendation(@Valid @PathVariable int id, @RequestBody ArtistRecommendation artistRecommendation) {
        if (id != artistRecommendation.getArtistRecommendationId()) {
            throw new IllegalArgumentException("Input ID does not exists or match");
        }
        service.updateArtistRecommendation(artistRecommendation);
    }

    @DeleteMapping("/artistRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtistRecommendation(@PathVariable int id) {
        if (service.getArtistRecommendationById(id) == null) {
            throw new NotFoundException("Artist recommendation with that ID does not exists.");
        }
        service.deleteArtistRecommendation(id);
    }
}
