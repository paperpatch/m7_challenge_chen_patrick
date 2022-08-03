package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Track;
import com.company.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class TrackController {
    @Autowired
    ServiceLayer serviceLayer;

    @GetMapping("/tracks")
    @ResponseStatus(HttpStatus.OK)
    public List<Track> getAllTracks() {
        return serviceLayer.getAllTracks();
    }

    @GetMapping("/tracks/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Track> getTrackById(@PathVariable int id) {
        if (serviceLayer.getTrackById(id).orElse(null) == null) {
            throw new NotFoundException("No track with that ID exists");
        }
        return serviceLayer.getTrackById(id);
    }

    @PostMapping("/tracks")
    @ResponseStatus(HttpStatus.CREATED)
    public Track createTrack(@Valid @RequestBody Track track) {
        return serviceLayer.createTrack(track);
    }

    @PutMapping("/tracks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateTrack(@PathVariable int id, @Valid @RequestBody Track track) {
        if (id != track.getTrackId()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable ID");
        }
        serviceLayer.updateTrack(track);
    }

    @DeleteMapping("/tracks/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTrack(@PathVariable int id) {
        if (serviceLayer.getTrackById(id).orElse(null) == null) {
            throw new NotFoundException("No track with that ID exists");
        }
        serviceLayer.deleteTrack(id);
    }
}
