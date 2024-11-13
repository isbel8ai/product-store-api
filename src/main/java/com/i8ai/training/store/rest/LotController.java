package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.LotDto;
import com.i8ai.training.store.service.LotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

import static com.i8ai.training.store.util.DateTimeUtils.dateTimeOrMax;
import static com.i8ai.training.store.util.DateTimeUtils.dateTimeOrMin;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lots")
public class LotController {

    private final LotService lotService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public LotDto registerLot(@RequestBody LotDto newLot) {
        return new LotDto(lotService.registerLot(newLot));
    }

    @GetMapping
    public List<LotDto> getLots(@RequestParam(required = false) Long productId,
                                @RequestParam(required = false) ZonedDateTime start,
                                @RequestParam(required = false) ZonedDateTime end) {
        return lotService.getLots(productId, dateTimeOrMin(start), dateTimeOrMax(end)).stream()
                .map(LotDto::new).toList();
    }

    @DeleteMapping("{lotId}")
    public void deleteLot(@PathVariable Long lotId) {
        lotService.deleteLot(lotId);
    }

}
