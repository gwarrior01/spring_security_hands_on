package com.example.entity;

import com.example.enums.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private double price;

    @Enumerated(EnumType.STRING)
    private Currency currency;
}
