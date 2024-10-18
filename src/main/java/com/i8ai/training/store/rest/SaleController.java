package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.rest.dto.SaleDto;
import com.i8ai.training.store.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("sales")
public class SaleController {

    private final SaleService saleService;

    @PostMapping
    public Sale registerSale(@RequestBody SaleDto saleDto) {
        return saleService.registerSale(saleDto);
    }

    @GetMapping
    public List<Sale> getSales(@RequestParam(required = false) Date start,
                               @RequestParam(required = false) Date end,
                               @RequestParam(required = false) Long productId,
                               @RequestParam(required = false) Long shopId) {
        return saleService.getSales(start, end, productId, shopId);
    }
}
