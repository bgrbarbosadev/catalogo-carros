package br.com.bgrbarbosa.catalogo_carros_api.service.impl;

import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
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
import static org.mockito.Mockito.times;

@Slf4j
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class ManufacturerServiceImplTest {

    @InjectMocks
    private ManufacturerServiceImpl service;

    @Mock
    private ManufacturerRepository repository;

    private Long existId;

    private Long notExist;

    private Manufacturer manufacturer;

    private Manufacturer updateManufacturer;

    private Manufacturer existManufacturer;

    @BeforeEach
    void setUp() {
        this.inicializeValues();
    }

    public void inicializeValues(){

        this.existId = 4L;
        this.notExist = 9L;

        this.manufacturer = new Manufacturer(4L,"HONDA");
        this.updateManufacturer = new Manufacturer(4L, "HONDA UPDATE");
    }

    @Test
    @DisplayName("Must return a successfully registered vehicle manufacturer")
    void youMustEnterAVehicleManufacturer() {

        // Arrange
        when(repository.save(any())).thenReturn(this.manufacturer);

        // Act & Assert
        Manufacturer result = service.insert(this.manufacturer);

        // Verify
        assertEquals(Manufacturer.class, result.getClass());
        assertEquals(this.manufacturer.getId(), result.getId());
        assertEquals(this.manufacturer.getManufacturer(), result.getManufacturer());
        assertEquals(this.manufacturer.toString(), result.toString());
        assertEquals(4, result.getId());
        verify(repository, times(1)).save(this.manufacturer);
    }

    @Test
    @DisplayName("Must return an IllegalArgument when trying to register")
    void youMustNotEnterAVehicleManufacturer() {

        // Arrange
        when(repository.existByManufacturer(manufacturer.getManufacturer())).thenReturn(Optional.of(manufacturer));

        // Act & Assert
        assertThrows(IllegalArgument.class, () -> {
            service.insert(manufacturer);
        });

        // Verify
        verify(repository, times(1)).existByManufacturer(manufacturer.getManufacturer());
        verify(repository, never()).save(manufacturer);
    }

    @Test
    @DisplayName("Should return a list of vehicle manufacturer")
    void shouldReturnAVehicleManufacturerList() {

        // Arrange
        when(repository.findAll()).thenReturn(List.of(manufacturer));

        // Act & Assert
        List<Manufacturer> list = service.findAll();

        // Verify
        assertNotNull(list);
        assertEquals(Manufacturer.class, list.get(0).getClass());
        assertEquals(1, list.size());
        assertEquals(manufacturer.getId(), list.get(0).getId());
        assertEquals(manufacturer.getManufacturer(), list.get(0).getManufacturer());
    }

    @Test
    @DisplayName("Must return a vehicle manufacturer when id exists")
    void mustReturnVehicleManufacturerWithExistingId() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(manufacturer));

        // Act & Assert
        Manufacturer result = service.findById(existId);

        // Verify
        assertNotNull(result);
        assertEquals(Manufacturer.class, result.getClass());
        assertEquals(manufacturer.getId(), result.getId());
        assertEquals(manufacturer.getManufacturer(), result.getManufacturer());
        assertEquals(manufacturer.toString(), result.toString());
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void shouldNotReturnVehicleManufacturerWithNonExistentId() {

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
    void deleteVehicleManufacturerWhenIdExists() {

        when(repository.findById(existId)).thenReturn(Optional.of(manufacturer));

        assertDoesNotThrow(() -> {
            service.delete(existId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existId);

    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void notDeleteVehicleManufacturerWhenNotExists() {

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
    void mustUpdateTheVehicleManufacturer() {

        // Arrange
        when(repository.findById(4L)).thenReturn(Optional.of(manufacturer));
        when(repository.save(manufacturer)).thenReturn(updateManufacturer);

        // Act & Assert
        Manufacturer result = service.update(manufacturer);

        // Verify
        assertEquals(Manufacturer.class, result.getClass());
        assertEquals(updateManufacturer.getId(), result.getId());
        assertEquals(updateManufacturer.getManufacturer(), result.getManufacturer());
        assertEquals(updateManufacturer.toString(), result.toString());

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