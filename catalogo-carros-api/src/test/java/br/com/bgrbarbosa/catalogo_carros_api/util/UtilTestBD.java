package br.com.bgrbarbosa.catalogo_carros_api.util;

import br.com.bgrbarbosa.catalogo_carros_api.model.Manufacturer;
import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Type;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ManufacturerRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.ModelRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.TypeRepository;
import br.com.bgrbarbosa.catalogo_carros_api.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UtilTestBD {

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private TypeRepository typeRepository;

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @WithMockUser(roles = "ADMIN")
    public void initializeTestBD(){

        // Inserção dos Fabricantes
        var mf1 = new Manufacturer(1L, "FORD");
        var mf2 = new Manufacturer(2L, "FIAT");
        var mf3 = new Manufacturer(3L, "CHEVROLET");
        var mf4 = new Manufacturer(4L, "TOYOTA");
        this.manufacturerRepository.saveAll(Arrays.asList(mf1, mf2, mf3, mf4));

        // Inserção dos Tipos de Veículo
        var t1 = new Type(1L, "SUV");
        var t2 = new Type(2L, "POPULAR");
        var t3 = new Type(3L, "MOTO");
        var t4 = new Type(4L, "PICAPE");
        this.typeRepository.saveAll(Arrays.asList(t1, t2, t3, t4));

        // Inserção dos modelos de veículo
        var m1 = new Model(1L, "FOCUS", mf1, t2 );
        var m2 = new Model(2L, "ETIOS" , mf4, t2);
        var m3 = new Model(3L, "UNO", mf2, t2);
        this.modelRepository.saveAll(Arrays.asList(m1, m2, m3));

        var v1 = new Vehicle(1L, "LJM1890", "2025", 50000.00, 10000.00, "COMPLETO", EnumTransmission.AUTOMATIC, EnumFuel.FLEX, "PRETO", m1);
        var v2 = new Vehicle(2L, "KVC6525", "2020", 40000.00, 2000.00, "COMPLETO", EnumTransmission.AUTOMATIC, EnumFuel.FLEX, "VERMELHO", m2);
        var v3 = new Vehicle(3L, "LJM1891", "2025", 50000.00, 10000.00, "COMPLETO", EnumTransmission.AUTOMATIC, EnumFuel.FLEX, "BRANCO", m3);
        this.vehicleRepository.saveAll(Arrays.asList(v1, v2, v3));
    }
}
