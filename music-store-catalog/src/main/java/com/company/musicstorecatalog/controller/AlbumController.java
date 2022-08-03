package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Album;
import com.company.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class AlbumController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/album")
    @ResponseStatus(HttpStatus.OK)
    public List<Album> getAllAlbums() {
        return service.getAllAlbums();
    }

    @GetMapping("/album/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Album> getAlbumById(@PathVariable int id) {
        if (service.getAlbumById(id).orElse(null) == null) {
            throw new NotFoundException("No album with that ID exists");
        }
        return service.getAlbumById(id);
    }

    @PostMapping("/album")
    @ResponseStatus(HttpStatus.CREATED)
    public Album createAlbum(@Valid @RequestBody Album album) {
        return service.createAlbum(album);
    }

    @PutMapping("/album/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateAlbum(@PathVariable int id, @Valid @RequestBody Album album) {
        if (id != album.getAlbumId()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable ID");
        }
        service.updateAlbum(album);
    }

    @DeleteMapping("/album/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAlbum(@PathVariable int id) {
        if (service.getAlbumById(id).orElse(null) == null) {
            throw new NotFoundException("No album with that ID exists");
        }
        service.deleteAlbum(id);
    }
}
