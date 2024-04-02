package com.Backend.AtrapaUnMillon.services;

import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.repositories.PartidaRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository partidaRepository;

    public Partida createPartida(){
        String random = RandomStringUtils.randomAlphanumeric(7).toUpperCase();
        Partida new_partida = new Partida();
        new_partida.setId(random);
        return new_partida;
    }
}
