package br.com.bgrbarbosa.catalogo_carros_api.controller;


import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.TypeMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.TypeDTO;
import br.com.bgrbarbosa.catalogo_carros_api.service.TypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/type")
public class TypeController {

    private final TypeService service;

    private final TypeMapper mapper;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TypeDTO> insert(@RequestBody @Valid TypeDTO dto){
        Type result = service.insert(mapper.parseToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.parseToDto(result));
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
     public ResponseEntity<List<TypeDTO>> searchAll(){
        List<TypeDTO> listDTO = mapper.parseToListDTO(service.findAll());
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<TypeDTO> searchById(@PathVariable("id") Long id){
        Type result = service.findById(id);
        return ResponseEntity.ok(mapper.parseToDto(result));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.accepted().body("Successfully deleted!!");
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<TypeDTO> update(@RequestBody @Valid TypeDTO dto){
        Type aux = mapper.parseToEntity(dto);
        return ResponseEntity.ok().body(mapper.parseToDto(service.update(aux)));
    }

}
