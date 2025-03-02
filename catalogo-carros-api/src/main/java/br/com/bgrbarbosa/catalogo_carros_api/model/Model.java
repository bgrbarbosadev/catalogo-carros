package br.com.bgrbarbosa.catalogo_carros_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_model")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column
    private String model;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    @JsonIgnore
    private Manufacturer manufacturer;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @JsonIgnore
    private Type type;

    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Vehicle> vehicles;

    public Model(Long id, String model, Manufacturer manufacturer, Type type) {
        Id = id;
        this.model = model;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    public void setModel(String model) {
        this.model = model.toUpperCase();
    }
}
