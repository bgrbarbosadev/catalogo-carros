package br.com.bgrbarbosa.catalogo_carros_api.controller.mapper;


import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle parseToEntity(VehicleDTO dto);

    VehicleDTO parseToDto(Vehicle entity);

    List<VehicleDTO> parseToListDTO(List<Vehicle>list);
}
