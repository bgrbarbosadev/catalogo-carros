package br.com.bgrbarbosa.catalogo_carros_api.service.impl;



import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.ManufacturerService;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManufacturerServiceImpl implements ManufacturerService {

    private final ManufacturerRepository repository;

    @Override
    public Manufacturer insert(Manufacturer manufacturer) {
        if (existByManufacturer(manufacturer)) {
            throw new IllegalArgument("{illegal-arqument-exception}");
        }
        return repository.save(manufacturer);
    }

    @Override
    public List<Manufacturer> findAll() {
        return repository.findAll();
    }

    @Override
    public Manufacturer findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!")
        );
    }

    @Override
    public void delete(Long id) {
        Manufacturer aux = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        repository.deleteById(id);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        Manufacturer aux = repository.findById(manufacturer.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        aux.setManufacturer(manufacturer.getManufacturer());
        return repository.save(aux);
    }

    @Override
    public Boolean existByManufacturer(Manufacturer manufacturer) {
        Optional<Manufacturer> aux = repository.existByManufacturer(manufacturer.getManufacturer());
        return (aux.isPresent()) ? true : false;
    }
}
