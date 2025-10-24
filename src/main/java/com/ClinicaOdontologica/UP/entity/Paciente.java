package com.ClinicaOdontologica.UP.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column()
    private String  nombre;
    @Column
    private String apellido;
    @Column
    private Integer numeroContacto;
    @Column
    private LocalDate fechaIngreso;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "domicilio_id", referencedColumnName = "id")
    private Domicilio domicilio;
    @Column(unique = true)
    private String email;

    public Paciente(Long id, String nombre, String apellido, Integer numeroContacto, LocalDate fechaIngreso, Domicilio domicilio, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.numeroContacto = numeroContacto;
        this.fechaIngreso = fechaIngreso;
        this.domicilio = domicilio;
        this.email = email;
    }

    public Paciente() {
    }

    public Paciente(String email, Domicilio domicilio, LocalDate fechaIngreso, Integer numeroContacto, String apellido, String nombre) {
        this.email = email;
        this.domicilio = domicilio;
        this.fechaIngreso = fechaIngreso;
        this.numeroContacto = numeroContacto;
        this.apellido = apellido;
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Paciente{" +
                "nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", id=" + id +
                '}';
    }
}
