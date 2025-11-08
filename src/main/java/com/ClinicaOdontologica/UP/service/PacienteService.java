package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {
    @Autowired
    private PacienteRepository pacienteRepository;

    public Paciente guardarPaciente(Paciente paciente){
        return pacienteRepository.save(paciente);
    }


    public Optional<Paciente> buscarPorId(Long id){
        return pacienteRepository.findById(id);
    }
    public Optional<Paciente> buscarPorEmail(String email){
        return pacienteRepository.findByEmail(email);
    }
    public List<Paciente> buscarTodosLosPacientes(){
        return pacienteRepository.findAll();
    }
    public void borrarPaciente(Long id){
        pacienteRepository.deleteById(id);
    }

}
