package com.lg;

import com.lg.GraduatesRepository;
import com.lg.Graduates;
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
        Graduates saved = repository.save(graduate);
        Main.runPythonScript();
        return saved;
    }

    @DeleteMapping("/{year}")
    public ResponseEntity<Void> deleteGraduate(@PathVariable int year) {
        if (repository.existsById(year)) {
            repository.deleteById(year);
            Main.runPythonScript();
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    @PutMapping("/{year}")
    public ResponseEntity<Graduates> updateGraduate(@PathVariable int year, @RequestBody Graduates updatedGraduates) {
        return repository.findById(year)
                .map(existing -> {
                    existing.setNumber(updatedGraduates.getNumber()); // aktualizacja tylko 'number'
                    Graduates saved = repository.save(existing);
                    Main.runPythonScript();
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}