package com.lg;

import com.lg.InflationRepository;
import com.lg.Inflation;
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
        Inflation saved = repository.save(inflation);
        Main.runPythonScript();
        return saved;
    }

    @DeleteMapping("/{year}")
    public ResponseEntity<Void> deleteInflation(@PathVariable int year) {
        if (repository.existsById(year)) {
            repository.deleteById(year);
            Main.runPythonScript();
            return ResponseEntity.noContent().build(); // HTTP 204
        } else {
            return ResponseEntity.notFound().build(); // HTTP 404
        }
    }

    @PutMapping("/{year}")
    public ResponseEntity<Inflation> updateInflation(@PathVariable int year, @RequestBody Inflation updatedInflation) {
        return repository.findById(year)
                .map(existing -> {
                    existing.setRate(updatedInflation.getRate()); // aktualizacja tylko 'rate'
                    Inflation saved = repository.save(existing);
                    Main.runPythonScript();
                    return ResponseEntity.ok(saved);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}