package br.com.bgrbarbosa.car_catalog_api.service;



import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;

import java.util.List;

public interface VehicleTypeService {

    VehicleType insert(VehicleType tipo);

    List<VehicleType> findAll();

    VehicleType findById(Long id);

    void delete(Long id);

    VehicleType update(VehicleType type);

    Boolean existType(VehicleType type);
}
