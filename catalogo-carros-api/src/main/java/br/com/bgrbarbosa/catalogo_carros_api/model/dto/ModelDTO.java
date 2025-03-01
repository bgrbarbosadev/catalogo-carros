package br.com.bgrbarbosa.catalogo_carros_api.model.dto;



import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public record ModelDTO(
        Long id,
        @NotBlank(message = "{not.blank.message}")
        @Size(min = 3, max = 50, message = "{size.message}")
        String model,
        @NotNull
        Manufacturer manufacturer,
        @NotNull
        Type type) {
}
