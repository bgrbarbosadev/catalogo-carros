package br.com.bgrbarbosa.car_catalog_api.controller.exception.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

public record ErrorResponse(int status, String message, List<Error> erros) {

    public static ErrorResponse responseDefault(String menssage){
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), menssage, List.of());
    }
}
