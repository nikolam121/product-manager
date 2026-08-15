package hr.tis.academy.controller;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Stores", description = "Store management")
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    @GetMapping
    public ResponseEntity<List<StoreDto>> getAllStores() {
        return ResponseEntity.ok(storeService.findAll());
    }

    @Operation(summary = "Get store by id")
    @GetMapping("/{storeId}")
    public StoreDto getStoreById(@PathVariable("storeId") Long storeId) {
        StoreDto store = storeService.findById(storeId);
        return store;
    }
    @Operation(summary = "Create new store")
    @PostMapping
    public ResponseEntity<StoreDto> createStore(@RequestBody StoreDto storeDto) {
        StoreDto savedStore = storeService.save(storeDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedStore);
    }

    @PutMapping("/{storeId}")
    public ResponseEntity<StoreDto> updateStore(@PathVariable("storeId") Long storeId, @RequestBody StoreDto storeDto) {
        return ResponseEntity.ok(storeService.update(storeId, storeDto));
    }


    @DeleteMapping("/{storeId}")
    public ResponseEntity<StoreDto> deleteStore(@PathVariable("storeId") Long storeId) {
        storeService.deleteById(storeId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{storeId}")
    public ResponseEntity<StoreDto> patchStore(@PathVariable("storeId") Long storeId, @RequestBody StoreDto storeDto) {
        StoreDto patchedStore = storeService.patch(storeId, storeDto);
        return ResponseEntity.ok(patchedStore);
    }

}
