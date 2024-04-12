package com.malick.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.malick.ecommerce.entity.State;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "country")
@Data
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String code;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> states;

}
