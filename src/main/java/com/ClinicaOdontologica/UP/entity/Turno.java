package com.ClinicaOdontologica.UP.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "turnos")
public class Turno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "paciente_id", referencedColumnName = "id")
    @JsonBackReference(value="paciente-turno")
    private Paciente paciente;
    @ManyToOne
    @JoinColumn(name = "odontologo_id", referencedColumnName = "id")
    @JsonBackReference(value="odontologo-turno")
    private Odontologo odontologo;
    @Column
    private LocalDate fecha;

    public Turno(Paciente paciente, Odontologo odontologo, LocalDate fecha) {
        this.paciente = paciente;
        this.odontologo = odontologo;
        this.fecha = fecha;
    }
}
