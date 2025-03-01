package br.com.bgrbarbosa.catalogo_carros_api.controller.mapper;


import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ModelDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    Model parseToEntity(ModelDTO dto);

    ModelDTO parseToDto(Model entity);

    List<ModelDTO> parseToListDTO(List<Model>list);
}
