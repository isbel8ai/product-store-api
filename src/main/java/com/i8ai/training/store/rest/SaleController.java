package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Sale;
import com.i8ai.training.store.service.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sale")
public class SaleController {
    private final SaleService saleService;

    @Autowired
    public SaleController(SaleService saleService) {
        this.saleService = saleService;
    }

    @GetMapping
    public List<Sale> getSales(@RequestParam(required = false) Date start,
                               @RequestParam(required = false) Date end,
                               @RequestParam(required = false) Long productId,
                               @RequestParam(required = false) Long shopId) {
        return saleService.getSales(start, end, productId, shopId);
    }

    @PostMapping
    public Sale registerSale(@RequestBody Sale newSale) {
        return saleService.registerSale(newSale);
    }

    @DeleteMapping("/{saleId}")
    public void deleteSale(@PathVariable Long saleId) {
        saleService.deleteSale(saleId);
    }
}
