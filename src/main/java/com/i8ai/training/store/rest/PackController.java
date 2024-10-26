package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.PackDto;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public PackDto registerPack(@RequestBody PackDto packDto) {
        return new PackDto(packService.registerPack(packDto));
    }

    @GetMapping
    public List<PackDto> getPacks(@RequestParam(required = false) Long productId,
                               @RequestParam(required = false) Long shopId,
                               @RequestParam(required = false) Date start,
                               @RequestParam(required = false) Date end) {
        return packService.getPacks(productId, shopId, start, end).stream().map(PackDto::new).toList();
    }

    @DeleteMapping(value = "{packId}")
    public void deletePack(@PathVariable Long packId) {
        packService.deletePack(packId);
    }
}
