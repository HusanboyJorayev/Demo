package org.example.task2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2.dto.RegionDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.entity.Region;
import org.example.task2.mapper.RegionMapper;
import org.example.task2.repository.RegionRepository;
import org.example.task2.service.RegionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RegionServiceImpl implements RegionService {
    private final RegionRepository regionRepository;
    private final RegionMapper regionMapper;

    @Override
    public ResponseDto<?> create(RegionDto dto) {
        Region entity = this.regionMapper.toEntity(dto);
        this.regionRepository.save(entity);
        return ResponseDto.builder()
                .status(HttpStatus.CREATED)
                .message("Region created")
                .data(this.regionMapper.toDto(entity))
                .build();
    }

    @Override
    public ResponseDto<?> get(Integer id) {
        Optional<Region> optional = this.regionRepository.findById(id);
        if (optional.isPresent()) {
            Region region = optional.get();
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("Region found")
                    .data(this.regionMapper.toDto(region))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Region not found")
                .build();
    }

    @Override
    public ResponseDto<?> delete(Integer id) {
        Optional<Region> optional = this.regionRepository.findById(id);
        if (optional.isPresent()) {
            Region region = optional.get();
            this.regionRepository.delete(region);
            return ResponseDto.builder()
                    .status(HttpStatus.NO_CONTENT)
                    .message("Region deleted")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Region not found")
                .build();
    }

    @Override
    public ResponseDto<?> update(Integer id, RegionDto dto) {
        Optional<Region> optional = this.regionRepository.findById(id);
        if (optional.isPresent()) {
            Region region = optional.get();
            this.regionMapper.updateRegion(dto, region);
            this.regionRepository.save(region);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("Region updated")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("Region not found")
                .build();
    }

    @Override
    public ResponseDto<?> getAll() {
        List<Region> regions = this.regionRepository.findAll();
        if (!regions.isEmpty()) {
            List<RegionDto> dtoList = this.regionMapper.toDtoList(regions);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("Regions found")
                    .data(dtoList)
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("Regions are empty")
                .build();
    }
}
