package com.Backend.AtrapaUnMillon.services;

import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.models.Pregunta;
import com.Backend.AtrapaUnMillon.repositories.PartidaRepository;
import com.Backend.AtrapaUnMillon.repositories.PreguntaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;
    @Autowired
    private PreguntaRepository preguntarepository;

    public List<Pregunta> createPartida(String nivel, String dificultad, String asignatura){
        String random = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        //Partida new_partida = new Partida();
        //new_partida.setId(random);
        //return new_partida;
        List<Pregunta> preguntas_filtered  = preguntarepository.findPartidaByNivelAndDificultadAndAsignatura(nivel, dificultad, asignatura);
        return preguntas_filtered;
    }
}
