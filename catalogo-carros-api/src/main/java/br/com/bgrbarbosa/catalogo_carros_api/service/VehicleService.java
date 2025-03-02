package br.com.bgrbarbosa.catalogo_carros_api.service;


import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import java.util.List;

public interface VehicleService {

    Vehicle insert(Vehicle vehicle);

    List<Vehicle> findAll();

    Vehicle findById(Long id);

    void delete(Long id);

    Vehicle update(Vehicle vehicle);

}
