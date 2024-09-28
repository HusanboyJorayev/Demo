package org.example.task2.service;

import org.example.task2.dto.RegionDto;
import org.example.task2.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface RegionService {
    ResponseDto<?> create(RegionDto dto);

    ResponseDto<?> get(Integer id);

    ResponseDto<?> delete(Integer id);

    ResponseDto<?> update(Integer id, RegionDto dto);

    ResponseDto<?> getAll();
}
