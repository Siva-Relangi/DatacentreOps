package com.datacentreops.security;

import com.datacentreops.iam.entity.*;
import com.datacentreops.iam.service.AuditLogService;
import com.datacentreops.iam.service.UserService;
import com.datacentreops.security.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final AuditLogService auditLogService;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(UserService userService,
                          AuditLogService auditLogService,
                          AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.userService = userService;
        this.auditLogService = auditLogService;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public AuthResponse register(@Valid @RequestBody RegisterRequest req) {

        if (userService.existsByEmail(req.email())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");
        }

        User user = new User();
        user.setName(req.name());
        user.setEmail(req.email());
        user.setPassword(req.password());
        user.setRole(Role.COLO_CUSTOMER);   //  ENUM
        user.setPhone(req.phone());
        user.setOrganisationId(req.organisationId());
        user.setDataCentreId(req.dataCentreId());
        user.setStatus(UserStatus.ACTIVE);  //  ENUM

        User saved = userService.create(user);
        audit(saved.getUserId(), AuditAction.REGISTER);

        String token = jwtService.generateToken(
                saved.getEmail(),
                saved.getRole().name(),   //  IMPORTANT
                saved.getUserId()
        );

        return AuthResponse.bearer(token,
                saved.getUserId(),
                saved.getEmail(),
                saved.getRole().name());
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest req) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.email(), req.password()));
        } catch (BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,
                    "Invalid email or password");
        }

        User user = userService.findByEmail(req.email());

        audit(user.getUserId(), AuditAction.LOGIN);

        String token = jwtService.generateToken(
                user.getEmail(),
                user.getRole().name(),
                user.getUserId()
        );

        return AuthResponse.bearer(token,
                user.getUserId(),
                user.getEmail(),
                user.getRole().name());
    }

    private void audit(Long userId, AuditAction action) {

        AuditLog log = new AuditLog();
        log.setUserId(userId);
        log.setAction(action);
        log.setEntityType(EntityType.USER);
        log.setRecordId(userId);

        auditLogService.create(log);
    }
}
