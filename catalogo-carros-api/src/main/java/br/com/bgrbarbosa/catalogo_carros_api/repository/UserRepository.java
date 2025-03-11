package br.com.bgrbarbosa.catalogo_carros_api.repository;

import br.com.bgrbarbosa.catalogo_carros_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    UserDetails findByLogin(String login);
}
