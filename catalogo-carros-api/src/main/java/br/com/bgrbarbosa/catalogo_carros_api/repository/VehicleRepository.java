package br.com.bgrbarbosa.catalogo_carros_api.repository;

import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface VehicleRepository extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {

}
