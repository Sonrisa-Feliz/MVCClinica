package com.ClinicaOdontologica.UP.repository;

import com.ClinicaOdontologica.UP.entity.Turno;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface TurnoRepository extends JpaRepository<Turno, Long> {
    Optional<Turno> findByFecha(LocalDate fecha);
}
