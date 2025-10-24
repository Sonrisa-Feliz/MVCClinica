package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id){
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(id);
        if(pacienteBuscado.isPresent()){
            //System.out.println("Paciente encontrado");
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
//    @GetMapping
//    public ResponseEntity<List<Paciente>> listarPacientes(){
//        return ResponseEntity.ok(pacienteService.buscarPacientes());
//    }
    @PostMapping
    public ResponseEntity<Paciente> registrarPaciente(@RequestBody Paciente paciente){
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorEmail(paciente.getEmail());
        if(pacienteBuscado.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(pacienteService.guardarPaciente(paciente));
        }

    }

}
