package com.Backend.AtrapaUnMillon.controllers;

import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.models.Pregunta;
import com.Backend.AtrapaUnMillon.services.PartidaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;

    @PostMapping("/create")
    public ResponseEntity<List<Pregunta>> crearPartida(@RequestParam String nivel,
                                                @RequestParam String dificultad,
                                                @RequestParam String asignatura){
        List<Pregunta> preguntas = partidaService.createPartida(nivel, dificultad, asignatura);
        return new ResponseEntity<>(preguntas, HttpStatus.OK);
    }
}
