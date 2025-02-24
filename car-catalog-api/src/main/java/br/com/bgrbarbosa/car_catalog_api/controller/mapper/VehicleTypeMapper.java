package br.com.bgrbarbosa.car_catalog_api.controller.mapper;

import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import br.com.bgrbarbosa.car_catalog_api.model.dto.VehicleTypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleTypeMapper {

    VehicleType parseToEntity(VehicleTypeDTO dto);

    VehicleTypeDTO parseToDto(VehicleType entity);

    List<VehicleTypeDTO> parseToListDTO(List<VehicleType>list);
}
