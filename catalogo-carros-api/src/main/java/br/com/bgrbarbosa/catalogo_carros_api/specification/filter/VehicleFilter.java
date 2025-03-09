package br.com.bgrbarbosa.catalogo_carros_api.specification.filter;

import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import static br.com.bgrbarbosa.catalogo_carros_api.specification.VehicleSpec.*;

@Data
public class VehicleFilter {

    Long id;
    String plate;
    String yearOfManufacture;
    Double price;
    Double mileage;
    String items;
    EnumTransmission transmission;
    EnumFuel fuel;
    String cor;
    String  model;

    public Specification<Vehicle> toSpecification() {
        return searchYearOfManufacture(plate)
                .and(searchPrice(price))
                .and(searchMileage(mileage))
                .and(searchTransmission(transmission))
                .and(searchFuel(fuel))
                .and(searchColor(cor))
                .and(searchModel(model));
    }
}
