package com.lg;

import com.lg.User;
import com.lg.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class ProfileController {

    private final UserRepository userRepo;

    public ProfileController(UserRepository repo) {
        this.userRepo = repo;
    }

    @GetMapping
    public ResponseEntity<?> whoami(@AuthenticationPrincipal UserDetails ud) {
        User user = userRepo.findByLogin(ud.getUsername())
                .orElseThrow();
        // Usuń hasło z odpowiedzi:
        user.setPassword(null);
        return ResponseEntity.ok(user);
    }
}
