package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.StoreDto;
import hr.tis.academy.mappers.StoreMapper;
import hr.tis.academy.model.Store;
import hr.tis.academy.repository.StoreRepository;
import hr.tis.academy.repository.exception.NoStoreFoundException;
import hr.tis.academy.service.StoreService;
import hr.tis.academy.service.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
        this.storeRepository = storeRepository;
        this.storeMapper = storeMapper;
    }

    @Override
    public List<StoreDto> findAll() {
        return storeRepository.fetchAll().stream()
                .map(storeMapper::toDto)
                .toList();
    }

    @Override
    public StoreDto findById(Long id) throws NoStoreFoundException {
        Store store = storeRepository.fetchById(id);
        if (store == null) {
            throw new NoStoreFoundException("No Store with id " + id + " was found");
        }
        return storeMapper.toDto(store);
    }

    @Override
    @Transactional
    public StoreDto save(StoreDto storeDto) {
        Store storeEntity = storeMapper.toEntity(storeDto);
        Store savedStore = storeRepository.save(storeEntity);
        return storeMapper.toDto(savedStore);
    }

    @Override
    @Transactional
    public StoreDto update(Long id, StoreDto storeDto) {
        Store existingStore = storeRepository.fetchById(id);
        if (existingStore == null) {
            throw new ResourceNotFoundException("Store with id " + id + " not found");
        }

        StoreDto updatedDto = new StoreDto(
                id,
                storeDto.storeName(),
                storeDto.address(),
                storeDto.telephoneNumber(),
                storeDto.email(),
                storeDto.workingDays(),
                storeDto.employees()
        );

        Store savedStore = storeRepository.save(storeMapper.toEntity(updatedDto));
        return storeMapper.toDto(savedStore);
    }

    @Override
    @Transactional
    public StoreDto patch(Long id, StoreDto patchDto) throws ResourceNotFoundException {
        Store existingStore = storeRepository.fetchById(id);
        if (existingStore == null) {
            throw new ResourceNotFoundException("Store with id " + id + " not found");
        }

        StoreDto existingDto = storeMapper.toDto(existingStore);

        StoreDto updatedDto = new StoreDto(
                id,
                Objects.requireNonNullElse(patchDto.storeName(), existingDto.storeName()),
                Objects.requireNonNullElse(patchDto.address(), existingDto.address()),
                Objects.requireNonNullElse(patchDto.telephoneNumber(), existingDto.telephoneNumber()),
                Objects.requireNonNullElse(patchDto.email(), existingDto.email()),
                Objects.requireNonNullElse(patchDto.workingDays(), existingDto.workingDays()),
                Objects.requireNonNullElse(patchDto.employees(), existingDto.employees())
        );

        Store savedStore = storeRepository.save(storeMapper.toEntity(updatedDto));
        return storeMapper.toDto(savedStore);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        storeRepository.deleteById(id);
    }
}