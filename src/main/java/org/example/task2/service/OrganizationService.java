package org.example.task2.service;

import org.example.task2.dto.OrganizationDto;
import org.example.task2.dto.ResponseDto;
import org.springframework.stereotype.Component;

@Component
public interface OrganizationService {

    ResponseDto<?> create(OrganizationDto dto);

    ResponseDto<?> get(Integer id);

    ResponseDto<?> update(OrganizationDto dto, Integer id);

    ResponseDto<?> delete(Integer id);

    ResponseDto<?> getAll();
    ResponseDto<?>countOrganizationAndAmount();
}
