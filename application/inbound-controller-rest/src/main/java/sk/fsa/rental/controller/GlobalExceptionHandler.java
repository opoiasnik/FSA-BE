package sk.fsa.rental.controller;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import sk.fsa.rental.domain.RentalException;
import sk.fsa.rental.rest.dto.ErrorResponseDto;

import java.time.OffsetDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RentalException.class)
    public ResponseEntity<ErrorResponseDto> handleRentalException(RentalException ex, WebRequest request) {
        LoggerFactory.getLogger(GlobalExceptionHandler.class).warn("Domain error: {}", ex.getMessage());
        return new ResponseEntity<>(
                createError(ex.getType().name(), ex.getMessage()),
                resolveStatus(ex));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(
                createError("VALIDATION", "Invalid request body"),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponseDto> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        LoggerFactory.getLogger(GlobalExceptionHandler.class).warn("Access denied: {}", ex.getMessage());
        return new ResponseEntity<>(
                createError("FORBIDDEN", "Access denied"),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorResponseDto> handleThrowable(Throwable ex, WebRequest request) {
        LoggerFactory.getLogger(GlobalExceptionHandler.class).error("Global error occurred", ex);
        return new ResponseEntity<>(
                createError("INTERNAL_ERROR", "Unexpected internal error"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ErrorResponseDto createError(String type, String message) {
        return new ErrorResponseDto()
                .type(type)
                .message(message)
                .timestamp(OffsetDateTime.now());
    }

    private HttpStatus resolveStatus(RentalException ex) {
        return switch (ex.getType()) {
            case NOT_FOUND -> HttpStatus.NOT_FOUND;
            case UNAUTHORIZED -> HttpStatus.UNAUTHORIZED;
            case FORBIDDEN -> HttpStatus.FORBIDDEN;
            case VALIDATION -> HttpStatus.BAD_REQUEST;
        };
    }
}
