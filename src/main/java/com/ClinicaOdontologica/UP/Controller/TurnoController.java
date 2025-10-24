package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import com.ClinicaOdontologica.UP.service.PacienteService;
import com.ClinicaOdontologica.UP.service.TurnoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
