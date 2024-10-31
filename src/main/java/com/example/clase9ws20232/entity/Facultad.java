package com.example.clase9ws20232.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "categories")
@Getter
@Setter
@JsonIgnoreProperties(value = {"picture"})
public class Facultad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFacultad", nullable = false)
    private Integer idFacultad;

    @Column(name = "nombreFacultad", nullable = false, length = 30)
    private String nombreFacultad;


}