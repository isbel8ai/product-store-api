package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.LotDto;
import com.i8ai.training.store.service.LotService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "lots")
public class LotController {

    private final LotService lotService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LotDto registerLot(@RequestBody LotDto newLot) {
        return new LotDto(lotService.registerLot(newLot));
    }

    @GetMapping
    public List<LotDto> getLots(@RequestParam(required = false) Long productId,
                                @RequestParam(required = false) ZonedDateTime start,
                                @RequestParam(required = false) ZonedDateTime end) {
        return lotService.getLots(productId, DateTimeUtils.dateTimeOrMin(start), DateTimeUtils.dateTimeOrMax(end))
                .stream().map(LotDto::new).toList();
    }

    @DeleteMapping("{lotId}")
    public void deleteLot(@PathVariable Long lotId) {
        lotService.deleteLot(lotId);
    }

}
