package br.com.bgrbarbosa.catalogo_carros_api.service.impl;


import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import br.com.bgrbarbosa.catalogo_carros_api.repository.VehicleRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.VehicleService;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import br.com.bgrbarbosa.catalogo_carros_api.specification.filter.VehicleFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Override
    public Vehicle insert(Vehicle vehicle) {
        return repository.save(vehicle);
    }

    @Override
    public List<Vehicle> findAll(Pageable page, VehicleFilter filter) {
        return repository.findAll(filter.toSpecification());
    }

    @Override
    public Vehicle findById(Long id) {
        return repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!")
        );
    }

    @Override
    public void delete(Long id) {
        Vehicle aux = repository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        repository.deleteById(id);
    }

    @Override
    public Vehicle update(Vehicle vehicle) {
        Vehicle aux = repository.findById(vehicle.getId()).orElseThrow(
                () -> new ResourceNotFoundException("Resource not found!"));
        aux.setCor(vehicle.getCor());
        aux.setFuel(vehicle.getFuel());
        aux.setTransmission(vehicle.getTransmission());
        aux.setMileage(vehicle.getMileage());
        aux.setItems(vehicle.getItems());
        aux.setModel(vehicle.getModel());
        aux.setPrice(vehicle.getPrice());
        aux.setYearOfManufacture(vehicle.getYearOfManufacture());
        aux.setPlate(vehicle.getPlate());
        return repository.save(aux);
    }

}
