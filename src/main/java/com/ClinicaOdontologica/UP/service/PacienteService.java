package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dao.iDao;
import com.ClinicaOdontologica.UP.model.Paciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PacienteService {
    private iDao<Paciente> pacienteiDao;

    @Autowired
    public PacienteService(iDao<Paciente> pacienteiDao) {
        this.pacienteiDao = pacienteiDao;
    }
    public Paciente guardarPaciente(Paciente paciente){
        return pacienteiDao.guardar(paciente);
    }
    public Paciente buscarPacientePorId(Integer id){
        return pacienteiDao.buscar(id);
    }
    public List<Paciente> buscarPacientes(){
        return pacienteiDao.buscarTodos();
    }
    public void eliminarPaciente(Integer id){
        pacienteiDao.eliminar(id);
    }
    public void actualizar(Paciente paciente){
        pacienteiDao.actualizar(paciente);
    }
}
