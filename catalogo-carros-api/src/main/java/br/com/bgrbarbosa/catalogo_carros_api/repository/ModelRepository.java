package br.com.bgrbarbosa.catalogo_carros_api.repository;


import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ModelRepository extends JpaRepository<Model, Long> {

    @Query("SELECT m FROM Model m WHERE m.model = :model")
    Optional<Model> existByType(String model);
}
