package com.ClinicaOdontologica.UP.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class TurnoDTO {
    private Long id;
    private LocalDate fecha;
    private Long pacienteId;
    private Long odontologoId;

}
