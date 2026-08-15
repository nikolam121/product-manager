package hr.tis.academy.service;

import hr.tis.academy.common.dto.*;
import java.util.List;

public interface StoreService {
    List<StoreDto> findAll();
    StoreDto findById(Long id);
    StoreDto save(StoreDto storeDto);
    StoreDto update(Long id, StoreDto storeDto);
    StoreDto patch(Long id, StoreDto storeDto);
    void deleteById(Long id);
}