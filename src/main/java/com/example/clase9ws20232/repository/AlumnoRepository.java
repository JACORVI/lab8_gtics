package com.example.clase9ws20232.repository;

import com.example.clase9ws20232.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer>{

    @Query(value = "SELECT * FROM estudiante WHERE facultad_idfacultad = :facultadId ORDER BY GPA DESC", nativeQuery = true)
    List<Alumno> findEstudiantesByGpaAndFacultad(@Param("facultadId") Integer facultadId);

    // Obtener todos los estudiantes ordenados por GPA sin filtrar por facultad
    @Query(value = "SELECT * FROM estudiante ORDER BY GPA DESC", nativeQuery = true)
    List<Alumno> findAllByOrderByGpaDesc();

}

