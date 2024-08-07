package com.wanted.pre_onboarding.advice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.wanted.pre_onboarding.dto.response.ValidationErrorResponse;
import com.wanted.pre_onboarding.dto.response.ValidationResult;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ValidationExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        List<ValidationResult> errors = bindingResult.getFieldErrors()
                .stream()
                .map(fieldError -> ValidationResult.builder()
                        .field(fieldError.getField())
                        .rejectedValue(String.valueOf(fieldError.getRejectedValue()))
                        .message(fieldError.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());
        return ResponseEntity.badRequest().body(new ValidationErrorResponse(errors));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handle(HttpMessageNotReadableException e) {
        if (e.getCause() instanceof InvalidFormatException ex) {

            String fieldName = ex.getPath().get(0).getFieldName();
            String rejectedValue = String.valueOf(ex.getValue());
            ValidationResult errors = ValidationResult.builder()
                    .field(fieldName)
                    .rejectedValue(rejectedValue)
                    .message("Invalid Format")
                    .build();
            return ResponseEntity.badRequest().body(new ValidationErrorResponse(List.of(errors)));
        }

        ValidationResult defaultError = ValidationResult.builder()
                .field("unknown")
                .rejectedValue("unknown")
                .message("Malformed JSON request")
                .build();

        return ResponseEntity.badRequest().body(new ValidationErrorResponse(Collections.singletonList(defaultError)));
    }
}
