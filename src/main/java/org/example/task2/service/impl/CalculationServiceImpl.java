package org.example.task2.service.impl;

import lombok.AllArgsConstructor;
import org.example.task2.dto.CalculationDto;
import org.example.task2.dto.EmployeeCalculationResponse;
import org.example.task2.dto.ResponseDto;
import org.example.task2.entity.Calculation;
import org.example.task2.enums.CalculationType;
import org.example.task2.mapper.CalculationMapper;
import org.example.task2.repository.CalculationRepository;
import org.example.task2.repository.EmployeeRepository;
import org.example.task2.service.CalculationService;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class CalculationServiceImpl implements CalculationService {
    private final CalculationRepository calculationRepository;
    private final EmployeeRepository employeeRepository;
    private final CalculationMapper calculationMapper;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public ResponseDto<?> create(CalculationDto dto) {
        if (this.employeeRepository.findById(dto.getEmployeeId()).isPresent()) {
            Calculation entity = this.calculationMapper.toEntity(dto);
            entity.setDate(LocalDate.now());
            this.calculationRepository.save(entity);
            return ResponseDto.builder()
                    .status(HttpStatus.CREATED)
                    .message("SUCCESS")
                    .data(this.calculationMapper.toDto(entity))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("EMPLOYEE is not found")
                .build();
    }

    @Override
    public ResponseDto<?> get(Integer id) {
        Optional<Calculation> optional = this.calculationRepository.findById(id);
        if (optional.isPresent()) {
            Calculation calculation = optional.get();
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(this.calculationMapper.toDto(calculation))
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("CALCULATION is not found")
                .build();
    }

    @Override
    public ResponseDto<?> update(CalculationDto dto, Integer id) {
        Optional<Calculation> optional = this.calculationRepository.findById(id);
        if (optional.isPresent()) {
            Calculation calculation = optional.get();
            if (this.employeeRepository.findById(dto.getEmployeeId()).isPresent()) {
                this.calculationMapper.update(dto, calculation);
                calculation.setDate(LocalDate.now());
                this.calculationRepository.save(calculation);
                return ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("UPDATED")
                        .build();
            }
            return ResponseDto.builder()
                    .status(HttpStatus.NOT_FOUND)
                    .message("EMPLOYEE  is not found")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("CALCULATION  is not found")
                .build();
    }

    @Override
    public ResponseDto<?> delete(Integer id) {
        Optional<Calculation> optional = this.calculationRepository.findById(id);
        if (optional.isPresent()) {
            Calculation calculation = optional.get();
            this.calculationRepository.delete(calculation);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("DELETED")
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NOT_FOUND)
                .message("CALCULATION  is not found")
                .build();
    }

    @Override
    public ResponseDto<?> getAll() {
        List<Calculation> list = this.calculationRepository.findAll();
        if (!list.isEmpty()) {
            List<CalculationDto> response = this.calculationMapper.toDtoList(list);
            return ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("SUCCESS")
                    .data(response)
                    .build();
        }
        return ResponseDto.builder()
                .status(HttpStatus.NO_CONTENT)
                .message("LIS is empty")
                .build();
    }

    @Override
    public ResponseDto<?> getNPinflRate(LocalDate date, String pinfl, Double rate) {
        String sql = "select c.id               as cId,\n" +
                "       c.rate             as cRate,\n" +
                "       c.date             as cDate,\n" +
                "       c.amount           as cAmount,\n" +
                "       c.calculation_type as cCalculationType,\n" +
                "       c.organization_id  as cOrganizationId,\n" +
                "       e.id               as eId,\n" +
                "       e.organization_id  as eOrganizationId,\n" +
                "       e.pinfl            as ePinfl,\n" +
                "       e.last_name        as eLastname,\n" +
                "       e.first_name       as eFirstname,\n" +
                "       e.hire_date        as eHireDate\n" +
                "from calculation as c\n" +
                "         join employee as e on c.employee_id = e.id";
        MapSqlParameterSource params = new MapSqlParameterSource();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM");
        var format = date.format(formatter);
        sql += " where DATE_FORMAT(c.date, '%Y.%m')=:format";
        params.addValue("format", format);

        sql += " and e.pinfl=:pinfl";
        params.addValue("pinfl", pinfl);

        sql += " and c.rate >:rate";
        params.addValue("rate", rate);

        List<EmployeeCalculationResponse> responses = jdbcTemplate.query(sql, params, rs -> {
            List<EmployeeCalculationResponse> e = new ArrayList<>();
            while (rs.next()) {
                int cid = rs.getInt("cId");
                double cRate = rs.getDouble("cRate");
                Timestamp cDate = rs.getTimestamp("cDate");
                LocalDate cLocalDate = null;
                if (cDate != null) {
                    cLocalDate = cDate.toLocalDateTime().toLocalDate();
                }
                double cAmount = rs.getDouble("cAmount");
                CalculationType calculationType = null;
                String cCalculationType = rs.getString("cCalculationType");
                if (rs.getString("cCalculationType") != null) {
                    calculationType = CalculationType.valueOf(cCalculationType);
                }
                int cOrganizationId = rs.getInt("cOrganizationId");
                int eId = rs.getInt("eId");
                int eOrganizationId = rs.getInt("eOrganizationId");
                String ePinfl = rs.getString("ePinfl");
                String eLastname = rs.getString("eLastname");
                String eFirstname = rs.getString("eFirstname");
                Timestamp eHireDate = rs.getTimestamp("eHireDate");
                LocalDate eLocalDate = null;
                if (eHireDate != null) {
                    eLocalDate = eHireDate.toLocalDateTime().toLocalDate();
                }
                e.add(
                        new EmployeeCalculationResponse(
                                cid,
                                cRate,
                                cLocalDate,
                                cAmount,
                                calculationType,
                                cOrganizationId,
                                eId,
                                eOrganizationId,
                                ePinfl,
                                eFirstname,
                                eLastname,
                                eLocalDate
                        )
                );
            }
            return e;
        });
        return ResponseDto.builder()
                .status(HttpStatus.OK)
                .message("SUCCESS")
                .data(responses)
                .build();
    }
}
