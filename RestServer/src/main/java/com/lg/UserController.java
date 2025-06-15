package com.lg;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepo;
    private final RoleRepository roleRepo;
    private final PasswordEncoder encoder;

    public UserController(UserRepository ur, RoleRepository rr, PasswordEncoder pe) {
        this.userRepo = ur;
        this.roleRepo = rr;
        this.encoder = pe;
    }

    @PostMapping
    public ResponseEntity<?> register(@RequestBody User dto) {
        if (userRepo.existsByLogin(dto.getLogin())) {
            return ResponseEntity.badRequest().body("Login już zajęty");
        }
        dto.setPassword(encoder.encode(dto.getPassword()));
        Role userRole = roleRepo.findByName("User")
                .orElseGet(() -> roleRepo.save(new Role("User")));
        dto.addRole(userRole);
        User saved = userRepo.save(dto);
        return ResponseEntity.ok(saved);
    }
}
