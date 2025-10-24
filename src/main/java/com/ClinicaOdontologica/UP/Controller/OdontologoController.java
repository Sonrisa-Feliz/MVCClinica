package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController// Sin tecnologia de Vista
@RequestMapping("/odontologo")  // Todo lo que venga desde /paciente endpoint.
public class OdontologoController {

    private OdontologoService odontologoService;
    @Autowired
    public OdontologoController(OdontologoService odontologoService){
        this.odontologoService = odontologoService;

    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologoPaciente(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(odontologo.getMatricula());
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }

    }
}
