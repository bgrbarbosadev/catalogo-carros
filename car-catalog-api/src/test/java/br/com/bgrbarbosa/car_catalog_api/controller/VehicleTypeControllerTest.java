package br.com.bgrbarbosa.car_catalog_api.controller;

import br.com.bgrbarbosa.car_catalog_api.controller.mapper.VehicleTypeMapper;
import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import br.com.bgrbarbosa.car_catalog_api.model.dto.VehicleTypeDTO;
import br.com.bgrbarbosa.car_catalog_api.repository.VehicleTypeRepository;
import br.com.bgrbarbosa.car_catalog_api.service.VehicleTypeService;
import br.com.bgrbarbosa.car_catalog_api.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;




import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class VehicleTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleTypeRepository repository;

    @Autowired
    private VehicleTypeService service;

    @Autowired
    private VehicleTypeMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private VehicleType v1;
    private VehicleType v2;
    private VehicleType v3;
    private VehicleTypeDTO dto;

    public static final long EXISTID = 1L;
    public static final long NOTEXISTID = 15L;


    @BeforeEach
    void setUp() {
        inicializeData();
    }

    void inicializeData(){
        this.v1 = new VehicleType(1L, "SUV");
        this.v2 = new VehicleType(2L, "UTILITARIO");
        this.v3 = new VehicleType(3L, "POPULAR");
        this.dto = new VehicleTypeDTO(4L, "MOTO");

        List<VehicleType> list = Arrays.asList(v1,v2,v3);
        repository.saveAll(list);
    }

    @Test
    @DisplayName("Should return a List of vehicle types")
    void testFindAll() throws Exception {
        ResultActions result = mockMvc.perform(get("/vehicle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").exists())
                .andExpect(jsonPath("$[0].type").value("SUV"))
                .andExpect(jsonPath("$.length()").value(3));

    }

    @Test
    @DisplayName("Must return a vehicle type successfully")
    void testFindByIdSuccess() throws Exception {
        Long id = EXISTID;

        VehicleTypeDTO dto = mapper.parseToDto(service.findById(id));

        mockMvc.perform(get("/vehicle/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.type").value(dto.type()));
    }

    @Test
    @DisplayName("Must not return vehicle type")
    void testFindByIdFailure () throws Exception {
        Long id = NOTEXISTID;

        mockMvc.perform(get("/vehicle/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Notification not found!!"))
                .andExpect(jsonPath("$.path").value("/vehicle/" + id))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Must enter a vehicle type record successfully")
    void testInsertVehicleTypeSuccessfully() throws Exception {

        String jsonBody = objectMapper.writeValueAsString(this.dto);

        ResultActions result =
                mockMvc.perform(post("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(4L));
        result.andExpect(jsonPath("$.type").exists());
        result.andExpect(jsonPath("$.type").value("MOTO"));

    }

    @Test
    @DisplayName("Should not process entity insertion")
    void insertTestWithValidationFailure() throws Exception {

        VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO(null, "te");
        String jsonBody = objectMapper.writeValueAsString(vehicleTypeDTO);

        ResultActions result =
                mockMvc.perform(post("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isUnprocessableEntity());
    }

    @Test
    @DisplayName("You must not insert an existing vehicle type")
    void insertTestOfExistingVehicleType() throws Exception {

        VehicleTypeDTO vehicleTypeDTO = new VehicleTypeDTO(null, "POPULAR");
        String jsonBody = objectMapper.writeValueAsString(vehicleTypeDTO);

        ResultActions result =
                mockMvc.perform(post("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Must delete the record with existing code successfully")
    void testDeleteRecordSuccessfully() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/{id}", EXISTID))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Successfully deleted!!"));
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundExeption when deleting a non-existent code record")
    void testReturnAResourceNotFoundException()
            throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/{id}", NOTEXISTID))
                .andExpect(status().isNotFound())
                .andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }

    @Test
    @DisplayName("Should update the registry successfully")
    void testUpdateRecordSuccessfully() throws Exception {
        VehicleTypeDTO dtoUpdate = new VehicleTypeDTO(3l, "POPULAR ATUALIZADO");
        String jsonBody = objectMapper.writeValueAsString(dtoUpdate);

        ResultActions result =
                mockMvc.perform(put("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(3L));
        result.andExpect(jsonPath("$.type").exists());
        result.andExpect(jsonPath("$.type").value("POPULAR ATUALIZADO"));
    }

    @Test
    @DisplayName("Should not update the registry successfully")
    void testUpdateLogFailedUpdate() throws Exception {
        VehicleTypeDTO dtoUpdate = new VehicleTypeDTO(9l, "POPULAR ATUALIZADO");
        String jsonBody = objectMapper.writeValueAsString(dtoUpdate);

        ResultActions result =
                mockMvc.perform(put("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }
}