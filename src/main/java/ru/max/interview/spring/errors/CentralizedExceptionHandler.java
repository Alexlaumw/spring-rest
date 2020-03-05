package ru.max.interview.spring.errors;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class CentralizedExceptionHandler {


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleError(MethodArgumentNotValidException ex) {
        log.error("Request validation error:", ex);
        return new ErrorResponse(HttpStatus.BAD_GATEWAY.value(), "There is a NaN in request string");
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.BAD_GATEWAY)
    public ErrorResponse handleError(Exception ex) {
        log.error("Unexpected error:", ex);
        return new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                "Internal application error: " + ex.getLocalizedMessage()
        );
    }
}
