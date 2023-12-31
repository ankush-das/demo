package com.example.cyclerestApi.demo;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Cycle")
@Data
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String brand;

    private String color;

    private boolean available;

    private int stock;

    private int numBorrowed;

    public int getNumAvailable() {

        return stock - numBorrowed;

    }

}
