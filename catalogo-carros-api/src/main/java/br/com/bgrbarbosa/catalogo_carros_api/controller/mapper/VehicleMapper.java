package br.com.bgrbarbosa.catalogo_carros_api.controller.mapper;


import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

    Vehicle parseToEntity(VehicleDTO dto);

    VehicleDTO parseToDto(Vehicle entity);

    List<VehicleDTO> parseToListDTO(List<Vehicle>list);

    default Page<VehicleDTO> toPageDTO(List<VehicleDTO> list, Pageable pageable) {
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), list.size());
        List<VehicleDTO> pageContent = list.subList(start, end);

        return new PageImpl<>(pageContent, pageable, list.size());
    }


}
