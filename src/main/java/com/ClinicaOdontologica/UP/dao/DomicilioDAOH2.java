package com.ClinicaOdontologica.UP.dao;

import com.ClinicaOdontologica.UP.model.Domicilio;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DomicilioDAOH2 implements iDao<Domicilio> {
    private static final Logger LOGGER = Logger.getLogger(DomicilioDAOH2.class);
    private static final String SQL_DOMICILIOS="SELECT * FROM DOMICILIOS WHERE ID=?";

    @Override
    public Domicilio guardar(Domicilio domicilio) {
        return null;
    }

    @Override
    public Domicilio buscar(Integer id) {
        LOGGER.debug("Inicia el proceso de buscar el domicilio con el ID: " + id);
        Connection connection= null;
        Domicilio domicilio= null;
        try{
            connection= BD.getConnection();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_DOMICILIOS);
            ps_select_one.setInt(1,id);
            ResultSet rs= ps_select_one.executeQuery();
            while(rs.next()){

                domicilio= new Domicilio(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getString(4),rs.getString(5));
            }

        }catch (Exception e){
            LOGGER.error("Error al buscar al domicilio" + e.getMessage());
        }
        LOGGER.debug("Domicilio encontrado con ID: " + id);
        return domicilio;
    }

    @Override
    public void eliminar(Integer id) {

    }

    @Override
    public void actualizar(Domicilio domicilio) {

    }

    @Override
    public Domicilio buscarGenerico(String parametro) {
        return null;
    }

    @Override
    public List<Domicilio> buscarTodos() {
        return List.of();
    }
}
