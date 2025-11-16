package com.ClinicaOdontologica.UP.Controller;

import com.ClinicaOdontologica.UP.Exception.ResourceNotFoundException;
import com.ClinicaOdontologica.UP.dto.OdontologoDTO;
import com.ClinicaOdontologica.UP.entity.Odontologo;
import com.ClinicaOdontologica.UP.service.OdontologoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController// Sin tecnologia de Vista
@RequestMapping("/odontologo")  // Todo lo que venga desde /paciente endpoint.
public class OdontologoController {

    private OdontologoService odontologoService;

    @Autowired
    public OdontologoController(OdontologoService odontologoService){
        this.odontologoService = odontologoService;

    }

    @GetMapping("/BuscarPorId/{id}")
    public ResponseEntity<Optional<Odontologo>> buscarPorId(@PathVariable Long id){
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.ok(odontologoBuscado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Odontologo>> listaOdontologos(){
        return ResponseEntity.ok(odontologoService.buscarTodosLosOdontologos());
    }

    @GetMapping("/porDTO")
    public ResponseEntity<List<OdontologoDTO>> listaOdontologosDTO(){
        return ResponseEntity.ok(odontologoService.buscarOdontologosDTO());
    }

    @PostMapping
    public ResponseEntity<Odontologo> registrarOdontologoMatricula(@RequestBody Odontologo odontologo){
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(odontologo.getMatricula());
        if(odontologoBuscado.isPresent()){
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Odontologo> actualizarOdontologo(@RequestBody Odontologo odontologo) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorId(odontologo.getId());
        if(odontologoBuscado.isEmpty()){
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }else {
            return ResponseEntity.ok(odontologoService.guardarOdontologo(odontologo));
        }
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<Optional<Odontologo>> buscarPorMatricula(@PathVariable String matricula) throws ResourceNotFoundException {
        Optional<Odontologo> odontologoBuscado = odontologoService.buscarPorMatricula(matricula);
        if(odontologoBuscado.isEmpty()){
            return ResponseEntity.ok(odontologoBuscado);
        }else {
            throw new ResourceNotFoundException("Odontologo no encontrado");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarOdontologo(@PathVariable Long id) throws ResourceNotFoundException{
        Optional <Odontologo> odontologoBuscado = odontologoService.buscarPorId(id);
        if(odontologoBuscado.isPresent()){
            odontologoService.borrarOdontologo(id);
            return ResponseEntity.ok().build();
        } else {
            throw  new ResourceNotFoundException("Odontologo no encontrado");
        }
    }
}
