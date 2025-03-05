package br.com.bgrbarbosa.catalogo_carros_api.controller;

import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.TypeMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.TypeDTO;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import br.com.bgrbarbosa.catalogo_carros_api.service.impl.TypeServiceImpl;
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
class TypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TypeRepository repository;

    @Autowired
    private TypeServiceImpl service;

    @Autowired
    private TypeMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private TypeDTO dto;

    public static final long EXISTID = 1L;
    public static final long NOTEXISTID = 15L;


    @BeforeEach
    void setUp() {
        List<Type> list = repository.findAll();
        this.dto = new TypeDTO(5L, "LUXO");
    }

    @Test
    @DisplayName("Should return a List of type")
    @Order(1)
    void searchAll() throws Exception {
        ResultActions result = mockMvc.perform(get("/type"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].type").exists())
                .andExpect(jsonPath("$[0].type").value("SUV"))
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @DisplayName("Must return a type successfully")
    @Order(2)
    void testFindByIdSuccess() throws Exception {

        TypeDTO dto = mapper.parseToDto(service.findById(EXISTID));

        mockMvc.perform(get("/type/{id}", EXISTID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.type").value(dto.type()));
    }

    @Test
    @DisplayName("Must not return type")
    @Order(3)
    void testFindByIdFailure () throws Exception {
        Long id = NOTEXISTID;

        mockMvc.perform(get("/type/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found!"))
                .andExpect(jsonPath("$.path").value("/type/" + id))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Must enter a type record successfully")
    @Order(4)
    void testInsertTypeSuccessfully() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(this.dto);

        ResultActions result =
                mockMvc.perform(post("/type")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(5L));
        result.andExpect(jsonPath("$.type").exists());
        result.andExpect(jsonPath("$.type").value("LUXO"));
    }

    @Test
    @DisplayName("Should update the registry successfully")
    @Order(5)
    void testUpdateRecordSuccessfully() throws Exception {
        TypeDTO dto = new TypeDTO(1L, "POPULAR ALTERADO");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/type")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.type").exists());
        result.andExpect(jsonPath("$.type").value("POPULAR ALTERADO"));
    }

    @Test
    @DisplayName("Should not update the registry successfully")
    @Order(6)
    void testUpdateLogFailedUpdate() throws Exception {
        TypeDTO dto = new TypeDTO(9l, "ECOSPORT");
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/type")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }

    @Test
    @DisplayName("Must delete the record with existing code successfully")
    @Order(7)
    @Transactional
    void testDeleteRecordSuccessfully() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.delete("/type/{id}", EXISTID))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Successfully deleted!!"));
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundExeption when deleting a non-existent code record")
    @Order(8)
    void testReturnAResourceNotFoundException()
            throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/type/{id}", NOTEXISTID))
                .andExpect(status().isNotFound())
                .andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
    }
}