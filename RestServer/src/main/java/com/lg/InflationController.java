package com.lg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/inflations")
public class InflationController {

    @Autowired
    private InflationRepository repository;

    @GetMapping
    public List<Inflation> getAllInflations() {
        return repository.findAll();
    }

    @PostMapping
    public Inflation addInflation(@RequestBody Inflation inflation) {
        if (repository.existsById(inflation.getYear())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Inflation for this year already exists.");
        }
        return repository.save(inflation);
    }

    @DeleteMapping("/{year}")
    public ResponseEntity<Void> deleteInflation(@PathVariable int year) {
        if (repository.existsById(year)) {
            repository.deleteById(year);
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

}