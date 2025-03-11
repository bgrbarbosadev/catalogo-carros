package br.com.bgrbarbosa.catalogo_carros_api.controller;



import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.ManuFacturerMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ManufacturerDTO;
import br.com.bgrbarbosa.catalogo_carros_api.service.ManufacturerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/manufacturer")
public class ManufacturerController {

    private final ManufacturerService service;

    private final ManuFacturerMapper mapper;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ManufacturerDTO> insert(@RequestBody @Valid ManufacturerDTO dto){
        Manufacturer result = service.insert(mapper.parseToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.parseToDto(result));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
     public ResponseEntity<List<ManufacturerDTO>> searchAll(){
        List<ManufacturerDTO> listDTO = mapper.parseToListDTO(service.findAll());
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<ManufacturerDTO> searchById(@PathVariable("id") Long id){
        ManufacturerDTO result = mapper.parseToDto(service.findById(id));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.accepted().body("Successfully deleted!!");
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ManufacturerDTO> update(@RequestBody @Valid ManufacturerDTO dto){
        Manufacturer aux = mapper.parseToEntity(dto);
        return ResponseEntity.ok().body(mapper.parseToDto(service.update(aux)));
    }

}
