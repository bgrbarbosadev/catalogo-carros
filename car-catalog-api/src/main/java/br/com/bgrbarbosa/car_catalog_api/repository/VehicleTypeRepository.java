package br.com.bgrbarbosa.car_catalog_api.repository;

import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface VehicleTypeRepository extends JpaRepository<VehicleType, Long> {

    @Query("SELECT v FROM VehicleType v WHERE v.type = :type")
    Optional<VehicleType> existByType(String type);
}
