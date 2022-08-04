package com.company.musicstorerecommendations.controller;

import com.company.musicstorerecommendations.exceptions.NotFoundException;
import com.company.musicstorerecommendations.model.LabelRecommendation;
import com.company.musicstorerecommendations.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LabelRecommendationController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/labelRecommendation")
    @ResponseStatus(HttpStatus.OK)
    public List<LabelRecommendation> getAllLabelRecommendations() {
        return service.getAllLabelRecommendations();
    }

    @GetMapping("/labelRecommendation/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<LabelRecommendation> getLabelRecommendationById(@PathVariable int id) {
        if (service.getLabelRecommendationById(id).orElse(null) == null) {
            throw new NotFoundException("Label recommendation with that ID does not exists.");
        }
        return service.getLabelRecommendationById(id);
    }

    @PostMapping("/labelRecommendation")
    @ResponseStatus(HttpStatus.CREATED)
    public LabelRecommendation createLabelRecommendation(@Valid @RequestBody LabelRecommendation labelRecommendation) {
        return service.createLabelRecommendation(labelRecommendation);
    }

    @PutMapping("/labelRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabelRecommendation(@Valid @PathVariable int id, @RequestBody LabelRecommendation labelRecommendation) {
        if (id != labelRecommendation.getLabelRecommendationId()) {
            throw new IllegalArgumentException("Input ID does not exists or match");
        }
        service.updateLabelRecommendation(labelRecommendation);
    }

    @DeleteMapping("/labelRecommendation/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabelRecommendation(@PathVariable int id) {
        if (service.getLabelRecommendationById(id).orElse(null) == null) {
            throw new NotFoundException("Label recommendation with that ID does not exists.");
        }
        service.deleteLabelRecommendation(id);
    }
}
