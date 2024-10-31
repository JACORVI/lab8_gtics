package com.example.clase9ws20232.controller;

import com.example.clase9ws20232.entity.Alumno;
import com.example.clase9ws20232.repository.AlumnoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/alumnos")
public class AlumnoController {

    private final AlumnoRepository alumnoRepository;

    public AlumnoController(AlumnoRepository alumnoRepository) {
        this.alumnoRepository = alumnoRepository;
    }

    // 1. List Students - Filtered by faculty and ordered by GPA
    @GetMapping("/list")
    public ResponseEntity<List<HashMap<String, Object>>> listAlumnos(
            @RequestParam(value = "facultad", required = false) Integer facultadId) {

        List<Alumno> alumnos;
        if (facultadId != null) {
            alumnos = alumnoRepository.findByFacultadIdOrderByGpaDesc(facultadId);
        } else {
            alumnos = alumnoRepository.findAllByOrderByGpaDesc();
        }

        List<HashMap<String, Object>> responseList = alumnos.stream().map(alumno -> {
            HashMap<String, Object> data = new HashMap<>();
            data.put("id", alumno.getId());
            data.put("name", alumno.getName());
            data.put("gpa", alumno.getGpa());
            data.put("facultad", alumno.getFacultad().getNombreFacultad());
            data.put("creditsCompleted", alumno.getCreditsCompleted());
            return data;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(responseList);
    }

    // 2. Add a Student and calculate position based on GPA
    @PostMapping("/add")
    public ResponseEntity<HashMap<String, Object>> addAlumno(@RequestBody Alumno alumno) {
        alumnoRepository.save(alumno);
        updateRankings();

        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "created");
        response.put("id", alumno.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 3. Update GPA and adjust ranking
    @PutMapping("/{id}/updateGpa")
    public ResponseEntity<HashMap<String, Object>> updateGpa(
            @PathVariable Integer id, @RequestParam("gpa") Double newGpa) {

        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        if (alumnoOpt.isPresent()) {
            Alumno alumno = alumnoOpt.get();
            alumno.setGpa(newGpa);
            alumnoRepository.save(alumno);
            updateRankings();

            HashMap<String, Object> response = new HashMap<>();
            response.put("status", "updated");
            response.put("newGpa", newGpa);
            return ResponseEntity.ok(response);
        } else {
            HashMap<String, Object> response = new HashMap<>();
            response.put("status", "error");
            response.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // 4. Delete a Student and update rankings
    @DeleteMapping("/{id}")
    public ResponseEntity<HashMap<String, Object>> deleteAlumno(@PathVariable Integer id) {
        Optional<Alumno> alumnoOpt = alumnoRepository.findById(id);
        HashMap<String, Object> response = new HashMap<>();

        if (alumnoOpt.isPresent()) {
            alumnoRepository.deleteById(id);
            updateRankings();

            response.put("status", "deleted");
            return ResponseEntity.ok(response);
        } else {
            response.put("status", "error");
            response.put("message", "Student not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    // Helper method to update rankings based on GPA
    private void updateRankings() {
        List<Alumno> alumnos = alumnoRepository.findAllByOrderByGpaDesc();
        // Update any ranking field if necessary or sort order in response
        // Assuming no explicit ranking column in the schema
    }
}
