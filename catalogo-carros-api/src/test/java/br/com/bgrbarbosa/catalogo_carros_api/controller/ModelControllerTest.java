package br.com.bgrbarbosa.catalogo_carros_api.controller;

import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.ModelMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ModelDTO;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ModelRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.ModelService;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.*;
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

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ModelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelRepository repository;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ModelService service;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Manufacturer manufacturer;

    private Type type;

    private ModelDTO dto;

    public static final long EXISTID = 1L;
    public static final long NOTEXISTID = 15L;

    @BeforeEach
    @Transactional
    void setUp() {

        manufacturer = new Manufacturer(1L, "FORD");
        manufacturerRepository.save(this.manufacturer);

        type = new Type(2L, "POPULAR");
        typeRepository.save(this.type);

        this.dto = new ModelDTO(4L, "ECOSPORT", this.manufacturer, this.type);
    }

    @Test
    @DisplayName("Should return a List of model")
    @Order(1)
    void testSearchAllListVehicleType() throws Exception {
        ResultActions result = mockMvc.perform(get("/model"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].model").exists())
                .andExpect(jsonPath("$[0].model").value("FOCUS"))
                .andExpect(jsonPath("$.length()").value(6));
    }

    @Test
    @DisplayName("Must return a model successfully")
    @Transactional
    @Order(2)
    void testFindByIdSuccess() throws Exception {

        ModelDTO dto = mapper.parseToDto(service.findById(EXISTID));

        mockMvc.perform(get("/model/{id}", EXISTID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.model").value(dto.model()));
    }

    @Test
    @DisplayName("Must not return Model")
    @Order(3)
    void testFindByIdFailure () throws Exception {
        Long id = NOTEXISTID;

        mockMvc.perform(get("/model/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found!"))
                .andExpect(jsonPath("$.path").value("/model/" + id))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Must enter a model record successfully")
    @Order(4)
    void testInsertModelSuccessfully() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(this.dto);

        ResultActions result =
                mockMvc.perform(post("/model")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(4L));
        result.andExpect(jsonPath("$.model").exists());
        result.andExpect(jsonPath("$.model").value("ECOSPORT"));
    }

    @Test
    @DisplayName("Should update the registry successfully")
    @Order(5)
    void testUpdateRecordSuccessfully() throws Exception {

        ModelDTO dto = new ModelDTO(1L, "FOCUS ALTERADO", manufacturer , type);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/model")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.model").exists());
        result.andExpect(jsonPath("$.model").value("FOCUS ALTERADO"));
    }

    @Test
    @DisplayName("Should not update the registry successfully")
    @Order(6)
    void testUpdateLogFailedUpdate() throws Exception {
        ModelDTO dto = new ModelDTO(100L, "FOCUS ALTERADO", manufacturer , type);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/model")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }

    @Test
    @DisplayName("Must delete the record with existing code successfully")
    @Order(7)
    void testDeleteRecordSuccessfully() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/model/{id}", 4L))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Successfully deleted!!"));
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundExeption when deleting a non-existent code record")
    @Order(8)
    void testReturnAResourceNotFoundException()
            throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/model/{id}", NOTEXISTID))
                .andExpect(status().isNotFound())
                .andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }

}