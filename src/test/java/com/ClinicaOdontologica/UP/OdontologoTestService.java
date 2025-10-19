package com.ClinicaOdontologica.UP;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.ClinicaOdontologica.UP.dao.BD;
import com.ClinicaOdontologica.UP.dao.OdontologoDAOH2;
import com.ClinicaOdontologica.UP.model.Odontologo;
import com.ClinicaOdontologica.UP.service.OdontologoService;

import java.util.List;

public class OdontologoTestService {
    @Test
    public void buscarOdontologo(){
        System.out.println("\nBuscando un odontologo");
        //DADO
        BD.crearTablas();
        OdontologoService odontologoService= new OdontologoService(new OdontologoDAOH2());
        //CUANDO
        Odontologo odontologo = odontologoService.buscarOdontologoPorId(2);
        System.out.println("datos encontrados: "+odontologo.toString());
        //ENTONCES
        Assertions.assertTrue(odontologo!=null);
    }
    @Test
    public void buscarTodosLosOdontologo(){
        //DADO
        System.out.println("\nBuscando a todos los odontologos");
        BD.crearTablas();
        OdontologoService odontologoService= new OdontologoService(new OdontologoDAOH2());
        //CUANDO
        List<Odontologo> odontologos = odontologoService.buscarOdontologos();
        System.out.println("Lista de Odontologos encontrados: "+odontologos.toString());
        //ENTONCES
        Assertions.assertTrue(odontologos != null);
    }
    @Test
    public void eliminarOdontologo() {
        // DADO
        System.out.println("\neliminando al odontologo");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        service.eliminarOdontologo(1);

        // ENTONCES
        Odontologo odontologo = service.buscarOdontologoPorId(1);
        Assertions.assertNull(odontologo, "El odontólogo con ID 1 debería haber sido eliminado");
    }
    @Test
    public void agregarOdontologo() {
        // DADO
        System.out.println("\nAgregando un odontologo");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo nuevoOdontologo = new Odontologo(546123,"Sangrantes","Encias");
        Odontologo guardado = service.guardarOdontologo(nuevoOdontologo);

        // ENTONCES
        Odontologo desdeDb = service.buscarOdontologoPorId(guardado.getId());
        Assertions.assertEquals(guardado.getId(), desdeDb.getId());

    }
    @Test
    public void actualizarSoloNombre() {
        // DADO
        System.out.println("\nModificando odontologo");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo antes = service.buscarOdontologoPorId(1);
        Assertions.assertNotNull(antes);
        service.actualizarOdontologo(new Odontologo(1, "NuevoNombre", null, null));
        Odontologo despues = service.buscarOdontologoPorId(1);
        Assertions.assertNotNull(despues);

        // ENTONCES
        Assertions.assertEquals("NuevoNombre", despues.getNombre());
        // Los otros campos deben permanecer iguales
        Assertions.assertEquals(antes.getApellido(), despues.getApellido());
        Assertions.assertEquals(antes.getMatricula(), despues.getMatricula());
        System.out.println("Nombre de Odontologo modificado: "+despues.toString());
    }

    @Test
    public void actualizarSoloApellido() {
        // DADO
        System.out.println("\nModificando odontologo");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo antes = service.buscarOdontologoPorId(2);
        Assertions.assertNotNull(antes);
        service.actualizarOdontologo(new Odontologo(2, null, "NuevoApellido", null));
        Odontologo despues = service.buscarOdontologoPorId(2);
        Assertions.assertNotNull(despues);

        // ENTONCES
        Assertions.assertEquals("NuevoApellido", despues.getApellido());
        Assertions.assertEquals(antes.getNombre(), despues.getNombre());
        Assertions.assertEquals(antes.getMatricula(), despues.getMatricula());
        System.out.println("Apellido de Odontologo modificado: "+despues.toString());
    }

    @Test
    public void actualizarSoloMatricula() {
        // DADO
        System.out.println("\nModificando odontologo");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo antes = service.buscarOdontologoPorId(1);
        Assertions.assertNotNull(antes);
        service.actualizarOdontologo(new Odontologo(1, null, null, 55555));
        Odontologo despues = service.buscarOdontologoPorId(1);
        Assertions.assertNotNull(despues);

        // ENTONCES
        Assertions.assertEquals(55555, despues.getMatricula());
        Assertions.assertEquals(antes.getNombre(), despues.getNombre());
        Assertions.assertEquals(antes.getApellido(), despues.getApellido());
        System.out.println("Matricula de Odontologo modificada: "+despues.toString());
    }

    @Test
    public void buscarPorNombre() {
        // DADO
        System.out.println("\nBuscando odontologo por Nombre");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo odontologo = service.buscarGenerico("Nic");

        // ENTONCES
        Assertions.assertNotNull(odontologo, "Debería encontrar por nombre con LIKE");
        Assertions.assertTrue(
                odontologo.getNombre().toUpperCase().contains("NIC") ||
                        odontologo.getApellido().toUpperCase().contains("NIC")
        );

        System.out.println("Odontologo encontrado: "+odontologo.toString());
    }

    @Test
    public void buscarPorApellido() {
        // DADO
        System.out.println("\nBuscando odontologo por Apellido");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo odontologo = service.buscarGenerico("wol");

        // ENTONCES
        Assertions.assertNotNull(odontologo, "Debería encontrar por apellido con LIKE");
        Assertions.assertTrue(
                odontologo.getApellido().toUpperCase().contains("WOL") ||
                        odontologo.getNombre().toUpperCase().contains("WOL")
        );
        System.out.println("Odontologo encontrado: "+odontologo.toString());
    }

    @Test
    public void buscarPorMatricula() {
        // DADO
        System.out.println("\nBuscando odontologo por matricula");
        BD.crearTablas();
        OdontologoService service = new OdontologoService(new OdontologoDAOH2());

        // CUANDO
        Odontologo odontologo = service.buscarGenerico("987654");

        // ENTONCES
        Assertions.assertNotNull(odontologo, "Debería encontrar por matrícula exacta");
        Assertions.assertEquals(987654, odontologo.getMatricula());
        System.out.println("Odontologo encontrado: "+odontologo.toString());
    }
}
