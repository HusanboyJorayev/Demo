package org.example.task2.service;

import org.example.task2.dto.EmployeeDto;
import org.example.task2.dto.ResponseDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public interface EmployeeService {

    ResponseDto<?> create(EmployeeDto dto);

    ResponseDto<?> get(Integer id);

    ResponseDto<?> update(EmployeeDto dto, Integer id);

    ResponseDto<?> delete(Integer id);

    ResponseDto<?> getAll();

    ResponseDto<?> avgAmount(LocalDate date);

    ResponseDto<?> employeeSalaryVacation(LocalDate date);
}
