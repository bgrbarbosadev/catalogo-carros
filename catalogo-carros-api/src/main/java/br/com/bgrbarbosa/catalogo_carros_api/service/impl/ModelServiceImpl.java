package br.com.bgrbarbosa.catalogo_carros_api.service.impl;



import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ModelRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.ModelService;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository repository;

    @Override
    public Model insert(Model model) {
        if (existType(model)) {
            throw new IllegalArgument("{illegal-arqument-exception}");
        }
        return repository.save(model);
    }

    @Override
    public List<Model> findAll() {
        return repository.findAll();
    }

    @Override
    public Model findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!")
        );
    }

    @Override
    public void delete(Long id) {
        Model aux = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        repository.deleteById(id);
    }

    @Override
    public Model update(Model model) {
        Model aux = repository.findById(model.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        aux.setModel(model.getModel());
        return repository.save(aux);
    }

    @Override
    public Boolean existType(Model type) {
        Optional<Model> aux = repository.existByType(type.getModel());
        return (aux.isPresent()) ? true : false;
    }
}
