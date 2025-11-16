package com.ClinicaOdontologica.UP.security;

import com.ClinicaOdontologica.UP.entity.*;
import com.ClinicaOdontologica.UP.repository.OdontologoRepository;
import com.ClinicaOdontologica.UP.repository.PacienteRepository;
import com.ClinicaOdontologica.UP.repository.TurnoRepository;
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
    @Autowired
    private TurnoRepository turnoRepository;


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

        Domicilio domicilioCarl = new Domicilio("Avenida Siempre Viva", 742, "Springfield","Mississipi");
        Paciente pacienteCarl = new Paciente("Carl","Carlson", 111222333, LocalDate.of(2025,11,10), "carl@planta.com", domicilioCarl);
        pacienteRepository.save(pacienteCarl);

        Domicilio domicilioLenny = new Domicilio("Avenida Siempre Viva", 744, "Springfield","Mississipi");
        Paciente pacienteLenny = new Paciente("Lenny","Leonard", 222333444, LocalDate.of(2025,11,11), "lenny@planta.com", domicilioLenny);
        pacienteRepository.save(pacienteLenny);

        Domicilio domicilioBurns = new Domicilio("Mansión Burns", 1, "Springfield","Mississipi");
        Paciente pacienteBurns = new Paciente("Montgomery","Burns", 333444555, LocalDate.of(2025,11,12), "burns@planta.com", domicilioBurns);
        pacienteRepository.save(pacienteBurns);

        Domicilio domicilioSmithers = new Domicilio("Mansión Burns", 2, "Springfield","Mississipi");
        Paciente pacienteSmithers = new Paciente("Waylon","Smithers", 444555666, LocalDate.of(2025,11,13), "smithers@planta.com", domicilioSmithers);
        pacienteRepository.save(pacienteSmithers);

        Odontologo odontologo = new Odontologo("Fake123123","Rivera","Nick");
        odontologoRepository.save(odontologo);

        Odontologo odontologoHibbert = new Odontologo("SPR1001","Hibbert","Julius");
        odontologoRepository.save(odontologoHibbert);

        Odontologo odontologoMonroe = new Odontologo("SPR1002","Monroe","Marvin");
        odontologoRepository.save(odontologoMonroe);

        Turno turno = new Turno(paciente,odontologo,LocalDate.of(2025,11,16));
        turnoRepository.save(turno);

        Turno turno2 = new Turno(paciente3,odontologo,LocalDate.of(2025,11,15));
        turnoRepository.save(turno2);

        Turno turno3 = new Turno(paciente3,odontologo,LocalDate.of(2025,9,15));
        turnoRepository.save(turno3);

    }
}
