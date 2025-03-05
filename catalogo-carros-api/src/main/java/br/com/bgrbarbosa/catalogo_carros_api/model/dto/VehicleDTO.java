package br.com.bgrbarbosa.catalogo_carros_api.model.dto;

import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record VehicleDTO(
        Long id,

        @NotBlank(message = "{not.blank.message}")
        @Size(min = 7, max = 7, message = "{size.message}")
        String plate,

        @NotBlank(message = "{not.blank.message}")
        @Size(min = 2, max = 4, message = "{size.message}")
        String yearOfManufacture,

        @NotNull
        Double price,

        @NotBlank(message = "{not.blank.message}")
        @Size(min = 1, max = 7, message = "{size.message}")
        String mileage,

        @NotBlank(message = "{not.blank.message}")
        String items,

        @NotNull
        EnumTransmission transmission,

        @NotNull
        EnumFuel fuel,

        @NotBlank(message = "{not.blank.message}")
        @Size(min = 3, max = 50, message = "{size.message}")
        String cor,

        Model model
) { }
