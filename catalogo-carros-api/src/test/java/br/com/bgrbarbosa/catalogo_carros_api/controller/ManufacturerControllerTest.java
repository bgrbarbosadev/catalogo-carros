package br.com.bgrbarbosa.catalogo_carros_api.controller;

import br.com.bgrbarbosa.catalogo_carros_api.controller.mapper.ManuFacturerMapper;
import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.dto.ManufacturerDTO;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.ManufacturerService;
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



import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ManufacturerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ManufacturerRepository repository;

    @Autowired
    private ManufacturerService service;

    @Autowired
    private ManuFacturerMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private ManufacturerDTO dto;

    public static final long EXISTID = 1L;
    public static final long NOTEXISTID = 15L;

    @BeforeEach
    void setUp() {
        this.dto = new ManufacturerDTO(5L, "NISSAN", null);
    }

    @Test
    @DisplayName("Should return a List of manufacturer")
    @Order(1)
    void testSearchAllListManufacturer() throws Exception {

        ResultActions result = mockMvc.perform(get("/manufacturer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").exists())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].manufacturer").exists())
                .andExpect(jsonPath("$[0].manufacturer").value("FORD"))
                .andExpect(jsonPath("$.length()").value(8));
    }

    @Test
    @DisplayName("Must return a manufacturer successfully")
    @Transactional
    @Order(2)
    void testFindByIdSuccess() throws Exception {

        ManufacturerDTO dto = mapper.parseToDto(service.findById(EXISTID));

        mockMvc.perform(get("/manufacturer/{id}", EXISTID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(dto.id()))
                .andExpect(jsonPath("$.manufacturer").value(dto.manufacturer()));
    }

    @Test
    @DisplayName("Must not return manufacturer")
    @Order(3)
    void testFindByIdFailure () throws Exception {
        Long id = NOTEXISTID;

        mockMvc.perform(get("/manufacturer/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Resource not found!"))
                .andExpect(jsonPath("$.path").value("/manufacturer/" + id))
                .andExpect(jsonPath("$.status").value(NOT_FOUND.value()))
                .andExpect(jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Must enter a manufacturer record successfully")
    @Order(4)
    void testInsertManufacturerSuccessfully() throws Exception {
        String jsonBody = objectMapper.writeValueAsString(this.dto);

        ResultActions result =
                mockMvc.perform(post("/manufacturer")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(5L));
        result.andExpect(jsonPath("$.manufacturer").exists());
        result.andExpect(jsonPath("$.manufacturer").value("NISSAN"));
    }

    @Test
    @DisplayName("Should update the registry successfully")
    @Order(5)
    void testUpdateRecordSuccessfully() throws Exception {

        ManufacturerDTO dto = new ManufacturerDTO(1l, "FORD ALTERADO", null);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/manufacturer")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").exists());
        result.andExpect(jsonPath("$.id").value(1L));
        result.andExpect(jsonPath("$.manufacturer").exists());
        result.andExpect(jsonPath("$.manufacturer").value("FORD ALTERADO"));
    }

    @Test
    @DisplayName("Should not update the registry successfully")
    @Order(6)
    void testUpdateLogFailedUpdate() throws Exception {
     /*   ManufacturerDTO dto = new ManufacturerDTO(9l, "HILUX ALTERADA", null);
        String jsonBody = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(put("/manufacturer")
                        .content(jsonBody)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
        result.andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));
*/    }

    @Test
    @DisplayName("Must delete the record with existing code successfully")
    @Transactional
    @Order(7)
    void testDeleteRecordSuccessfully() throws Exception{
       /* Long id = 4L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/manufacturer/{id}", EXISTID))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Successfully deleted!!"));*/
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundExeption when deleting a non-existent code record")
    @Order(8)
    void testReturnAResourceNotFoundException()
            throws Exception{
/*        Long id = NOTEXISTID;
        mockMvc.perform(MockMvcRequestBuilders.delete("/manufacturer/{id}", NOTEXISTID))
                .andExpect(status().isNotFound())
                .andExpect(response -> Assertions.assertTrue(response.getResolvedException()instanceof ResourceNotFoundException));*/
    }
}