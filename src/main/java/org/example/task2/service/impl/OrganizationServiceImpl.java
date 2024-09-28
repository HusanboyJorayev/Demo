package org.example.task2.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.task2.dto.OrganizationDto;
import org.example.task2.dto.ResponseDto;
import org.example.task2.entity.Organization;
import org.example.task2.mapper.OrganizationMapper;
import org.example.task2.repository.OrganizationRepository;
import org.example.task2.repository.RegionRepository;
import org.example.task2.service.OrganizationService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {
    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final RegionRepository regionRepository;

    @Override
    public ResponseDto<?> create(OrganizationDto dto) {
        if (this.regionRepository.findById(dto.getRegionId()).isPresent()) {
            Organization entity = this.organizationMapper.toEntity(dto);
            this.organizationRepository.save(entity);
            return ResponseDto.builder()
                    .status(HttpStatus.CREATED)
                    .message("SUCCESS")
                    .data(this.organizationMapper.toDto(entity))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .data("REGION NOT FOUND")
                .build();
    }

    @Override
    public ResponseDto<?> get(Integer id) {
        Optional<Organization> optional = this.organizationRepository.findById(id);
        if (optional.isPresent()) {
            Organization organization = optional.get();
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(this.organizationMapper.toDto(organization))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("NOT FOUND")
                .build();
    }

    @Override
    public ResponseDto<?> update(OrganizationDto dto, Integer id) {
        Optional<Organization> optional = this.organizationRepository.findById(id);
        if (optional.isPresent()) {
            Organization organization = optional.get();
            if (this.regionRepository.findById(dto.getRegionId()).isPresent()) {
                this.organizationMapper.updateDto(dto, organization);
                this.organizationRepository.save(organization);
                return ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("SUCCESS")
                        .build();
            }
            return ResponseDto.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("REGION IS NOT FOUND")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("ORGANIZATION IS NOT FOUND")
                .build();
    }

    @Override
    public ResponseDto<?> delete(Integer id) {
        if (this.organizationRepository.findById(id).isPresent()) {
            this.organizationRepository.deleteById(id);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("DELETED")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("ORGANIZATION is not found")
                .build();
    }

    @Override
    public ResponseDto<?> getAll() {
        List<Organization> list = this.organizationRepository.findAll();
        if (!list.isEmpty()) {
            List<OrganizationDto> response = this.organizationMapper.toDtoList(list);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(response)
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("list is empty")
                .build();
    }

    @Override
    public ResponseDto<?> countOrganizationAndAmount() {
        List<Object[]> objects = this.organizationRepository.countOrganizationAndAmount();
        List<OrganizationDto.ShortInfo> shortInfos = new ArrayList<>();
        for (Object[] o : objects) {
            shortInfos.add(
                    new OrganizationDto.ShortInfo(
                            (Integer) o[0],
                            (Integer) o[1]
                    )
            );
        }
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .data(shortInfos)
                .build();
    }
}
