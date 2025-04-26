package com.i8ai.training.store.rest;

import com.i8ai.training.store.rest.dto.PackDto;
import com.i8ai.training.store.service.PackService;
import com.i8ai.training.store.util.DateTimeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("packs")
public class PackController {
    private final PackService packService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PackDto registerPack(@RequestBody PackDto packDto) {
        return new PackDto(packService.registerPack(packDto));
    }

    @GetMapping
    public List<PackDto> getPacks(@RequestParam(required = false) Long productId,
                                  @RequestParam(required = false) Long shopId,
                                  @RequestParam(required = false) ZonedDateTime start,
                                  @RequestParam(required = false) ZonedDateTime end) {
        return packService.getPacks(
                productId,
                shopId,
                DateTimeUtils.dateTimeOrMin(start),
                DateTimeUtils.dateTimeOrMax(end)
        ).stream().map(PackDto::new).toList();
    }

    @DeleteMapping(value = "{packId}")
    public void deletePack(@PathVariable Long packId) {
        packService.deletePack(packId);
    }
}
