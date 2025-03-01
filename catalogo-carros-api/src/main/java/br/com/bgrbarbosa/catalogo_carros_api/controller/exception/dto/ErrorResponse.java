package br.com.bgrbarbosa.catalogo_carros_api.controller.exception.dto;

import org.springframework.http.HttpStatus;

import java.lang.Error;
import java.util.List;

public record ErrorResponse(int status, String message, List<Errors> erros) {

    public static ErrorResponse responseDefault(String menssage){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), menssage, List.of());
    }
}
