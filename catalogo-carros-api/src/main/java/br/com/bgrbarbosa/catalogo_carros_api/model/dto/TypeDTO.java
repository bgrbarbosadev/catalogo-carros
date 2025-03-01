package br.com.bgrbarbosa.catalogo_carros_api.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public record TypeDTO(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Size(min = 3, max = 50, message = "{size.message}")
        String type) {
}
