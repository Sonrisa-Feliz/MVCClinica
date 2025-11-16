package com.ClinicaOdontologica.UP;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class OdontologoTestService {
    @Autowired
    private OdontologoService odontologoService;

    private Odontologo crearOdontologoTestUnitario() {
        Odontologo odontologo = new Odontologo();
        odontologo.setNombre("Andrés");
        odontologo.setApellido("Serrano");
        odontologo.setMatricula("FakeTU1234");

        return odontologo;
    }

    @Test
    void guardarOdontologo_DeberiaPersisteYRetornar() {
        // DADO
        System.out.println("Probando guardar un Odontólogo");
        Odontologo odontologo = crearOdontologoTestUnitario();

        // CUANDO
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);

        // ENTONCES
        assertNotNull(odontologoGuardado.getId(),"Debe retornar un ID luego de guardar");
        Optional<Odontologo> buscado = odontologoService.buscarPorId(odontologoGuardado.getId());
        assertTrue(buscado.isPresent(),"El odontólogo debe existir");
        assertEquals(odontologoGuardado.getId(), buscado.get().getId(),"El id del Odontólogo es Incorrecto.");
        assertEquals(odontologoGuardado.getNombre(), buscado.get().getNombre(),"El nombre del odontólogo guardado es incorrecto");

    }

    @Test
    void borrarOdontologo_DeberiaEliminarOdontologo() {
        // DADO
        System.out.println("Iniciando prueba eliminar un Odontologo");
        Odontologo odontologo = crearOdontologoTestUnitario();
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        Long idOdontologo = odontologoGuardado.getId();
        assertNotNull(odontologoGuardado.getId(),"Debe retornar un ID luego de guardar");

        // CUANDO
        odontologoService.borrarOdontologo(idOdontologo);

        // ENTONCES
        Optional<Odontologo> eliminado = odontologoService.buscarPorId(idOdontologo);
        assertTrue(eliminado.isEmpty(),"El odontólogo debería haber sido eliminado");
    }

    @Test
    void modificarOdontologo_DeberiaModificarOdontologo() {
        // DADO
        System.out.println("Iniciando prueba modificar un Odontologo");
        Odontologo odontologo = crearOdontologoTestUnitario();
        Odontologo odontologoGuardado = odontologoService.guardarOdontologo(odontologo);
        Long idOdontologo = odontologoGuardado.getId();
        assertNotNull(odontologoGuardado.getId(),"Debe retornar un ID luego de guardar");

        // CUANDO
        odontologoGuardado.setNombre("Carlos");
        odontologoService.guardarOdontologo(odontologoGuardado);

        // ENTONCES
        Optional<Odontologo> actualizado = odontologoService.buscarPorId(idOdontologo);
        assertEquals("Carlos", actualizado.get().getNombre(),"El nombre del odontólogo no coincide luego de modificarlo");
    }
}
