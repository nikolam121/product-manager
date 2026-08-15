package hr.tis.academy.service.impl;

import hr.tis.academy.common.dto.*;
import hr.tis.academy.mappers.PositionMapper;
import hr.tis.academy.model.Position;
import hr.tis.academy.repository.PositionRepository;
import hr.tis.academy.service.PositionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PositionServiceImplementation implements PositionService {

    private final PositionMapper positionMapper;
    private final PositionRepository positionRepository;

    public PositionServiceImplementation(PositionMapper positionMapper, PositionRepository positionRepository) {
        this.positionMapper = positionMapper;
        this.positionRepository = positionRepository;
    }

    @Override
    @Transactional
    public PositionDto save(PositionDto positionDto) {
        Position position = positionMapper.toEntity(positionDto);
        Position savedPosition = positionRepository.save(position);
        return positionMapper.toDto(savedPosition);
    }
}
