package br.com.bgrbarbosa.catalogo_carros_api.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "tb_manufacturer")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String manufacturer;

    @OneToMany(mappedBy = "manufacturer", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Model> model;

    public Manufacturer(Long id, String manufacturer) {
        this.id = id;
        this.manufacturer = manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer.toUpperCase();
    }
}
