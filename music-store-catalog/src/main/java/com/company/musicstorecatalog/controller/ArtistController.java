package com.company.musicstorecatalog.controller;

import com.company.musicstorecatalog.service.ServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtistController {

    @Autowired
    ServiceLayer service;


}
