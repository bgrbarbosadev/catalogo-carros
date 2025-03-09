package br.com.bgrbarbosa.catalogo_carros_api.controller;

import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.VehicleMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.VehicleDTO;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ModelRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.VehicleRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import br.com.bgrbarbosa.catalogo_carros_api.service.impl.VehicleServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private VehicleServiceImpl service;

    @Autowired
    private VehicleRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VehicleMapper mapper;

    private List<Vehicle> list;

    public static final long EXISTID = 1L;
    public static final long NOTEXISTID = 15L;

    public Type type;
    public Manufacturer manufacturer;
    public Model model;
    public VehicleDTO dto;

    @BeforeEach
    void setUp() {
        this.manufacturer = new Manufacturer(1L, "FORD");

        this.type = new Type(2L, "POPULAR");

        this.model = new Model(1L, "FORD", this.manufacturer, this.type);

        this.dto = new VehicleDTO(
                4L,
                "LKR6017",
                "2025",
                50000.00,
                10000.00,
                "COMPLETO",
                EnumTransmission.AUTOMATIC,
                EnumFuel.FLEX,
                "PRETO",
                this.model
        );

        this.list = repository.findAll();
    }

/*    @Test
    @DisplayName("Should return a List of vehicle")
    @Transactional
    @Order(1)
    void testSearchAllListVehicle() throws Exception {
        Page<VehicleDTO> list = mapper.parseToListDTO(service.findAll());
        ResultActions result = mockMvc.perform(get("/vehicle"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].plate").exists())
                .andExpect(jsonPath("$[0].plate").value("LJM1890"));
    }*/

   @Test
   @DisplayName("Must return a type successfully")
   @Transactional
   @Order(2)
    void testFindByIdSuccess() throws Exception {
        VehicleDTO dto = mapper.parseToDto(service.findById(EXISTID));

        ResultActions result = mockMvc.perform(get("/vehicle/{id}", EXISTID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.plate").value(dto.plate()));
    }

    @Test
    @DisplayName("Must not return vehicle")
    @Transactional
    @Order(3)
    void testFindByIdFailure () throws Exception {
        Long id = NOTEXISTID;

        mockMvc.perform(get("/vehicle/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found!"))
                .andExpect(jsonPath("$.path").value("/vehicle/" + id))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Must enter a manufacturer record successfully")
    @Transactional
    @Order(4)
    void testInsertModelSuccessfully() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(this.dto);
        ResultActions result =
                mockMvc.perform(post("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(4L));
        result.andExpect(jsonPath("$.plate").exists());
        result.andExpect(jsonPath("$.plate").value("LKR6017"));
    }

    @Test
    @DisplayName("Should update the registry successfully")
    @Transactional
    @Order(5)
    void testUpdateRecordSuccessfully() throws Exception {
        VehicleDTO updateDTO = new VehicleDTO(
                4L,
                "LKR6017",
                "2025",
                50000.00,
                10000.00,
                "COMPLETO",
                EnumTransmission.MANUAL,
                EnumFuel.FLEX,
                "VERMELHO",
                this.model
        );

        String jsonBody = objectMapper.writeValueAsString(updateDTO);

        ResultActions result =
                mockMvc.perform(put("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(4L));
        result.andExpect(jsonPath("$.plate").exists());
        result.andExpect(jsonPath("$.plate").value("LKR6017"));
        result.andExpect(jsonPath("$.transmission").value("MANUAL"));
        result.andExpect(jsonPath("$.cor").value("VERMELHO"));
    }

    @Test
    @DisplayName("Should not update the registry successfully")
    @Transactional
    @Order(6)
    void testUpdateLogFailedUpdate() throws Exception {
        VehicleDTO dto = new VehicleDTO(
                100L,
                "LJM1890",
                "2025",
                50000.00,
                1000.00,
                "COMPLETO ALTERADO",
                EnumTransmission.AUTOMATIC,
                EnumFuel.FLEX,
                "PRETO ALTERADO",
                null
        );

        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/vehicle")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }

    @Test
    @DisplayName("Must delete the record with existing code successfully")
    @Transactional
    @Order(7)
    void testDeleteRecordSuccessfully() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/{id}", EXISTID))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Successfully deleted!!"));
    }
    @Test
    @DisplayName("Must return a ResourceNotFoundExeption when deleting a non-existent code record")
    @Transactional
    @Order(8)
    void testReturnAResourceNotFoundException() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/vehicle/{id}", NOTEXISTID))
                .andExpect(status().isNotFound())
                .andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }
}