package br.com.bgrbarbosa.catalogo_carros_api.service.impl;



import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ModelRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class ModelServiceImplTest {

    @InjectMocks
    private ModelServiceImpl service;

    @Mock
    private ModelRepository repository;

    private Long existId;

    private Long notExist;

    private Model model;

    private Model updateModel;

    private Manufacturer manufacturer;

    private Type type;

    @BeforeEach
    void setUp() {
        this.inicializeValues();
    }

    public void inicializeValues(){

        this.existId = 1L;
        this.notExist = 2L;
        this.model = new Model(4L, "CELTA 1.0", this.manufacturer, this.type );
        this.updateModel = new Model(4L, "CELTA 1.0 ALTERADO", this.manufacturer, this.type );
        this.manufacturer = new Manufacturer(3L, "CHEVROLET");
        this.type = new Type(2L, "POPULAR");
    }

    @Test
    @DisplayName("Must return a successfully registered vehicle model")
    void youMustEnterAVehicleModel() {

        // Arrange
        when(repository.save(model)).thenReturn(model);

        // Act & Assert
        Model result = service.insert(model);

        // Verify
        assertEquals(Model.class, result.getClass());
        assertEquals(model.getId(), result.getId());
        assertEquals(model.getModel(), result.getModel());
        assertEquals(model.toString(), result.toString());
        verify(repository, times(1)).save(model);
    }

    @Test
    @DisplayName("Must return an IllegalArgument when trying to register")
    void youMustNotEnterAVehicleModel() {

        // Arrange
        when(repository.existByModel(model.getModel())).thenReturn(Optional.of(model));

        // Act & Assert
        assertThrows(IllegalArgument.class, () -> {
           service.insert(model);
        });

        // Verify
        verify(repository, times(1)).existByModel(model.getModel());
        verify(repository, never()).save(model);
    }


    @Test
    @DisplayName("Should return a list of vehicle model")
    void shouldReturnAVehicleModelList() {

        // Arrange
        when(repository.findAll()).thenReturn(List.of(model));

        // Act & Assert
        List<Model> list = service.findAll();

        // Verify
        assertNotNull(list);
        assertEquals(Model.class, list.get(0).getClass());
        assertEquals(1, list.size());
        assertEquals(model.getId(), list.get(0).getId());
        assertEquals(model.getModel(), list.get(0).getModel());



    }

    @Test
    @DisplayName("Must return a vehicle model when id exists")
    void mustReturnVehicleModelWithExistingId() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(model));

        // Act & Assert
        Model result = service.findById(existId);

        // Verify
        assertNotNull(result);
        assertEquals(Model.class, result.getClass());
        assertEquals(model.getId(), result.getId());
        assertEquals(model.getModel(), result.getModel());
        assertEquals(model.toString(), result.toString());
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void shouldNotReturnVehicleModelWithNonExistentId() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());

        // Act & Assert
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(notExist);
        });

        // Verify
        assertEquals(ResourceNotFoundException.class, exception.getClass());
        verify(repository, times(1)).findById(notExist);

    }


    @Test
    @DisplayName("Must delete a record when the id exists")
    void deleteVehicleModelWhenIdExists() {

        when(repository.findById(existId)).thenReturn(Optional.of(model));

        assertDoesNotThrow(() -> {
            service.delete(existId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existId);

    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void notDeleteVehicleWhenNotExists() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(notExist);
        });

        // Verify that the repository's findById method was called
        verify(repository, times(1)).findById(notExist);
        verify(repository, never()).deleteById(notExist);
    }

    @Test
    @DisplayName("Must update the registry")
    void mustUpdateTheVehicleModel() {

        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.of(model));
        when(repository.save(any())).thenReturn(updateModel);

        // Act & Assert
        Model result = service.update(model);

        // Verify
        assertEquals(Model.class, result.getClass());
        assertEquals(updateModel.getId(), result.getId());
        assertEquals(updateModel.getModel(), result.getModel());
        assertEquals(updateModel.toString(), result.toString());

    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when updating a record with non-existent id")
    void shouldThrowAResourceNotFoundException() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(ResourceNotFoundException.class, () -> {
            service.findById(notExist);
        });

        // Verify
        assertEquals(ResourceNotFoundException.class, exception.getClass());
        verify(repository, times(1)).findById(notExist);
    }


}