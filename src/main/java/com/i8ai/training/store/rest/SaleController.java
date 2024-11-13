package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.SaleDto;
import com.i8ai.training.store.service.SaleService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sales")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public SaleDto registerSale(@RequestBody SaleDto saleDto) {
        return new SaleDto(saleService.registerSale(saleDto));
    }

    @GetMapping
    public List<SaleDto> getSales(@RequestParam(required = false) ZonedDateTime start,
                                  @RequestParam(required = false) ZonedDateTime end,
                                  @RequestParam(required = false) Long productId,
                                  @RequestParam(required = false) Long shopId) {
        return saleService.getSales(
                DateTimeUtils.dateTimeOrMin(start),
                DateTimeUtils.dateTimeOrMax(end),
                productId, shopId
        ).stream().map(SaleDto::new).toList();
    }

    @DeleteMapping("{saleId}")
    public void deleteSale(@PathVariable Long saleId) {
        saleService.deleteSale(saleId);
    }
}
