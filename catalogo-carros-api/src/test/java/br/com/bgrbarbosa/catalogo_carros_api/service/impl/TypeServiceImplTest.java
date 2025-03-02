package br.com.bgrbarbosa.catalogo_carros_api.service.impl;

import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
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

@Slf4j
@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class TypeServiceImplTest {

    @InjectMocks
    private TypeServiceImpl service;

    @Mock
    private TypeRepository repository;

    private Long existId;

    private Long notExist;

    private Type type;

    private Type notExistType;

    private Type updateType;

    private Type existType;

    @BeforeEach
    void setUp() {
        this.inicializeValues();
    }

    public void inicializeValues(){

        this.existId = 4L;
        this.type = new Type(4L,"LUXO");
        this.notExistType = new Type(4L,"LUXO");
        this.updateType = new Type(4L, "LUXO UPDATE");
    }

    @Test
    @DisplayName("Must return a successfully registered vehicle type")
    void youMustEnterAVehicleType() {

        // Arrange
        when(repository.save(type)).thenReturn(type);

        // Act & Assert
        Type result = service.insert(type);

        // Verify
        assertEquals(Type.class, result.getClass());
        assertEquals(type.getId(), result.getId());
        assertEquals(type.getType(), result.getType());
        assertEquals(type.toString(), result.toString());
        assertEquals(4, result.getId());
        verify(repository, times(1)).save(type);
    }

    @Test
    @DisplayName("Must return an IllegalArgument when trying to register")
    void youMustNotEnterAVehicleType() {

        // Arrange
        when(repository.existByType(type.getType())).thenReturn(Optional.of(type));

        // Act & Assert
        assertThrows(IllegalArgument.class, () -> {
            service.insert(type);
        });

        // Verify
        verify(repository, times(1)).existByType(type.getType());
        verify(repository, never()).save(type);
    }

    @Test
    @DisplayName("Should return a list of vehicle types")
    void shouldReturnAVehicleTypeList() {

        // Arrange
        when(repository.findAll()).thenReturn(List.of(type));

        // Act & Assert
        List<Type> list = service.findAll();

        // Verify
        assertNotNull(list);
        assertEquals(Type.class, list.get(0).getClass());
        assertEquals(1, list.size());
        assertEquals(type.getId(), list.get(0).getId());
        assertEquals(type.getType(), list.get(0).getType());
    }

    @Test
    @DisplayName("Must return a vehicle type when id exists")
    void mustReturnVehicleTypeWithExistingId() {

        // Arrange
        when(repository.findById(existId)).thenReturn(Optional.of(type));

        // Act & Assert
        Type result = service.findById(existId);

        // Verify
        assertNotNull(result);
        assertEquals(Type.class, result.getClass());
        assertEquals(type.getId(), result.getId());
        assertEquals(type.getType(), result.getType());
        assertEquals(type.toString(), result.toString());
    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void shouldNotReturnVehicleTypeWithNonExistentId() {

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
    void deleteVehicleTypeWhenIdExists() {

        when(repository.findById(existId)).thenReturn(Optional.of(type));

        assertDoesNotThrow(() -> {
            service.delete(existId);
        });

        Mockito.verify(repository, Mockito.times(1)).deleteById(existId);

    }

    @Test
    @DisplayName("Must return a ResourceNotFoundException when id does not exist")
    void notDeleteVehicleTypeWhenNotExists() {

        // Arrange
        when(repository.findById(notExist)).thenReturn(Optional.empty());
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
    void mustUpdateTheVehicleType() {

        // Arrange
        when(repository.findById(4L)).thenReturn(Optional.of(type));
        when(repository.save(type)).thenReturn(updateType);

        // Act & Assert
        Type result = service.update(type);

        // Verify
        assertEquals(Type.class, result.getClass());
        assertEquals(updateType.getId(), result.getId());
        assertEquals(updateType.getType(), result.getType());
        assertEquals(updateType.toString(), result.toString());

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