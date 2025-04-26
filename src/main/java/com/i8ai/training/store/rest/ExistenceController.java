package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.ExistenceDto;
import com.i8ai.training.store.service.ExistenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("existence")
public class ExistenceController {

    private final ExistenceService existenceService;

    @GetMapping
    public List<ExistenceDto> getAllProductsExistenceInMain() {
        return existenceService.getAllProductsExistenceInMain();
    }

    @GetMapping("{productId}/main")
    public ExistenceDto getProductExistenceInMain(@PathVariable Long productId) {
        return existenceService.getProductExistenceInMain(productId);
    }

    @GetMapping("{productId}/shops")
    public List<ExistenceDto> getProductExistenceInAllShops(@PathVariable Long productId) {
        return existenceService.getProductExistenceInAllShops(productId);
    }

    @GetMapping("{productId}/shops/{shopId}")
    public ExistenceDto getProductExistenceInShop(@PathVariable Long productId, @PathVariable Long shopId) {
        return existenceService.getProductExistenceInShop(productId, shopId);
    }
}
