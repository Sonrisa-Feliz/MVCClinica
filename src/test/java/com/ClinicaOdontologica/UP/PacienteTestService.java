package com.ClinicaOdontologica.UP;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ClinicaOdontologica.UP.dao.BD;
import com.ClinicaOdontologica.UP.dao.PacienteDAOH2;
import com.ClinicaOdontologica.UP.model.Domicilio;
import com.ClinicaOdontologica.UP.model.Paciente;
import com.ClinicaOdontologica.UP.service.PacienteService;

import java.time.LocalDate;
import java.util.List;

public class PacienteTestService {
    @Test
    public void buscarPaciente(){
        //DADO
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        Paciente paciente= pacienteService.buscarPacientePorId(1);
        System.out.println("datos encontrados: "+paciente.toString());
        //ENTONCES
        Assertions.assertTrue(paciente!=null);
    }

    @Test
    public void buscarTodosLosPaciente(){
        //DADO
        System.out.println("\nBscando Los Pacientes");
        BD.crearTablas();
        PacienteService pacienteService= new PacienteService(new PacienteDAOH2());
        //CUANDO
        List<Paciente> pacientes= pacienteService.buscarPacientes();
        System.out.println("Lista de Pacientes encontrados: "+pacientes.toString());
        //ENTONCES
        Assertions.assertTrue(pacientes!=null);
    }

    @Test
    public void agregarPacientes(){
        // DADO
        System.out.println("\nAgregando Paciente");
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());
        Paciente nuevoPaciente = new Paciente("Cosme","Fulanito",44444444,LocalDate.parse("2024-03-05"), new Domicilio(1),"cosme@fulanito.com.ar");

        // CUANDO
        Paciente guardado = service.guardarPaciente(nuevoPaciente);

        // ENTONCES
        Paciente desdeDb = service.buscarPacientePorId(guardado.getId());
        Assertions.assertEquals(guardado.getId(),desdeDb.getId());
    }
    @Test
    public void elminiarPaciente(){
        // DADO
        System.out.println("\nEliminando Paciente");
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());

        // CUANDO
        service.eliminarPaciente(1);

        //  ENTONCES
        Paciente paciente = service.buscarPacientePorId(1);
        Assertions.assertNull(paciente, "el paciente con ID deberia haber sido eliminado");
    }
    @Test
    public void actualizarPaciente() {
        BD.crearTablas();
        PacienteService service = new PacienteService(new PacienteDAOH2());

        // Traigo uno existente
        Paciente p = service.buscarPacientePorId(1);
        Assertions.assertNotNull(p);

        // Modifico sólo el nombre
        p.setNombre("Gregory");
        service.actualizar(p);

        // Verifico que se haya actualizado
        Paciente actualizado = service.buscarPacientePorId(1);
        Assertions.assertEquals("Gregory", actualizado.getNombre());
        Assertions.assertEquals(p.getApellido(), actualizado.getApellido());
    }
}
