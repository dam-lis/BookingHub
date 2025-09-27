package dev.damianlis.identity.rest;

import dev.damianlis.identity.IdentityFacade;
import dev.damianlis.identity.dto.IdentityActivateResult;
import dev.damianlis.identity.dto.IdentityRegisterResult;
import dev.damianlis.identity.exception.ActivationTokenNotFoundException;
import dev.damianlis.identity.exception.IdentityExistException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/identity")
class IdentityRestController {

    private final IdentityFacade identityFacade;

    IdentityRestController(IdentityFacade identityFacade) {
        this.identityFacade = identityFacade;
    }

    @PostMapping("/register")
    ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        IdentityRegisterResult result = identityFacade.register(request.email(), request.name(), request.rawPassword());
        RegisterResponse response = new RegisterResponse(result.id(), IdentityStatus.valueOf(result.status().name()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/activate")
    ResponseEntity<ActivateResponse> activate(@RequestBody ActivateRequest request) {
        IdentityActivateResult result = identityFacade.activate(request.activationToken());
        ActivateResponse response = new ActivateResponse(result.id(), IdentityStatus.valueOf(result.status().name()));
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(exception = IdentityExistException.class)
    ResponseEntity<ErrorResponse> handleIdentityExistException(HttpServletRequest request, Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT.value()).body(errorResponse);
    }

    @ExceptionHandler(exception = ActivationTokenNotFoundException.class)
    ResponseEntity<ErrorResponse> handleActivationTokenNotFoundException(HttpServletRequest request, Exception exception) {
        ErrorResponse errorResponse = new ErrorResponse(exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND.value()).body(errorResponse);
    }

    record RegisterRequest(String email, String name, String rawPassword)  {}

    record RegisterResponse(UUID id, IdentityStatus status) {}

    record ActivateRequest(String activationToken) {}

    record ActivateResponse(UUID id, IdentityStatus status) {}

    enum IdentityStatus {
        PENDING,
        ACTIVE
    }

    record ErrorResponse(String message) {}


}
