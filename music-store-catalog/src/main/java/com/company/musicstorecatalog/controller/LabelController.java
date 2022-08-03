package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.exceptions.NotFoundException;
import com.company.musicstorecatalog.model.Label;
import com.company.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class LabelController {
    @Autowired
    ServiceLayer service;

    @GetMapping("/labels")
    @ResponseStatus(HttpStatus.OK)
    public List<Label> getAllLabels() {
        return service.getAllLabels();
    }

    @GetMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Label> getLabelById(@PathVariable int id) {
        if (service.getLabelById(id).orElse(null) == null) {
            throw new NotFoundException("No label with that ID exists");
        }
        return service.getLabelById(id);
    }

    @PostMapping("/labels")
    @ResponseStatus(HttpStatus.CREATED)
    public Label createLabel(@Valid @RequestBody Label label) {
        return service.createLabel(label);
    }

    @PutMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLabel(@PathVariable int id, @Valid @RequestBody Label label) {
        if (id != label.getLabelId()) {
            throw new DataIntegrityViolationException("Your request body ID does not match your Path Variable ID");
        }
        service.updateLabel(label);
    }

    @DeleteMapping("/labels/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLabel(@PathVariable int id) {
        if (service.getLabelById(id).orElse(null) == null) {
            throw new NotFoundException("No label with that ID exists");
        }
        service.deleteLabel(id);
    }
}
