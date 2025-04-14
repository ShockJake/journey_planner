package io.jp.security;

import io.jp.services.UserService.UserAlreadyRegisteredException;
import io.jp.services.UserService.UserUnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;


import static io.jp.api.WebConstants.MESSAGE_KEY;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionsHandler {
    private Map<String, String> buildResponse(final String message, final HttpStatus code) {
        return Map.of("errorCode", String.valueOf(code.value()), "reason", code.getReasonPhrase(),
                "errorType", code.series().name(), MESSAGE_KEY, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handle(final Exception ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(buildResponse(ex.getMessage(), INTERNAL_SERVER_ERROR), INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyRegisteredException(UserAlreadyRegisteredException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(buildResponse("User already exists", BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(UserUnauthorizedException.class)
    public ResponseEntity<Map<String, String>> handleUserUnauthorizedException(final UserAlreadyRegisteredException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(buildResponse("Bad credentials provided", UNAUTHORIZED), UNAUTHORIZED);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleUsernameNotFoundException(final UsernameNotFoundException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(buildResponse("User not found", BAD_REQUEST), BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, String>> handleAccessDeniedException(final AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(buildResponse("Access denied", UNAUTHORIZED), UNAUTHORIZED);
    }

    @ExceptionHandler(BadDataException.class)
    public ResponseEntity<Map<String, String>> handleBadDataException(final BadDataException e) {
        log.error(e.getMessage(), e);
        return new ResponseEntity<>(buildResponse("Bad data provided: %s".formatted(e.getMessage()), BAD_REQUEST), BAD_REQUEST);
    }

    public static class BadDataException extends RuntimeException {
        public BadDataException(String message) {
            super(message);
        }
    }
}
