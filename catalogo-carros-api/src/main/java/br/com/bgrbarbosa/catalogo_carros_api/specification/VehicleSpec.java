package br.com.bgrbarbosa.catalogo_carros_api.specification;

import br.com.bgrbarbosa.catalogo_carros_api.model.Model;
import br.com.bgrbarbosa.catalogo_carros_api.model.Vehicle;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;


public class VehicleSpec {
    public static Specification<Vehicle> searchYearOfManufacture(String year) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(year)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("yearOfManufacture"), year);
        });
    }

    public static Specification<Vehicle> searchPrice(Double price) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(price)) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("price"), price);
        });
    }

    public static Specification<Vehicle> searchMileage(Double mileage) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(mileage)) {
                return null;
            }
            return criteriaBuilder.lessThanOrEqualTo(root.get("mileage"), mileage);
        });
    }

    public static Specification<Vehicle> searchTransmission(EnumTransmission transmission) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(transmission)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("transmission"), transmission);
        });
    }

    public static Specification<Vehicle> searchFuel(EnumFuel fuel) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(fuel)) {
                return null;
            }
            return criteriaBuilder.equal(root.get("fuel"), fuel);
        });
    }

    public static Specification<Vehicle> searchColor(String cor) {
        return ((root, query, criteriaBuilder) -> {
            if (ObjectUtils.isEmpty(cor)) {
                return null;
            }
            return criteriaBuilder.like(root.get("cor"), "%" + cor.toUpperCase() + "%");
        });
    }

    public static Specification<Vehicle> searchModel(String model) {
        return (root, query, builder) -> {
            if (ObjectUtils.isEmpty(model)) {
                return null;
            }
            return builder.like(root.join("model").get("model"), "%" + model.toUpperCase() + "%");
        };
    }
}
