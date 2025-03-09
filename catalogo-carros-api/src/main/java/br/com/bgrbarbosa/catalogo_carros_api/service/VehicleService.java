package br.com.bgrbarbosa.catalogo_carros_api.service;


import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import br.com.bgrbarbosa.catalogo_carros_api.specification.filter.VehicleFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VehicleService {

    Vehicle insert(Vehicle vehicle);

    List<Vehicle> findAll(Pageable page, VehicleFilter filter);

    Vehicle findById(Long id);

    void delete(Long id);

    Vehicle update(Vehicle vehicle);


}
