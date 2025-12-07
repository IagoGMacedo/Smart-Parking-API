package br.ufrn.imd.smartparking.smartparking.controller;

import java.util.List;
import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.ufrn.imd.smartparking.smartparking.controller.dto.CreateUserDto;
import br.ufrn.imd.smartparking.smartparking.entities.Role;
import br.ufrn.imd.smartparking.smartparking.entities.User;
import br.ufrn.imd.smartparking.smartparking.repository.RoleRepository;
import br.ufrn.imd.smartparking.smartparking.repository.UserRepository;
import br.ufrn.imd.smartparking.smartparking.service.CurrentUserService;
import lombok.RequiredArgsConstructor;


@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final CurrentUserService currentUserService;

    @GetMapping("/me")    
    public String getMethodName() {

        var user = currentUserService.getCurrentUser();

        return new StringBuilder()
                .append("Username: ")
                .append(user.getUsername())
                .append(" | Roles: ")
                .append(user.getRoles().stream().map(Role::getName).toList())
                .toString();
    }
    

    @PostMapping("/users")
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {

        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "username already exists");
        }

        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setRoles(Set.of(basicRole));

        userRepository.save(user);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/users")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity<List<User>> listUsers() {
        var users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }
}
