package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.model.Paciente;
import com.ClinicaOdontologica.UP.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController// Sin tecnologia de Vista
@RequestMapping("/paciente")  // Todo lo que venga desde /paciente endpoint.
public class PacienteController {
    //Quien representa el modelo DAO??
    private PacienteService pacienteService;

    @Autowired
    public PacienteController(PacienteService pacienteService) {
        this.pacienteService = pacienteService;
    }
    // Aqui deben venir todos los metodos que conectan al service
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Integer id){
        Paciente pacienteBuscado = pacienteService.buscarPacientePorId(id);
        if(pacienteBuscado != null){
            //System.out.println("Paciente encontrado");
            return ResponseEntity.ok(pacienteBuscado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping
    public ResponseEntity<List<Paciente>> listarPacientes(){
        return ResponseEntity.ok(pacienteService.buscarPacientes());
    }

}
