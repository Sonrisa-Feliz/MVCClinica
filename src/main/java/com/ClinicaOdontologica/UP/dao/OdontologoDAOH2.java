package com.ClinicaOdontologica.UP.dao;

import com.ClinicaOdontologica.UP.model.Odontologo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OdontologoDAOH2 implements iDao<Odontologo> {
    private static final String SQL_SELECT_ONE = " SELECT * FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM ODONTOLOGOS";
    private static final String SQL_DELETE_ONE = "DELETE FROM ODONTOLOGOS WHERE ID=?";
    private static final String SQL_INSERT_ONE = "INSERT INTO ODONTOLOGOS (NOMBRE,APELLIDO,MATRICULA) VALUES (?,?,?)";
    private static final String SQL_UPDATE_ONE = "UPDATE ODONTOLOGOS SET ";
    private static final String SQL_FIND_POR_MATRICULA = "SELECT * FROM ODONTOLOGOS WHERE MATRICULA = ? ORDER BY ID";
    private static final String SQL_FIND_POR_NOMBRE_O_APELLIDO = "SELECT * FROM ODONTOLOGOS WHERE UPPER(NOMBRE) LIKE ? OR UPPER(APELLIDO) LIKE ? ORDER BY ID";


    @Override
    public Odontologo guardar(Odontologo odontologo) {
        Connection connection = null;
        Odontologo odontologoCreado = null;
        try {
            connection = BD.getConnection();

            PreparedStatement ps_insert_one = connection.prepareStatement(SQL_INSERT_ONE, Statement.RETURN_GENERATED_KEYS);

            ps_insert_one.setString(1, odontologo.getNombre());
            ps_insert_one.setString(2, odontologo.getApellido());
            ps_insert_one.setInt(3, odontologo.getMatricula());

            int filasCreadas = ps_insert_one.executeUpdate();
            //System.out.println("Filas creadas:"+filasCreadas);

            if (filasCreadas > 0) {
                ResultSet rs = ps_insert_one.getGeneratedKeys();

                while (rs.next()) {
                    int nuevoId = rs.getInt(1);
                    odontologoCreado = new Odontologo(
                            nuevoId,
                            odontologo.getNombre(),
                            odontologo.getApellido(),
                            odontologo.getMatricula()
                    );
                }
            }
        } catch (Exception e) {
            System.out.println("Error al guardar al odontologo: " + e.getMessage());
        }
        return odontologoCreado;
    }

    @Override
    public Odontologo buscar(Integer id) {
        Connection connection = null;
        Odontologo odontologo = null;
        try {
            connection = BD.getConnection();
            //statement mundo java a sql
            PreparedStatement ps_select_one = connection.prepareStatement(SQL_SELECT_ONE);
            ps_select_one.setInt(1, id);
            //ResultSet mundo bdd a java
            ResultSet rs = ps_select_one.executeQuery();
            while (rs.next()) {
                odontologo = new Odontologo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
            }
        } catch (Exception e) {
            System.out.println("Error al buscar el odontologo: " + e.getMessage());
        }
        //System.out.println("Odontologo encontrado");
        return odontologo;
    }

    @Override
    public void eliminar(Integer id) {
        Connection connection = null;
        try {
            connection = BD.getConnection();
            //statement mundo java a sql
            PreparedStatement ps_delete_one = connection.prepareStatement(SQL_DELETE_ONE);
            ps_delete_one.setInt(1, id);
            //ResultSet mundo bdd a java
            int odontologosEliminados = ps_delete_one.executeUpdate();

            if (odontologosEliminados > 0) {
                System.out.println("Odontologo eliminado con ID: " + id);
            } else {
                System.out.println("Odontologo no encontrado");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar al odontologo: " + e.getMessage());
        }
    }

    @Override
    public void actualizar(Odontologo odontologo) {
        if (odontologo == null || odontologo.getId() == null) {
            System.out.println("Actualizar: se requiere un ID válido");
            return;
        }

        StringBuilder sb = new StringBuilder(SQL_UPDATE_ONE);
        List<Object> parametros = new ArrayList<>();

        if (odontologo.getNombre() != null) {
            sb.append("NOMBRE = ?, ");
            parametros.add(odontologo.getNombre());
        }
        if (odontologo.getApellido() != null) {
            sb.append("APELLIDO = ?, ");
            parametros.add(odontologo.getApellido());
        }
        if (odontologo.getMatricula() != null) {
            sb.append("MATRICULA = ?, ");
            parametros.add(odontologo.getMatricula());
        }

        // Si no hay nada para actualizar, salimos
        if (parametros.isEmpty()) {
            System.out.println("Actualizar: no hay campos no nulos para actualizar");
            return;
        }

        // quitar la última coma y espacio
        sb.setLength(sb.length() - 2);
        sb.append(" WHERE ID = ?");

        Connection connection = null;
        try {
            connection = BD.getConnection();
            PreparedStatement ps_update_one = connection.prepareStatement(sb.toString());

            int idx = 1;
            for (Object p : parametros) {
                if (p instanceof Integer) ps_update_one.setInt(idx++, (Integer) p);
                else ps_update_one.setString(idx++, p.toString());
            }
            ps_update_one.setInt(idx, odontologo.getId());

            int filas = ps_update_one.executeUpdate();
            if (filas == 0) {
                System.out.println("Actualizar: no se encontró odontólogo con ID " + odontologo.getId());
            }
        } catch (Exception e) {
            System.out.println("Error al actualizar al odontólogo: " + e.getMessage());
        }
    }

    @Override
    public Odontologo buscarGenerico(String parametro) {
        if (parametro == null || parametro.trim().isEmpty()) return null;
        parametro = parametro.trim();

        Odontologo odontologo = null;
        Connection connection = null;
        try{
            connection = BD.getConnection();
            try{
                int matricula = Integer.parseInt(parametro);
                PreparedStatement ps_select_one = connection.prepareStatement(SQL_FIND_POR_MATRICULA);
                ps_select_one.setInt(1, matricula);

                ResultSet rs = ps_select_one.executeQuery();
                while (rs.next()) {
                    odontologo = new Odontologo(
                            rs.getInt(1),
                            rs.getString(2),
                            rs.getString(3),
                            rs.getInt(4));
                }
                return odontologo;
            }catch (NumberFormatException ignore) {
                // Buscamos ahora por nombre o apellido.
            }

            String like = "%" + parametro.toUpperCase() + "%";
            PreparedStatement ps_select_one = connection.prepareStatement(SQL_FIND_POR_NOMBRE_O_APELLIDO);
            ps_select_one.setString(1, like);
            ps_select_one.setString(2, like);

            ResultSet rs = ps_select_one.executeQuery();

            while (rs.next()) {
                odontologo = new Odontologo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4));
            }

        } catch (Exception e) {
            System.out.println("Error al buscar el odontologo: " + e.getMessage());
        }


        return odontologo;
    }

    @Override
    public List<Odontologo> buscarTodos() {
        Connection connection = null;
        List<Odontologo> odontologos = new ArrayList<>();
        try {
            connection = BD.getConnection();
            //statement mundo java a sql
            PreparedStatement ps_select_all = connection.prepareStatement(SQL_SELECT_ALL);
            //ResultSet mundo bdd a java
            ResultSet rs = ps_select_all.executeQuery();
            while (rs.next()) {
                Odontologo odontologo = new Odontologo(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getInt(4)
                );
                odontologos.add(odontologo);
            }

        } catch (Exception e) {
            System.out.println("Error al buscar los odontologos: " + e.getMessage());
        }
        return odontologos;
    }
}
