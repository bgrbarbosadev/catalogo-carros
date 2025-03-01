package br.com.bgrbarbosa.catalogo_carros_api.repository;


import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long> {

    @Query("SELECT m FROM Manufacturer m WHERE m.manufacturer = :manufacturer")
    Optional<Manufacturer> existByManufacturer(String manufacturer);
}
