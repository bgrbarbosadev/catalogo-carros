package br.com.bgrbarbosa.catalogo_carros_api.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StandardError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String path;
    private Instant timestamp;
    private Integer status;
    private String error;
    private String message;
}
