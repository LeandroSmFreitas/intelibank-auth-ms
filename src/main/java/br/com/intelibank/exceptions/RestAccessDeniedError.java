package br.com.intelibank.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class RestAccessDeniedError extends RuntimeException {
    private String message;
}
