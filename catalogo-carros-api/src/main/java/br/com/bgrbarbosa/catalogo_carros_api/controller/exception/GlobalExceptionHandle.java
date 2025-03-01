package br.com.bgrbarbosa.catalogo_carros_api.controller.exception;





import br.com.bgrbarbosa.catalogo_carros_api.controller.exception.dto.ErrorResponse;
import br.com.bgrbarbosa.catalogo_carros_api.controller.exception.dto.Errors;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandle {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> dataIntegrationViolationException(DataIntegrityViolationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError();
        err.setPath(request.getRequestURI());
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Registration is being used by another!");
        log.error("Registration is being used by another! - " + "Path: " + err.getPath());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError();
        err.setPath(request.getRequestURI());
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Notification not found!!");
        log.error("Notification not found! - " + "Path: " + err.getPath());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(IllegalArgument.class)
    public ResponseEntity<StandardError> illegalArgument(IllegalArgument e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError();
        err.setPath(request.getRequestURI());
        err.setTimestamp(Instant.now());
        err.setStatus(status.value());
        err.setError("Vehicle type already exists");
        log.error("Vehicle type already exists - " + "Path: " + err.getPath());
        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e){

        log.error("Validation error: {} ", e.getMessage());
        List<FieldError> fieldErrors = e.getFieldErrors();
        List<Errors> listErros = fieldErrors
                .stream()
                .map(ev -> new Errors(ev.getField(), ev.getDefaultMessage()))
                .collect(Collectors.toList());

        return new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Erro de validação.",
                listErros);
    }
}
