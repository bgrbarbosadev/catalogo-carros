package br.com.bgrbarbosa.car_catalog_api.controller;

import br.com.bgrbarbosa.car_catalog_api.controller.mapper.VehicleTypeMapper;
import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import br.com.bgrbarbosa.car_catalog_api.model.dto.VehicleTypeDTO;
import br.com.bgrbarbosa.car_catalog_api.service.VehicleTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicle")
public class VehicleTypeController {

    private final VehicleTypeService service;

    private final VehicleTypeMapper mapper;

    @PostMapping
    public ResponseEntity<VehicleTypeDTO> insert(@RequestBody @Valid VehicleTypeDTO dto){
        VehicleType result = service.insert(mapper.parseToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.parseToDto(result));
    }

    @GetMapping
     public ResponseEntity<List<VehicleTypeDTO>> searchAll(){
        List<VehicleTypeDTO> listDTO = mapper.parseToListDTO(service.findAll());
        return ResponseEntity.ok(listDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehicleTypeDTO> searchById(@PathVariable("id") Long id){
        VehicleType result = service.findById(id);
        return ResponseEntity.ok(mapper.parseToDto(result));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.accepted().body("Successfully deleted!!");
    }

    @PutMapping
    public ResponseEntity<VehicleTypeDTO> update(@RequestBody @Valid VehicleTypeDTO dto){
        VehicleType aux = mapper.parseToEntity(dto);
        return ResponseEntity.ok().body(mapper.parseToDto(service.update(aux)));
    }

}
