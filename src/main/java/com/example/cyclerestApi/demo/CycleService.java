package com.example.cyclerestApi.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CycleService {

    @Autowired
    private CycleRepository cycleRepository;

    public void restockBy(long id, int count) {

        var cycle = findByIdOrThrow404(id);

        cycle.setStock(cycle.getStock() + count);

        cycleRepository.save(cycle);

    }

}
