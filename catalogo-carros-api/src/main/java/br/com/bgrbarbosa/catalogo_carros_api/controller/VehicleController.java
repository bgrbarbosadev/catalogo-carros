package br.com.bgrbarbosa.catalogo_carros_api.controller;


import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.VehicleMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import br.com.bgrbarbosa.catalogo_carros_api.service.VehicleService;
import br.com.bgrbarbosa.catalogo_carros_api.specification.filter.VehicleFilter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequiredArgsConstructor
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

    private final VehicleService service;

    private final VehicleMapper mapper;

    @PostMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<VehicleDTO> insert(@RequestBody @Valid VehicleDTO dto){
        Vehicle result = service.insert(mapper.parseToEntity(dto));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.parseToDto(result));
    }

    @GetMapping
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
     public ResponseEntity<Page<VehicleDTO>> searchAll(
            VehicleFilter filter,
            @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page){
        List<VehicleDTO> listDTO = mapper.parseToListDTO(service.findAll(page, filter));
        Page<VehicleDTO> pageDTO = mapper.toPageDTO(listDTO, page);
        return ResponseEntity.ok(pageDTO);
    }

    @GetMapping("/{id}")
    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    public ResponseEntity<VehicleDTO> searchById(@PathVariable("id") Long id){
        VehicleDTO result = mapper.parseToDto(service.findById(id));
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        service.delete(id);
        return ResponseEntity.accepted().body("Successfully deleted!!");
    }

    @PutMapping
    @Secured("ROLE_ADMIN")
    public ResponseEntity<VehicleDTO> update(@RequestBody @Valid VehicleDTO dto){
        Vehicle aux = mapper.parseToEntity(dto);
        return ResponseEntity.ok().body(mapper.parseToDto(service.update(aux)));
    }

}
