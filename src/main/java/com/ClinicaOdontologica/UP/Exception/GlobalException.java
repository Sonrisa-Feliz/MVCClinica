package com.ClinicaOdontologica.UP.Exception;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<String> tratamientoResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler({BadEmailException.class})
    public ResponseEntity<String> tratamientoBadEmailException(BadEmailException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler({PacienteYaRegistradoException.class})
    public ResponseEntity<String> tratamientoPacienteYaRegistradoException(PacienteYaRegistradoException e) {
        return ResponseEntity.status(HttpStatus.I_AM_A_TEAPOT).body(e.getMessage());
    }
}
