package train_ticket_api.exception;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import org.springframework.http.converter.HttpMessageNotReadableException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(
            ResourceNotFoundException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(BusinessValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleBusinessValidation(
            BusinessValidationException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNoResourceFound(
            NoResourceFoundException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Endpoint not found");
        return response;
    }

    @ExceptionHandler(DuplicateResourceException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleDuplicateResource(
            DuplicateResourceException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Map<String, String> handleInvalidCredentials(
            InvalidCredentialsException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", ex.getMessage());
        return response;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleHttpMessageNotReadable(
            HttpMessageNotReadableException ex
    ) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Invalid request format");

        Throwable cause = ex.getCause();
        if (cause instanceof com.fasterxml.jackson.databind.JsonMappingException jsonException) {
            if (!jsonException.getPath().isEmpty()) {
                response.put("field", jsonException.getPath().get(0).getFieldName());
            }
        }

        return response;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidationException(
            MethodArgumentNotValidException ex
    ) {

        Map<String, String> errors = new LinkedHashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.putIfAbsent(
                                error.getField(),
                                error.getDefaultMessage()
                        ));

        return errors;
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleException(
            Exception ex
    ) {
        ex.printStackTrace();   // <-- sementara untuk debugging
        Map<String, String> response = new LinkedHashMap<>();
        response.put("message", "Internal server error");
        return response;
    }
}