package com.i8ai.training.storeapi.rest;

import com.i8ai.training.storeapi.service.ExistenceService;
import com.i8ai.training.storeapi.rest.dto.ExistenceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/existence")
public class ExistenceController {
    private final ExistenceService existenceService;

    @Autowired
    public ExistenceController(ExistenceService existenceService) {
        this.existenceService = existenceService;
    }

    @GetMapping
    public List<ExistenceDTO> getAllProductsExistenceInMain() {
        return existenceService.getAllProductsExistenceInMain();
    }

    @GetMapping("/{productId}")
    public ExistenceDTO getProductExistenceInMain(@PathVariable Long productId) {
        return existenceService.getProductExistenceInMain(productId);
    }

    @GetMapping("/{productId}/shop")
    public List<ExistenceDTO> getProductExistenceInAllShops(@PathVariable Long productId) {
        return existenceService.getProductExistenceInAllShops(productId);
    }

    @GetMapping("/{productId}/shop/{shopId}")
    public ExistenceDTO getProductExistenceInShop(@PathVariable Long productId, @PathVariable Long shopId) {
        return existenceService.getProductExistenceInShop(productId, shopId);
    }
}
