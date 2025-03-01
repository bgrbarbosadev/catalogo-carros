package br.com.bgrbarbosa.catalogo_carros_api.service;

import br.com.bgrbarbosa.catalogo_carros_api.model.Model;

import java.util.List;

public interface ModelService {

    Model insert(Model model);

    List<Model> findAll();

    Model findById(Long id);

    void delete(Long id);

    Model update(Model model);

    Boolean existType(Model model);
}
