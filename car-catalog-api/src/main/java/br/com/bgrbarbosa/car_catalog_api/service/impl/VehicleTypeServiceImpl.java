package br.com.bgrbarbosa.car_catalog_api.service.impl;


import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import br.com.bgrbarbosa.car_catalog_api.repository.VehicleTypeRepository;
import br.com.bgrbarbosa.car_catalog_api.service.VehicleTypeService;
import br.com.bgrbarbosa.car_catalog_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.car_catalog_api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VehicleTypeServiceImpl implements VehicleTypeService {

    private final VehicleTypeRepository repository;

    @Override
    public VehicleType insert(VehicleType type) {
        if (existType(type)) {
            throw new IllegalArgument("{illegal-arqument-exception}");
        }
        return repository.save(type);
    }

    @Override
    public List<VehicleType> findAll() {
        return repository.findAll();
    }

    @Override
    public VehicleType findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!")
        );
    }

    @Override
    public void delete(Long id) {
        VehicleType aux = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        repository.deleteById(id);
    }

    @Override
    public VehicleType update(VehicleType type) {
        VehicleType aux = repository.findById(type.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        aux.setType(type.getType());
        return repository.save(aux);
    }

    @Override
    public Boolean existType(VehicleType type) {
        Optional<VehicleType> aux = repository.existByType(type.getType());
        return (aux.isPresent()) ? true : false;
    }
}
