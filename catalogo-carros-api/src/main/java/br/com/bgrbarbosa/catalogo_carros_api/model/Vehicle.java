package br.com.bgrbarbosa.catalogo_carros_api.model;

import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumTransmission;
import br.com.bgrbarbosa.catalogo_carros_api.model.enums.EnumFuel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_vehicle")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String plate;

    @Column
    private String yearOfManufacture;

    @Column
    private Double price;

    @Column
    private String mileage;

    @Column
    private String items;

    @Column
    @Enumerated(EnumType.STRING)
    private EnumTransmission transmission;

    @Column
    @Enumerated(EnumType.STRING)
    private EnumFuel fuel;

    @Column
    private String cor;

    @ManyToOne
    @JoinColumn(name = "model_id")
    @JsonIgnore
    private Model model;

    public String getPlate() {
        return plate.toUpperCase();
    }

    public String getMileage() {
        return mileage.toUpperCase();
    }

    public String getItems() {
        return items.toUpperCase();
    }

    public String getCor() {
        return cor.toUpperCase();
    }
}
