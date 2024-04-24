package com.Backend.AtrapaUnMillon.services;

import com.Backend.AtrapaUnMillon.exceptions.AdminBadRequestException;
import com.Backend.AtrapaUnMillon.models.Jugador;
import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.repositories.JugadorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JugadorService {
    @Autowired
    private JugadorRepository jugadorRepository;

    public Jugador crearJugador(String nickname, Partida partida){
        for(Jugador jugador : partida.getJugadores()){
            if(nickname.equals(jugador.getNombre())){
                throw new AdminBadRequestException("Este nickname ya existe");
            }
        }
        Jugador nuevo_jugador = new Jugador(nickname, partida);
        jugadorRepository.save(nuevo_jugador);
        return nuevo_jugador;
    }
}
