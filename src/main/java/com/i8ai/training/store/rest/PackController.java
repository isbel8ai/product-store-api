package com.i8ai.training.store.rest;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.service.PackService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("packs")
public class PackController {
    private final PackService packService;

    @GetMapping
    public List<Pack> getPacks(@RequestParam(required = false) Long productId,
                               @RequestParam(required = false) Long shopId,
                               @RequestParam(required = false) Date start,
                               @RequestParam(required = false) Date end) {
        return packService.getPacks(productId, shopId, start, end);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pack registerPack(@RequestBody Pack newPack) {
        return packService.registerPack(newPack);
    }

    @DeleteMapping(value = "{packId}")
    public void deletePack(@PathVariable Long packId) {
        packService.deletePack(packId);
    }
}
