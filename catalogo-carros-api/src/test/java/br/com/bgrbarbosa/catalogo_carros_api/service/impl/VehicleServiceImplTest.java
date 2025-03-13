package br.com.bgrbarbosa.catalogo_carros_api.service.impl;

import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import br.com.bgrbarbosa.catalogo_carros_api.repository.VehicleRepository;
import br.com.bgrbarbosa.catalogo_carros_api.service.exception.ResourceNotFoundException;
import br.com.bgrbarbosa.catalogo_carros_api.specification.filter.VehicleFilter;
import org.springframework.data.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@Slf4j
@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

    @InjectMocks
    private VehicleServiceImpl service;

    @Mock
    private VehicleRepository repository;

    private Long existId;

    private Long notExist;

    private Model model;

    private Manufacturer manufacturer;

    private Type type;

    private Vehicle vehicle;

    private Vehicle vehicleUpdate;

    private VehicleFilter filter;

    private Pageable page;

    @BeforeEach
    void setUp() {
        this.existId = 1L;

        this.notExist = 4L;

        this.manufacturer= new Manufacturer(1L, "TOYOTA");

        this.type = new Type(1L, "POPULAR");

        this.model = new Model(1L, "ETIOS",this.manufacturer, this.type);

        this.vehicle = new Vehicle(1L, "KJR-9999", "2024", 50000.00, 20000.00,
                                    "Completo", EnumTransmission.AUTOMATIC, EnumFuel.FLEX, "Vermelho", this.model);

        this.vehicleUpdate = new Vehicle(1L, "KJR-9999", "2024", 50000.00, 20000.00,
                "Completo", EnumTransmission.AUTOMATIC, EnumFuel.FLEX, "Vermelho", this.model);

        this.filter = new VehicleFilter();
        this.page = PageRequest.of(0, 10, Sort.by(Sort.Direction.ASC, "id"));      // Arrange

    }

    @Test
    @DisplayName("Must return a successfully registered vehicle")
    void youMustEnterAVehicle() {

        // Arrange
        when(repository.save(this.vehicle)).thenReturn(this.vehicle);

        // Act & Assert
        Vehicle result = service.insert(this.vehicle);

        // Verify
        assertEquals(Vehicle.class, result.getClass());
        assertEquals(vehicle.getId(), result.getId());
        assertEquals(vehicle.getPlate(), result.getPlate());
        assertEquals(vehicle.toString(), result.toString());
        verify(repository, times(1)).save(vehicle);
    }

    @Test
    @DisplayName("Must return an IllegalArgument when trying to register")
    void shouldReturnAVehicleList() {

        Pageable pageable = mock(Pageable.class); // Mock do Pageable
        VehicleFilter filter = mock(VehicleFilter.class); // Mock do filtro
        Specification<Vehicle> specification = mock(Specification.class);

        when(filter.toSpecification()).thenReturn(specification);
        when(repository.findAll(filter.toSpecification())).thenReturn(List.of(vehicle));

        // Act & Assert
        List<Vehicle> list = service.findAll(page, filter);

        // Verify
        assertNotNull(list);
        assertEquals(Vehicle.class, list.get(0).getClass());
        assertEquals(1, list.size());
        assertEquals(vehicle.getId(), list.get(0).getId());
        assertEquals(vehicle.getPlate(), list.get(0).getPlate());
        assertEquals(vehicle.toString(), list.get(0).toString());
    }

    @Test
    @DisplayName("Should return a list of vehicle")
    void mustReturnVehicleWithExistingId() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(vehicle));

        // Act & Assert
        Vehicle result = service.findById(existId);

        // Verify
        assertNotNull(result);
        assertEquals(Vehicle.class, result.getClass());
        assertEquals(vehicle.getId(), result.getId());
        assertEquals(vehicle.getPlate(), result.getPlate());
        assertEquals(vehicle.toString(), result.toString());
    }

    @Test
    @DisplayName("Must return a vehicle when id exists")
    void shouldNotReturnVehicleWithNonExistentId() {

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
    void deleteVehicleWhenIdExists() {

        when(repository.findById(existId)).thenReturn(Optional.of(vehicle));

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
    void mustUpdateTheVehicle() {

        // Arrange
        when(repository.findById(anyLong())).thenReturn(Optional.of(vehicle));
        when(repository.save(any())).thenReturn(vehicleUpdate);

        // Act & Assert
        Vehicle result = service.update(vehicleUpdate);

        // Verify
        assertEquals(Vehicle.class, result.getClass());
        assertEquals(vehicleUpdate.getId(), result.getId());
        assertEquals(vehicleUpdate.getModel(), result.getModel());
        assertEquals(vehicleUpdate.getItems(), result.getItems());
        assertEquals(vehicleUpdate.toString(), result.toString());

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