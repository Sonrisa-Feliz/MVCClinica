package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dao.iDao;
import com.ClinicaOdontologica.UP.model.Odontologo;

import java.util.List;

public class OdontologoService {
    private iDao<Odontologo> odontologoiDao;

    public OdontologoService(iDao<Odontologo> odontologoiDao) {
        this.odontologoiDao = odontologoiDao;
    }
    public Odontologo guardarOdontologo(Odontologo odontologo){
        return odontologoiDao.guardar(odontologo);
    }
    public Odontologo buscarOdontologoPorId(Integer id){
        return odontologoiDao.buscar(id);
    }
    public List<Odontologo> buscarOdontologos(){
        return odontologoiDao.buscarTodos();
    }
    public void eliminarOdontologo(Integer id) {
        odontologoiDao.eliminar(id);
    }
    public void actualizarOdontologo(Odontologo parcial) {
        odontologoiDao.actualizar(parcial);
    }
    public Odontologo buscarGenerico(String parametro) {
        return odontologoiDao.buscarGenerico(parametro);
    }
}
