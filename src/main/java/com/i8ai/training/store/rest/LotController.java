package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Lot;
import com.i8ai.training.store.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lots")
public class LotController {

    private final LotService lotService;

    @GetMapping
    public List<Lot> getLots(@RequestParam(required = false) Long productId,
                             @RequestParam(required = false) Date start,
                             @RequestParam(required = false) Date end) {
        return lotService.getLots(productId, start, end);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Lot registerLot(@RequestBody Lot newLot) {
        return lotService.registerLot(newLot);
    }

    @DeleteMapping("{lotId}")
    public void deleteLot(@PathVariable Long lotId) {
        lotService.deleteLot(lotId);
    }

}
