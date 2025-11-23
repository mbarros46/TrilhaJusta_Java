package com.trilhajusta.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final MessageSource messageSource;

    public GlobalExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest req) {
        var locale = LocaleContextHolder.getLocale();
        List<String> details = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(err -> messageSource.getMessage(err, locale))
                .toList();
        String title = messageSource.getMessage("error.validation.title", null, LocaleContextHolder.getLocale());
        String message = messageSource.getMessage("error.validation", null, LocaleContextHolder.getLocale());
        ApiError err = new ApiError(Instant.now(), req.getRequestURI(), "VALIDATION_ERROR",
                message, details);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ApiError> handleResponseStatus(ResponseStatusException ex, HttpServletRequest req) {
        var locale = LocaleContextHolder.getLocale();
        String title = messageSource.getMessage("error.generic.title", null, ex.getStatusCode().toString(), locale);
        ApiError body = new ApiError(Instant.now(), req.getRequestURI(), ex.getStatusCode().toString(), ex.getReason(), List.of());
        return ResponseEntity.status(ex.getStatusCode()).body(body);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> handleDataIntegrity(DataIntegrityViolationException ex, HttpServletRequest req) {
        var locale = LocaleContextHolder.getLocale();
        String title = messageSource.getMessage("error.data.integrity.title", null, "Data integrity violation", locale);
        ApiError body = new ApiError(Instant.now(), req.getRequestURI(), "DATA_INTEGRITY_VIOLATION", title, List.of());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(body);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiError> handleRuntime(RuntimeException ex, HttpServletRequest req) {
        var locale = LocaleContextHolder.getLocale();
        String title = messageSource.getMessage("error.generic.title", null, LocaleContextHolder.getLocale());
        String message = messageSource.getMessage("error.generic", null, ex.getMessage(), LocaleContextHolder.getLocale());
        ApiError err = new ApiError(Instant.now(), req.getRequestURI(), "ERROR",
                message, List.of());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
    }
}
