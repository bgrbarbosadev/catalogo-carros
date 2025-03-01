package br.com.bgrbarbosa.catalogo_carros_api.controller.mapper;


import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ManufacturerDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ManuFacturerMapper {

    Manufacturer parseToEntity(ManufacturerDTO dto);

    ManufacturerDTO parseToDto(Manufacturer entity);

    List<ManufacturerDTO> parseToListDTO(List<Manufacturer>list);
}
