package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.repository.OdontologoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OdontologoService {
    @Autowired
    private OdontologoRepository odontologoRepository;

    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoRepository.save(odontologo);
    }
    public Optional<Odontologo> buscarPorId(Long id){
        return odontologoRepository.findById(id);
    }
    public List<Odontologo> buscarTodosLosOdontologos(){
        return odontologoRepository.findAll();
    }
    public Optional<Odontologo> buscarPorMatricula(String matricula){
        return odontologoRepository.findByMatricula(matricula);
    }
    public void borrarOdontologo(Long id){
        odontologoRepository.deleteById(id);
    }
}
