package br.com.bgrbarbosa.catalogo_carros_api.model.dto;


import br.com.bgrbarbosa.catalogo_carros_api.model.UserRole;

public record RegisterDTO(String login, String password, UserRole role) {
}
