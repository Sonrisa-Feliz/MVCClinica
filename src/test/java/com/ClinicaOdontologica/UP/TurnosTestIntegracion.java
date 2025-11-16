package com.ClinicaOdontologica.UP;

import com.ClinicaOdontologica.UP.dto.TurnoDTO;
import com.ClinicaOdontologica.UP.entity.Domicilio;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.entity.Paciente;
import com.ClinicaOdontologica.UP.entity.Turno;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import com.ClinicaOdontologica.UP.service.PacienteService;
import com.ClinicaOdontologica.UP.service.TurnoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TurnosTestIntegracion {
    @Autowired
    private TurnoService turnoService;
    @Autowired
    private PacienteService pacienteService;
    @Autowired
    private OdontologoService odontologoService;
    @Autowired
    private MockMvc mockMvc;

    private void cargaDatos(){
        Paciente paciente = pacienteService.guardarPaciente(new Paciente("Andres","Andres", 12345678, LocalDate.now(),"andreserranop@gmail.com",new Domicilio("Roosevelt",5061,"Caba","Caba")));
        Odontologo odontologo = odontologoService.guardarOdontologo(new Odontologo("AB1234","Diaz","Gustavo"));
        TurnoDTO turnoDTO = turnoService.guardarTurno(new Turno(paciente,odontologo,LocalDate.of(2025,12,12)));
    }

    @Test
    public void listarTurnos() throws Exception{
        cargaDatos();
        MvcResult respuesta = mockMvc.perform(MockMvcRequestBuilders.get("/turno")
                .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        assertFalse(respuesta.getResponse().getContentAsString().isEmpty());
    }
}
