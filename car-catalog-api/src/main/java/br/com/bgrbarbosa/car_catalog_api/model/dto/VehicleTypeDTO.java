package br.com.bgrbarbosa.car_catalog_api.model.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


public record VehicleTypeDTO(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Size(min = 3, max = 50, message = "{size.message}")
        String type) {
}
