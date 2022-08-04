package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exceptions.NotFoundException;
import com.company.musicstorerecommendations.model.TrackRecommendation;
import com.company.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TrackRecommendationController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/trackRecommendation")
    @ResponseStatus(HttpStatus.OK)
    public List<TrackRecommendation> getAllTrackRecommendations() {
        return service.getAllTrackRecommendations();
    }

    @GetMapping("/trackRecommendation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<TrackRecommendation> getTrackRecommendationById(@PathVariable int id) {
        if (service.getTrackRecommendationById(id) == null) {
            throw new NotFoundException("Track recommendation with that ID does not exists.");
        }
        return service.getTrackRecommendationById(id);
    }

    @PostMapping("/trackRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public TrackRecommendation createTrackRecommendation(@Valid @RequestBody TrackRecommendation trackRecommendation) {
        return service.createTrackRecommendation(trackRecommendation);
    }

    @PutMapping("/trackRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrackRecommendation(@Valid @PathVariable int id, @RequestBody TrackRecommendation trackRecommendation) {
        if (id != trackRecommendation.getTrackRecommendationId()) {
            throw new IllegalArgumentException("Input ID does not exists or match");
        }
        service.updateTrackRecommendation(trackRecommendation);
    }

    @DeleteMapping("/trackRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrackRecommendation(@PathVariable int id) {
        if (service.getTrackRecommendationById(id) == null) {
            throw new NotFoundException("Track recommendation with that ID does not exists.");
        }
        service.deleteTrackRecommendation(id);
    }
}
