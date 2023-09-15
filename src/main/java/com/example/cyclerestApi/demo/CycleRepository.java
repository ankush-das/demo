package com.example.cyclerestApi.demo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {
    Cycle findById(int id);

    List<Cycle> findByAvailable(boolean b);

}
