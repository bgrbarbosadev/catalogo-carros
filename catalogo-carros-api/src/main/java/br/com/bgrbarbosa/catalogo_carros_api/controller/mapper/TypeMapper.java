package br.com.bgrbarbosa.catalogo_carros_api.controller.mapper;


import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.TypeDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TypeMapper {

    Type parseToEntity(TypeDTO dto);

    TypeDTO parseToDto(Type entity);

    List<TypeDTO> parseToListDTO(List<Type>list);
}
