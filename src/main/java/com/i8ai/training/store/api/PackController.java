package com.i8ai.training.store.api;

import com.i8ai.training.store.model.Pack;
import com.i8ai.training.store.service.PackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/pack")
public class PackController {
    private final PackService packService;

    @Autowired
    public PackController(PackService packService) {
        this.packService = packService;
    }

    @GetMapping
    public List<Pack> getPacks(@RequestParam(required = false) Long productId,
                               @RequestParam(required = false) Long shopId,
                               @RequestParam(required = false) Date startDate,
                               @RequestParam(required = false) Date endDate) {
        return packService.getPacks(productId, shopId, startDate, endDate);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Pack registerPack(@RequestBody Pack newPack) {
        return packService.registerPack(newPack);
    }

    @DeleteMapping(value = "/{packId}")
    public void deletePack(@PathVariable Long packId) {
        packService.deletePack(packId);
    }
}
