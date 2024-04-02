package com.Backend.AtrapaUnMillon.controllers;

import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.services.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/create")
    public ResponseEntity<Partida> crearPartida(){
        Partida partida = partidaService.createPartida();
        return new ResponseEntity<>(partida, HttpStatus.OK);
    }
}
