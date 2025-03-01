package br.com.bgrbarbosa.catalogo_carros_api.service;





import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;

import java.util.List;

public interface ManufacturerService {

    Manufacturer insert(Manufacturer manufacturer);

    List<Manufacturer> findAll();

    Manufacturer findById(Long id);

    void delete(Long id);

    Manufacturer update(Manufacturer manufacturer);

    Boolean existByManufacturer(Manufacturer manufacturer);
}
