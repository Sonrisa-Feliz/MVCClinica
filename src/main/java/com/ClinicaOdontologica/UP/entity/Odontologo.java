package com.ClinicaOdontologica.UP.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Table(name = "odontologos")
public class Odontologo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column(unique = true, nullable = false)
    private String matricula;
    @OneToMany(mappedBy = "odontologo", fetch = FetchType.LAZY)
    @JsonManagedReference(value="odontologo-turno")
    private Set<Turno> turnos = new HashSet<>();

    public Odontologo(Long id, String nombre, String apellido, String matricula) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.matricula = matricula;
    }

    public Odontologo(String matricula, String apellido, String nombre) {
        this.matricula = matricula;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    public Odontologo() {
    }

    @Override
    public String toString() {
        return "Odontologo{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", matricula='" + matricula + '\'' +
                ", id=" + id +
                '}';
    }
}
