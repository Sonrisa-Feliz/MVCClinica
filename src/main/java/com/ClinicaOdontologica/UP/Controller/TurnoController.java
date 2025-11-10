package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.Exception.ResourceNotFoundException;
import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import com.ClinicaOdontologica.UP.service.PacienteService;
import com.ClinicaOdontologica.UP.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController// Sin tecnologia de Vista
@RequestMapping("/turno")
public class TurnoController {

    private OdontologoService odontologoService;
    private PacienteService pacienteService;
    private TurnoService turnoService;

    @Autowired
    public TurnoController(OdontologoService odontologoService, PacienteService pacienteService, TurnoService turnoService) {
        this.odontologoService = odontologoService;
        this.pacienteService = pacienteService;
        this.turnoService = turnoService;
    }

    @PostMapping
    public ResponseEntity<TurnoDTO> registrarTurno(@RequestBody Turno turno) {
        Optional<Paciente> pacienteBuscado = pacienteService.buscarPorId(turno.getPaciente().getId());
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(turno.getOdontologo().getId());

        if (pacienteBuscado.isPresent() && odontologoBuscado.isPresent()) {
            return ResponseEntity.ok(turnoService.guardarTurno(turno));
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<TurnoDTO>> listarTurnosDTO() {
        return ResponseEntity.ok(turnoService.listarTurnos());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarTurno(@PathVariable Long id) throws ResourceNotFoundException {
        Optional<TurnoDTO> turnoBuscado = turnoService.buscarPorId(id);
        if(turnoBuscado.isPresent()){
            turnoService.borrarTurno(id);
            return ResponseEntity.ok().build();

        } else {
            throw new ResourceNotFoundException("Paciente no encontrado");
        }
    }
}
