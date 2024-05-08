package com.Backend.AtrapaUnMillon.services;

import com.Backend.AtrapaUnMillon.exceptions.AdminBadRequestException;
import com.Backend.AtrapaUnMillon.exceptions.PreguntaBadRequestException;
import com.Backend.AtrapaUnMillon.models.Admin;
import com.Backend.AtrapaUnMillon.models.Pregunta;
import com.Backend.AtrapaUnMillon.repositories.AdminRepository;
import com.Backend.AtrapaUnMillon.repositories.PreguntaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PreguntaService {

    @Autowired
    private PreguntaRepository preguntaRepository;

    @Autowired
    private AdminRepository adminRepository;

    public List<Pregunta> getAllPreguntas(){
        return preguntaRepository.findAll();
    }

    public Pregunta getPreguntaById(Long id){
        Optional<Pregunta> pregunta = preguntaRepository.findById(id);
        if(pregunta.isPresent()){
            return pregunta.get();
        }else{
            throw new PreguntaBadRequestException("No existe pregunta con ese id");
        }
    }

    public List<Pregunta> getPreguntaByIdAdmin(Long idAdmin){
        List<Pregunta> preguntas = preguntaRepository.findByAdminId(idAdmin);
        return preguntas;
    }

    public Pregunta createPregunta(String pregunta, String respuestaCorrecta, String respuesta1,
                               String respuesta2, String respuesta3, String nivel,
                               String dificultad, String asignatura, int tiempo,
                               byte[] imagen, Long idAdmin){
        Optional<Admin> optionalAdmin = adminRepository.findById(idAdmin);
        if(optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            Pregunta new_pregunta = new Pregunta(pregunta, respuestaCorrecta, respuesta1, respuesta2,
                    respuesta3, nivel, dificultad, asignatura, tiempo, imagen, admin);
            preguntaRepository.save(new_pregunta);
            return new_pregunta;
        }else{
            throw new AdminBadRequestException("No existe admin");
        }
    }

    public List<Pregunta> procesarAsignarPreguntas(MultipartFile file, Long idAdmin) throws IOException {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            List<Pregunta> nuevas_preguntas = new ArrayList<>();
            Optional<Admin> adminOptional = adminRepository.findById(idAdmin);
            int lineCount = 0;
            while ((line = br.readLine()) != null) {
                if (lineCount > 0) { // Saltar la primera línea
                    String[] data = line.split(",");
                    Pregunta new_pregunta = new Pregunta();
                    if (adminOptional.isPresent()) {
                        Admin admin = adminOptional.get();
                        new_pregunta.setAdmin(admin);

                        new_pregunta.setAsignatura(data[1]);
                        new_pregunta.setDificultad(data[2]);
                        new_pregunta.setNivel(data[3]);
                        new_pregunta.setPregunta(data[4]);
                        new_pregunta.setRespuestaCorrecta(data[5]);
                        new_pregunta.setRespuesta1(data[6]);
                        new_pregunta.setRespuesta2(data[7]);
                        new_pregunta.setRespuesta3(data[8]);
                        new_pregunta.setTiempo(Integer.parseInt(data[9]));

                        preguntaRepository.save(new_pregunta);
                        nuevas_preguntas.add(new_pregunta);
                    } else {
                        throw new AdminBadRequestException("No se encontró un administrador con el ID especificado");
                    }
                }
                lineCount++;
            }
            return nuevas_preguntas;
        } catch (IOException e) {
            throw new IOException("Error al procesar el archivo CSV: " + e.getMessage());
        }
    }

    public Pregunta editPregunta(Pregunta existingPregunta, String pregunta, String respuestaCorrecta,
                                 String respuesta1, String respuesta2, String respuesta3, String nivel,
                                 String dificultad, String asignatura, int tiempo, byte[] imagen, Long idAdmin) {
        Optional<Admin> optionalAdmin = adminRepository.findById(idAdmin);
        if(optionalAdmin.isPresent()){
            Admin admin = optionalAdmin.get();
            if(admin == existingPregunta.getAdmin()){
                existingPregunta.setPregunta(pregunta);
                existingPregunta.setRespuestaCorrecta(respuestaCorrecta);
                existingPregunta.setRespuesta1(respuesta1);
                existingPregunta.setRespuesta2(respuesta2);
                existingPregunta.setRespuesta3(respuesta3);
                existingPregunta.setNivel(nivel);
                existingPregunta.setDificultad(dificultad);
                existingPregunta.setAsignatura(asignatura);
                existingPregunta.setTiempo(tiempo);
                existingPregunta.setImagen(imagen);
                preguntaRepository.save(existingPregunta);
                return existingPregunta;
            }else{
                throw new PreguntaBadRequestException("No coincide el admin creador con el que edita");
            }
        }else{
            throw new AdminBadRequestException("No existe admin");
        }
    }
}
