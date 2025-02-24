package br.com.bgrbarbosa.car_catalog_api.service.impl;


import br.com.bgrbarbosa.car_catalog_api.model.VehicleType;
import br.com.bgrbarbosa.car_catalog_api.repository.VehicleTypeRepository;

import br.com.bgrbarbosa.car_catalog_api.service.exception.IllegalArgument;
import br.com.bgrbarbosa.car_catalog_api.service.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;


import org.junit.jupiter.api.BeforeEach;
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
class VehicleTypeServiceImplTest {

    @InjectMocks
    private VehicleTypeServiceImpl service;

    @Mock
    private VehicleTypeRepository repository;

    private Long existId;

    private Long notExist;

    private VehicleType vehicleType;

    private VehicleType updateVehicleType;

    @BeforeEach
    void setUp() {
        this.inicializeValues();
    }

    public void inicializeValues(){

        this.existId = 1L;
        this.notExist = 2L;
        this.vehicleType = new VehicleType(1L, "SUV");
        this.updateVehicleType = new VehicleType(1L, "SUV-ALTERADO");
    }

    @Test
    void youMustEnterAVehicleType() {

        // Arrange
        when(repository.save(vehicleType)).thenReturn(vehicleType);

        // Act & Assert
        VehicleType result = service.insert(vehicleType);

        // Verify
        assertEquals(VehicleType.class, result.getClass());
        assertEquals(vehicleType.getId(), result.getId());
        assertEquals(vehicleType.getType(), result.getType());
        assertEquals(vehicleType.toString(), result.toString());
        verify(repository, times(1)).save(vehicleType);
    }

    @Test
    void youMustNotEnterAVehicleType() {

        // Arrange
        when(repository.existByType(vehicleType.getType())).thenReturn(Optional.of(vehicleType));

        // Act & Assert
        assertThrows(IllegalArgument.class, () -> {
           service.insert(vehicleType);
        });

        // Verify

        // Verifica se o método existByType foi chamado
        verify(repository, times(1)).existByType(vehicleType.getType());

        // Verifica que o método save nunca foi chamado
        verify(repository, never()).save(vehicleType);
    }


    @Test
    void shouldReturnAVehicleList() {

        // Arrange
        when(repository.findAll()).thenReturn(List.of(vehicleType));

        // Act & Assert
        List<VehicleType> list = service.findAll();

        // Verify
        assertNotNull(list);
        assertEquals(VehicleType.class, list.get(0).getClass());
        assertEquals(1, list.size());
        assertEquals(vehicleType.getId(), list.get(0).getId());
        assertEquals(vehicleType.getType(), list.get(0).getType());



    }

    @Test
    void mustReturnVehicleWithExistingId() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(vehicleType));

        // Act & Assert
        VehicleType result = service.findById(existId);

        // Verify
        assertNotNull(result);
        assertEquals(VehicleType.class, result.getClass());
        assertEquals(vehicleType.getId(), result.getId());
        assertEquals(vehicleType.getType(), result.getType());
        assertEquals(vehicleType.toString(), result.toString());
    }

    @Test
    void shouldNotReturnVehicleWithNonExistentId() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
           service.findById(notExist);
        });

        // Verify
        verify(repository, times(1)).findById(notExist);

    }


    @Test
    void deleteVehicleWhenIdExists() {

        when(repository.findById(existId)).thenReturn(Optional.of(vehicleType));

        assertDoesNotThrow(() -> {
            service.delete(existId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existId);

    }

    @Test
    void notDeleteVehicleWhenNotExists() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());
        //when(repository.findById(notExist)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ResourceNotFoundException.class, () -> {
            service.delete(notExist);
        });

        // Verify that the repository's findById method was called
        verify(repository, times(1)).findById(notExist);

        // Verify that the repository's deleteById method was never called
        verify(repository, never()).deleteById(notExist);



    }

    @Test
    void mustUpdateTheVehicle() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(vehicleType));
        when(repository.save(vehicleType)).thenReturn(updateVehicleType);

        // Act & Assert
        VehicleType result = service.update(vehicleType);

        // Verify
        assertEquals(VehicleType.class, result.getClass());
        assertEquals(updateVehicleType.getId(), result.getId());
        assertEquals(updateVehicleType.getType(), result.getType());
        assertEquals(updateVehicleType.toString(), result.toString());

    }

    @Test
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