package br.com.bgrbarbosa.catalogo_carros_api.controller;



import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.ModelMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ModelDTO;
import br.com.bgrbarbosa.catalogo_carros_api.service.ModelService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/model")
public class ModelController {

    private final ModelService service;

    private final ModelMapper mapper;

    @PostMapping
    public ResponseEntity<ModelDTO> insert(@RequestBody @Valid ModelDTO dto){
        Model result = service.insert(mapper.parseToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.parseToDto(result));
    }

    @GetMapping
     public ResponseEntity<List<ModelDTO>> searchAll(){
        List<ModelDTO> listDTO = mapper.parseToListDTO(service.findAll());
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModelDTO> searchById(@PathVariable("id") Long id){
        ModelDTO result = mapper.parseToDto(service.findById(id));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.accepted().body("Successfully deleted!!");
    }

    @PutMapping
    public ResponseEntity<ModelDTO> update(@RequestBody @Valid ModelDTO dto){
        Model aux = service.findById(dto.id());
        if (aux.getId() != null) {
            aux.setModel(dto.model());
            aux.setManufacturer(dto.manufacturer());
            return ResponseEntity.ok().body(mapper.parseToDto(service.update(aux)));
        }
        return ResponseEntity.notFound().build();
    }

}
