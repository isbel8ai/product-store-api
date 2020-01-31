package com.i8ai.training.storeapi.controller;

import com.i8ai.training.storeapi.service.ExistenceService;
import com.i8ai.training.storeapi.service.dto.ExistenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/existence/{productId}")
public class ExistenceController {
    private final ExistenceService existenceService;

    @Autowired
    public ExistenceController(ExistenceService existenceService) {
        this.existenceService = existenceService;
    }

    @GetMapping
    public ExistenceDTO getProductExistence(@PathVariable Long productId,
                                     @RequestParam(required = false) Long shopId) {
        return existenceService.getProductExistence(productId, shopId);
    }
}
