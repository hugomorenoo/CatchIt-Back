package com.Backend.AtrapaUnMillon.repositories;

import com.Backend.AtrapaUnMillon.models.Partida;
import com.Backend.AtrapaUnMillon.models.Pregunta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PreguntaRepository extends JpaRepository<Pregunta, Long> {
    List<Pregunta> findByAdminId(Long idAdmin);

    List<Pregunta> findPartidaByNivelAndDificultadAndAsignatura(String nivel, String dificultad, String asignatura);
}
