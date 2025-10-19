package com.ClinicaOdontologica.UP.dao;

import com.ClinicaOdontologica.UP.model.Domicilio;
import com.ClinicaOdontologica.UP.model.Paciente;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PacienteDAOH2 implements iDao<Paciente>{
    private static final Logger LOGGER = Logger.getLogger(PacienteDAOH2.class);

    private static final String SQL_SELECT_ONE=" SELECT * FROM PACIENTES WHERE ID=?";
    private static final String SQL_SELECT_ALL=" SELECT * FROM PACIENTES";
    private static final String SQL_INSERT_ONE=" INSERT INTO PACIENTES (NOMBRE, APELLIDO, NUMEROCONTACTO,FECHAINGRESO,DOMICILIO_ID,EMAIL) VALUES (?,?,?,?,?,?)";
    private static final String SQL_DELETE_ONE=" DELETE FROM PACIENTES WHERE ID=?";
    private static final String SQL_UPDATA_ONE=" UPDATE PACIENTES SET NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?,FECHAINGRESO=?,DOMICILIO_ID=?,EMAIL=? WHERE ID=?";


    @Override
    public Paciente guardar(Paciente paciente) {
        LOGGER.debug("Inicia el proceso de guardar al Paciente " + paciente.getNombre() + " " + paciente.getApellido());
        Connection connection = null;
        Paciente pacienteCreado = null;
        Domicilio domicilio = null;
        try{
            connection=BD.getConnection();

            Statement statement= connection.createStatement();
            PreparedStatement ps_insert_one= connection.prepareStatement(SQL_INSERT_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_insert_one.setString(1, paciente.getNombre());
            ps_insert_one.setString(2, paciente.getApellido());
            ps_insert_one.setInt(3, paciente.getNumeroContacto());
            ps_insert_one.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_insert_one.setInt(5, paciente.getDomicilio().getId());
            ps_insert_one.setString(6, paciente.getEmail());

            int filasCreadas = ps_insert_one.executeUpdate();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();

            if(filasCreadas > 0 ) {
                ResultSet rs = ps_insert_one.getGeneratedKeys();
                while (rs.next()) {
                    int nuevoId = rs.getInt(1);
                    pacienteCreado = new Paciente(
                            nuevoId,
                            paciente.getNombre(),
                            paciente.getApellido(),
                            paciente.getNumeroContacto(),
                            paciente.getFechaIngreso(),
                            paciente.getDomicilio(),
                            paciente.getEmail());
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error al guardar al Paciente: "+ e.getMessage());
        }
        return pacienteCreado;
    }

    @Override
    public Paciente buscar(Integer id) {
        LOGGER.debug("Inicia el proceso de buscar Paciente con el ID: " + id);
        Connection connection=null;
        Paciente paciente= null;
        Domicilio domicilio= null;
        try{
            connection=BD.getConnection();
            //statement mundo java a sql
            Statement statement= connection.createStatement();
            PreparedStatement ps_select_one= connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1,id);
            //ResultSet mundo bdd a java
            ResultSet rs= ps_select_one.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                paciente= new Paciente(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5).toLocalDate(),
                        domicilio,
                        rs.getString(7));
            }
        }catch (Exception e){
            LOGGER.error("Error al buscar al paciente" + e.getMessage());
        }
        return paciente;
    }

    @Override
    public void eliminar(Integer id) {
        LOGGER.debug("Inicia el proceso de eliminar al Paciente con el ID: " + id);
        Connection connection=null;
        try{
            connection=BD.getConnection();

            PreparedStatement ps_delete_one = connection.prepareStatement(SQL_DELETE_ONE);
            ps_delete_one.setInt(1,id);

            int pacienteEliminado = ps_delete_one.executeUpdate();

            if(pacienteEliminado > 0){
                LOGGER.debug("Paciente eliminado con ID: " + id);
            } else {
                LOGGER.error("Paciente a eliminar no encontrado.");
            }
        }catch (Exception e){
            LOGGER.error("Error al eliminar al Paciente: "+e.getMessage());
        }
    }

    @Override
    public void actualizar(Paciente paciente) {
        LOGGER.debug("Inicia el proceso de actualizar al Paciente con el ID: " + paciente.getId());
//        NOMBRE=?, APELLIDO=?, NUMEROCONTACTO=?,FECHAINGRESO=?,DOMICILIO_ID=?,EMAIL=? WHERE ID=?
        Connection connection = null;
        try{
            connection = BD.getConnection();
            PreparedStatement ps_update_one = connection.prepareStatement(SQL_UPDATA_ONE);
            ps_update_one.setString(1, paciente.getNombre());
            ps_update_one.setString(2, paciente.getApellido());
            ps_update_one.setInt(3, paciente.getNumeroContacto());
            ps_update_one.setDate(4, Date.valueOf(paciente.getFechaIngreso()));
            ps_update_one.setInt(5, paciente.getDomicilio().getId());
            ps_update_one.setString(6, paciente.getEmail());
            ps_update_one.setInt(7, paciente.getId());

            int filasActulizadas = ps_update_one.executeUpdate();
            if(filasActulizadas > 0 ) {
                System.out.println("Se Actualizaron los datos con exito");
            }

        }catch (Exception e){
            LOGGER.error("No se pudo actualizar los datos " +e.getMessage());
        }

    }

    @Override
    public Paciente buscarGenerico(String parametro) {
        return null;
    }

    @Override
    public List<Paciente> buscarTodos() {
        LOGGER.debug("Inicia el proceso de buscar todos los Pacientes.");
        Connection connection = null;
        List<Paciente> pacientes = new ArrayList<>();
        Domicilio domicilio = null;
        try{
            connection=BD.getConnection();
            PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
            ResultSet rs= ps_select_all.executeQuery();
            DomicilioDAOH2 daoAux= new DomicilioDAOH2();
            while(rs.next()){
                domicilio=daoAux.buscar(rs.getInt(6));
                Paciente paciente = new Paciente(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4),
                        rs.getDate(5).toLocalDate(),
                        domicilio,
                        rs.getString(7)
                );
                pacientes.add(paciente);
            }
        } catch (Exception e){
            LOGGER.error("Error al buscar a los paciente: "+e.getMessage());
        }
        return pacientes;
    }
}
