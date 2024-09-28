package org.example.task2.service;

import org.example.task2.dto.CalculationDto;
import org.example.task2.dto.ResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface CalculationService {

    ResponseDto<?> create(CalculationDto dto);

    ResponseDto<?> get(Integer id);

    ResponseDto<?> update(CalculationDto dto, Integer id);

    ResponseDto<?> delete(Integer id);

    ResponseDto<?> getAll();

    ResponseDto<?> getNPinflRate(LocalDate date, String pinfl, Double rate);
}
