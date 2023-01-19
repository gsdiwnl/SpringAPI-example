package com.spring.data;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product 
{
    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY )
    private int id;
    private String name;
    private int quantity;
    private double price;

    @OneToMany( mappedBy = "product", 
                cascade = CascadeType.ALL, 
                orphanRemoval = true,
                fetch = FetchType.LAZY )
    @JsonIgnoreProperties(value = "product", allowGetters = false)
    private List<Image> images;
}
