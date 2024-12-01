package br.com.intelibank.exceptions;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExeceptionHandler {

    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {

        List<RestFieldErrors> fieldErrors = ex.getFieldErrors().stream()
                .map(f -> new RestFieldErrors(
                        f.getObjectName(),
                        f.getField(),
                        f.getDefaultMessage() != null ? f.getDefaultMessage() : f.getCode())
                ).collect(Collectors.toList());

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                "Values cant be empty",
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ResponseBody
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> eventInvalidType(HttpMessageNotReadableException ex) {
        String message = "Invalid type provided. Please check your input data.";
        String fieldName = "";

        if (ex.getCause() instanceof MismatchedInputException) {
            MismatchedInputException mismatchedInputException = (MismatchedInputException) ex.getCause();
            fieldName = mismatchedInputException.getPath().stream()
                    .map(JsonMappingException.Reference::getFieldName)
                    .findFirst()
                    .orElse("Unknown field");

            String expectedType = mismatchedInputException.getTargetType().getSimpleName();
            message = String.format("The field '%s' should be of type '%s'.", fieldName, expectedType);
        }

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                message,
                List.of(new RestFieldErrors("requestBody", fieldName, message))
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RestNotFound.class)
    @ResponseBody
    public ResponseEntity<Object> eventNotFound(RestNotFound ex) {

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }

    @ExceptionHandler(RestAccessDeniedError.class)
    @ResponseBody
    public ResponseEntity<Object> eventAccessDenied(RestAccessDeniedError ex) {

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.FORBIDDEN,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessage);
    }

    @ExceptionHandler(RestAlreadyExist.class)
    @ResponseBody
    public ResponseEntity<Object> eventAlreadyExist(RestAlreadyExist ex) {

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.CONFLICT,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorMessage);
    }

    @ExceptionHandler(RestErrorActivate.class)
    @ResponseBody
    public ResponseEntity<Object> eventErrorActivate(RestErrorActivate ex) {

        RestErrorMessage errorMessage = new RestErrorMessage(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage);
    }


}
