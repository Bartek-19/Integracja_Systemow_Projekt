package com.lg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/graduates")
public class GraduatesController {

    @Autowired
    private GraduatesRepository repository;

    @GetMapping
    public List<Graduates> getAllGraduates() {
        return repository.findAll();
    }

    @PostMapping
    public Graduates addGraduate(@RequestBody Graduates graduate) {
        if (repository.existsById(graduate.getYear())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Graduate for this year already exists.");
        }
        return repository.save(graduate);
    }

    @DeleteMapping("/{year}")
    public ResponseEntity<Void> deleteGraduate(@PathVariable int year) {
        if (repository.existsById(year)) {
            repository.deleteById(year);
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }
}