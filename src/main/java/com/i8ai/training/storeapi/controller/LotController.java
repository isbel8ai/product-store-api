package com.i8ai.training.storeapi.controller;

import com.i8ai.training.storeapi.domain.Lot;
import com.i8ai.training.storeapi.service.LotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/lot")
public class LotController {
    private final LotService lotService;

    @Autowired
    public LotController(LotService lotService) {
        this.lotService = lotService;
    }

    @GetMapping
    public List<Lot> getLots(@RequestParam(required = false) Date start,
                             @RequestParam(required = false) Date end,
                             @RequestParam(required = false) Long productId) {
        return lotService.getLots(start, end, productId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Lot addLot(@RequestBody Lot newLot) {
        return lotService.addLot(newLot);
    }

    @DeleteMapping("/{lotId}")
    public void deleteLot(@PathVariable Long lotId) {
        lotService.deleteLot(lotId);
    }
}
