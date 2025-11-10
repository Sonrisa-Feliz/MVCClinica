package com.ClinicaOdontologica.UP.service;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.repository.TurnoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TurnoService {
    private TurnoRepository turnoRepository;

    @Autowired
    public TurnoService(TurnoRepository turnoRepository) {
        this.turnoRepository = turnoRepository;
    }

    public TurnoDTO guardarTurno(Turno turno) {
        Turno turnoGuardado = turnoRepository.save(turno);
        return turnoATurnoDTO(turnoGuardado);
    }

    public void borrarTurno(Long id){
        turnoRepository.deleteById(id);
    }

    public Optional<TurnoDTO> buscarPorId(Long id) {
        return turnoRepository.findById(id)
                .map(this::turnoATurnoDTO);
    }

    public List<TurnoDTO> listarTurnos() {
        List<Turno> turnos = turnoRepository.findAll();
        List<TurnoDTO> listaTurnosDTO = new ArrayList<>();
        for (Turno turno : turnos) {
            listaTurnosDTO.add(turnoATurnoDTO(turno));
        }
        return listaTurnosDTO.stream()
                .sorted((e1, e2) -> e1.getFecha().compareTo(e2.getFecha()))
                .collect(Collectors.toList());
    }

    public TurnoDTO turnoATurnoDTO(Turno turno) {
        TurnoDTO turnoDTO = new TurnoDTO();

        turnoDTO.setId(turno.getId());
        turnoDTO.setPacienteNombre(turno.getPaciente().getNombre());
        turnoDTO.setPacienteApellido(turno.getPaciente().getApellido());
        turnoDTO.setOdontologoNombre(turno.getOdontologo().getNombre());
        turnoDTO.setOdontologoApellido(turno.getOdontologo().getApellido());
        turnoDTO.setFecha(turno.getFecha());

        return turnoDTO;
    }
}
