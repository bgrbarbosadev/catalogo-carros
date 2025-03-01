package br.com.bgrbarbosa.catalogo_carros_api.model.dto;


import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record ManufacturerDTO(

        Long id,

        @NotBlank(message = "{not.blank.message}")
        @Size(min = 3, max = 50, message = "{size.message}")
        String manufacturer,

        List<Model> model) {
}
