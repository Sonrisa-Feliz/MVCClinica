package com.ClinicaOdontologica.UP.security;

import com.ClinicaOdontologica.UP.entity.*;
import com.ClinicaOdontologica.UP.repository.OdontologoRepository;
import com.ClinicaOdontologica.UP.repository.PacienteRepository;
import com.ClinicaOdontologica.UP.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class DatosIniciales implements ApplicationRunner {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PacienteRepository pacienteRepository;
    @Autowired
    private OdontologoRepository odontologoRepository;
    @Autowired
    private BCryptPasswordEncoder codificador;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        String pass = "admin";
        String passCifrado = codificador.encode(pass);
        Usuario usuario = new Usuario("Andy","admin", passCifrado, "admin@admin.com", UsuarioRole.ROLE_USER);
        System.out.println("Pass sin cifrar: " + pass + " Pass cifrado: " + passCifrado);
        usuarioRepository.save(usuario);

        Domicilio domicilio = new Domicilio("Siempre Viva", 1212, "Springfield","Mississipi");
        Paciente paciente = new Paciente("Homero","Simpson", 123123, LocalDate.of(2025,11,6), "Homero@Fox.com", domicilio);
        pacienteRepository.save(paciente);

        Domicilio domicilio2 = new Domicilio("Siempre Viva", 1212, "Springfield","Mississipi");
        Paciente paciente2 = new Paciente("Marge","Simpson", 123123, LocalDate.of(2025,11,6), "Marge@Fox.com", domicilio2);
        pacienteRepository.save(paciente2);

        Domicilio domicilioNed = new Domicilio("Siempre Viva", 456456, "Springfield","Mississipi");
        Paciente paciente3 = new Paciente("Ned","Flanders", 567890345, LocalDate.of(2025,5,6), "Ned@EnCristo.com", domicilioNed);
        pacienteRepository.save(paciente3);

        Odontologo odontologo = new Odontologo("Fake123123","Nick","Rivera");
        odontologoRepository.save(odontologo);


    }
}
