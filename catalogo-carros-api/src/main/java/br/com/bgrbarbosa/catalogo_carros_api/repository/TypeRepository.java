package br.com.bgrbarbosa.catalogo_carros_api.repository;


import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface TypeRepository extends JpaRepository<Type, Long> {

    @Query("SELECT t FROM Type t WHERE t.type = :type")
    Optional<Type> existByType(String type);
}
