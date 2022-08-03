package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Artist;
import com.company.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class ArtistController {

    @Autowired
    ServiceLayer service;

    @GetMapping("/artists")
    @ResponseStatus(HttpStatus.OK)
    public List<Artist> getAllArtists() {
        return service.getAllArtists();
    }

    @GetMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Artist> getArtistById(@PathVariable int id) {
        if (service.getArtistById(id).orElse(null) == null) {
            throw new NotFoundException("No artist with that ID exists");
        }
        return service.getArtistById(id);
    }

    @PostMapping("/artists")
    @ResponseStatus(HttpStatus.CREATED)
    public Artist createArtist(@Valid @RequestBody Artist artist) {
        return service.createArtist(artist);
    }

    @PutMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateArtist(@PathVariable int id, @Valid @RequestBody Artist artist) {
        if (id != artist.getArtistId()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable ID");
        }
        service.updateArtist(artist);
    }

    @DeleteMapping("/artists/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteArtist(@PathVariable int id) {
        if (service.getArtistById(id).orElse(null) == null) {
            throw new NotFoundException("No artist with that ID exists");
        }
        service.deleteArtist(id);
    }
}
