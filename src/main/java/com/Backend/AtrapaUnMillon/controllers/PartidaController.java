package com.Backend.AtrapaUnMillon.controllers;

import com.Backend.AtrapaUnMillon.exceptions.AdminBadRequestException;
import com.Backend.AtrapaUnMillon.exceptions.PreguntaBadRequestException;
import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.models.Pregunta;
import com.Backend.AtrapaUnMillon.services.PartidaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PartidaController {

    @Autowired
    private PartidaService partidaService;
    @Operation(summary = "Crea una partida", tags = {"partidas"})
    @PostMapping("/create")
    public ResponseEntity<Partida> crearPartida(@RequestParam(required = false) String nivel,
                                                @RequestParam(required = false) String dificultad,
                                                @RequestParam(required = false) String  asignatura,
                                               @RequestParam int numRondas,
                                               @RequestParam int numVidas,
                                               @RequestParam String titulo,
                                               @RequestParam Long idAdmin){
        try{
            Partida partida = partidaService.createPartida(nivel, dificultad, asignatura, numRondas,numVidas, titulo, idAdmin );
            return new ResponseEntity<>(partida, HttpStatus.OK);
        }catch (AdminBadRequestException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Operation(summary = "Obtiene una partida por id", tags = {"partidas"})
    @ApiResponse(responseCode = "200", description = "Pregunta")
    @ApiResponse(responseCode = "404", description = "No hay preguntas")
    @Parameter(name = "id", required = true, description = "ID de la pregunta", example = "1")
    @GetMapping("/partida/{id}")
    public ResponseEntity<Partida> getPartida(@PathVariable String id){
        try{
            Partida partida = partidaService.getPartida(id);
            return new ResponseEntity<>(partida, HttpStatus.OK);
        }catch (PreguntaBadRequestException exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
