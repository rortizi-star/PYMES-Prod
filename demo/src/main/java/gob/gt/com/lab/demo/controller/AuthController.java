package gob.gt.com.lab.demo.controller;

import gob.gt.com.lab.demo.dto.AuthRequest;
import gob.gt.com.lab.demo.security.JwtUtil;
import gob.gt.com.lab.demo.entity.User;
import gob.gt.com.lab.demo.entity.Role;
import gob.gt.com.lab.demo.service.UserService;
import gob.gt.com.lab.demo.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody AuthRequest request) {
        Map<String, Object> response = new HashMap<>();
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
            String token = jwtUtil.generateToken(request.getUsername());
            response.put("token", token);
            response.put("success", true);
        } catch (AuthenticationException e) {
            response.put("success", false);
            response.put("error", "Invalid username or password");
        }
        return response;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, Object> req) {
        try {
            String username = (String) req.get("username");
            String password = (String) req.get("password");
            String fullName = (String) req.getOrDefault("fullName", "");
            String email = (String) req.getOrDefault("email", "");

            if (username == null || password == null) {
                return ResponseEntity.badRequest().body("Username and password are required");
            }


            if (userService.getUserByUsername(username).isPresent()) {
                return ResponseEntity.badRequest().body("Username already exists");
            }

            User user = new User();
            user.setUsername(username);
            user.setPassword(password); // Password should be encoded in UserService
            user.setFullName(fullName);
            user.setEmail(email);
            user.setIsActive(true);
            user.setRoles(new java.util.HashSet<>());

            // Assign ADMIN role if first user, else USER

            Role adminRole = roleService.getRoleByName("ADMIN").orElse(null);
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ADMIN");
                adminRole = roleService.saveRole(adminRole);
            }
            user.getRoles().add(adminRole);

            userService.saveUser(user);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Registration failed: " + e.getMessage());
        }
    }
}
