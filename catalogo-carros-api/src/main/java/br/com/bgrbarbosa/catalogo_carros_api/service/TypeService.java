package br.com.bgrbarbosa.catalogo_carros_api.service;





import br.com.bgrbarbosa.catalogo_carros_api.model.Type;

import java.util.List;

public interface TypeService {

    Type insert(Type tipo);

    List<Type> findAll();

    Type findById(Long id);

    void delete(Long id);

    Type update(Type type);

    Boolean existType(Type type);
}
