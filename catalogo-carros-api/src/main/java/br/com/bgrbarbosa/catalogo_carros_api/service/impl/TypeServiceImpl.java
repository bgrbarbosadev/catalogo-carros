package br.com.bgrbarbosa.catalogo_carros_api.service.impl;



import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.TypeService;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TypeServiceImpl implements TypeService {

    private final TypeRepository repository;

    @Override
    public Type insert(Type type) {
        if (existType(type)) {
            throw new IllegalArgument("{illegal-arqument-exception}");
        }
        return repository.save(type);
    }

    @Override
    public List<Type> findAll() {
        return repository.findAll();
    }

    @Override
    public Type findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!")
        );
    }

    @Override
    public void delete(Long id) {
        Type aux = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        repository.deleteById(id);
    }

    @Override
    public Type update(Type type) {
        Type aux = repository.findById(type.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        aux.setType(type.getType());
        return repository.save(aux);
    }

    @Override
    public Boolean existType(Type type) {
        Optional<Type> aux = repository.existByType(type.getType());
        return (aux.isPresent()) ? true : false;
    }
}
